package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.Group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ModelMapperGroup implements ModelMapper<Group> {
    public Group creatObject(ResultSet result) throws DaoException {

        try {
            while (result.next()) {

                Group group = new Group(result.getString("group_name"),result.getInt("group_id"));
                return group;
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка получения группы ",e);
        }
        return null;
    }

    public List<Group> creatObjectList(ResultSet result) throws DaoException {
        List<Group> groupList=new ArrayList<>();
        try {
            while (result.next()) {
                Group group = new Group(result.getString("group_name"),result.getInt("group_id"));
                groupList.add(group);
            }
        } catch (SQLException e) {
            throw new DaoException("Ошибка получения групп ",e);
        }
        return groupList;
    }
}
