package com.openpayd.currency.domain.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRateResponse implements Serializable {

    private final BigDecimal rate;

    public ExchangeRateResponse(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateResponse response = (ExchangeRateResponse) o;
        return Objects.equals(rate, response.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }

    @Override
    public String toString() {
        return "ExchangeRateResponse{" +
                "rate=" + rate +
                '}';
    }
}
