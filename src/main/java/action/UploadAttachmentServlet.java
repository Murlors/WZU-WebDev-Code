package action;

import entity.Attachments;
import service.AttachmentsService;
import service.AttachmentsServiceImpl;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet("/uploadAttachment")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5,    // 5 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 20   // 20 MB
)
public class UploadAttachmentServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "upload";
    private final AttachmentsService attachmentsService = new AttachmentsServiceImpl();
    private final UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Part filePart = request.getPart("attachment");
            String senderUsername = request.getParameter("sender_username");
            if (filePart == null || senderUsername == null || senderUsername.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "error: Missing required parameters");
                return;
            }
            String fileName = getFileName(filePart);
            String emailId = String.valueOf(userService.findIdByUsername(senderUsername));

            File file = new File(getUploadDirectoryPath(emailId));
            if (!file.exists() && !file.mkdirs()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error: Failed to create upload directory");
                return;
            }

            String filePath = getFilePath(emailId, fileName);
            filePart.write(filePath);

            Attachments attachments = new Attachments();
            attachments.setFilePath(filePath);
            attachments.setUploaderId(Integer.parseInt(emailId));
            attachments.setUploadTime(new Timestamp(new Date().getTime()));

            int attachmentsId = attachmentsService.save(attachments);

            if (attachmentsId > 0) {
                System.out.println("Uploaded Attachment: " + fileName);
                System.out.println("Attachment ID: " + attachmentsId);
                response.getWriter().write(String.valueOf(attachmentsId));
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error: Failed to save attachment information");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "error: Failed to upload attachment");
        }
    }

    private String getUploadDirectoryPath(String emailId) {
        return getServletContext().getRealPath("") + File.separator +
                UPLOAD_DIRECTORY + File.separator + emailId;
    }

    private String getFilePath(String emailId, String fileName) {
        return getUploadDirectoryPath(emailId) + File.separator + fileName;
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
