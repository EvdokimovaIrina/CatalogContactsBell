package catalogContacts.dao.factory.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.factory.AbstractFactoryDao;
import catalogContacts.dao.impl.DaoContactSax;
import catalogContacts.dao.impl.DaoGroupSax;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;

/**
 *
 */
public class FactoryDaoSax implements AbstractFactoryDao{

    public CrudDAO createDao(Class cl) {
        if(cl == Contact.class){
            return  new DaoContactSax();
        }else if(cl == Group.class){
            return new DaoGroupSax();
        }
        return null;
    }
}
