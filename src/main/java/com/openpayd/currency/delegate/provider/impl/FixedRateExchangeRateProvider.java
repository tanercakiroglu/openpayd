package com.openpayd.currency.delegate.provider.impl;

import com.openpayd.currency.delegate.provider.ExchangeRateProvider;
import com.openpayd.currency.delegate.service.ExchangeRateDelegateService;
import com.openpayd.currency.delegate.service.impl.FixedRateDelegateService;

public class FixedRateExchangeRateProvider  implements ExchangeRateProvider {

    @Override
    public ExchangeRateDelegateService create() {
        return new FixedRateDelegateService();
    }
}
