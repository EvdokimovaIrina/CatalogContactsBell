package catalogContacts.dao.factory;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.TypesOfParsers;
import catalogContacts.dao.factory.impl.FactoryDaoDOM;

/**
 *
 */
public class SelectingAParcer {
    public AbstractFactoryDao<CrudDAO> factoryDao(TypesOfParsers typesOfParsers){
        AbstractFactoryDao<CrudDAO> factoryDaoSelected = new FactoryDaoDOM();
        return factoryDaoSelected;
    }

}
