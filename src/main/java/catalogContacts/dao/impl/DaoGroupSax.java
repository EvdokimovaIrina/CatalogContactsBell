package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Group;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DaoGroupSax extends DaoGroup{

    public Group getObject(int id) throws DaoXmlException {
        List<Group> groupList = getAll();
        for (Group group:groupList) {
            if (group.getNumber()==id){
                return group;
            }
        }
        return null;
    }

    public List<Group> getAll() throws DaoXmlException {
        return xmlToListObject();
    }

    public List<Group> findByName(String name) throws DaoXmlException {
        List<Group> groupList = getAll();
        List<Group> groupListReturn = new ArrayList<>();
        for (Group group:groupList) {
            if (group.getName().contains(name)){
                groupListReturn.add(group);
            }
        }
        return groupListReturn;
    }

    public int toFormANewId() throws DaoXmlException {
        List<Group> groupList = getAll();
        int max=0;
        for (Group group:groupList) {
            int number= group.getNumber();
            if (max<number){
                max = number;
            }
        }
        return max+1;
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
