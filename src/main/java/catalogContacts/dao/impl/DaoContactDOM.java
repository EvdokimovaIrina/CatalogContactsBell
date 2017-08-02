package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;
import org.w3c.dom.*;
import javax.xml.xpath.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class DaoContactDOM extends DaoContact {

    private NodeList getNodeListDocByCondition(Document doc, String expression) throws DaoXmlException {
        try {
            XPathExpression xPathExpression = xpath.compile(expression);
            NodeList nodeContact = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);

            return nodeContact;

        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Ошибка при получении списока контактов." + e.getMessage());

        }
    }

    @Override
    public Contact getObject(int id) throws DaoXmlException {
        Document doc = getDocumentParse();
        Node nodeContact = getNodeDocByCondition(doc, "/contactList/contact[id='" + id + "']");
        List<Contact> contactList = null;
        try {
            contactList = getContactList((NodeList) nodeContact);
            if (contactList.size() > 0) {
                return contactList.get(0);
            }
        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Не найден");
        }


        return null;
    }

    @Override
    public List<Contact> getAll() throws DaoXmlException {
        return xmlToListObject();
    }

    @Override
    public List<Contact> findByName(String name) throws DaoXmlException {
        Document doc = getDocumentParse();
        NodeList nodeContactList = getNodeListDocByCondition(doc, "/contactList/contact[contains(name, '"+name+"')]");
        List<Contact> contactList;
        try {
            contactList = getContactList(nodeContactList);

            return contactList;

        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Не найден");
        }

    }

    @Override
    public int toFormANewId() throws DaoXmlException {
        Document doc = getDocumentParse();
        NodeList nodeContactList = getNodeListDocByCondition(doc, "//id");
        int max = 0;
        for (int i = 0; i < nodeContactList.getLength(); i++) {
            try {
                int value = Integer.parseInt(nodeContactList.item(i).getTextContent());
                max = max < value ? value : max;

            } catch (Exception ignored) {
            }

        }
        return max + 1;
    }

    @Override
    List<Contact> xmlToListObject() throws DaoXmlException {
        List<Contact> contactList;
        Document doc = getDocumentParse();

        try {
            XPathExpression xPathExpression = xpath.compile("/contactList/contact");
            NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            contactList = getContactList(nodeList);
            return contactList;
        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Ошибка при обработке списока контактов." + e.getMessage());
        }
    }


    private List<Contact> getContactList(NodeList nodeList) throws DaoXmlException, XPathExpressionException {
        List<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {

                String idContactStr = (String) xpath.compile("./id").evaluate(node, XPathConstants.STRING);

                int idContact = 0;
                try {
                    idContact = Integer.parseInt(idContactStr);
                } catch (Exception ignored) {
                }
                String nameContact = (String) xpath.compile("./name").evaluate(node, XPathConstants.STRING);
                NodeList contactListNode = (NodeList) xpath.compile("./contactDetailsList/contactDetails").evaluate(node, XPathConstants.NODESET);
                NodeList groupListNode = (NodeList) xpath.compile("./groupList/group").evaluate(node, XPathConstants.NODESET);

                Contact contact = new Contact(nameContact, idContact);

                List<ContactDetails> contactDetailsList = getContactDetailseList(contactListNode);
                contact.setContactDetailsList(contactDetailsList);

                List<Group> getGroupListList = getGroupListList(groupListNode);
                contact.setGroupList(getGroupListList);

                contactList.add(contact);
            }
        }

        return contactList;
    }


    private List<ContactDetails> getContactDetailseList(NodeList nodeList) throws DaoXmlException, XPathExpressionException {
        List<ContactDetails> listDetails = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                String typeStr = (String) xpath.compile("./type").evaluate(node, XPathConstants.STRING);
                String valueDetails = (String) xpath.compile("./value").evaluate(node, XPathConstants.STRING);
                try {
                    TypeContact typeDetails = TypeContact.valueOf(typeStr);
                    ContactDetails contactDetails = new ContactDetails(typeDetails, valueDetails);
                    listDetails.add(contactDetails);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        return listDetails;

    }

    private List<Group> getGroupListList(NodeList nodeList) throws DaoXmlException, XPathExpressionException {
        List<Group> listDetails = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                String idGroupStr = (String) xpath.compile("./idGroup").evaluate(node, XPathConstants.STRING);
                String nameGroup = (String) xpath.compile("./nameGroup").evaluate(node, XPathConstants.STRING);

                try {
                    int idgroup = Integer.parseInt(idGroupStr);
                    Group group = new Group(nameGroup, idgroup);
                    listDetails.add(group);
                } catch (Exception ignored) {
                }
            }
        }
        return listDetails;

    }
}

