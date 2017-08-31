package catalogContacts.service.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.Group;
import catalogContacts.service.GroupService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("groupService")
public final class GroupServiceImpl implements GroupService {
    @Autowired
    private CrudDAO<Group> crudDAOGroup;

    private static Logger logger = Logger.getLogger(GroupServiceImpl.class.getName());


    public void addGroup(String name) throws DaoException {
        Group group = new Group(name);
        saveGroup(group);
    }

    public void saveGroup(Group group) throws DaoException {
        synchronized (this) {
            crudDAOGroup.create(group);
        }
    }

    public void deleteGroup(int numberGroup) throws DaoException {

        synchronized (this) {
            crudDAOGroup.delete(numberGroup);
        }
    }

    public void changeGroup(int numberGroup, String value) throws DaoException {
        synchronized (this) {
            Group group = crudDAOGroup.getObject(numberGroup);
            group.setName(value);
            logger.debug("Изменение наименования группы ");
            logger.debug("Данные группы : id = " + numberGroup + ", name = " + group.getName());
            crudDAOGroup.update(group);
            logger.debug("новые данные группы : id = " + numberGroup + ", name = " + value);

        }

    }

    public List<Group> showGroupList() throws DaoException {
        return crudDAOGroup.getAll();

    }


    public Group findByNumber(int number) throws DaoException {
        Group group;
        synchronized (this) {
            group = crudDAOGroup.getObject(number);
        }
        return group;
    }

    public void setCrudDAOGroup(CrudDAO<Group> crudDAOGroup) {
        this.crudDAOGroup = crudDAOGroup;
    }
}
