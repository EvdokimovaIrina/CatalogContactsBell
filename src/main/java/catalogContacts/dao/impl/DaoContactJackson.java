package catalogContacts.dao.impl;

import catalogContacts.dao.entityForJackson.ContactDetailsListJackson;
import catalogContacts.dao.entityForJackson.GroupListJackson;
import catalogContacts.dao.entityForJackson.ContactJackson;
import catalogContacts.dao.entityForJackson.ContactListJackson;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DaoContactJackson extends DaoContact {

    @Override
    public Contact getObject(int id) throws DaoXmlException {
        Contact contact = null;
        ContactListJackson contactListJackson = getContactListJackson();
        for (ContactJackson contactJackson: contactListJackson.getContact()) {
            if (contactJackson.getNumber()==id){
                contact = contactJacksonToContact(contactJackson);
                return contact;
            }
        }
        return contact;
    }

    @Override
    public List<Contact> getAll() throws DaoXmlException {
        List<Contact> contactList = xmlToListObject();

        return contactList;
    }

    @Override
    public List<Contact> findByName(String name) throws DaoXmlException {
        List<Contact> contactList = null;
        ContactListJackson contactListJackson = getContactListJackson();
        for (ContactJackson contactJackson: contactListJackson.getContact()) {
            if (contactJackson.getFio().contains(name)){
                contactList.add(contactJacksonToContact(contactJackson));
            }
        }
        return contactList;
    }

    @Override
    public int toFormANewId() throws DaoXmlException {
        ContactListJackson contactListJackson = getContactListJackson();
        int max = 0;
        for (ContactJackson contactJackson : contactListJackson.getContact()) {
            int id = contactJackson.getNumber();
            if (max < id) {
                max = id;
            }
        }
        return max;
    }

    @Override
    List<Contact> xmlToListObject() throws DaoXmlException {
        List<Contact> contactList = contactListJacksonToContactList(getContactListJackson());
        return contactList;
    }

    private ContactListJackson getContactListJackson() throws DaoXmlException {
        ContactListJackson contactListJackson = null;
        try {
            ObjectMapper objectMapper = new XmlMapper();
            contactListJackson = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8), ContactListJackson.class);

        } catch (IOException | NullPointerException e) {
            throw new DaoXmlException("Ошибка чтения данных");
        }
        return contactListJackson;
    }

    private List<Contact> contactListJacksonToContactList(ContactListJackson contactListMapper) {
        List<Contact> contactList = new ArrayList<>();
        for (ContactJackson contactJackson : contactListMapper.getContact()) {
            contactList.add(contactJacksonToContact(contactJackson));
        }
        return contactList;
    }

    private Contact contactJacksonToContact(ContactJackson contactJackson){
        ContactDetailsListJackson contactDetailsList = contactJackson.getContactDetailsList();
        GroupListJackson contactGroupList = contactJackson.getGroupList();
        Contact contact = new Contact(contactJackson.getFio(), contactJackson.getNumber());
        if (!(contactDetailsList == null)) {
            contact.setContactDetailsList(contactDetailsList.getContactDetails());
        }
        if (!(contactGroupList == null)) {
            contact.setGroupList(contactGroupList.getGroup());
        }
        return contact;
    }

    @Override
    public void create(Contact contact) throws DaoXmlException {

        /*ContactListJackson contactListJackson = getContactListJackson();
        contactListJackson.getContact().add(contactToContactJackson(contact));
        saveListToFile(contactListJackson);*/

        super.create(contact);
    }

    ContactJackson contactToContactJackson(Contact contact) {
        ContactJackson contactJackson = new ContactJackson(contact.getFio(), contact.getNumber());
        List<ContactDetails> contactDetailsList = contact.getContactDetailsList();
        List<Group> contactGroupList = contact.getGroupList();
        if (!(contactDetailsList == null)) {
            contactJackson.setContactDetailsList(new ContactDetailsListJackson(contactDetailsList));
        }
        if (!(contactGroupList == null)) {
            contactJackson.setGroupList(new GroupListJackson(contactGroupList));
        }

        return contactJackson;
    }

    private void saveListToFile(ContactListJackson contactListJackson) throws DaoXmlException {
        XmlMapper mapper = new XmlMapper();
        try {
            String xml = mapper.writeValueAsString(contactListJackson);

            FileWriter writer = new FileWriter("Контакт1.xml", false);

            writer.write(xml);
            writer.flush();

        } catch (IOException e) {
            throw new DaoXmlException("Ошибка сохранения файла");
        }
    }
}
