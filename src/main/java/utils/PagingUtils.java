package utils;

import entity.Page;
import service.PagingService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PagingUtils {
    public static Page calculatePageInfo(HttpServletRequest req, PagingService pagingService) {
        return calculatePageInfo(req, pagingService, "");
    }

    public static Page calculatePageInfo(HttpServletRequest req, PagingService pagingService, String condition) {
        String pageInfo = req.getParameter("pageNow");
        int pageNow = (pageInfo != null) ? Integer.parseInt(pageInfo) : 1;
        int pageCount = 10;
        int totalRows = pagingService.getTotalRows(condition);

        int totalPages = (totalRows % pageCount == 0) ? (totalRows / pageCount) : (totalRows / pageCount + 1);

        pageNow = Math.min(Math.max(pageNow, 1), totalPages);
        return new Page(pageNow, pageCount, totalRows, totalPages);
    }

    public static <T> Map<String, Object> createResultMap(List<T> list, Page page, String type) {
        return new HashMap<String, Object>() {{
            put(type, list);
            put("page", page);
        }};
    }
}
