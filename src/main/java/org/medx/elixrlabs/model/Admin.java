package org.medx.elixrlabs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Admin {
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Id
    @Column
    @SequenceGenerator(name = "AdminIdGenerator", initialValue = 100)
    @GeneratedValue(generator = "AdminIdGenerator", strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
