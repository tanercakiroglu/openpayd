package com.openpayd.currency.domain.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CalculatedExchangeRateDto {

    private final String source;
    private final String target;
    private final BigDecimal amount;
    private final BigDecimal rate;
    private final BigDecimal amountInTargetCurrency;


    public CalculatedExchangeRateDto(Builder builder) {
        this.source = builder.source;
        this.amount = builder.amount;
        this.target = builder.target;
        this.rate = builder.rate;
        this.amountInTargetCurrency = builder.amountInTargetCurrency;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getAmountInTargetCurrency() {
        return amountInTargetCurrency;
    }

    public static class Builder {

        private String source, target;
        private BigDecimal amount, rate, amountInTargetCurrency;

        public Builder() {
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder target(String target) {
            this.target = target;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder rate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }


        public Builder amountInTargetCurrency(BigDecimal amountInTargetCurrency) {
            this.amountInTargetCurrency = amountInTargetCurrency;
            return this;
        }

        public CalculatedExchangeRateDto build() {
            return new CalculatedExchangeRateDto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatedExchangeRateDto that = (CalculatedExchangeRateDto) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(target, that.target) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, amount, rate);
    }

    @Override
    public String toString() {
        return "CalculatedExchangeRateDto{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", amount=" + amount +
                ", rate=" + rate +
                '}';
    }
}
