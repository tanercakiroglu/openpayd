package com.openpayd.currency.domain.service;

import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionHistoryService {

    Optional<Long> save(TransactionHistoryDto transactionHistoryDto);
    Optional<List<TransactionHistoryDto>> findAllByIdOrInsertDate(Long id, LocalDate insertDate, Pageable pageable);
}
