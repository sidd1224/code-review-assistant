package com.internship.codereview.dto;


import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class ResponseDto {
    @NonNull
    private List<IssuesDto> issues;
}
