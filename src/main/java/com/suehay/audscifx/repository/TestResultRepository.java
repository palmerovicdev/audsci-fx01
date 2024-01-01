package com.suehay.audscifx.repository;

import com.suehay.audscifx.model.TestResultEntity;
import jakarta.persistence.EntityManager;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class TestResultRepository {
    private static final EntityManager entityManager = getEntityManager();
    public static TestResultEntity findById(String id) {
        return entityManager.find(TestResultEntity.class, id);
    }
}