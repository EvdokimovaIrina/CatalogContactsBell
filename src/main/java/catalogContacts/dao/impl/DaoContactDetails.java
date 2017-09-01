package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.model.ContactDetails;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DaoContactDetails extends DaoGeneral implements CrudDAO<ContactDetails> {
    private static Logger logger = Logger.getLogger(DaoContact.class.getName());

    private SessionFactory sessionFactory;

    public DaoContactDetails() throws DaoException {
        super();
    }

    public void create(ContactDetails contactDetails) throws DaoException {
        logger.debug("Добавление новой контактной информации");
        logger.debug("Данные контакта : id = " + contactDetails.getContactByContactId().getNumber() +
                ", name = " + contactDetails.getContactByContactId().getFio());
        logger.debug("Данные контактной информации : id = " + contactDetails.getId() +
                ", type = " + contactDetails.getType() + ", value = " + contactDetails.getValue());
        saveObgectToBD(contactDetails);
    }

    public void update(ContactDetails contactDetails) throws DaoException {
        saveObgectToBD(contactDetails);
    }

    public void delete(int number) throws DaoException {
        ContactDetails contactDetails = getObject(number);
        logger.debug("Добавление новой контактной информации");
        logger.debug("Данные контакта : id = " + contactDetails.getContactByContactId().getNumber() +
                ", name = " + contactDetails.getContactByContactId().getFio());
        logger.debug("Данные контактной информации : id = " + contactDetails.getId() +
                ", type = " + contactDetails.getType() + ", value = " + contactDetails.getValue());
        deleteObgectFromBD(ContactDetails.class, number);
    }

    public ContactDetails getObject(int id) throws DaoException {
        return getObjectFromBDById(ContactDetails.class, id);
    }

    public List<ContactDetails> getAll() throws DaoException {
        return null;
    }

    public List<ContactDetails> findByName(String name) throws DaoException {
        return null;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
