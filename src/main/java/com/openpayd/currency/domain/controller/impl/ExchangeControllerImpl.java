package com.openpayd.currency.domain.controller.impl;

import com.openpayd.currency.aspect.loggable.Loggable;
import com.openpayd.currency.domain.controller.ExchangeController;
import com.openpayd.currency.domain.model.response.ExchangeRateResponse;
import com.openpayd.currency.domain.service.ExchangeService;
import com.openpayd.currency.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Loggable
class ExchangeControllerImpl implements ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeControllerImpl(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    public ResponseEntity<ExchangeRateResponse> example(String from, String to) throws IOException, BusinessException {
        return ResponseEntity.ok(exchangeService.getExchangeRateByPair(from, to));
    }
}
