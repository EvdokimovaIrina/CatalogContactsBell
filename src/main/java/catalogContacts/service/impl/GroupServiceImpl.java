package catalogContacts.service.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.impl.DaoGroup;

import catalogContacts.model.Group;

import catalogContacts.service.GroupService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public final class GroupServiceImpl implements GroupService {
    private static GroupServiceImpl instance;
    private CrudDAO<Group> crudDAOGroup;
    private static Logger logger = Logger.getLogger(GroupServiceImpl.class.getName());

    // Singleton

    private GroupServiceImpl() {
        try {
            crudDAOGroup = new DaoGroup();
        } catch (DaoException e) {
            crudDAOGroup = null;
        }
    }

    public static GroupServiceImpl getInstance() {
        return GroupServiceImplHolder.instance;
    }

    private static class GroupServiceImplHolder {
        private static final GroupServiceImpl instance = new GroupServiceImpl();
    }

    //////


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
            logger.info("Изменение наименования группы ");
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
