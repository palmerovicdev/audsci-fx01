package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Getter
@Setter
@ToString
@Entity
@Table(name = "area", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class AreaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "area_name", nullable = false, length = 30)
    private String areaName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaEntity that = (AreaEntity) o;
        return id == that.id && Objects.equals(areaName, that.areaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, areaName);
    }

}