package org.medx.elixrlabs.dto;

import lombok.Builder;
import lombok.Data;

import org.medx.elixrlabs.model.TestPackage;

import java.util.List;

@Builder
@Data
public class ResponseCartDto {

    private long id;
    private boolean isPurchased;
    private List<LabTestDto> tests;
    private TestPackage testPackage;

}
