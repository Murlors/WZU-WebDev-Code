package service;

import dao.UserDao;
import dao.UserDaoImpl;
import entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> findAllUsers() {
        return userDao.findUsers();
    }

    @Override
    public boolean login(User user) {
        return userDao.userLogin(user);
    }

    @Override
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }

    @Override
    public List<User> findAllUsersPageable(int pageNow, int pageCount) {
        return userDao.findAllUsersPageable(pageNow, pageCount);
    }

    @Override
    public int getTotalRows(String condition) {
        return userDao.getTotalRows();
    }

    @Override
    public boolean findUserByName(String username) {
        return userDao.findUserByName(username);
    }

    @Override
    public int findIdByUsername(String username) {
        return userDao.findIdByUsername(username);
    }

    @Override
    public void updateInformation(User user) {
        userDao.updateInformation(user);
    }

    @Override
    public User findUserByIdWithPic(int id) {
        return userDao.findUserByIdWithPic(id);
    }
}