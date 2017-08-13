package catalogContacts.dao;

import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.User;

import java.util.List;

/**
 *работа с хранилищем данных
 */
public interface CrudDAO<T> {
    /**
     * Запись нового объекта в хранилище
     * @param object объект для записи
     */
    void create(T object) throws DaoException;

    /**
     * Изменение существующего объекта в хранилище
     * @param object объект сведения по которому изменились
     */
    void update(T object) throws DaoException;

    /**
     * Удаление данных по объекту из хранилища
     * @number объект данные по которому нужно удалить
     */
    void delete(int number) throws DaoException;

    /**
     * Получает данные по запрошенному объекту из хранилища
     * @param id номер объекта
     * @return возвращает объект, см указанным типом
     */
    T getObject(int id) throws DaoException;

    /**
     * Получает список всех объектов указанного типа
     *
     * @return список объектов указанного типа
     */
    List<T> getAll() throws DaoException;

    /**
     * Находит объект по заданному имени
     *
     * @name имя объекта
     */
    List<T> findByName(String name) throws DaoException;



}