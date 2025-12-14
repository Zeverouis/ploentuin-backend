package nl.ploentuin.ploentuin.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String link = "https://ploentuin.nl/reset?token=" + resetToken;
            String html = "<p>Hey hey!</p>"
                    + "<p>Klik op de link om je wachtwoord te resette:</p>"
                    + "<a href=\"" + link + "\">Reset Password</a>";

            helper.setTo(toEmail);
            helper.setSubject("Password Reset Request");
            helper.setText(html, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email to " + toEmail, e);
        }
    }
}