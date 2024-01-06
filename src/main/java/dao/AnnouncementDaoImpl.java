package dao;

import entity.Announcement;
import utils.JDBCUtiils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDaoImpl implements AnnouncementDao {
    @Override
    public int getTotalRows() {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "Select count(*) from announcement";
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public List<Announcement> findAllAnnouncementsPageable(int pageNow, int pageCount) {
        List<Announcement> announcements = new ArrayList<>();
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from announcement limit ?,?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (pageNow - 1) * pageCount);
            preparedStatement.setInt(2, pageCount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Announcement announcement = new Announcement(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3));
                announcements.add(announcement);
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return announcements;
    }
}
