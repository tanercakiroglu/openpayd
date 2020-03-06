package com.openpayd.currency.domain.service.impl;

import com.openpayd.currency.domain.converter.ConversationRateResponseConverter;
import com.openpayd.currency.domain.converter.ExchangeRateCalculationDtoConverter;
import com.openpayd.currency.domain.model.dto.CalculatedExchangeRateDto;
import com.openpayd.currency.domain.model.dto.ExchangeRateCalculationDto;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;
import com.openpayd.currency.domain.service.ConversionService;
import com.openpayd.currency.domain.service.ExchangeService;
import com.openpayd.currency.domain.service.TransactionHistoryService;
import com.openpayd.currency.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class ConversionServiceImplTest {

    @Mock
    ExchangeService exchangeService;
    @Mock
    ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter;
    @Mock
    ConversationRateResponseConverter conversationRateResponseConverter;
    @Mock
    TransactionHistoryService transactionHistoryService;

    private ConversionService conversionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        conversionService = new ConversionServiceImpl(
                exchangeService,
                exchangeRateCalculationDtoConverter,
                conversationRateResponseConverter,
                transactionHistoryService
        );
    }

    @Test
    public void givenAmountTargetCurrencyPair_whenConversionAmountWanted_thenReturnAmount() throws IOException, BusinessException {
        when(exchangeRateCalculationDtoConverter.convert(any())).thenReturn(
                new ExchangeRateCalculationDto.Builder()
                        .amount(new BigDecimal("12"))
                        .source("TRY")
                        .target("USD")
                        .build());

        when(transactionHistoryService.save(any())).thenReturn(1L);
        when(exchangeService.getExchangeRateAndCalculatePair(any())).thenReturn(new CalculatedExchangeRateDto.Builder()
                .amount(new BigDecimal("12"))
                .amountInTargetCurrency((new BigDecimal("12")))
                .rate((new BigDecimal("12")))
                .source("TRY")
                .target("USD").build());
        when(conversationRateResponseConverter.convert(any(), any())).thenReturn(new ConversionRateResponse.Builder()
                .amount(new BigDecimal("12"))
                .transactionId(1L)
                .build());
        var result = conversionService.getConversionAmount(any());
        assertNotNull(result);
        assertNotNull(result.getAmount());
        assertNotNull(result.getTransactionId());
    }

    @Test
    public void givenDateOrId_whenConversionHistoryWanted_thenReturnHistory()  {

        when(transactionHistoryService.findAllByIdOrInsertDate(any(),any(),any())).thenReturn(new ArrayList<>());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            conversionService.findAllConversionsByIdOrInsertDate(any(),any(),any(),any());
        });

    }
}