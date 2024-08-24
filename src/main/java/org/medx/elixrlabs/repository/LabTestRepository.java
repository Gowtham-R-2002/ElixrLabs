package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    List<LabTest> findByIsDeletedFalse();

    LabTest findByIdAndIsDeletedFalse(Long id);
}
