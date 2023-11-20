package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "question", schema = "audsci", catalog = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "label", nullable = false, length = 150)
    private String label;
    @Basic
    @Column(name = "description", nullable = true, length = 150)
    private String description;
    @Basic
    @Column(name = "result", nullable = false)
    private boolean result;
    @Basic
    @Column(name = "code", nullable = false, length = 5)
    private String code;
    @Basic
    @Column(name = "regulation_id", nullable = false)
    private int regulationId;
    @Basic
    @Column(name = "superquestion_id", nullable = false)
    private int superquestionId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionEntity that = (QuestionEntity) o;
        return id == that.id && result == that.result && regulationId == that.regulationId && superquestionId == that.superquestionId && Objects.equals(label, that.label) && Objects.equals(description, that.description) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, description, result, code, regulationId, superquestionId);
    }
}