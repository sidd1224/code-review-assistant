package com.internship.codereview.dto;

import lombok.Data;

import java.util.List;

@Data
public class OpenAiResponseDto {
    private List<OpenAiChoiceDto> choices;
}
