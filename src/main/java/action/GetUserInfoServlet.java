package action;

import entity.User;
import service.UserService;
import service.UserServiceImpl;
import utils.JsonUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = "/getUserInfoServlet")
public class GetUserInfoServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        int id = userService.findIdByUsername(username);
        User user = userService.findUserByIdWithPic(id);

        if (user.getPicture() == null) {
            ServletContext context = req.getServletContext();
            try (InputStream defaultImageStream = context.getResourceAsStream("/image/icon.jpg")) {
                if (defaultImageStream != null) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] data = new byte[4096];
                    int nRead;
                    while ((nRead = defaultImageStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    byte[] imageBytes = buffer.toByteArray();
                    user.setPicture(imageBytes);
                }
            }
        }

        JsonUtils.objectMapper.writeValue(resp.getOutputStream(), user);
    }
}
