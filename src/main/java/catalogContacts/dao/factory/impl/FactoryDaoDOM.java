package catalogContacts.dao.factory.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.factory.AbstractFactoryDao;
import catalogContacts.dao.impl.DaoContactDOM;
import catalogContacts.dao.impl.DaoGroupDOM;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;

/**
 *
 */
public class FactoryDaoDOM implements AbstractFactoryDao{

    public CrudDAO createDao(Class cl) {
        if(cl == Contact.class){
           return  new DaoContactDOM();
        }else if(cl == Group.class){
            return new DaoGroupDOM();
        }
        return null;
    }

}
