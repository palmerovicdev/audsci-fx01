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
public class TestTemplate {
    private String code;
    private String guideVersion;
    private String startDate;
    private String finishDate;
    List<ComponentTemplate> componentTemplates = new ArrayList<>();
}