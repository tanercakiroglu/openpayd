package com.openpayd.currency.domain.service.impl;

import com.openpayd.currency.domain.converter.ConversationRateResponseConverter;
import com.openpayd.currency.domain.converter.ExchangeRateCalculationDtoConverter;
import com.openpayd.currency.domain.model.dto.TransactionHistoryDto;
import com.openpayd.currency.domain.model.request.ConversionAmountRequest;
import com.openpayd.currency.domain.model.response.ConversionRateResponse;
import com.openpayd.currency.domain.service.ConversionService;
import com.openpayd.currency.domain.service.ExchangeService;
import com.openpayd.currency.domain.service.TransactionHistoryService;
import com.openpayd.currency.exception.BusinessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
class ConversionServiceImpl implements ConversionService {

    private final ExchangeService exchangeService;
    private final ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter;
    private final ConversationRateResponseConverter conversationRateResponseConverter;
    private final TransactionHistoryService transactionHistoryService;


    public ConversionServiceImpl(ExchangeService exchangeService, ExchangeRateCalculationDtoConverter exchangeRateCalculationDtoConverter,
                                 ConversationRateResponseConverter conversationRateResponseConverter,
                                 TransactionHistoryService transactionHistoryService) {
        this.exchangeService = exchangeService;
        this.exchangeRateCalculationDtoConverter = exchangeRateCalculationDtoConverter;
        this.conversationRateResponseConverter = conversationRateResponseConverter;
        this.transactionHistoryService = transactionHistoryService;
    }

    @Override
    public ConversionRateResponse getConversionAmount(ConversionAmountRequest rateRequest) throws IOException, BusinessException {
        var calculationDto = exchangeRateCalculationDtoConverter.convert(rateRequest);
        var calculatedExchangeRateDto = exchangeService.getExchangeRateAndCalculatePair(calculationDto);
        var transactionId = transactionHistoryService.save(new TransactionHistoryDto
                .Builder()
                .amount(calculatedExchangeRateDto.getAmount())
                .amountInTargetCurrency(calculatedExchangeRateDto.getAmountInTargetCurrency())
                .source(calculatedExchangeRateDto.getSource())
                .target(calculatedExchangeRateDto.getTarget())
                .insertDate(LocalDate.now())
                .build());
        return conversationRateResponseConverter.convert(calculatedExchangeRateDto, transactionId);
    }

    @Override
    public List<TransactionHistoryDto> findAllConversionsByIdOrInsertDate(Long id, LocalDate insertDate, Integer pageNo, Integer pageSize) {
        if (Objects.isNull(id) && Objects.isNull(insertDate))
            throw new IllegalArgumentException("At least one parameter has value id or insert date");
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return transactionHistoryService.findAllByIdOrInsertDate(id, insertDate, paging);
    }
}
