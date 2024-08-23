package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    @Query("FROM LabTest l WHERE l.isDeleted = false")
    public List<LabTest> findAllLabTests();

    @Query("FROM LabTest l WHERE l.isDeleted = false AND l.id = :id")
    public LabTest findLabTestById(long id);
}
