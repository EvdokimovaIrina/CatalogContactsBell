package catalogContacts.service;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Group;

import java.util.List;

/**
 * Created by iren on 16.07.2017.
 */
public interface GroupService {
    void addGroup(String name);
    void saveGroup(Group group);
    void deleteGroup(int numberGroup);
    void changeGroup(int numberGroup,String value);
    List<Group> showGroupList() throws DaoException;
    Group findByNumber(int number);
    void setCrudDAOGroup(CrudDAO<Group> crudDAO);
}
