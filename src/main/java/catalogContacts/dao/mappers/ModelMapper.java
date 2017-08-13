package catalogContacts.dao.mappers;

import catalogContacts.dao.exception.DaoException;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 */
public interface ModelMapper<T> {
    T creatObject(ResultSet result) throws DaoException;
    List<T> creatObjectList(ResultSet result) throws DaoException;

}
