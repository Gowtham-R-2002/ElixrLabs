package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.CreateAndRetrieveLabTestDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * Interface for LabTestService, defining the business operations related to LabTest.
 * This interface is implemented by LabTestServiceImpl and defines the contract for
 * managing LabTest entities.
 * </p>
 */
@Service
public interface LabTestService {

    CreateAndRetrieveLabTestDto createOrUpdateTest(CreateAndRetrieveLabTestDto labTestDto);

    List<CreateAndRetrieveLabTestDto> getAllLabTests();

    CreateAndRetrieveLabTestDto getLabTestById(long id);

    boolean removeLabTestById(long id);
}
