package action;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Email;
import entity.Page;
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
import java.util.List;
import java.util.Map;

import utils.JsonUtils;
import utils.PagingUtils;
@WebServlet(urlPatterns = "/emailPagingServlet")
public class EmailPagingServlet extends HttpServlet {
    private final EmailService emailService = new EmailServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        String recipient = String.valueOf(userService.findIdByUsername(username));

        Page page = PagingUtils.calculatePageInfo(req, emailService, recipient);
        List<Email> emails = emailService.findAllEmailsPageable(page.getPageNow(), page.getPageCount(), recipient);

        Map<String, Object> map = PagingUtils.createResultMap(emails, page, "emails");

        String json = JsonUtils.RestultMapToJson(map);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }
}
