package com.openpayd.currency.delegate.service.impl;

import com.openpayd.currency.delegate.model.ExchangeRateDelegateResponse;
import com.openpayd.currency.delegate.service.ExchangeRateDelegateService;
import com.openpayd.currency.exception.RemoteCallException;
import com.openpayd.currency.util.Mapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;


public class ExchangeRateDelegateServiceImpl implements ExchangeRateDelegateService {

    private static final String URL_PROVIDER = "https://api.ratesapi.io/api/latest";
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public ExchangeRateDelegateResponse getExchangeRateByDate() throws IOException {
        return map(doGetRequest());
    }

    private String doGetRequest() throws IOException {
        final Request request = new Request.Builder()
                .url(URL_PROVIDER)
                .build();

        try(Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new RemoteCallException(String.valueOf(response.code()));
            return  Objects.requireNonNull(response.body()).string();
        }
    }
    private ExchangeRateDelegateResponse map(String response) throws IOException {
        return Mapper.mapper.readValue(response, ExchangeRateDelegateResponse.class);
    }
}
