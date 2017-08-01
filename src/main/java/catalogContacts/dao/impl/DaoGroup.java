package catalogContacts.dao.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoXmlException;

import catalogContacts.model.Group;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
abstract class DaoGroup implements CrudDAO<Group>{
     final String fileName = "GroupList.xml";
     XPath xpath;

    DaoGroup() {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        this.xpath = xpath;
    }
    public void create(Group group) throws DaoXmlException {
        try {
            Document doc = getDocumentParse();

            XPathExpression xPathExpression = xpath.compile("/groupList");
            Node nodeGroup = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
            if (nodeGroup instanceof Element) {
                Element element = (Element) nodeGroup;
                saveGroupToXMl(group, doc, element);
                saveDocumentToFile(doc);
            }
        } catch (Exception e) {

            List<Group> groupList = new ArrayList<>();

            groupList.add(group);

            saveToXML(groupList);
        }
    }

    public void update(Group group) throws DaoXmlException {
        Document doc = getDocumentParse();

        try {

            Node nodegroup = getNodeDocByCondition(doc, "/groupList/group[id='" + group.getNumber() + "']");
            if (nodegroup == null) {
                throw new DaoXmlException("Данные не сохранены");
            }
            Element element = (Element) nodegroup;
            doc.removeChild(nodegroup);
            saveGroupToXMl(group, doc, element);
            saveDocumentToFile(doc);
        } catch (TransformerException e) {
            throw new DaoXmlException("Ошибка при сохранении изменений." + e.getMessage());
        }
    }

    public void delete(int number) throws DaoXmlException {
        Document doc = getDocumentParse();
        try {
            Node node = getNodeDocByCondition(doc, "/groupList/group[id='" + number + "']");
            doc.removeChild(node);
            saveDocumentToFile(doc);
        } catch (TransformerException e) {
            throw new DaoXmlException("Ошибка при сохранении изменений." + e.getMessage());
        }
    }

    public Group getObject(int id) throws DaoXmlException {
        return null;
    }

    public List<Group> getAll() throws DaoXmlException {
        return null;
    }

    public List<Group> findByName(String name) {
        return null;
    }

    public int toFormANewId() throws DaoXmlException {
        return 0;
    }

    List<Group> xmlToListObject() throws DaoXmlException {
        List<Group> groupListList = new ArrayList<>();
        return groupListList;
    }
    Node getNodeDocByCondition(Document doc, String expression) throws DaoXmlException {
        try {
            XPathExpression xPathExpression = xpath.compile(expression);
            Node nodeGroup = (Node) xPathExpression.evaluate(doc, XPathConstants.NODE);
            if (nodeGroup instanceof Element) {
                return nodeGroup;
            }

        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Ошибка при получении списока групп." + e.getMessage());

        }

        return null;
    }

    Document getDocumentParse() throws DaoXmlException {
        File file = fileXml();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(file);
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

    private void saveGroupToXMl(Group group, Document document, Element groupListO) {
        Element groupO = document.createElement("group");
        groupListO.appendChild(groupO);

        Element id = document.createElement("id");
        id.setTextContent(String.valueOf(group.getNumber()));
        groupO.appendChild(id);

        Element name = document.createElement("name");
        name.setTextContent(group.getName());
        groupO.appendChild(name);

    }

    void saveDocumentToFile(Document document) throws TransformerException {
        File file = new File(fileName);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(file));
    }

    private void saveToXML(List<Group> groupList) throws DaoXmlException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().newDocument();
            Element groupListO = document.createElement("groupList");
            document.appendChild(groupListO);

            for (Group group : groupList) {
                saveGroupToXMl(group, document, groupListO);
            }
            saveDocumentToFile(document);
        } catch (Exception e) {
            throw new DaoXmlException("Не удалось сохранть список групп");
        }


    }
}
