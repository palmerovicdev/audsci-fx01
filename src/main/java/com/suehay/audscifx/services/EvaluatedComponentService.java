package com.suehay.audscifx.services;

import com.suehay.audscifx.model.EvaluatedComponentEntity;
import com.suehay.audscifx.repository.EvaluatedComponentRepository;

import java.util.List;

public class EvaluatedComponentService {
    private static final EvaluatedComponentRepository evaluatedComponentRepository = new EvaluatedComponentRepository();
    public static void saveEvaluatedComponents(List<EvaluatedComponentEntity> evaluatedComponents) {
        evaluatedComponents.forEach(evaluatedComponentRepository::save);
    }
}