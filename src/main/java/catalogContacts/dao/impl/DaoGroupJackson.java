package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Group;

import java.util.List;

/**
 *
 */
public class DaoGroupJackson implements CrudDAO<Group>{

    public void create(Group object) {

    }

    public void update(Group object) {

    }

    public void delete(int number) {

    }

    public Group getObject(int id) {
        return null;
    }

    public List<Group> getAll() {
        return null;
    }

    public Group findTheName(String name) {
        return null;
    }

    public int toFormANewId() throws DaoXmlException {
        return 0;
    }

    public List<Group> xmlToListObject() {
        return null;
    }

    public Group getGroup() {
        return null;
    }
}
