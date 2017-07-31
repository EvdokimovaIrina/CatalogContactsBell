package catalogContacts.dao;

import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;

/**
 *работа с хранилищем данных
 */
public interface CrudDAO<T> {
    /**
     * Запись нового объекта в хранилище
     * @param object объект для записи
     */
    void create(T object) throws DaoXmlException;

    /**
     * Изменение существующего объекта в хранилище
     * @param object объект сведения по которому изменились
     */
    void update(T object) throws DaoXmlException;

    /**
     * Удаление данных по объекту из хранилища
     * @number объект данные по которому нужно удалить
     */
    void delete(int number) throws DaoXmlException;

    /**
     * Получает данные по запрошенному объекту из хранилища
     * @param id номер объекта
     * @return возвращает объект, см указанным типом
     */
    T getObject(int id) throws DaoXmlException;

    /**
     * Получает список всех объектов указанного типа
     *
     * @return список объектов указанного типа
     */
    List<T> getAll() throws DaoXmlException;

    /**
     * Находит объект по заданному имени
     *
     * @name имя объекта
     */
    T findTheName(String name);

    int toFormANewId() throws DaoXmlException;;
}
