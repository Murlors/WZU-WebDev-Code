package service;

import entity.User;

import java.util.List;

public interface UserService extends PagingService {
    List<User> findAllUsers();

    boolean login(User user);

    User findUserById(int id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUserById(int id);

    List<User> findAllUsersPageable(int pageNow, int pageCount);

    int getTotalRows(String condition);

    boolean findUserByName(String username);

    int findIdByUsername(String username);

    void updateInformation(User user);

    User findUserByIdWithPic(int id);
}
