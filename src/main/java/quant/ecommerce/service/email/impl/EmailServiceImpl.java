package quant.ecommerce.service.email.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import quant.ecommerce.service.email.EmailService;
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
    public void sentOTP(String toEmail, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Mã xác thực OTP");
            helper.setText(buildEmailContent(otp), true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private String buildEmailContent(String otp) {
        return """
            <div style="font-family: Arial, sans-serif; max-width: 480px; margin: auto;">
                <h2 style="color: #333;">Mã xác thực OTP</h2>
                <p>Mã OTP của bạn là:</p>
                <div style="font-size: 32px; font-weight: bold; letter-spacing: 8px;
                            color: #4F46E5; padding: 16px; background: #F3F4F6;
                            border-radius: 8px; text-align: center;">
                    %s
                </div>
                <p style="color: #888; font-size: 13px; margin-top: 16px;">
                    Mã có hiệu lực trong 5 phút. Không chia sẻ mã này cho bất kỳ ai.
                </p>
            </div>
        """.formatted(otp);
    }
}
