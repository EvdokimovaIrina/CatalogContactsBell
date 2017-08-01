package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Contact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Group;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public abstract class DaoContact implements CrudDAO<Contact> {
    protected final String fileName = "ContactList.xml";
    protected XPath xpath;

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

    Node getNodeDocByCondition(Document doc, String expression) throws DaoXmlException {
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

    public Contact getObject(int id) throws DaoXmlException {
        return null;
    }

    public List<Contact> getAll() throws DaoXmlException {
        return null;
    }

    public List<Contact> findByName(String name) throws DaoXmlException {
        return null;
    }

    public int toFormANewId() throws DaoXmlException {
        return 0;
    }

    List<Contact> xmlToListObject() throws DaoXmlException {
        List<Contact> contactList = new ArrayList<>();
        return contactList;
    }

    DaoContact() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        this.xpath = xpath;
    }

    Document getDocumentParse() throws DaoXmlException {
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

    File fileXml() throws DaoXmlException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new DaoXmlException("Отсутствует файл.");
        }
        return file;
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

    void saveDocumentToFile(Document document) throws TransformerException {
        File file = new File(fileName);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(document), new StreamResult(file));

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
}
