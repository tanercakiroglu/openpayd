package com.openpayd.currency.domain.service;

import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionHistoryService {
    Long save(TransactionHistoryDto transactionHistoryDto);
    List<TransactionHistoryDto> findAllByIdOrInsertDate(Long id, LocalDate insertDate, Pageable pageable);
}
