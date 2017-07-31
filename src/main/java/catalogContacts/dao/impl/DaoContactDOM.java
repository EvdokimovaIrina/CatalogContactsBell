package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DaoContactDOM implements CrudDAO<Contact> {
    private final String fileName = "ContactList.xml";

    public void create(Contact object) {

        List<Contact> contactList = xmlToListObject();

        contactList.add(object);

        saveToXML(contactList);
    }

    public void update(Contact object) {

    }

    public void delete(Contact object) {

    }

    public Contact getObject(int id) {
        return null;
    }

    public List<Contact> getAll() {
        return null;
    }

    public Contact getContact() {
        return null;
    }


    public List<Contact> xmlToListObject() {
        List<Contact> contactList = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return contactList;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(file);
            NodeList nodeList = doc.getElementsByTagName("contact");
            contactList = getContactList(nodeList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contactList;
    }

    private List<Contact> getContactList(NodeList nodeList) throws DaoXmlException {
        List<Contact> contactList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Map<String, Node> mapChildrenNode = getChildrenNode(node);

                String idContactStr = getValueFromNode("id", mapChildrenNode);
                int idContact = 0;
                try {
                    idContact = Integer.parseInt(idContactStr);
                } catch (Exception ignored) {
                }

                String nameContact = getValueFromNode("name", mapChildrenNode);
                Node contactListNode = mapChildrenNode.get("contactDetailsList");
                Node groupListNode = mapChildrenNode.get("groupList");
                Contact contact = new Contact(nameContact, idContact);

                if (!(contactListNode == null)) {
                    List<ContactDetails> contactDetailsList = getContactDetailseList(contactListNode);
                    contact.setContactDetailsList(contactDetailsList);
                }

                if (!(groupListNode == null)) {
                    List<Group> getGroupListList = getGroupListList(groupListNode);
                    contact.setGroupList(getGroupListList);
                }
                contactList.add(contact);
            }
        }

        return contactList;
    }

    private List<ContactDetails> getContactDetailseList(Node node) throws DaoXmlException {
        List<ContactDetails> listDetails = new ArrayList<>();
        for (Node childNode = node.getFirstChild(); childNode != null;
             childNode = childNode.getNextSibling()) {
            if (childNode.getNodeName().equals("contactDetails")) {
                Map<String, Node> mapChildrenNode = getChildrenNode(childNode);
                String typeStr = getValueFromNode("type", mapChildrenNode);
                String valueDetails = getValueFromNode("value", mapChildrenNode);

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


    private Map<String, Node> getChildrenNode(Node node) {
        Map<String, Node> mapReturn = new HashMap<>();
        for (Node childNode = node.getFirstChild(); childNode != null;
             childNode = childNode.getNextSibling()) {
            if (childNode instanceof Element) {
                String nameNode = childNode.getNodeName();
                mapReturn.put(nameNode, childNode);
            }
        }

        return mapReturn;
    }

    private List<Group> getGroupListList(Node node) throws DaoXmlException {
        List<Group> listDetails = new ArrayList<>();
        for (Node childNode = node.getFirstChild(); childNode != null;
             childNode = childNode.getNextSibling()) {
            if (childNode.getNodeName().equals("group")) {
                Map<String, Node> mapChildrenNode = getChildrenNode(childNode);
                String nameGroup = getValueFromNode("nameGroup", mapChildrenNode);
                String idGroupStr = getValueFromNode("idGroup", mapChildrenNode);
                int idGroup = 0;
                try {
                    idGroup = Integer.parseInt(idGroupStr);
                } catch (Exception ignored) {
                }
                Group group = new Group(nameGroup, idGroup);
                listDetails.add(group);

            }
        }
        return listDetails;
    }

    private String getValueFromNode(String nameNode, Map<String, Node> mapChildrenNode) {

        Node node = mapChildrenNode.get(nameNode);
        if (!(node == null)) {
            Element element = (Element) node;
            Text text = (Text) element.getFirstChild();
            return text.getData().trim();
        }
        return "";
    }


    public void saveToXML(List<Contact> contactList) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().newDocument();
            Element contactListO = document.createElement("contactList");
            document.appendChild(contactListO);
            for (Contact contact : contactList) {

                Element contactO = document.createElement("contact");
                contactListO.appendChild(contactO);

                Element id = document.createElement("id");
                id.setTextContent(String.valueOf(contact.getNumber()));
                contactO.appendChild(id);

                Element name = document.createElement("name");
                name.setTextContent(contact.getFio());
                contactO.appendChild(name);

                Element contactDetailsList = document.createElement("contactDetailsList");
                contactO.appendChild(contactDetailsList);

                for (ContactDetails contactDetails : contact.getContactDetailsList()) {
                    Element contactDetails1 = document.createElement("contactDetails");
                    contactDetailsList.appendChild(contactDetails1);

                    Element type = document.createElement("type");
                    type.setTextContent(String.valueOf(contactDetails.getType()));
                    contactDetails1.appendChild(type);

                    Element value = document.createElement("value");
                    value.setTextContent(String.valueOf(contactDetails.getValue()));
                    contactDetails1.appendChild(value);
                }

                Element groupList = document.createElement("groupList");
                contactO.appendChild(groupList);

                for (Group group : contact.getGroupList()) {
                    Element group1 = document.createElement("group");
                    groupList.appendChild(group1);

                    Element numberGroup = document.createElement("idGroup");
                    numberGroup.setTextContent(String.valueOf(group.getNumber()));
                    group1.appendChild(numberGroup);

                    Element nameGroup = document.createElement("nameGroup");
                    nameGroup.setTextContent(String.valueOf(group.getName()));
                    group1.appendChild(nameGroup);
                }

            }
            File file = new File(fileName);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(document), new StreamResult(file));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

