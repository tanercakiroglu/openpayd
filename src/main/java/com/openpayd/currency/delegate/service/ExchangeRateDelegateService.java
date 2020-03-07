package com.openpayd.currency.delegate.service;

import com.openpayd.currency.delegate.model.ExchangeRateDelegateResponse;

import java.io.IOException;

public interface ExchangeRateDelegateService {

    ExchangeRateDelegateResponse getLatestExchangeRate() throws IOException;
}