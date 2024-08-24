package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.CreateAndRetrieveLabTestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LabTestService {

    CreateAndRetrieveLabTestDto createOrUpdateTest(CreateAndRetrieveLabTestDto labTestDto);

    List<CreateAndRetrieveLabTestDto> getAllLabTests();

    CreateAndRetrieveLabTestDto getLabTestById(long id);

    boolean removeLabTestById(long id);
}
