package catalogContacts.dao.impl;

import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class SurveyReaderSAXGroup extends DefaultHandler {
    private Group group = null;
    private List<Group> groupList = new ArrayList<>();
    private String thisElement = "";

    public List<Group> getGroup(){
        return groupList;
    }
    public SurveyReaderSAXGroup() {
       super();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        thisElement = qName;
        if (qName.equals("group")) {
            group = new Group();
        }
    }

    @Override
    public void endElement(String uri, String name, String qName) {
        if (qName.equals("group")) {
            groupList.add(group);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String str = new String(ch, start, length);
        if (str.contains("\n")){
            return;
        }
        if (thisElement.equals("id")) {
            int id = 0;
            try {
                id = new Integer(str);
            }catch (NumberFormatException e){
                return;
            }
            group.setNumber(id);
        }

        if (thisElement.equals("name")) {
            String name = str;
            group.setName(name);
        }

    }
}

