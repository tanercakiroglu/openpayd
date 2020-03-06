package com.openpayd.currency.domain.converter.impl;

import com.openpayd.currency.domain.converter.ConversationRateResponseConverter;
import com.openpayd.currency.domain.model.dto.CalculatedExchangeRateDto;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;
import org.springframework.stereotype.Component;

@Component
class ConversationRateResponseConverterImpl implements ConversationRateResponseConverter {

    @Override
    public ConversionRateResponse convert(CalculatedExchangeRateDto source, Long transactionId) {
        return new ConversionRateResponse
                .Builder()
                .amount(source.getAmountInTargetCurrency())
                .transactionId(transactionId)
                .build();
    }
}
