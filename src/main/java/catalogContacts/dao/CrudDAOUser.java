package catalogContacts.dao;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

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
}
