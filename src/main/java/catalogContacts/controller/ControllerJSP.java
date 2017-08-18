package catalogContacts.controller;

import catalogContacts.dao.exception.DaoException;

/**
 *
 */
public interface ControllerJSP {
    int numberOfUsers() throws DaoException;
}
