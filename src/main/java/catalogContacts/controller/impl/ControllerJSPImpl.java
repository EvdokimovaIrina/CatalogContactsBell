package catalogContacts.controller.impl;

import catalogContacts.controller.ControllerJSP;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl.DaoUser;
import catalogContacts.service.UserService;
import catalogContacts.service.impl.UserServiceImpl;

/**
 *
 */
public class ControllerJSPImpl implements ControllerJSP {
    private UserService userService;

    public ControllerJSPImpl() throws DaoException {
        this.userService = UserServiceImpl.getInstance();
        this.userService.setCrudDAOUser(new DaoUser());
    }

    public int numberOfUsers() throws DaoException {

        return userService.numberOfUsers();

    }

}
