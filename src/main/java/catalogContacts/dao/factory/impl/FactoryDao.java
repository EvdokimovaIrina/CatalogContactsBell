package catalogContacts.dao.factory.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.TypeObject;
import catalogContacts.dao.TypesOfParsers;
import catalogContacts.dao.factory.AbstractFactoryDao;
import catalogContacts.dao.factory.AbstractFactoryParsers;

/**
 *
 */
public class FactoryDao implements AbstractFactoryDao{
    private TypesOfParsers typesOfParsers;
    public CrudDAO createDao(TypeObject typeObject) {
        AbstractFactoryParsers factoryDaoParsers = null;
        switch (typesOfParsers) {
            case DOM:
                factoryDaoParsers = new FactoryDaoDOM();
                break;
            case SAX:
                factoryDaoParsers = new FactoryDaoSax();
            case JACKSON:
                factoryDaoParsers = new FactoryDaoSax();
        }


        return factoryDaoParsers.createDao(typeObject);
    }

    public void setTypesOfParsers(TypesOfParsers typesOfParsers) {
        this.typesOfParsers = typesOfParsers;
    }
}
