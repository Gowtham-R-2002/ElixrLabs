package org.medx.elixrlabs.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.model.TestResult;

/**
 * <p> Interface for EmailService, defining the business operations related to email.
 * This interface is implemented by EmailServiceImpl and defines the contract for
 * managing operations which are linked to email. </p>
 *
 * @author Gowtham R
 */

@Service
public interface EmailService {

    /**
     * Sends an OTP to a specified email
     *
     * @param email email to which OTP has to be sent
     * @return OTP that has been generated
     * @throws MessagingException Exception thrown when there is an issue while
     *                            sending email
     * @throws IOException        Exception thrown when there is an issue in
     *                            getting resources
     */

    OTP sendMailAndGetOtp(String email) throws MessagingException, IOException;

    /**
     * Sends Test result to the patient's email
     *
     * @param testResult {@link TestResult} test result that has to be sent
     * @throws MessagingException Exception thrown when there is an issue while
     *                            sending email
     * @throws IOException        Exception thrown when there is an issue in
     *                            getting resources
     */

    void sendTestResult(TestResult testResult) throws MessagingException, IOException;

    /**
     * Sends an email which contains the invoice for the patient's order
     *
     * @param labTests      {@link LabTest} lab tests that the patient has ordered
     * @param testPackage   {@link TestPackage} test package that the patient has ordered
     * @param totalPrice    the total price of the lab tests and package
     * @param email         email of the patient to whom the invoice has to be sent
     * @param orderDateTime date and time of the order
     * @throws MessagingException Exception thrown when there is an issue while
     *                            sending email
     * @throws IOException        Exception thrown when there is an issue in
     *                            getting resources
     */

    void sendInvoice(List<LabTest> labTests, TestPackage testPackage, double totalPrice, String email, Calendar orderDateTime) throws MessagingException, IOException;
}