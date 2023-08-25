package com.picpay.dtos;

import com.picpay.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String name, String lastName, String cpf, BigDecimal balance, String email, String password, UserType userType) {
}
