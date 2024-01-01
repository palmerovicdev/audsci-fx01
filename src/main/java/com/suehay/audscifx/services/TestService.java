package com.suehay.audscifx.services;

import com.suehay.audscifx.model.TestEntity;
import com.suehay.audscifx.repository.TestRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TestService {
    private static final TestRepository testRepository = new TestRepository();

    public static void saveTest(TestEntity testEntity) {
        testRepository.save(testEntity);
    }

    public static void deleteTest(String code) {
        testRepository.deleteByCode(code);
    }

    public static List<TestEntity> findAll() {
        return testRepository.findAll().stream().toList();
    }

}