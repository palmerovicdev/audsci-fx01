package com.suehay.audscifx.model.templates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTemplate {
    private Integer id;
    private String label;
    private String description;
    private String code;
    private Integer regulationId;
    private Integer superquestionId;
    private Boolean result;
    private List<QuestionTemplate> subQuestions = new ArrayList<>();
}