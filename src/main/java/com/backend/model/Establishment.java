package com.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESTABLISHMENTS")
@EntityListeners(AuditingEntityListener.class)
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "establishment_seq")
    @SequenceGenerator(name = "establishment_seq", sequenceName = "ESTABLISHMENT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "INCOME", nullable = false, precision = 12, scale = 2)
    private BigDecimal income;

    @Column(name = "EMPLOY_NUMBER", nullable = false)
    private Integer employNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MERCHANT_ID", nullable = false)
    private Merchant merchant;
}
