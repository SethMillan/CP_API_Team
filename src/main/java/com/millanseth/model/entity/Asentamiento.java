package com.millanseth.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "asentamientos")
public class Asentamiento {
    @Id
    @Column(name="asentamiento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String asentamiento;

    @ManyToOne
    @JoinColumn(name="codigo_postal")
    private CodigoPostal codigoPostal;
}
