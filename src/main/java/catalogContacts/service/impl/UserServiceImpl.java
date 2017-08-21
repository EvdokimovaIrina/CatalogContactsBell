package catalogContacts.service.impl;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl.DaoUser;
import catalogContacts.model.User;
import catalogContacts.service.UserService;

import java.util.List;

/**
 *
 */
public class UserServiceImpl implements UserService {

    private CrudDAOUser<User> crudDAOUser;

    // Singleton

    private UserServiceImpl() {
        try {
            synchronized (this) {
                crudDAOUser = new DaoUser();
            }
        } catch (DaoException e) {
            crudDAOUser=null;
        }
    }

    public static UserServiceImpl getInstance() {
        return UserServiceImplHolder.instance;
    }

    private static class UserServiceImplHolder {
        private static final UserServiceImpl instance = new UserServiceImpl();
    }

    //////

    public void setUserThread(String login, String password) throws DaoException {
        User user;
        synchronized (this) {
            user = crudDAOUser.authorizationUser(login, password);
        }
        SecurityContextHolder.setLoggedUser(user);
    }

    public void setUserThread(int id) throws DaoException {
        User user;
        synchronized (this) {
            user = crudDAOUser.getObject(id);
        }
        SecurityContextHolder.setLoggedUser(user);
    }

    public int numberOfUsers() throws DaoException {
        int quantity=0;
        synchronized (this) {
            quantity = crudDAOUser.numberOfUsers();
        }
        return quantity;
    }

    public float averageUserContact() throws DaoException {
        float quantity=0;
        synchronized (this) {
            quantity = crudDAOUser.averageUserContact();
        }
        return quantity;
    }

    public float averageUserGroup() throws DaoException {
        float quantity=0;
        synchronized (this) {
            quantity = crudDAOUser.averageUserGroup();
        }
        return quantity;
    }

    public List<User> inactiveUsersList(int n) throws DaoException {
        List<User> userList;
        synchronized (this) {
            userList = crudDAOUser.inactiveUsers(n);
        }
        return userList;
    }

    public List<User> countingUserContact() throws DaoException {
        List<User> userList;
        synchronized (this) {
            userList = crudDAOUser.CountingUserContact();
        }
        return userList;
    }

    public List<User> countingUserGroup() throws DaoException {
        List<User> userList;
        synchronized (this) {
            userList = crudDAOUser.CountingUserGroup();
        }
        return userList;
    }

    public void setCrudDAOUser(CrudDAOUser<User> crudDAOUser) {
        this.crudDAOUser = crudDAOUser;
    }



}
