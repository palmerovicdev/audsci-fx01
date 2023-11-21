package com.suehay.audscifx.services;

import com.suehay.audscifx.model.EvaluatorComponentEntity;
import com.suehay.audscifx.repository.EvaluatorComponentRepository;

import java.util.List;

public class EvaluatorComponentService {
    private static final EvaluatorComponentRepository evaluatorComponentRepository = new EvaluatorComponentRepository();

    public static void saveEvaluatorComponents(List<EvaluatorComponentEntity> evaluatorComponents) {
        evaluatorComponents.forEach(evaluatorComponentRepository::save);
    }
}