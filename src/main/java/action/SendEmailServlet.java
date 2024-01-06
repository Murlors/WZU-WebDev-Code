package action;

import entity.Email;
import service.EmailService;
import service.EmailServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/sendEmailServlet")
public class SendEmailServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    EmailService emailService = new EmailServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sender = req.getParameter("sender");
        sender = String.valueOf(userService.findIdByUsername(sender));
        String recipient = req.getParameter("recipient");
        String attachmentIdParam = req.getParameter("attachment_id");

        int attachmentId = -1;

        if (attachmentIdParam != null && !attachmentIdParam.isEmpty()) {
            attachmentId = Integer.parseInt(attachmentIdParam);
        }
        if (userService.findUserByName(recipient)) {
            recipient = String.valueOf(userService.findIdByUsername(recipient));
            String subject = req.getParameter("subject");
            String content = req.getParameter("content");

            Email email = new Email();
            email.setSender(sender);
            email.setRecipient(recipient);
            email.setSubject(subject);
            email.setContent(content);
            email.setAttachmentId(attachmentId);

            emailService.addEmail(email);
        }
    }
}
