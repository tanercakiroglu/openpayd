package com.openpayd.currency.domain.repository;

import com.openpayd.currency.domain.entity.TransactionHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Long> {
    List<TransactionHistory> findAllByIdOrInsertDate(Long id, LocalDate insertDate, Pageable pageable);
}
