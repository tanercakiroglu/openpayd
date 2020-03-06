package com.openpayd.currency.delegate.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class ExchangeRateDelegateResponse {

    private String base;
    private Map<String, BigDecimal> rates;
    private LocalDate date;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateDelegateResponse quote = (ExchangeRateDelegateResponse) o;
        return Objects.equals(base, quote.base) &&
                Objects.equals(rates, quote.rates) &&
                Objects.equals(date, quote.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, rates, date);
    }

    @Override
    public String toString() {
        return "Quote{" +
                "base='" + base + '\'' +
                ", rates=" + rates +
                ", date=" + date +
                '}';
    }
}
