package com.openpayd.currency.domain.converter.impl;

import com.openpayd.currency.domain.entity.TransactionHistory;
import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class TransactionHistoryDtoConverterImpl implements Converter<TransactionHistoryDto, TransactionHistory> {

    @Override
    public TransactionHistory convert(TransactionHistoryDto source) {
        final TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setId(source.getId());
        transactionHistory.setInsertDate(source.getInsertDate());
        transactionHistory.setAmount(source.getAmount());
        transactionHistory.setTarget(source.getTarget());
        transactionHistory.setAmountInTargetCurrency(source.getAmountInTargetCurrency());
        transactionHistory.setSource(source.getSource());
        return transactionHistory;
    }
}
