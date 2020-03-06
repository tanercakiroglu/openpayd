package com.openpayd.currency.domain.model.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ConversionRateResponse implements Serializable {

    private final Long transactionId;
    private final BigDecimal amount;

    public ConversionRateResponse(Builder builder) {
        this.transactionId =builder.transactionId;
        this.amount=builder.amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public static class Builder{

        private Long transactionId;
        private BigDecimal amount;

        public Builder(){ }

        public  Builder transactionId(Long transactionId){
            this.transactionId = transactionId;
            return this;
        }

        public Builder amount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public ConversionRateResponse build(){
            return  new ConversionRateResponse(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionRateResponse that = (ConversionRateResponse) o;
        return Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, amount);
    }

    @Override
    public String toString() {
        return "ConversionRateResponse{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
