package com.openpayd.currency.delegate.provider;

import com.openpayd.currency.delegate.service.ExchangeRateDelegateService;

public interface ExchangeRateProvider {
    ExchangeRateDelegateService create();
}
