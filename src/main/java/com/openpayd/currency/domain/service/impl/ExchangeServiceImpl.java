package com.openpayd.currency.domain.service.impl;

import com.openpayd.currency.delegate.ExchangeRateProviderManager;
import com.openpayd.currency.domain.calculator.ExchangeRateCalculator;
import com.openpayd.currency.domain.converter.ExchangeRateCalculationDtoConverter;
import com.openpayd.currency.domain.model.dto.CalculatedExchangeRateDto;
import com.openpayd.currency.domain.model.dto.ExchangeRateCalculationDto;
import com.openpayd.currency.domain.model.response.ExchangeRateResponse;
import com.openpayd.currency.domain.service.ExchangeService;
import com.openpayd.currency.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

@Service
class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRateCalculator exchangeRateCalculator;
    private final ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter;

    public ExchangeServiceImpl(ExchangeRateCalculator exchangeRateCalculator, ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter) {
        this.exchangeRateCalculator = exchangeRateCalculator;
        this.exchangeRateCalculationDtoConverter = exchangeRateCalculationDtoConverter;
    }

    @Override
    public ExchangeRateResponse getExchangeRateByPair(String from, String to) throws IOException, BusinessException {
        var exchangeRateCalculationDto= exchangeRateCalculationDtoConverter.convert(from,to,BigDecimal.ONE);
        var calculatedExchangeRateDto=getCalculatedExchangeRateDto(exchangeRateCalculationDto);
        return new ExchangeRateResponse(calculatedExchangeRateDto.getRate());
    }

    @Override
    public CalculatedExchangeRateDto getExchangeRateAndCalculatePair(ExchangeRateCalculationDto exchangeRateCalculationDto) throws IOException, BusinessException {
        return getCalculatedExchangeRateDto(exchangeRateCalculationDto);
    }

    private CalculatedExchangeRateDto getCalculatedExchangeRateDto(ExchangeRateCalculationDto exchangeRateCalculationDto) throws IOException, BusinessException {
        var exchangeRateDelegateResponse = ExchangeRateProviderManager.defaultProvider().create().getExchangeRateByDate();
        var source = exchangeRateDelegateResponse.getRates().get(exchangeRateCalculationDto.getSource());
        var target = exchangeRateDelegateResponse.getRates().get(exchangeRateCalculationDto.getTarget());
        if (Objects.isNull(source) || Objects.isNull(target)) {
            throw new BusinessException("Currency value not found");
        }
        var rate = exchangeRateCalculator.getExchangeRatePair(source, target);
        var amountInTargetCurrency= exchangeRateCalculator.calculateAmountInTargetCurrency(exchangeRateCalculationDto.getAmount(),rate);
        return new  CalculatedExchangeRateDto.Builder()
                .amount(exchangeRateCalculationDto.getAmount())
                .rate(rate)
                .source(exchangeRateCalculationDto.getSource())
                .target(exchangeRateCalculationDto.getTarget())
                .amountInTargetCurrency(amountInTargetCurrency)
                .build();
    }
}