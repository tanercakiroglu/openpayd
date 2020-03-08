package com.openpayd.currency.domain.service.impl;

import com.openpayd.currency.delegate.ExchangeRateProviderManager;
import com.openpayd.currency.delegate.model.ExchangeRateDelegateResponse;
import com.openpayd.currency.delegate.provider.ExchangeRateProvider;
import com.openpayd.currency.domain.calculator.ExchangeRateCalculator;
import com.openpayd.currency.domain.converter.ExchangeRateCalculationDtoConverter;
import com.openpayd.currency.domain.model.dto.CalculatedExchangeRateDto;
import com.openpayd.currency.domain.model.dto.ExchangeRateCalculationDto;
import com.openpayd.currency.domain.model.response.ExchangeRateResponse;
import com.openpayd.currency.domain.service.ExchangeService;
import com.openpayd.currency.exception.BusinessException;
import com.openpayd.currency.exception.RemoteCallException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@Service
class ExchangeServiceImpl implements ExchangeService {

    private static final String CURRENCY_VALUE_NOT_FOUND = "Currency value not found";
    private static final String BASE_CURRENCY = "EUR";
    private final ExchangeRateCalculator exchangeRateCalculator;
    private final ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter;

    public ExchangeServiceImpl(ExchangeRateCalculator exchangeRateCalculator, ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter) {
        this.exchangeRateCalculator = exchangeRateCalculator;
        this.exchangeRateCalculationDtoConverter = exchangeRateCalculationDtoConverter;
    }

    @Override
    public ExchangeRateResponse getExchangeRateByPair(String from, String to) throws IOException, BusinessException {
        var exchangeRateCalculationDto = exchangeRateCalculationDtoConverter.convert(from, to, BigDecimal.ONE);
        var calculatedExchangeRateDto = getCalculatedExchangeRateDto(exchangeRateCalculationDto);
        return new ExchangeRateResponse(calculatedExchangeRateDto.getRate());
    }

    @Override
    public CalculatedExchangeRateDto getExchangeRateAndCalculatePair(ExchangeRateCalculationDto exchangeRateCalculationDto) throws IOException, BusinessException {
        return getCalculatedExchangeRateDto(exchangeRateCalculationDto);
    }

    private CalculatedExchangeRateDto getCalculatedExchangeRateDto(ExchangeRateCalculationDto exchangeRateCalculationDto) throws IOException, BusinessException {
        var providers = ExchangeRateProviderManager.providers();
        ExchangeRateDelegateResponse exchangeRateDelegateResponse = null;
        for (ExchangeRateProvider service : providers) {
            exchangeRateDelegateResponse =  service.create().getLatestExchangeRate();
             if(!Objects.isNull(exchangeRateDelegateResponse) && !Objects.isNull(exchangeRateDelegateResponse.getRates()))
                 break;
        }
        if(Objects.isNull(exchangeRateDelegateResponse))
            throw new RemoteCallException(HttpStatus.NOT_FOUND.toString());
        exchangeRateDelegateResponse.getRates().put(BASE_CURRENCY, BigDecimal.ONE);
        var source = exchangeRateDelegateResponse.getRates().get(exchangeRateCalculationDto.getSource());
        var target = exchangeRateDelegateResponse.getRates().get(exchangeRateCalculationDto.getTarget());
        if (Objects.isNull(source) || Objects.isNull(target)) {
            throw new BusinessException(CURRENCY_VALUE_NOT_FOUND);
        }
        var rate = exchangeRateCalculator.getExchangeRatePair(source, target);
        var amountInTargetCurrency = exchangeRateCalculator.calculateAmountInTargetCurrency(exchangeRateCalculationDto.getAmount(), rate);
        return new CalculatedExchangeRateDto.Builder()
                .amount(exchangeRateCalculationDto.getAmount())
                .rate(rate)
                .source(exchangeRateCalculationDto.getSource())
                .target(exchangeRateCalculationDto.getTarget())
                .amountInTargetCurrency(amountInTargetCurrency)
                .build();
    }
}
