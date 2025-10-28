package br.com.fiap.tradutoria.dto;

import jakarta.validation.constraints.NotBlank;

public record TranslateRequest(@NotBlank(message = "Texto é obrigatório") String text) {}
