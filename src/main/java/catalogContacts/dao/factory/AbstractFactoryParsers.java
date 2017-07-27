package catalogContacts.dao.factory;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.TypeObject;

/**
 *
 */
public interface AbstractFactoryParsers {
    CrudDAO createDao(TypeObject typeObject);

}
