package action;

import entity.User;
import service.UserService;
import service.UserServiceImpl;
import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String providedCaptcha = request.getParameter("captcha");
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);

        String storedCaptcha = (String) request.getSession().getAttribute("captcha");
        request.getSession().removeAttribute("captcha"); // 方便登录
        if (storedCaptcha == null || storedCaptcha.equalsIgnoreCase(providedCaptcha)) {
            if (userService.login(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user);
                session.setAttribute("username", username);
                session.setAttribute("isAuthenticated", true);

                Cookie uCookie = new Cookie("username", username);
                Cookie pCookie = new Cookie("password", password);
                uCookie.setMaxAge(60 * 60 * 24 * 30);
                pCookie.setMaxAge(60 * 60 * 24 * 30);
                response.addCookie(uCookie);
                response.addCookie(pCookie);

                request.setAttribute("username", username);
                String redirectUrl = "/welcome.html";
                Map<String, Object> map = new HashMap<String, Object>() {
                    {
                        put("success", true);
                        put("redirectUrl", redirectUrl);
                    }
                };
                JsonUtils.objectMapper.writeValue(response.getOutputStream(), map);

            } else {
                Map<String, Object> map = new HashMap<String, Object>() {
                    {
                        put("success", false);
                        put("message", "用户名或密码错误");
                    }
                };
                JsonUtils.objectMapper.writeValue(response.getOutputStream(), map);
            }
        } else {
            Map<String, Object> map = new HashMap<String, Object>() {
                {
                    put("success", false);
                    put("message", "验证码错误");
                }
            };
            JsonUtils.objectMapper.writeValue(response.getOutputStream(), map);
        }
    }
}
