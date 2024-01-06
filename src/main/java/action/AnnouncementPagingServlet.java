package action;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Announcement;
import entity.Page;
import service.AnnouncementService;
import service.AnnouncementServiceImpl;
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

@WebServlet(urlPatterns = "/announcementPagingServlet")
public class AnnouncementPagingServlet extends HttpServlet {
    private final AnnouncementService announcementService = new AnnouncementServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = PagingUtils.calculatePageInfo(req, announcementService);
        List<Announcement> announcements = announcementService.findAllAnnouncementsPageable(page.getPageNow(), page.getPageCount());

        Map<String, Object> map = PagingUtils.createResultMap(announcements, page, "announcements");

        String json = JsonUtils.RestultMapToJson(map);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }
}
