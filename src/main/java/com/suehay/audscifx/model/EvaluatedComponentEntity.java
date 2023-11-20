package com.suehay.audscifx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;
@Getter
@Setter
@ToString
@Entity
@jakarta.persistence.Table(name = "evaluated_component", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class EvaluatedComponentEntity {
    @Id
    @jakarta.persistence.Column(name = "employee_id", nullable = false)
    private int employeeId;

    @Id
    @jakarta.persistence.Column(name = "component_id", nullable = false)
    private int componentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluatedComponentEntity that = (EvaluatedComponentEntity) o;
        return employeeId == that.employeeId && componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, componentId);
    }
}