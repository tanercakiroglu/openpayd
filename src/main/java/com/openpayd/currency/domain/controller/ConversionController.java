package com.openpayd.currency.domain.controller;

import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import com.openpayd.currency.domain.model.request.ConversionAmountRequest;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;
import com.openpayd.currency.exception.BusinessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface ConversionController {

    @PostMapping("/exchangeRate/conversion")
    ResponseEntity<ConversionRateResponse> convertRate(@Valid @RequestBody ConversionAmountRequest rateRequest) throws IOException, BusinessException;

    @GetMapping("/exchangeRate/conversions")
    List<TransactionHistoryDto> getConversionList(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "insertDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate insertDate,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) throws BusinessException;
}
