package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DaoContactSax implements CrudDAO<Contact> {

    private final String fileName = "ContactList.xml";

    public void create(Contact object) {

    }

    public void update(Contact object) {

    }

    public void delete(int number) {

    }

    public Contact getObject(int id) {
        return null;
    }

    public List<Contact> getAll() throws DaoXmlException {

        return getListSAXParser();
    }

    public Contact findTheName(String name) {
        return null;
    }

    public int toFormANewId() throws DaoXmlException {
        return 0;
    }

    private List<Contact> getListSAXParser() throws DaoXmlException {
        List<Contact> contactList = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser  = null;
        InputStream xmlData = null;
        try
        {
            xmlData = new FileInputStream(fileXml());

            saxParser  = factory.newSAXParser();
            SurveyReaderSAXContact surveyReaderSAXContact= new SurveyReaderSAXContact();
            saxParser.parse(xmlData,surveyReaderSAXContact);
            contactList = surveyReaderSAXContact.getContact();

        } catch (Exception e)
        {
            throw new DaoXmlException("Ошибка обработки файла "+e.getMessage());

        }
        return contactList ;
    }

    private File fileXml() throws DaoXmlException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new DaoXmlException("Отсутствует файл.");
        }
        return file;
    }

}
