package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.dao.mappers.ModelMapper;
import catalogContacts.dao.mappers.impl.ModelMapperContact;
import catalogContacts.dao.mappers.impl.ModelMapperContactDetails;
import catalogContacts.dao.mappers.impl.ModelMapperGroup;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.context.SecurityContextHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DaoContact extends DaoParsing implements CrudDAO<Contact> {
    private ModelMapper<Contact> modelMapperContact;
    private ModelMapper<ContactDetails> modelMapperContactDetails;
    private ModelMapper<Group> modelMapperGroup;
    private final String selectInsertContact = "SELECT insertcontact(?,?)";
    private final String selectInsertContactDetails = "SELECT insertcontactdetails(?,?,?)";
    private final String selectInsertGroupInContact = "SELECT insertgroupincontact(?,?)";
    private final String selectChangeContact = "SELECT changecontact(?,?)";
    private final String selectChangeContactDetails = "SELECT changecontactdetails(?,?,?)";
    private final String selectDeleteContact = "SELECT deletecontact(?)";
    private final String selectDeleteContactDetails = "SELECT deletecontactdetails(?)";
    private final String selectDeleteGroupFromContact = "SELECT deletegroupfromcontact(?,?)";
    private final String selectGetContact = "SELECT * FROM getcontact(?)";
    private final String selectGetContactList = "SELECT * FROM getlistcontact(?,?)";
    private final String selectGetContactDetails = "SELECT * FROM getcontactdetails(?)";
    private final String selectGetListContactsGroup = "SELECT * FROM getlistcontactsgroup(?)";
    private final String selectGetListGroupInContact = "SELECT * FROM getlistgroupincontact(?)";

    public DaoContact() throws DaoException {
        super();
        modelMapperContact = new ModelMapperContact();
        modelMapperContactDetails = new ModelMapperContactDetails();
        modelMapperGroup = new ModelMapperGroup();
    }

    public void create(Contact contact) throws DaoException {
        //запишем новый контакт
        executionQuery(selectInsertContact, SecurityContextHolder.getLoggedUser().getId(), contact.getFio());
    }


    public void update(Contact contact) throws DaoException {
        executionQuery(selectChangeContact, SecurityContextHolder.getLoggedUser().getId(), contact.getFio());
        changeContactDetails(contact);
        changeGroupToContact(contact);

    }

    private void changeContactDetails(Contact contact) throws DaoException {
        //получим список контактной информации и сравним его с тем что записано в контакте,
        //если не совпадает, то запишем её в БД
        ResultSet result = null;
        List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
        List<ContactDetails> contactDetailsListBD = getContactDetailsList(contact);
        for (ContactDetails contactDetails : contactDetailsList) {
            boolean isIdFound = false;
            Iterator<ContactDetails> iter = contactDetailsListBD.iterator();
            while (iter.hasNext()) {
                ContactDetails contactDetailsBD = iter.next();
                if (contactDetails.getId() == contactDetailsBD.getId()) {
                    isIdFound = true;
                    if ((contactDetails.getId() != contactDetailsBD.getId() |
                            contactDetails.getId() != contactDetailsBD.getId())) {
                        executionQuery(selectChangeContactDetails, contactDetails.getId(), contactDetails.getType().toString(), contactDetails.getValue());
                    }
                    iter.remove();
                    break;
                }
            }

            if (!(isIdFound)) {
                executionQuery(selectInsertContactDetails, contact.getNumber(), contactDetails.getType().toString(), contactDetails.getValue());
            }
        }

        //те контакты, что остались лишними в листе из БД удалим, т.к. их не нашлось в основном списке и это означает, что их удалили из контакта
        for (ContactDetails contactDetailsBD : contactDetailsListBD) {
            executionQuery(selectDeleteContactDetails, contactDetailsBD.getId());
        }
    }

    private void changeGroupToContact(Contact contact) throws DaoException {
        //получим список контактной информации и сравним его с тем что записано в контакте,
        //если не совпадает, то запишем её в БД
        ResultSet result = null;
        List<Group> groupListBD = getGroupListInContact(contact);
        /*for (Group group : contact.getGroupList()) {
            boolean isIdFound = false;
            Iterator<Group> iter = groupListBD.iterator();
            while (iter.hasNext()) {
                Group groupBD = iter.next();
                if (group.getNumber() == groupBD.getNumber()) {
                    isIdFound = true;
                    iter.remove();
                    break;
                }
            }
            if (!(isIdFound)) {
                executionQuery(selectInsertGroupInContact, contact.getNumber(), group.getNumber());
            }
        }*/
        //те группы, что остались лишними в листе из БД удалим, т.к. их не нашлось в основном списке и это означает, что их удалили из контакта
        for (Group groupBD : groupListBD) {
            executionQuery(selectDeleteGroupFromContact, groupBD.getNumber(), contact.getNumber());
        }
    }

    public void delete(int number) throws DaoException {
        executionQuery(selectDeleteContact, number);
    }

    public Contact getObject(int id) throws DaoException {
        Contact contact = modelMapperContact.getObject(executionQuery(selectGetContact, id));
        contact.setContactDetailsList(getContactDetailsList(contact));
       // contact.setGroupList(getGroupListInContact(contact));
        return contact;
    }

    public List<Contact> getAll() throws DaoException {


        List<Contact> contactList = null;
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            session.beginTransaction().begin();
            Criteria criteria = session.createCriteria(Contact.class);
            contactList = (List<Contact>) criteria.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException("Ошибка получения списка контактов ", e);
        }finally {
            if (session != null) session.close();
        }
        return contactList;

    }

    private List<ContactDetails> getContactDetailsList(Contact contact) throws DaoException {
        return modelMapperContactDetails.getListOfObjects(executionQuery(selectGetContactDetails, contact.getNumber()));
    }

    private List<Group> getGroupListInContact(Contact contact) throws DaoException {
        return modelMapperGroup.getListOfObjects(executionQuery(selectGetListGroupInContact, contact.getNumber()));
    }

    public List<Contact> findByName(String name) throws DaoException {
        return modelMapperContact.getListOfObjects(executionQuery(selectGetContactList, SecurityContextHolder.getLoggedUser().getId(), name));
    }

}
