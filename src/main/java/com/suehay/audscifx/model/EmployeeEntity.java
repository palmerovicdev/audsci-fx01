package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
@Getter
@Setter
@ToString
@Entity
@Table(name = "employee", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "employee_name", nullable = false, length = 30)
    private String employeeName;
    @Basic
    @Column(name = "position", nullable = false, length = 30)
    private String position;
    @Basic
    @Column(name = "area_id", nullable = false)
    private int areaId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return id == that.id && areaId == that.areaId && Objects.equals(employeeName, that.employeeName) && Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeName, position, areaId);
    }

}