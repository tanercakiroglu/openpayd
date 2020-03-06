package com.openpayd.currency.domain.converter.impl;

import com.openpayd.currency.domain.entity.TransactionHistory;
import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TransactionHistoryEntityConverterImpl implements Converter<TransactionHistory, TransactionHistoryDto> {

    @Override
    public TransactionHistoryDto convert(TransactionHistory source) {
        return new TransactionHistoryDto
                .Builder()
                .id(source.getId())
                .amount(source.getAmount())
                .amountInTargetCurrency(source.getAmountInTargetCurrency())
                .source(source.getSource())
                .target(source.getTarget())
                .insertDate(LocalDate.now())
                .build();
    }
}
