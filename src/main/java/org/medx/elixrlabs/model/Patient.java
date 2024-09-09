package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Patient {
    @Id
    @Column
    @SequenceGenerator(name = "PatientIdGenerator", initialValue = 10000)
    @GeneratedValue(generator = "PatientIdGenerator", strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade=CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.LAZY)
    private List<Order> orders;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
