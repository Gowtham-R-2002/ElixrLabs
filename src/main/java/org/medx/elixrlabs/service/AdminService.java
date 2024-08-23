package org.medx.elixrlabs.service;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.SampleCollector;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.RoleRepository;
import org.medx.elixrlabs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminService {

    /**
     * <p>
     *
     * </p>
     */

    void setupInitialData();

    /**
     * <p>
     * Updates a specific sample collectors status as a verified which allows the
     * sample collector to access the fields associated with them
     * </p>
     * @param id of the sample collector whose status has to be changed
     * @return the verification status of the sample collector
     */

    boolean verifySampleCollector(long id);

    /**
     * <p>
     * Fetches all the sample collectors who are verified
     * </p>
     * @return list of verified sample collectors
     */

    //List<SampleCollectorDto> getAllSampleCollectors();

    /**
     * <p>
     * Removes a specific sample collector
     * </p>
     * @param id of the sample collector who has to be removed
     */

    boolean deleteSampleCollector(String id);
}

