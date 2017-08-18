package catalogContacts.service.impl;

import catalogContacts.context.SecurityContextHolder;
import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;
import catalogContacts.service.UserService;

/**
 *
 */
public class UserServiceImpl implements UserService {

    private CrudDAOUser<User> crudDAOUser;

    // Singleton

    private UserServiceImpl() {
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

    public void setCrudDAOUser(CrudDAOUser<User> crudDAOUser) {
        this.crudDAOUser = crudDAOUser;
    }



}
