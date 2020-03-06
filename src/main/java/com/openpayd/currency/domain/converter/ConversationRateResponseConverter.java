package com.openpayd.currency.domain.converter;

import com.openpayd.currency.domain.model.dto.CalculatedExchangeRateDto;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;

public interface ConversationRateResponseConverter {

    ConversionRateResponse convert(CalculatedExchangeRateDto source,Long transactionId);
}
