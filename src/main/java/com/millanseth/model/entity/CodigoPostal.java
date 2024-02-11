package com.millanseth.model.entity;

import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="codigos_postales")
public class CodigoPostal {
    @Id
    @Column(name="codigo_postal")
    private Integer cp;
    @ManyToOne
    @JoinColumn(name="municipio_id")
    private Municipio municipio;
}
