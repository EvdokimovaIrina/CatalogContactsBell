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
public class SurveyReaderSAXContact extends DefaultHandler {
    private Contact contact = null;
    private List<Contact> contactList = new ArrayList<>();
    private String thisElement = "";
    private ContactDetails contactDetails=null;
    private Group group = null;
    private List<ContactDetails> contactDetailsesList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();

    public List<Contact> getContact(){
        return contactList;
    }
    public SurveyReaderSAXContact() {
       super();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        thisElement = qName;
        if (qName.equals("contact")) {
            contact = new Contact();

        }
        if (qName.equals("contactDetailsList")) {
            contactDetails = new ContactDetails();

        }
        if (qName.equals("groupList")) {
            group = new Group();

        }
    }

    @Override
    public void endElement(String uri, String name, String qName) {
        if (qName.equals("contact")) {
            contact.setContactDetailsList(contactDetailsesList);
            contact.setGroupList(groupList);
            contactList.add(contact);

        }
        if (qName.equals("contactDetailsList")) {
            if(!(contactDetails.getValue()==null)){
                contactDetailsesList.add(contactDetails);
            }
        }
        if (qName.equals("groupList")) {
            if(!(group.getName()==null)){
                groupList.add(group);
            }
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
            contact.setNumber(id);
        }

        if (thisElement.equals("name")) {
            String name = str;
            contact.setFio( name);
        }

        if (thisElement.equals("type")) {
            TypeContact typeContact=null;
            try {
                typeContact = TypeContact.valueOf(str);

            }catch (IllegalArgumentException e){
                return;
            }
            contactDetails.setType(typeContact);
        }

        if (thisElement.equals("value")) {
            String value = str;
            contactDetails.setValue(value);

        }

        if (thisElement.equals("idGroup")) {
            int  idGroup= 0;
            try {
                idGroup = new Integer(str);

            }catch (NumberFormatException e){
                return;
            }
            group.setNumber(idGroup);
        }

        if (thisElement.equals("nameGroup")) {
            String nameGroup = str;
            group.setName(nameGroup);
        }

    }
}

