package nl.ploentuin.servicetest;

import jakarta.mail.internet.MimeMessage;
import nl.ploentuin.ploentuin.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setup() {
    }

    @Test
    void testSendPasswordResetEmailSuccess() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        assertDoesNotThrow(() ->
                emailService.sendPasswordResetEmail("user@example.com", "token123")
        );

        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testSendVerificationEmailSuccess() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        assertDoesNotThrow(() ->
                emailService.sendVerificationEmail("user@example.com", "token456")
        );

        verify(javaMailSender, times(1)).send(mimeMessage);
    }


    @Test
    void testSendPasswordResetEmailThrows() throws Exception {

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(new RuntimeException("fail")).when(javaMailSender).send(mimeMessage);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                emailService.sendPasswordResetEmail("fail@example.com", "token123")
        );

        assertTrue(ex.getMessage().contains("fail"));
    }

    @Test
    void testSendVerificationEmailThrows() {
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new RuntimeException("fail")).when(javaMailSender).send(mimeMessage);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                emailService.sendVerificationEmail("fail@example.com", "token456")
        );

        assertTrue(ex.getMessage().contains("fail"));
    }
}

