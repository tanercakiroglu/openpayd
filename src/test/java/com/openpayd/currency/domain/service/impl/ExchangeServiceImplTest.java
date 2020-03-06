package com.openpayd.currency.domain.service.impl;

import com.openpayd.currency.domain.calculator.ExchangeRateCalculator;
import com.openpayd.currency.domain.converter.ExchangeRateCalculationDtoConverter;
import com.openpayd.currency.domain.model.dto.ExchangeRateCalculationDto;
import com.openpayd.currency.domain.service.ExchangeService;
import com.openpayd.currency.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class ExchangeServiceImplTest {

    @Mock
    ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter;
    @Spy
    ExchangeRateCalculator exchangeRateCalculator;

    private ExchangeService exchangeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        exchangeService = new ExchangeServiceImpl(
                exchangeRateCalculator,
                exchangeRateCalculationDtoConverter
        );
    }

    @Test
    public void givenAmountTargetCurrencyPair_whenRateWanted_thenReturnRate() throws IOException, BusinessException {
        when(exchangeRateCalculationDtoConverter.convert(any(), any(), any())).thenReturn(
                new ExchangeRateCalculationDto.Builder()
                        .amount(new BigDecimal("12"))
                        .source("TRY")
                        .target("USD")
                        .build());
        var result = exchangeService.getExchangeRateByPair("TRY", "USD");
        assertNotNull(result);
        assertNotNull(result.getRate());
    }

    @Test
    public void givenAmountTargetCurrencyPair_whenAmountInTargetWanted_thenReturnAmount() throws IOException, BusinessException {
        when(exchangeRateCalculationDtoConverter.convert(any(), any(), any())).thenReturn(
                new ExchangeRateCalculationDto.Builder()
                        .amount(new BigDecimal("12"))
                        .source("TRY")
                        .target("USD")
                        .build());
        var result = exchangeService.getExchangeRateAndCalculatePair(new ExchangeRateCalculationDto.Builder()
                .source("TRY")
                .target("USD")
                .amount(new BigDecimal("12"))
                .build());
        assertNotNull(result);
        assertNotNull(result.getAmountInTargetCurrency());
        assertNotNull(result.getRate());
        assertNotNull(result.getSource());
        assertNotNull(result.getTarget());
        assertNotNull(result.getAmount());
    }

    @Test
    public void givenInvalidAmountTargetCurrencyPair_whenRateWanted_thenReturnException() throws IOException, BusinessException {
        when(exchangeRateCalculationDtoConverter.convert(any(), any(), any())).thenReturn(
                new ExchangeRateCalculationDto.Builder()
                        .amount(new BigDecimal("12"))
                        .source("")
                        .target("USD")
                        .build());
        Assertions.assertThrows(BusinessException.class, () -> {
            exchangeService.getExchangeRateByPair("", "USD");
        });
    }
}