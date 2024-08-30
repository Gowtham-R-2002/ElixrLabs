package org.medx.elixrlabs.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jakarta.mail.MessagingException;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.model.TestResult;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Interface for EmailService, defining the business operations related to email.
 * This interface is implemented by EmailServiceImpl and defines the contract for
 * managing operations which are linked to email.
 * </p>
 */

@Service
public interface EmailService {

    /**
     *
     * @param email
     * @return
     * @throws MessagingException
     * @throws IOException
     */

    OTP sendMailAndGetOtp(String email) throws MessagingException, IOException;

    void sendTestResult(TestResult testResult) throws MessagingException, IOException;

    void sendInvoice(List<LabTest> labTests, TestPackage testPackage, double totalPrice, String email, Calendar orderDateTime) throws MessagingException, IOException;
}