package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import catalogContacts.model.TypeContact;
import com.sun.org.apache.xpath.internal.jaxp.XPathImpl;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.xpath.*;
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
    private XPath xpath;

    public DaoContactDOM() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        this.xpath = xpath;
    }

    public void create(Contact contact) throws DaoXmlException {
        try {
            Document doc = getDocumentParse();

            XPathExpression xPathExpression = xpath.compile("/contactList");
            Node nodeContact = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
            if (nodeContact instanceof Element) {
                Element element = (Element) nodeContact;
                saveContactToXMl(contact, doc, element);
                saveDocumentToFile(doc);
            }
        } catch (Exception e) {

            List<Contact> contactList = new ArrayList<>();

            contactList.add(contact);

            saveToXML(contactList);
        }
    }


    public void update(Contact contact) throws DaoXmlException {

        Document doc = getDocumentParse();

        try {

            Node nodeContact = getNodeDocByCondition(doc, "/contactList/contact[id='" + contact.getNumber() + "']");
            if (nodeContact == null) {
                throw new DaoXmlException("Данные не сохранены");
            }
            Element element = (Element) nodeContact;
            doc.removeChild(nodeContact);
            saveContactToXMl(contact, doc, element);
            saveDocumentToFile(doc);
        } catch (TransformerException e) {
            throw new DaoXmlException("Ошибка при сохранении изменений." + e.getMessage());
        }


    }


    public void delete(int number) throws DaoXmlException {
        Document doc = getDocumentParse();
        try {
            Node nodeContact = getNodeDocByCondition(doc, "/contactList/contact[id='" + number + "']");
            doc.removeChild(nodeContact);
            saveDocumentToFile(doc);
        } catch (TransformerException e) {
            throw new DaoXmlException("Ошибка при сохранении изменений." + e.getMessage());
        }
    }


    private Node getNodeDocByCondition(Document doc, String expression) throws DaoXmlException {
        try {
            XPathExpression xPathExpression = xpath.compile(expression);
            Node nodeContact = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
            if (nodeContact instanceof Element) {
                return nodeContact;
            }

        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Ошибка при получении списока контактов." + e.getMessage());

        }

        return null;
    }

    private NodeList getNodeListDocByCondition(Document doc, String expression) throws DaoXmlException {
        try {
            XPathExpression xPathExpression = xpath.compile(expression);
            NodeList nodeContact = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);

            return nodeContact;


        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Ошибка при получении списока контактов." + e.getMessage());

        }

    }

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

    public List<Contact> getAll() throws DaoXmlException {
        return xmlToListObject();
    }

    public Contact findTheName(String name) {
        return null;
    }

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


    private File fileXml() throws DaoXmlException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new DaoXmlException("Отсутствует файл.");
        }
        return file;
    }

    private Document getDocumentParse() throws DaoXmlException {
        File file = fileXml();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document doc = db.parse(file);
            return doc;
        } catch (Exception e) {
            throw new DaoXmlException("Ошибка при парсере файла." + e.getMessage());

        }

    }

    private List<Contact> xmlToListObject() throws DaoXmlException {
        List<Contact> contactList = new ArrayList<>();
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


    private void saveToXML(List<Contact> contactList) throws DaoXmlException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().newDocument();
            Element contactListO = document.createElement("contactList");
            document.appendChild(contactListO);

            for (Contact contact : contactList) {
                saveContactToXMl(contact, document, contactListO);
            }

            saveDocumentToFile(document);


        } catch (Exception e) {
            throw new DaoXmlException("Не удалось сохранть список контактов");
        }


    }

    private void saveContactToXMl(Contact contact, Document document, Element contactListO) {
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

        saveContactDetailsToContactXMl(contact, document, contactDetailsList);

        Element groupList = document.createElement("groupList");
        contactO.appendChild(groupList);

        saveGroupToContactXMl(contact, document, groupList);
    }

    private void saveContactDetailsToContactXMl(Contact contact, Document document, Element contactDetailsList) {
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
    }

    private void saveGroupToContactXMl(Contact contact, Document document, Element groupList) {
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

    private void saveDocumentToFile(Document document) throws TransformerException {
        File file = new File(fileName);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(document), new StreamResult(file));

    }
}

