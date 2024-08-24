package org.medx.elixrlabs.repository;

import org.medx.elixrlabs.model.TestPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestPackageRepository extends JpaRepository<TestPackage, Long> {

    List<TestPackage> findByIsDeletedFalse();

    TestPackage findByIdAndIsDeletedFalse(Long id);
}
