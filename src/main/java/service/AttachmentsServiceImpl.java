package service;

import dao.AttachmentsDao;
import dao.AttachmentsDaoImpl;
import entity.Attachments;

import java.sql.Timestamp;

public class AttachmentsServiceImpl implements AttachmentsService {
    private final AttachmentsDao attachmentsDao = new AttachmentsDaoImpl();

    @Override
    public int save(Attachments attachments) {
        return attachmentsDao.save(attachments);
    }

    @Override
    public Attachments findById(int id) {
        return attachmentsDao.findById(id);
    }

    @Override
    public void deleteByUploaderId(int uploaderId) {
        attachmentsDao.deleteByUploaderId(uploaderId);
    }

    @Override
    public void deleteById(int id) {
        attachmentsDao.deleteById(id);
    }

    @Override
    public Attachments findByUploaderIdAndUploadTime(int i, Timestamp uploadTime) {
        return attachmentsDao.findByUploaderIdAndUploadTime(i, uploadTime);
    }
}
