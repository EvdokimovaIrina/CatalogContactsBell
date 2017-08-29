package catalogContacts.dao;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

import java.util.List;
import java.util.Map;

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
    int countingUsers() throws DaoException;
    float averageUserContact() throws DaoException;
    float averageUserGroup() throws DaoException;
    List<User> inactiveUsers(int n) throws DaoException;
    List<Map<User, Integer>> CountingUserContact() throws DaoException;
    List<Map<User, Integer>> CountingUserGroup() throws DaoException;
}
