package com.openpayd.currency.domain.converter.impl;

import com.openpayd.currency.domain.converter.ExchangeRateCalculationDtoConverter;
import com.openpayd.currency.domain.model.dto.ExchangeRateCalculationDto;
import com.openpayd.currency.domain.model.request.ConversionAmountRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
class ExchangeRateCalculationDtoConverterImpl implements ExchangeRateCalculationDtoConverter {

    @Override
    public ExchangeRateCalculationDto convert(String from, String to, BigDecimal amount) {

        return new ExchangeRateCalculationDto
                .Builder()
                .amount(amount)
                .source(from)
                .target(to)
                .build();
    }

    @Override
    public ExchangeRateCalculationDto convert(ConversionAmountRequest request) {

        return new ExchangeRateCalculationDto
                .Builder()
                .amount(request.getAmount())
                .source(request.getSource())
                .target(request.getTarget())
                .build();
    }
}
