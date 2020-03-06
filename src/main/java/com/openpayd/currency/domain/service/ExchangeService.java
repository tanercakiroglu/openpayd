package com.openpayd.currency.domain.service;

import com.openpayd.currency.domain.model.dto.CalculatedExchangeRateDto;
import com.openpayd.currency.domain.model.dto.ExchangeRateCalculationDto;
import com.openpayd.currency.domain.model.response.ExchangeRateResponse;
import com.openpayd.currency.exception.BusinessException;

import java.io.IOException;

public interface ExchangeService {

    ExchangeRateResponse getExchangeRateByPair(String from, String to) throws IOException, BusinessException;

    CalculatedExchangeRateDto getExchangeRateAndCalculatePair(ExchangeRateCalculationDto exchangeRateCalculationDto) throws IOException, BusinessException;
}
