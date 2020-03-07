package com.openpayd.currency.domain.controller.impl;

import com.openpayd.currency.aspect.loggable.Loggable;
import com.openpayd.currency.domain.controller.ConversionController;
import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import com.openpayd.currency.domain.model.request.ConversionAmountRequest;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;
import com.openpayd.currency.domain.service.ConversionService;
import com.openpayd.currency.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@Loggable
public class ConversionControllerImpl implements ConversionController {

    private final ConversionService conversionService;

    public ConversionControllerImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<ConversionRateResponse> convertRate(ConversionAmountRequest rateRequest) throws IOException, BusinessException {
        return ResponseEntity.ok(conversionService.getConversionAmount(rateRequest));
    }

    @Override
    public ResponseEntity<List<TransactionHistoryDto>> getConversionList(Long id, LocalDate insertDate, Integer pageNo, Integer pageSize) throws BusinessException {
        return ResponseEntity.ok(conversionService.findAllConversionsByIdOrInsertDate(id, insertDate, pageNo, pageSize));
    }

}
