package com.millanseth.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "municipios")
public class Municipio implements Serializable {
    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="municipio")
    private String municipio;
    @ManyToOne
    @JoinColumn(name="estado_id")
    private Estado estado;
}
