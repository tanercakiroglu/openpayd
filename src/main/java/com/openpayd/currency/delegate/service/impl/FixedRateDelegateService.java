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

public class FixedRateDelegateService implements ExchangeRateDelegateService {

    private static final String URL_PROVIDER = "http://data.fixer.io/api/latest?access_key=081ef95f9564b7d56209c954a431918e";
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public ExchangeRateDelegateResponse getLatestExchangeRate() throws IOException {
        return map(doGetRequest());
    }

    private String doGetRequest() throws IOException {
        var request = new Request.Builder()
                .url(URL_PROVIDER)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new RemoteCallException(String.valueOf(response.code()));
            return Objects.requireNonNull(response.body()).string();
        }
    }

    private ExchangeRateDelegateResponse map(String response) throws IOException {
        return Mapper.jsonMapper.readValue(response, ExchangeRateDelegateResponse.class);
    }
}
