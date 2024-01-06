package dao;

import entity.Attachments;

import java.sql.Timestamp;

public interface AttachmentsDao {
    int save(Attachments attachments);

    Attachments findById(int id);

    void deleteByUploaderId(int uploaderId);
    void deleteById(int id);

    Attachments findByUploaderIdAndUploadTime(int i, Timestamp uploadTime);
}
