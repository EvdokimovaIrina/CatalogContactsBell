package catalogContacts.dao.mappers;

import java.sql.ResultSet;

/**
 *
 */
public interface ModelMapper<T> {
    T creatObject(ResultSet result);

}
