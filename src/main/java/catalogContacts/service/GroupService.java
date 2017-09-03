package catalogContacts.service;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Group;

import java.util.List;

/**
 * Created by iren on 16.07.2017.
 */
public interface GroupService {
    Group addGroup(String name) throws DaoException;
    void saveGroup(Group group) throws DaoException;
    void deleteGroup(int numberGroup) throws DaoException;
    Group changeGroup(int numberGroup,String value) throws DaoException;
    List<Group> showGroupList() throws DaoException;
    Group findByNumber(int number) throws DaoException;
    void setCrudDAOGroup(CrudDAO<Group> crudDAO);
}
