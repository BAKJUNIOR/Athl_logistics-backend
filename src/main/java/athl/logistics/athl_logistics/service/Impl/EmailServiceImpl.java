package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.service.EmailService;
import athl.logistics.athl_logistics.service.dto.UserDTO;
import athl.logistics.athl_logistics.service.dto.UserValidationCodeDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    // Doit correspondre au compte SMTP authentifié (spring.mail.username) : la plupart des
    // serveurs (dont celui-ci) rejettent l'envoi si l'adresse "From" appartient à un autre
    // domaine/compte ("Sender address rejected: not owned by user ...").
    @Value("${spring.mail.username}")
    private String fromAddress;

    @Override
    public void sendActivationCode(UserValidationCodeDTO userValidationCodeDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(userValidationCodeDTO.getUser().getEmail());
            helper.setSubject("Activation de votre compte");

            Context context = new Context();
            context.setVariable("firstName", userValidationCodeDTO.getUser().getFirstName());
            context.setVariable("lastName", userValidationCodeDTO.getUser().getLastName());
            context.setVariable("code", userValidationCodeDTO.getCode());

            String htmlContent = templateEngine.process("account-activation-email", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email d'activation", e);
        }
    }

    @Override
    public void sendAccountCreationEmail(String userType, UserDTO userDTO, String plainPassword) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(userDTO.getEmail());
            helper.setSubject("Création de votre compte sur Athl-Logistics");

            Context context = new Context();
            context.setVariable("userType", userType);
            context.setVariable("firstName", userDTO.getFirstName());
            context.setVariable("email", userDTO.getEmail());
            context.setVariable("password", plainPassword);

            String htmlContent = templateEngine.process("user-account-info-email", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email d'informations de compte", e);
        }
    }

    @Override
    public void sendPasswordResetByAdmin(UserDTO userDTO, String newPassword) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(userDTO.getEmail());
            helper.setSubject("Réinitialisation de votre mot de passe");

            Context context = new Context();
            context.setVariable("firstName", userDTO.getFirstName());
            context.setVariable("newPassword", newPassword);

            String htmlContent = templateEngine.process("admin-password-reset-email", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email de réinitialisation", e);
        }
    }

    @Override
    public void sendAccountStatusEmail(UserDTO userDTO, String reason, String subject) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(userDTO.getEmail());
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("firstName", userDTO.getFirstName());
            context.setVariable("reason", reason);
            context.setVariable("status", userDTO.isActive() ? "activé" : "désactivé");

            String htmlContent = templateEngine.process("account-status-change-email", context);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email de changement de statut", e);
        }
    }

}
