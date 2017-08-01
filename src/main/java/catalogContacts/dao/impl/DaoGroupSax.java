package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import catalogContacts.model.Group;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 *
 */
public class DaoGroupSax extends DaoGroup{

    public Group getObject(int id) {
        return null;
    }

    public List<Group> getAll() {
        return null;
    }

    public List<Group> findByName(String name) {
        return null;
    }

    public int toFormANewId() throws DaoXmlException {
        return 0;
    }

    public List<Group> xmlToListObject() throws DaoXmlException {
        List<Group> groupList;
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            InputStream xmlData = new FileInputStream(fileXml());

            SAXParser saxParser = factory.newSAXParser();
            SurveyReaderSAXGroup surveyReaderSAXGroup = new SurveyReaderSAXGroup();
            saxParser.parse(xmlData, surveyReaderSAXGroup);
            groupList = surveyReaderSAXGroup.getGroup();

        } catch (Exception e) {
            throw new DaoXmlException("Ошибка обработки файла " + e.getMessage());

        }
        return groupList;
    }
}
