package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Entity
@Table(name = "carts")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean isPurchased;

    @OneToMany
    private List<LabTest> tests;

    @OneToOne
    private TestPackage testPackage;

    @OneToOne
    private User patient;
}
