package com.openpayd.currency.domain.repository;

import com.openpayd.currency.domain.entity.TransactionHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
class TransactionHistoryRepositoryTest {

    @Autowired
    TransactionHistoryRepository subject;

    @BeforeEach
    public void setup(){
        TransactionHistory history = null;
        for (int i = 0; i < 5; i++) {
            history = new TransactionHistory();
            history.setSource(String.valueOf(i));
            history.setTarget(String.valueOf(i));
            history.setAmountInTargetCurrency(new BigDecimal(12));
            history.setAmountInTargetCurrency(new BigDecimal(12));
            history.setInsertDate(LocalDate.now());
            subject.save(history);
        }
    }

    @Test
    public void save() {
        var saved = subject.save(new TransactionHistory());
        assertNotNull(saved);
    }

    @Test
    public void findByDate() {
        var result = subject.findAllByIdOrInsertDate(null, LocalDate.now(), null);
        assertNotNull(result);
        assertEquals(result.size(), 5);
    }

    @Test
    public void findById() {
        var result = subject.findAllByIdOrInsertDate(6L, null, null);
        assertEquals(result.size(), 1);
    }

    @Test
    public void findByDateWithPaging() {
        Pageable paging = PageRequest.of(0, 2);
        var result = subject.findAllByIdOrInsertDate(null, LocalDate.now(), paging);
        assertEquals(result.size(), 2);
    }

}