package org.medx.elixrlabs.service;

import org.medx.elixrlabs.dto.TestPackageDto;
import org.medx.elixrlabs.dto.ResponseTestPackageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestPackageService {

    ResponseTestPackageDto createOrUpdateTest(TestPackageDto TestPackageDto);

    List<ResponseTestPackageDto> getAllTestPackages();

    ResponseTestPackageDto getTestPackageById(long id);

    boolean removeTestPackageById(long id);
}
