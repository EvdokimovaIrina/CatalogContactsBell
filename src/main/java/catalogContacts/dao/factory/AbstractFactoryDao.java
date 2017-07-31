package catalogContacts.dao.factory;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.impl.DaoContactDOM;


/**
 * Created by iren on 26.07.2017.
 */
public interface AbstractFactoryDao<T> {
    T createDao(Class cl);

}
