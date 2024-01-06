package action;

import utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/getInitialData")
public class GetInitialDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        int count = (int) req.getSession().getServletContext().getAttribute("count");

        Map<String, Object> map = new HashMap<>();
        map.put("applicationCount", count);
        map.put("username", username);

        JsonUtils.objectMapper.writeValue(resp.getOutputStream(), map);
    }
}
