package catalogContacts.dao;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

import java.util.List;

/**
 *
 */
public interface CrudDAOUser<U> extends CrudDAO<U> {
    /**
     * Возвращает пользователя с найденным логином и паролем
     *
     *@login логин пользователя
     * @password пароль пользователя
     */
    //void setUserID(int userID);
    U authorizationUser(String login, String password) throws DaoException;

    List<U> userList();
    int numberOfUsers() throws DaoException;
    float averageUserContact() throws DaoException;
    float averageUserGroup() throws DaoException;
    List<User> inactiveUsers(int n) throws DaoException;
    List<User> CountingUserContact() throws DaoException;
    List<User> CountingUserGroup() throws DaoException;
}
