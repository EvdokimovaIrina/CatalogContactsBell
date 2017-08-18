package catalogContacts.dao.mappers.impl;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class StatisticMapperQuantity implements ModelMapper<Integer> {
    public Integer getObject(List<Map<String, String>> listMapResulSet) throws DaoException {
        for (Map<String, String> mapOfList : listMapResulSet) {
            return mapToObject(mapOfList);
        }
        return null;
    }

    public List<Integer> getListOfObjects(List<Map<String, String>> listMapResulSet) throws DaoException {
        List<Integer> intList = new ArrayList<>();

        for (Map<String, String> mapOfList : listMapResulSet) {
            intList.add(mapToObject(mapOfList));
        }
        return intList;
    }

    public Integer mapToObject(Map<String, String> mapOfList) throws DaoException {
        Integer quantity=null;
        try {
            for (Map.Entry entry : mapOfList.entrySet()) {
                quantity = Integer.parseInt((String) entry.getValue());

            }
        } catch (NumberFormatException | NullPointerException e) {
            throw new DaoException("Ошибка получения данных пользователя" + e.getMessage(), e);
        }
        return quantity;
    }
}
