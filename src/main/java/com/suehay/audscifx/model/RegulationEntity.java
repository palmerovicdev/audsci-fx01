package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Getter
@Setter
@ToString
@Entity
@Table(name = "regulation", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class RegulationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "label", nullable = false, length = 100)
    private String label;
    @Basic
    @Column(name = "component_id", nullable = false)
    private int componentId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegulationEntity that = (RegulationEntity) o;
        return id == that.id && componentId == that.componentId && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, componentId);
    }

}