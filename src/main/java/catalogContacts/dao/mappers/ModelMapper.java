package catalogContacts.dao.mappers;

import catalogContacts.dao.exception.DaoException;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ModelMapper<T> {
    T creatObject(List<Map<String, String>> listMapResulSet) throws DaoException;
    List<T> creatObjectList(List<Map<String, String>> listMapResulSet) throws DaoException;
    T mapToObject(Map<String, String> mapOfList) throws DaoException;
}
