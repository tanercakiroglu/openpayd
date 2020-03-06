package com.openpayd.currency.domain.controller.impl;

import com.openpayd.currency.domain.model.response.ExchangeRateResponse;
import com.openpayd.currency.domain.service.ExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExchangeControllerImpl.class)
class ExchangeControllerImplTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExchangeService exchangeService;

    @Test
    public void givenCurrency_whenGetRate_thenReturnExchangeRateResponse()
            throws Exception {

        ExchangeRateResponse response = new ExchangeRateResponse(new BigDecimal(12));
        when(exchangeService.getExchangeRateByPair(any(),any())).thenReturn(response);

        mvc.perform(get("/exchangeRate/from/USD/to/TRY")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(exchangeService, times(1)).getExchangeRateByPair(any(),any());
        verifyNoMoreInteractions(exchangeService);
    }

}