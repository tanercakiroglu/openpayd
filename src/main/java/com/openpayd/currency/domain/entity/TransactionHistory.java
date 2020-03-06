package com.openpayd.currency.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
public class TransactionHistory {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate insertDate;
    private String source;
    private String target;
    private BigDecimal amount;
    private BigDecimal amountInTargetCurrency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountInTargetCurrency() {
        return amountInTargetCurrency;
    }

    public void setAmountInTargetCurrency(BigDecimal amountInTargetCurrency) {
        this.amountInTargetCurrency = amountInTargetCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionHistory that = (TransactionHistory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(insertDate, that.insertDate) &&
                Objects.equals(source, that.source) &&
                Objects.equals(target, that.target) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(amountInTargetCurrency, that.amountInTargetCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, insertDate, source, target, amount, amountInTargetCurrency);
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "id=" + id +
                ", insertDate=" + insertDate +
                ", source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", amount=" + amount +
                ", amountInTargetCurrency=" + amountInTargetCurrency +
                '}';
    }
}
