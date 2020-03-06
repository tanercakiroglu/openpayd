package com.openpayd.currency.domain.service;

import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import com.openpayd.currency.domain.model.request.ConversionAmountRequest;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;
import com.openpayd.currency.exception.BusinessException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ConversionService {

     ConversionRateResponse getConversionAmount(ConversionAmountRequest rateRequest) throws IOException, BusinessException;
     List<TransactionHistoryDto> findAllConversionsByIdOrInsertDate(Long id, LocalDate insertDate, Integer pageNo, Integer pageSize) ;
}
