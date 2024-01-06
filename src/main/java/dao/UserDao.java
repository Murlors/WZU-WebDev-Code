package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    boolean userLogin(User user);

    List<User> findUsers();

    User findUserById(int id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUserById(int id);

    List<User> findAllUsersPageable(int pageNow, int pageCount);

    int getTotalRows();

    boolean findUserByName(String username);

    int findIdByUsername(String username);

    void updateInformation(User user);

    User findUserByIdWithPic(int id);
}
