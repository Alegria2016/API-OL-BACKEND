package com.backend.model;

import com.backend.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MERCHANTS")
@EntityListeners(AuditingEntityListener.class)
public class Merchant extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "merchant_seq")
    @SequenceGenerator(name = "merchant_seq", sequenceName = "MERCHANT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String companyName;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(name = "REGISTRATION_DATE", nullable = false)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status status;

    @OneToMany(mappedBy = "merchant", cascade = CascadeType.ALL)
    private List<Establishment> establishments = new ArrayList<>();




}
