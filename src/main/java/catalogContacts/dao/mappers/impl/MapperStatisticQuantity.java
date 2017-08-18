package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class MapperStatisticQuantity implements ModelMapper<Float> {
    public Float getObject(List<Map<String, String>> listMapResulSet) throws DaoException {
        for (Map<String, String> mapOfList : listMapResulSet) {
            return mapToObject(mapOfList);
        }
        return null;
    }

    public List<Float> getListOfObjects(List<Map<String, String>> listMapResulSet) throws DaoException {
        List<Float> flList = new ArrayList<>();

        for (Map<String, String> mapOfList : listMapResulSet) {
            flList.add(mapToObject(mapOfList));
        }
        return flList;
    }

    public Float mapToObject(Map<String, String> mapOfList) throws DaoException {
        Float quantity=null;
        try {
            for (Map.Entry entry : mapOfList.entrySet()) {
                quantity = Float.parseFloat((String) entry.getValue());

            }
        } catch (NumberFormatException | NullPointerException e) {
            throw new DaoException("Ошибка получения данных пользователя" + e.getMessage(), e);
        }
        return quantity;
    }
}
