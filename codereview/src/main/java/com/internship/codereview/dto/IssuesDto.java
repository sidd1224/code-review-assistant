package com.internship.codereview.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class IssuesDto {
  private int line ;
    @NonNull
  private String problem;
    @NonNull
  private String suggestions;
}
