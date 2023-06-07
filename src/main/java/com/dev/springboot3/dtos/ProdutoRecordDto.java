package com.dev.springboot3.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRecordDto(@NotBlank String nome, @NotNull BigDecimal valor) {
    
}
