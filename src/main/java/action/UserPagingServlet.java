package action;

import entity.Page;
import entity.User;
import service.UserService;
import service.UserServiceImpl;
import utils.JsonUtils;
import utils.PagingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/userPaging")
public class UserPagingServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = PagingUtils.calculatePageInfo(req, userService);
        List<User> users = userService.findAllUsersPageable(page.getPageNow(), page.getPageCount());

        Map<String, Object> map = PagingUtils.createResultMap(users, page, "users");

        String json = JsonUtils.RestultMapToJson(map);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }
}
