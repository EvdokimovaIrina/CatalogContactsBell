package catalogContacts.dao.factory;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.TypesOfParsers;
import catalogContacts.dao.factory.impl.FactoryDaoDOM;
import catalogContacts.dao.factory.impl.FactoryDaoJackson;
import catalogContacts.dao.factory.impl.FactoryDaoSax;

/**
 *
 */
public class FactoryMethodSelecting {
    public AbstractFactoryDao factoryDao(TypesOfParsers typesOfParsers){
        AbstractFactoryDao factoryDaoSelected =null;
        switch (typesOfParsers){
            case DOM:
                return new FactoryDaoDOM();

            case SAX:
                return new FactoryDaoSax();

            case JACKSON:
                return new FactoryDaoJackson();

        }

        return factoryDaoSelected;
    }

}
