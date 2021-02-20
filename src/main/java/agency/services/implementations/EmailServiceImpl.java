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


    //I get javax.mail.AuthenticationFailedException: 535 Authentication Credentials Invalid when i try to save member because authentication on given data don't work.
    // I make gmail acc for this occasion username:agencyjobapplication44 pass: thepassword04 so you can try with this

    @Override
    public void sendEmailToMember(String to) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("juricabacic@ag04.io");
        message.setTo(to);
        message.setSubject("subject");
        message.setText("You are assigned on heist");
        //this service is implemented in three other service so i need to disable this to make them work.
        //emailSender.send(message);

    }
}