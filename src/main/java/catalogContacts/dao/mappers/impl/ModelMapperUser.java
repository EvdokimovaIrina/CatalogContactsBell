package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ModelMapperUser implements ModelMapper<User>{

    public User getObject(List<Map<String, String>> listMapResulSet) throws DaoException {
        for (Map<String, String> mapOfList : listMapResulSet) {
            return mapToObject(mapOfList);
        }
        return null;
    }

    public List<User> getListOfObjects(List<Map<String, String>> listMapResulSet) throws DaoException {
        List<User> contactList = new ArrayList<>();

        for (Map<String, String> mapOfList : listMapResulSet) {
            contactList.add(mapToObject(mapOfList));
        }
        return contactList;
    }

    public User mapToObject(Map<String, String> mapOfList) throws DaoException {
        try {
            int id = Integer.parseInt(mapOfList.get("user_id"));
            String login = mapOfList.get("user_login");
            String password = mapOfList.get("user_password");
            User user = new User(id,login,password);
            return user;
        } catch (NumberFormatException | NullPointerException e) {
            throw new DaoException("Ошибка получения данных пользователя" + e.getMessage(), e);
        }
    }
}
