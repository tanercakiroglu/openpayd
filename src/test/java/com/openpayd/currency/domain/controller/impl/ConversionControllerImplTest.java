package com.openpayd.currency.domain.controller.impl;

import com.openpayd.currency.domain.model.request.ConversionAmountRequest;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;
import com.openpayd.currency.domain.service.ConversionService;
import com.openpayd.currency.util.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConversionControllerImpl.class)
class ConversionControllerImplTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConversionService conversionService;

    @Test
    public void givenCurrencyPairAndAmount_whenTargetAmountWanted_thenReturnTargetAmountAndTransactionId()
            throws Exception {
        var amount = new BigDecimal("12");

        when(conversionService.getConversionAmount(any())).thenReturn(new ConversionRateResponse
                .Builder()
                .amount(amount)
                .transactionId(1L)
                .build());

        var requestString = Mapper.mapper.writeValueAsString(createConversionRateRequest("TRY", "USD", amount));
        mvc.perform(post("/exchangeRate/conversion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andDo(print())
                .andExpect(status().isOk());

        verify(conversionService, times(1)).getConversionAmount(any());
        verifyNoMoreInteractions(conversionService);
    }

    @Test
    public void givenInvalidCurrencyPairAndAmount_whenTargetAmountWanted_thenReturnBadRequest()
            throws Exception {
        var amount = new BigDecimal("12");

        when(conversionService.getConversionAmount(any())).thenReturn(new ConversionRateResponse
                .Builder()
                .amount(amount)
                .transactionId(1L)
                .build());

        var requestString = Mapper.mapper.writeValueAsString(createConversionRateRequest(null, "USD", amount));
        mvc.perform(post("/exchangeRate/conversion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void givenInvalidTargetCurrencyPairAndAmount_whenTargetAmountWanted_thenReturnBadRequest()
            throws Exception {
        var amount = new BigDecimal("12");

        when(conversionService.getConversionAmount(any())).thenReturn(new ConversionRateResponse
                .Builder()
                .amount(amount)
                .transactionId(1L)
                .build());

        var requestString = Mapper.mapper.writeValueAsString(createConversionRateRequest("USD", null, amount));
        mvc.perform(post("/exchangeRate/conversion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void givenAmountTargetCurrencyPairAndAmount_whenTargetAmountWanted_thenReturnBadRequest()
            throws Exception {
        var amount = new BigDecimal("12");

        when(conversionService.getConversionAmount(any())).thenReturn(new ConversionRateResponse
                .Builder()
                .amount(amount)
                .transactionId(1L)
                .build());

        var requestString = Mapper.mapper.writeValueAsString(createConversionRateRequest("USD", "TRY", null));
        mvc.perform(post("/exchangeRate/conversion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void givenDateOrTransactionId_whenTransactionHistoryWanted_thenReturnTransactionHistory()
            throws Exception {

        when(conversionService.findAllConversionsByIdOrInsertDate(any(), any(), any(), any())).
                thenReturn(new ArrayList<>());
        mvc.perform(get("/exchangeRate/conversions")
                .param("id", "1")
                .param("insertDate", "2020-03-06")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    private ConversionAmountRequest createConversionRateRequest(String source, String target, BigDecimal amount) {
        ConversionAmountRequest request = new ConversionAmountRequest();
        request.setAmount(amount);
        request.setSource(source);
        request.setTarget(target);
        return request;
    }

}