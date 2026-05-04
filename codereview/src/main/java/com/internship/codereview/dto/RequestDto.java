package com.internship.codereview.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class RequestDto {
    @NonNull
  private String language;
    @NonNull
  private String code;
}
