package com.openpayd.currency.domain.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionHistoryDto {

    private final Long id;
    private final LocalDate insertDate;
    private final String source;
    private final String target;
    private final BigDecimal amount;
    private final BigDecimal amountInTargetCurrency;


    public BigDecimal getAmountInTargetCurrency() {
        return amountInTargetCurrency;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public TransactionHistoryDto(Builder builder) {
        this.source = builder.source;
        this.amount = builder.amount;
        this.target = builder.target;
        this.amountInTargetCurrency = builder.amountInTargetCurrency;
        this.insertDate = builder.insertDate;
        this.id=  builder.id;
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

    public static class Builder {

        private String source, target;
        private BigDecimal amount, amountInTargetCurrency;
        private LocalDate insertDate;
        private Long id;

        public Builder() {
        }
        public Builder id( Long id) {
            this.id = id;
            return this;
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

        public Builder amountInTargetCurrency(BigDecimal amountInTargetCurrency) {
            this.amountInTargetCurrency = amountInTargetCurrency;
            return this;
        }

        public Builder insertDate(LocalDate insertDate) {
            this.insertDate = insertDate;
            return this;
        }

        public TransactionHistoryDto build() {
            return new TransactionHistoryDto(this);
        }
    }

}
