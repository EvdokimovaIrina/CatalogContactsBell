package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ModelMapperUser implements ModelMapper<User>{
    public User creatObject(ResultSet result) throws DaoException {
        try {
            while (result.next()) {

                User user = new User(result.getInt("user_id"),result.getString("user_login"),result.getString("user_password"));
                return user;
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка получения контакта ",e);
        }
        return null;
    }

    public List<User> creatObjectList(ResultSet result) throws DaoException {
        List<User> groupList=new ArrayList<>();
        try {
            while (result.next()) {
                User group = new User(result.getInt("user_id"),result.getString("user_login"),result.getString("user_password"));
                groupList.add(group);
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка получения групп ",e);
        }
        return groupList;
    }


}
