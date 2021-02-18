package agency.services.implementations;

import agency.services.interfaces.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {


    private JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }



    @Override
    public void sendSimpleMessage(String to) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("juricabacic@ag04.io");
        message.setTo(to);
        message.setSubject("subject");
        message.setText("txt");
        //emailSender --> javax.mail.AuthenticationFailedException: 535 Authentication Credentials Invalid when i try to save member because authentication dont work.
        //emailSender.send(message);

    }
}