package com.suehay.audscifx.repository;

import jakarta.persistence.EntityManager;
import com.suehay.audscifx.model.ComponentEntity;
import com.suehay.audscifx.model.EmployeeEntity;
import com.suehay.audscifx.model.EvaluatedComponentEntity;

import java.util.Collection;

import static com.suehay.audscifx.config.EntityManagerProvider.getEntityManager;
import static com.suehay.audscifx.config.Injector.getContext;

public class EvaluatedComponentRepository {
    private final EntityManager entityManager = getEntityManager();

    public void save(EvaluatedComponentEntity evaluatedComponentEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(evaluatedComponentEntity);
        entityManager.getTransaction().commit();
    }

    public void delete(EvaluatedComponentEntity evaluatedComponentEntity) {
        entityManager.getTransaction().begin();
        entityManager.remove(evaluatedComponentEntity);
        entityManager.getTransaction().commit();
    }

    public void update(EvaluatedComponentEntity evaluatedComponentEntity) {
        entityManager.getTransaction().begin();
        entityManager.merge(evaluatedComponentEntity);
        entityManager.getTransaction().commit();
    }

    // implement the find all employees for componentId, using the entity manager
    public Collection<EmployeeEntity> findAllEmployeesForComponentId(int componentId) {
        // implement search for all employees for a componentId, using the entity manager
        return entityManager.createQuery("SELECT e FROM EmployeeEntity e WHERE e.id IN (SELECT ec.employeeId FROM EvaluatedComponentEntity ec WHERE ec.componentId = :componentId)", EmployeeEntity.class).setParameter("componentId", componentId).getResultList();
    }

    // implement search for a single componentEntity for an employeeId, using the entity manager
    public ComponentEntity findComponentEntityForEmployeeId(int employeeId) {
        // implement search for a single componentEntity for an employeeId, using the entity manager
        return entityManager.createQuery("SELECT c FROM ComponentEntity c WHERE c.id IN (SELECT ec.componentId FROM EvaluatedComponentEntity ec WHERE ec.employeeId = :employeeId)", ComponentEntity.class).setParameter("employeeId", employeeId).getSingleResult();
    }

}