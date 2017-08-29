package catalogContacts.service;

import catalogContacts.dao.CrudDAOUser;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface UserService {
    void setUserThread(String login,String password) throws DaoException;
    void setUserThread(int id) throws DaoException;
    void setCrudDAOUser(CrudDAOUser<User> crudDAOUser);
    int countingUsers() throws DaoException;
    float averageUserContact() throws DaoException;
    float averageUserGroup() throws DaoException;
    List<User> inactiveUsersList(int n) throws DaoException;
    List<Map<User, Integer>> countingUserContact() throws DaoException;
    List<Map<User, Integer>> countingUserGroup() throws DaoException;
}
