package service;

import entity.Attachments;

import java.sql.Timestamp;

public interface AttachmentsService{
    int save(Attachments attachments);

    Attachments findById(int id);

    void deleteByUploaderId(int uploaderId);

    void deleteById(int id);

    Attachments findByUploaderIdAndUploadTime(int i, Timestamp uploadTime);
}