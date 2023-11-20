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
public class RegulationTemplate {
    private Integer id;
    private String label;
    private Integer componentId;
    private List<QuestionTemplate> questionTemplates = new ArrayList<>();
}