package dao;

import entity.Email;
import utils.JDBCUtiils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDaoImpl implements EmailDao {

    @Override
    public void addEmail(Email email) {
        try (Connection conn = JDBCUtiils.getConnection()) {
            String sql = "insert into email(sender, recipient, subject, content, attachment_id) values(?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, email.getSender());
                preparedStatement.setString(2, email.getRecipient());
                preparedStatement.setString(3, email.getSubject());
                preparedStatement.setString(4, email.getContent());
                if (email.getAttachmentId() != -1) {
                    preparedStatement.setInt(5, email.getAttachmentId());
                } else {
                    preparedStatement.setNull(5, Types.INTEGER);
                }
                preparedStatement.executeUpdate();
                JDBCUtiils.release(conn, preparedStatement, null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Email> findEmailByRecipient(String recipient) {
        List<Email> emails = new ArrayList<>();
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from email_view where recipient=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipient);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Email email = new Email(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getString(8));
                emails.add(email);
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emails;
    }

    @Override
    public List<Email> findAllEmailByRecipient(int pageNow, int pageCount, String recipient) {
        List<Email> emails = new ArrayList<>();
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from email_view where recipient=? limit ?,?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipient);
            preparedStatement.setInt(2, (pageNow - 1) * pageCount);
            preparedStatement.setInt(3, pageCount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Email email = new Email(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getString(8));
                emails.add(email);
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return emails;
    }

    @Override
    public int getTotalRows(String recipient) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "Select count(*) from email where recipient=?";
        PreparedStatement preparedStatement;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, recipient);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
