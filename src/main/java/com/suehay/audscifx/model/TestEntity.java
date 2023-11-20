package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
@Getter
@Setter
@ToString
@Entity
@Table(name = "test", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    @Id
    @Column(name = "code", nullable = false, length = 10)
    private String code;
    @Basic
    @Column(name = "guide_version", nullable = false)
    private LocalDate guideVersion;
    @Basic
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Basic
    @Column(name = "finish_date", nullable = true)
    private LocalDate finishDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEntity that = (TestEntity) o;
        return Objects.equals(code, that.code) && Objects.equals(guideVersion, that.guideVersion) && Objects.equals(startDate, that.startDate) && Objects.equals(finishDate, that.finishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, guideVersion, startDate, finishDate);
    }
}