package catalogContacts.service;

import catalogContacts.dao.CrudDAO;
import catalogContacts.model.Group;

/**
 * Created by iren on 16.07.2017.
 */
public interface GroupService {
    void addGroup(String name);
    void saveGroup(Group group);
    void deleteGroup(int numberGroup);
    void changeGroup(int numberGroup,String value);
    void showGroupList();
    Group findByNumber(int number);
    void setCrudDAOGroup(CrudDAO<Group> crudDAO);
}
