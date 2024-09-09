package org.medx.elixrlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.medx.elixrlabs.model.TestResult;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
