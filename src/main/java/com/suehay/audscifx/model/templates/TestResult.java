package com.suehay.audscifx.model.templates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.suehay.audscifx.model.EvaluatedComponentEntity;
import com.suehay.audscifx.model.EvaluatorComponentEntity;
import com.suehay.audscifx.model.common.TestResultData;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResult {
    TestResultData testResultData;
    List<EvaluatedComponentEntity> evaluatedComponents;
    List<EvaluatorComponentEntity> evaluatorComponents;
    TestTemplate test;
}