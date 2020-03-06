package com.openpayd.currency.domain.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRateCalculationDto {

    private final String source;
    private final String target;
    private final BigDecimal amount;

    public ExchangeRateCalculationDto(Builder builder) {
        this.source=builder.source;
        this.amount=builder.amount;
        this.target=builder.target;

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

    public static class Builder{

        private String source, target;
        private BigDecimal amount;

        public Builder(){ }

        public Builder source(String source){
            this.source = source;
            return this;
        }

        public Builder target(String target){
            this.target = target;
            return this;
        }

        public Builder amount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public ExchangeRateCalculationDto build(){
            return  new ExchangeRateCalculationDto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateCalculationDto that = (ExchangeRateCalculationDto) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(target, that.target) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, amount);
    }

    @Override
    public String toString() {
        return "ExchangeRateCalculationDto{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", amount=" + amount +
                '}';
    }
}
