package com.openpayd.currency.domain.service.impl;

import com.openpayd.currency.domain.entity.TransactionHistory;
import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import com.openpayd.currency.domain.repository.TransactionHistoryRepository;
import com.openpayd.currency.domain.service.TransactionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
class TransactionHistoryServiceImplTest {

    @Mock
    TransactionHistoryRepository transactionHistoryRepository;
    @Mock
    Converter<TransactionHistoryDto, TransactionHistory> transactionHistoryDtoConverter;
    @Mock
    Converter<TransactionHistory, TransactionHistoryDto> transactionHistoryEntityConverter;

    private TransactionHistoryService transactionHistoryService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionHistoryService = new TransactionHistoryServiceImpl(
                transactionHistoryRepository,
                transactionHistoryDtoConverter,
                transactionHistoryEntityConverter
        );
    }

    @Test
    public void givenTransaction_whenSaveWanted_thenReturnSavedRecordId()  {
        var transactionHistory= new TransactionHistory();
        transactionHistory.setId(1L);
        when(transactionHistoryDtoConverter.convert(any())).thenReturn(transactionHistory);
        when(transactionHistoryRepository.save(any())).thenReturn(transactionHistory);
        var result = transactionHistoryService.save(new TransactionHistoryDto.Builder()
                .source("TRY")
                .target("USD")
                .amount(new BigDecimal("12"))
                .amountInTargetCurrency(new BigDecimal("12"))
                .build());
        assertNotNull(result);
    }

    @Test
    public void givenIdOrDate_whenHistoryWanted_thenReturnList()  {
        when(transactionHistoryRepository.findAllByIdOrInsertDate(any(), any(), any())).thenReturn(new ArrayList<>());
        var result = transactionHistoryService.findAllByIdOrInsertDate(1L, LocalDate.now(),null);
        assertNotNull(result);
    }
}