package catalogContacts.dao;

import java.util.List;

/**
 *работа с хранилищем данных
 */
public interface CrudDAO<T> {
    /**
     * Запись нового объекта в хранилище
     * @param object объект для записи
     */
    void create(T object);

    /**
     *
     */
    void read();

    /**
     * Изменение существующего объекта в хранилище
     * @param object объект сведения по которому изменились
     */
    void update(T object);

    /**
     * Удаление данных по объекту из хранилища
     * @param object объект данные по которому нужно удалить
     */
    void delete(T object);

    /**
     * Получает данные по запрошенному объекту из хранилища
     * @param id номер объекта
     * @return возвращает объект, см указанным типом
     */
    T getObject(int id);

    /**
     * Получает список всех объектов указанного типа
     *
     * @return список объектов указанного типа
     */
    List<T> getAll();
}
