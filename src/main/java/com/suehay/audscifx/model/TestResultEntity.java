package com.suehay.audscifx.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "test_results", schema = "audsci")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "test_code", nullable = false, length = 10)
    private String testCode;

    @Column(name = "yes", nullable = false)
    private Integer yes;

    @Column(name = "no", nullable = false)
    private Integer no;

    @Column(name = "undef", nullable = false)
    private Integer undef;

}