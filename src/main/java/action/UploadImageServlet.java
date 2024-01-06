package action;

import entity.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(urlPatterns = "/uploadImageServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,   // 1 MB
        maxFileSize = 1024 * 1024 * 2,     // 2 MB
        maxRequestSize = 1024 * 1024 * 3   // 3 MB
)
public class UploadImageServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String name = req.getParameter("name");
            int age = Integer.parseInt(req.getParameter("age"));
            String sex = req.getParameter("sex");
            Part filePart = req.getPart("picture");

            if (filePart == null || filePart.getSize() == 0) {
                throw new IllegalArgumentException("No picture provided");
            }
            InputStream pictureStream = filePart.getInputStream();
            try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                byte[] data = new byte[4096];
                int nRead;
                while ((nRead = pictureStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                byte[] picture = buffer.toByteArray();
                User user = new User(id, username, email, name, age, sex, picture);

                userService.updateInformation(user);
            }
            resp.getWriter().write("success");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric input");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}