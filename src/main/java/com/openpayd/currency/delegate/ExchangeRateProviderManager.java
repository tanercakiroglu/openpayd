package com.openpayd.currency.delegate;

import com.openpayd.currency.delegate.provider.ExchangeRateProvider;
import com.openpayd.currency.exception.ProviderNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public final class ExchangeRateProviderManager {
    private static final String DEFAULT_PROVIDER = "com.openpayd.currency.delegate.provider.impl.CentralBankExchangeRateProvider";

    //All providers
    public static List<ExchangeRateProvider> providers() {
        final List<ExchangeRateProvider> services = new ArrayList<>();
        final ServiceLoader<ExchangeRateProvider> loader = ServiceLoader.load(ExchangeRateProvider.class);
        loader.forEach(services::add);
        return services;
    }

    //Default provider
    public static ExchangeRateProvider defaultProvider() {
        return provider(DEFAULT_PROVIDER);
    }

    //provider by name
    public static ExchangeRateProvider provider(String providerName) {
        final ServiceLoader<ExchangeRateProvider> loader = ServiceLoader.load(ExchangeRateProvider.class);
        for (ExchangeRateProvider provider : loader) {
            if (providerName.equals(provider.getClass().getName())) {
                return provider;
            }
        }
        throw new ProviderNotFoundException("Exchange Rate provider " + providerName + " not found");
    }
}
