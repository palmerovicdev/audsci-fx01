package com.suehay.audscifx.services;

import com.suehay.audscifx.model.TestResultEntity;
import com.suehay.audscifx.repository.TestResultRepository;

public class TestResultService {
    public static TestResultEntity findById(String id) {
        return TestResultRepository.findById(id);
    }
}