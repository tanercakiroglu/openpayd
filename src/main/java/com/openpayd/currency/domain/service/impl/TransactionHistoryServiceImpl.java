package com.openpayd.currency.domain.service.impl;

import com.openpayd.currency.domain.entity.TransactionHistory;
import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import com.openpayd.currency.domain.repository.TransactionHistoryRepository;
import com.openpayd.currency.domain.service.TransactionHistoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;
    private final Converter<TransactionHistoryDto, TransactionHistory> transactionHistoryDtoConverter;
    private final Converter<TransactionHistory, TransactionHistoryDto> transactionHistoryEntityConverter;

    public TransactionHistoryServiceImpl(TransactionHistoryRepository transactionHistoryRepository, Converter<TransactionHistoryDto, TransactionHistory> transactionHistoryDtoConverter, Converter<TransactionHistory, TransactionHistoryDto> transactionHistoryEntityConverter) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryDtoConverter = transactionHistoryDtoConverter;
        this.transactionHistoryEntityConverter = transactionHistoryEntityConverter;
    }
    @Override
    public Optional<Long> save(TransactionHistoryDto transactionHistoryDto) {
        var transactionHistory = transactionHistoryDtoConverter.convert(transactionHistoryDto);
        if (Objects.nonNull(transactionHistory)) {
            return Optional.of(transactionHistoryRepository.save(transactionHistory).getId());
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<TransactionHistoryDto>> findAllByIdOrInsertDate(Long id, LocalDate insertDate, Pageable pageable) {
        var transactionHistories = transactionHistoryRepository.findAllByIdOrInsertDate(id, insertDate, pageable);
        if(CollectionUtils.isEmpty(transactionHistories))
            return Optional.empty();
        return Optional.of(transactionHistories.stream()
                .map(transactionHistoryEntityConverter::convert)
                .collect(Collectors.toList()));
    }
}
