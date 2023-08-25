package com.picpay.dtos;

import java.math.BigDecimal;

public record TransactionDto(Long senderId, Long receiverId, BigDecimal value) {
}
