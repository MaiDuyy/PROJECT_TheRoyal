package gui.validata;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPAuthEmail {

    private static SMTPAuthEmail instance;
    private Session session;

    private SMTPAuthEmail() {
    }

    public static SMTPAuthEmail getInstance() {
        if (instance == null) {
            instance = new SMTPAuthEmail();
        }
        return instance;
    }

    public void initializeSession(String smtpHost, String smtpPort, String username, String password) {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    public boolean sendOTP(String fromEmail, String toEmail, String otpCode) {
        if (session == null) {
            throw new IllegalStateException("SMTP session is not initialized. Call initializeSession first.");
        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP của bạn");
            message.setText("Xin chào,\n\nMã OTP của bạn là: " + otpCode + "\n\nVui lòng không chia sẻ mã này với bất kỳ ai.\n\nTrân trọng.");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
