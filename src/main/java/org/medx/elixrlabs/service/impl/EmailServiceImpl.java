package org.medx.elixrlabs.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.medx.elixrlabs.model.LabTest;
import org.medx.elixrlabs.model.OTP;
import org.medx.elixrlabs.model.TestPackage;
import org.medx.elixrlabs.model.TestResult;
import org.medx.elixrlabs.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public OTP sendMailAndGetOtp(String email) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        int otp = new Random().nextInt(900000) + 100000;
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("Elixr Labs <gowtham080802@gmail.com>"));
        helper.setTo(email);
        helper.setSubject("OTP Verification");
        String htmlContent = loadHtmlTemplate("templates/otp-template.html").replace("{{OTP}}", String.valueOf(otp));
        helper.setText(htmlContent, true);
        ClassPathResource logoResource = new ClassPathResource("static/logo.gif");
        helper.addInline("logoImage", logoResource);
        mailSender.send(message);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        calendar.add(Calendar.MINUTE, 5);
        return OTP.builder()
                .otp(String.valueOf(otp))
                .calendar(calendar)
                .build();
    }

    @Override
    public void sendTestResult(TestResult testResult) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("Elixr Labs <gowtham080802@gmail.com>"));
        helper.setTo(testResult.getName());
        helper.setSubject("Test Report Generated");
        StringBuilder result = new StringBuilder();
        for(String individualResult : testResult.getResult()){
            result.append("<br>").append(individualResult).append("<br>");
        }
        String htmlContent = loadHtmlTemplate("templates/report-template.html")
                .replace("{{orderDate}}", testResult.getOrderDate().toString())
                .replace("{{patientEmail}}", testResult.getName())
                .replace("{{ageAndGender}}", testResult.getAgeAndGender())
                .replace("{{result}}", result.toString())
                .replace("{{generatedAt}}", testResult.getGeneratedAt().toString());
                ;
        helper.setText(htmlContent, true);
        ClassPathResource logoResource = new ClassPathResource("static/logo.gif");
        helper.addInline("logoImage", logoResource);
        mailSender.send(message);
    }

    @Override
    public void sendInvoice(List<LabTest> labTests, TestPackage testPackage, double totalPrice, String email, Calendar orderDateTime) throws MessagingException, IOException {
        StringBuilder invoiceBody = new StringBuilder();
        if (null != labTests || !labTests.isEmpty()) {
            for (LabTest labTest : labTests) {
                invoiceBody.append("<tr>")
                        .append("<td>")
                        .append(labTest.getName())
                        .append("</td>")
                        .append("<td>")
                        .append("₹ ")
                        .append(labTest.getPrice())
                        .append("</td>")
                        .append("</tr>");
            }
        }
        if (null != testPackage) {
            invoiceBody.append("<tr>")
                    .append("<td>")
                    .append(testPackage.getName())
                    .append("</td>")
                    .append("<td>")
                    .append(testPackage.getPrice())
                    .append("</td>")
                    .append("</tr>");
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("Elixr Labs <gowtham080802@gmail.com>"));
        helper.setTo(email);
        helper.setSubject("Invoice for your order");
        String htmlContent = loadHtmlTemplate("templates/invoice-template.html")
                .replace("{{email}}", email)
                .replace("{{orderDateTime}}", orderDateTime.getTime().toString())
                .replace("{{testBody}}", invoiceBody.toString())
                .replace("{{totalPrice}}", ("₹ " + String.valueOf(totalPrice)))
        ;
        helper.setText(htmlContent, true);
        ClassPathResource logoResource = new ClassPathResource("static/logo.gif");
        helper.addInline("logoImage", logoResource);
        mailSender.send(message);
    }

    private String loadHtmlTemplate(String path) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + path);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
