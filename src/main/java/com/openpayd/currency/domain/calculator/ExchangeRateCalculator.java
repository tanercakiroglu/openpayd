package com.openpayd.currency.domain.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class ExchangeRateCalculator {

    public BigDecimal getExchangeRatePair(BigDecimal source, BigDecimal target) {
        return (target.divide(source, MathContext.DECIMAL32)).setScale(4, RoundingMode.HALF_EVEN);
    }

    public BigDecimal calculateAmountInTargetCurrency(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(4, RoundingMode.HALF_EVEN);
    }
}
