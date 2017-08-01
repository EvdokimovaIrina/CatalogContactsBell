package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DaoContactSax extends DaoContact {

    @Override
    public Contact getObject(int id) throws DaoXmlException {
        List<Contact> contactList = getAll();
        for (Contact contact:contactList) {
            if (contact.getNumber()==id){
                return contact;
            }
        }
        return null;
    }

    @Override
    public List<Contact> getAll() throws DaoXmlException {

        return xmlToListObject();
    }

    @Override
    public List<Contact> findByName(String name) throws DaoXmlException {
        List<Contact> contactList = getAll();
        List<Contact> contactListReturn = new ArrayList<>();
        for (Contact contact:contactList) {
            if (contact.getFio().contains(name)){
                contactListReturn.add(contact);
            }
        }
        return contactListReturn;
    }

    @Override
    public int toFormANewId() throws DaoXmlException {
        List<Contact> contactList = getAll();
        int max=0;
        for (Contact contact:contactList) {
            int number= contact.getNumber();
            if (max<number){
               max = number;
            }
        }
        return max+1;
    }

    @Override
    List<Contact> xmlToListObject() throws DaoXmlException {
        List<Contact> contactList;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        InputStream xmlData;
        try {
            xmlData = new FileInputStream(fileXml());

            saxParser = factory.newSAXParser();
            SurveyReaderSAXContact surveyReaderSAXContact = new SurveyReaderSAXContact();
            saxParser.parse(xmlData, surveyReaderSAXContact);
            contactList = surveyReaderSAXContact.getContact();

        } catch (Exception e) {
            throw new DaoXmlException("Ошибка обработки файла " + e.getMessage());

        }
        return contactList;
    }


}
