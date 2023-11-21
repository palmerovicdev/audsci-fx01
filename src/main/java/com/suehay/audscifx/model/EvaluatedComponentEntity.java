package com.suehay.audscifx.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "evaluated_component", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class EvaluatedComponentEntity {
    @jakarta.persistence.Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Id
    @jakarta.persistence.Column(name = "component_id", nullable = false)
    private Integer componentId;

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