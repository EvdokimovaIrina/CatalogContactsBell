package catalogContacts.dao.factory;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.TypesOfParsers;
import catalogContacts.dao.factory.impl.FactoryDaoDOM;

/**
 *
 */
public class FactoryMethodSelecting {
    public AbstractFactoryDao factoryDao(TypesOfParsers typesOfParsers){
        AbstractFactoryDao factoryDaoSelected = new FactoryDaoDOM();
        return factoryDaoSelected;
    }

}
