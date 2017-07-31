package catalogContacts.dao.factory.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.factory.AbstractFactoryDao;
import catalogContacts.dao.impl.DaoContactJackson;
import catalogContacts.dao.impl.DaoGroupJackson;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;

/**
 *
 */
public class FactoryDaoJackson implements AbstractFactoryDao<CrudDAO> {

    public CrudDAO createDao(Class cl) {
        if(cl == Contact.class){
            return  new DaoContactJackson();
        }else if(cl == Group.class){
            return new DaoGroupJackson();
        }
        return null;
    }
}
