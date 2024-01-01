package com.suehay.audscifx.repository;

import com.suehay.audscifx.model.QuestionEntity;
import jakarta.persistence.EntityManager;

import java.util.Collection;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class QuestionRepository {
    private final EntityManager entityManager = getEntityManager();

    public Collection<QuestionEntity> findAll() {
        return entityManager.createQuery("SELECT q FROM QuestionEntity q", QuestionEntity.class)
                            .getResultList();
    }

    public void save(QuestionEntity questionEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(questionEntity);
        entityManager.getTransaction().commit();
    }

    public QuestionEntity findById(int id) {
        return entityManager.find(QuestionEntity.class, id);
    }

    public void delete(QuestionEntity questionEntity) {
        entityManager.getTransaction().begin();
        entityManager.remove(questionEntity);
        entityManager.getTransaction().commit();
    }

    public void update(QuestionEntity questionEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(questionEntity);
        entityManager.getTransaction().commit();
    }

    // implement the label method
    public QuestionEntity findByLabel(String label) {
        return entityManager.createQuery("SELECT q FROM QuestionEntity q WHERE q.label = :label", QuestionEntity.class)
                            .setParameter("label", label)
                            .getSingleResult();
    }

    // implement the description method
    public QuestionEntity findByDescription(String description) {
        return entityManager.createQuery("SELECT q FROM QuestionEntity q WHERE q.description = :description", QuestionEntity.class)
                            .setParameter("description", description)
                            .getSingleResult();
    }

    // implement the code
    public QuestionEntity findByCode(String code) {
        return entityManager.createQuery("SELECT q FROM QuestionEntity q WHERE q.code = :code", QuestionEntity.class)
                            .setParameter("code", code)
                            .getSingleResult();
    }

    // implement the all for regulationId, retunrs a collection
    public Collection<QuestionEntity> findAllByRegulationId(int regulationId) {
        return entityManager.createQuery("SELECT q FROM QuestionEntity q WHERE q.regulationId = :regulationId ORDER BY q.id ASC",
                                         QuestionEntity.class)
                            .setParameter("regulationId", regulationId)
                            .getResultList();
    }

    public Collection<QuestionEntity> findAllBySuperQuestionId(Integer id) {
        return entityManager.createQuery("SELECT q FROM QuestionEntity q WHERE q.superquestionId = :superquestionId", QuestionEntity.class)
                            .setParameter("superquestionId", id)
                            .getResultList();
    }

    public String findTestCodeByQuestionId(Integer id) {
        return entityManager.createNativeQuery("select test_code from component where id = " +
                                                       "(select component_id from regulation where id = " +
                                                       "(select regulation_id from question  where id = :id limit 1) limit 1) limit 1;", String.class)
                                     .setParameter("id", id).getResultList().get(0).toString();
    }
}