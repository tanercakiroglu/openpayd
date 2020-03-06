package com.openpayd.currency.domain.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ConversionAmountRequest implements Serializable {

    @NotBlank(message = "Source must not be null or empty")
    private String source;
    @NotBlank(message = "Target must not be null or empty")
    private String target;
    @NotNull(message = "Amount must not be null")
    private BigDecimal amount;

    public ConversionAmountRequest() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversionAmountRequest that = (ConversionAmountRequest) o;
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
        return "ConversionRateRequest{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", amount=" + amount +
                '}';
    }
}
