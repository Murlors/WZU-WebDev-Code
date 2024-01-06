package action;

import entity.Attachments;
import service.AttachmentsService;
import service.AttachmentsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/getAttachment")
public class GetAttachmentServlet extends HttpServlet {
    private final AttachmentsService attachmentsService = new AttachmentsServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String attachmentIdParam = request.getParameter("attachment_id");
        if (attachmentIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing attachment ID parameter");
            return;
        }
        try {
            int attachmentId = Integer.parseInt(attachmentIdParam);
            Attachments attachments = attachmentsService.findById(attachmentId);
            if (attachments == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Attachment not found");
                return;
            }
            File file = new File(attachments.getFilePath());

            response.setContentType("application/octet-stream");
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid attachment ID");
        }
    }
}
