package dao;

import entity.Attachments;
import utils.JDBCUtiils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

public class AttachmentsDaoImpl implements AttachmentsDao {

    @Override
    public int save(Attachments attachments) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "insert into attachments(filepath, uploader_id, upload_time) values(?,?,?)";
        Timestamp timestamp = new Timestamp(new Date().getTime());
        PreparedStatement preparedStatement;
        int attachmentId = 0;
        try {
            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, attachments.getFilePath());
            preparedStatement.setInt(2, attachments.getUploaderId());
            preparedStatement.setTimestamp(3, timestamp);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                attachmentId = resultSet.getInt(1);
            }
            JDBCUtiils.release(connection, preparedStatement, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attachmentId;
    }

    @Override
    public Attachments findById(int id) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from attachments where id = ?";
        PreparedStatement preparedStatement;
        Attachments attachments = new Attachments();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                attachments.setId(resultSet.getInt(1));
                attachments.setFilePath(resultSet.getString(2));
                attachments.setUploaderId(resultSet.getInt(3));
                attachments.setUploadTime(resultSet.getTimestamp(4));
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attachments;
    }

    @Override
    public void deleteByUploaderId(int uploaderId) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "delete from attachments where uploader_id = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uploaderId);
            preparedStatement.executeUpdate();
            JDBCUtiils.release(connection, preparedStatement, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(int id) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "delete from attachments where id = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            JDBCUtiils.release(connection, preparedStatement, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Attachments findByUploaderIdAndUploadTime(int i, Timestamp uploadTime) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from attachments where uploader_id = ? and upload_time = ?";
        PreparedStatement preparedStatement;
        Attachments attachments = new Attachments();
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, i);
//            preparedStatement.setTimestamp(2, uploadTime);
            preparedStatement.setString(2, uploadTime.toLocaleString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                attachments.setId(resultSet.getInt(1));
                attachments.setFilePath(resultSet.getString(2));
                attachments.setUploaderId(resultSet.getInt(3));
                attachments.setUploadTime(resultSet.getTimestamp(4));
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return attachments;
    }
}
