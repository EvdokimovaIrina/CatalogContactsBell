package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class ModelMapperGroup implements ModelMapper<Group> {

    public Group getObject(List<Map<String, String>> listMapResulSet) throws DaoException {
        for (Map<String, String> mapOfList : listMapResulSet) {
            return mapToObject(mapOfList);
        }
        return null;
    }

    public List<Group> getListOfObjects(List<Map<String, String>> listMapResulSet) throws DaoException {
        List<Group> groupList = new ArrayList<>();
        for (Map<String, String> mapOfList : listMapResulSet) {
            groupList.add(mapToObject(mapOfList));
        }
        return groupList;
    }

    public Group mapToObject(Map<String, String> mapOfList) throws DaoException {
        try {
            String name = mapOfList.get("group_name");
            int id = Integer.parseInt(mapOfList.get("group_id"));
            Group group = new Group(name, id);
            return group;
        } catch (NumberFormatException | NullPointerException e) {
            throw new DaoException("Ошибка получения данных группы " + e.getMessage(), e);
        }
    }
}
