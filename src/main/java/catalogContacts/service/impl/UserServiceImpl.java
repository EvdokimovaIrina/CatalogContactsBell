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
    private static UserServiceImpl instance;
    private CrudDAOUser<User> crudDAOUser;

    // Singleton
    private UserServiceImpl() {

    }

    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }
    //////

    public void setUserThread(String login,String password) throws DaoException {
        User user = crudDAOUser.authorizationUser(login,password);
        SecurityContextHolder.setLoggedUser(user);
    }

    public void setCrudDAOUser(CrudDAOUser<User> crudDAOUser) {
        this.crudDAOUser = crudDAOUser;
    }
}
