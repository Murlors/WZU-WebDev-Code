package dao;

import entity.User;
import utils.JDBCUtiils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean userLogin(User user) {
        Connection conn = JDBCUtiils.getConnection();
        String sql = "select * from user where username=? and password=?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> findUsers() {
        List<User> users = new ArrayList<User>();
        Connection conn = JDBCUtiils.getConnection();
        String sql = "select * from user";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5));
                users.add(user);
            }
            JDBCUtiils.release(conn, pstmt, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findUserById(int id) {
        Connection conn = JDBCUtiils.getConnection();
        String sql = "select * from user where id=?";
        PreparedStatement preparedStatement;
        User user = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5));
            }
            JDBCUtiils.release(conn, preparedStatement, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "insert into user(username,password,email) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
            JDBCUtiils.release(connection, preparedStatement, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "update user set username=?,password=?,email=? where id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
            JDBCUtiils.release(connection, preparedStatement, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserById(int id) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "delete from user where id=?";
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
    public List<User> findAllUsersPageable(int pageNow, int pageCount) {
        List<User> users = new ArrayList<>();
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from user limit ?,?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, (pageNow - 1) * pageCount);
            preparedStatement.setInt(2, pageCount);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5));
                users.add(user);
            }
            JDBCUtiils.release(connection, preparedStatement, rs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public int getTotalRows() {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "Select count(*) from user";
        PreparedStatement preparedStatement;
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
    public boolean findUserByName(String username) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from user where username=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JDBCUtiils.release(connection, preparedStatement, resultSet);
                return true;
            }
            JDBCUtiils.release(connection, preparedStatement, resultSet);
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findIdByUsername(String username) {
        int id = 0;
        Connection connection = JDBCUtiils.getConnection();
        String sql = "select * from user where username=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public void updateInformation(User user) {
        Connection connection = JDBCUtiils.getConnection();
        String sql = "update user set name=?,age=?,sex=?,picture=? where id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.setString(3, user.getSex());
            preparedStatement.setBytes(4, user.getPicture());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
            JDBCUtiils.release(connection, preparedStatement, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserByIdWithPic(int id) {
        Connection conn = JDBCUtiils.getConnection();
        String sql = "select * from user where id=?";
        PreparedStatement preparedStatement;
        User user = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(4), resultSet.getString(5),
                        resultSet.getInt(6), resultSet.getString(7),
                        resultSet.getBytes(8));
            }
            JDBCUtiils.release(conn, preparedStatement, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
