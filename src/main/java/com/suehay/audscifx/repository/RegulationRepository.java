package com.suehay.audscifx.repository;

import jakarta.persistence.EntityManager;
import com.suehay.audscifx.model.RegulationEntity;

import java.util.Collection;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;

public class RegulationRepository {
    private final EntityManager entityManager = getEntityManager();

    public Collection<RegulationEntity> findAll() {
        return entityManager.createQuery("SELECT r FROM RegulationEntity r", RegulationEntity.class)
                            .getResultList();
    }

    public void save(RegulationEntity regulationEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(regulationEntity);
        entityManager.persist(regulationEntity);
        entityManager.getTransaction().commit();
    }

    public RegulationEntity findById(int id) {
        return entityManager.find(RegulationEntity.class, id);
    }
    // implement the delete by id method
    public void deleteById(int id) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM RegulationEntity r WHERE r.id = :id")
                            .setParameter("id", id)
                            .executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void delete(RegulationEntity regulationEntity) {
        entityManager.getTransaction().begin();
        entityManager.remove(regulationEntity);
        entityManager.getTransaction().commit();
    }

    public void update(RegulationEntity regulationEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(regulationEntity);
        entityManager.getTransaction().commit();
    }

    // implement the all for componentId, retunrs a collection
    public Collection<RegulationEntity> findAllByComponentId(int componentId) {
        return entityManager.createQuery("SELECT r FROM RegulationEntity r WHERE r.componentId = :componentId", RegulationEntity.class)
                            .setParameter("componentId", componentId)
                            .getResultList();
    }

}