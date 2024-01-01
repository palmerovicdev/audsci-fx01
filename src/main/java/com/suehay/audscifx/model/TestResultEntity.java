package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@ToString
@Entity
@Table(name = "test_results", schema = "audsci")
@AllArgsConstructor
@NoArgsConstructor
public class TestResultEntity {
    @Id
    @Column(name = "test_code", nullable = false, length = 10)
    private String testCode;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "test_code", nullable = false)
    @ToString.Exclude
    private TestEntity test;

    @Column(name = "yes", nullable = false)
    private Integer yes;

    @Column(name = "no", nullable = false)
    private Integer no;

    @Column(name = "undef", nullable = false)
    private Integer undef;

}