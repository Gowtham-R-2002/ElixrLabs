package org.medx.elixrlabs.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

import org.medx.elixrlabs.model.TestPackage;

@Builder
@Data
public class ResponseCartDto {

    private long id;
    private boolean isPurchased;
    private List<LabTestDto> tests;
    private TestPackage testPackage;
    private double price;

}
