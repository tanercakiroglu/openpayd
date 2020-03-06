package com.openpayd.currency.domain.converter;

import com.openpayd.currency.domain.model.dto.ExchangeRateCalculationDto;
import com.openpayd.currency.domain.model.request.ConversionAmountRequest;

import java.math.BigDecimal;

public interface ExchangeRateCalculationDtoConverter {

    ExchangeRateCalculationDto convert(String from, String to, BigDecimal amount);

    ExchangeRateCalculationDto convert(ConversionAmountRequest request);
}
