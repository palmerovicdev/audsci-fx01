package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Getter
@ToString
@Setter
@Entity
@Table(name = "component", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class ComponentEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "label", nullable = false, length = 100)
    private String label;
    @Basic
    @Column(name = "test_code", nullable = false, length = 10)
    private String testCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentEntity that = (ComponentEntity) o;
        return id == that.id && Objects.equals(label, that.label) && Objects.equals(testCode, that.testCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, testCode);
    }

}