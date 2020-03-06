package com.openpayd.currency.domain.controller;

import com.openpayd.currency.domain.model.response.ExchangeRateResponse;
import com.openpayd.currency.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

public interface ExchangeController {

    @GetMapping("/exchangeRate/from/{from}/to/{to}")
    ResponseEntity<ExchangeRateResponse> example(@PathVariable("from") String from, @PathVariable("to") String to) throws IOException, BusinessException;
}
