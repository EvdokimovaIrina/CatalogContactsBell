package catalogContacts.dao.impl;

import catalogContacts.dao.exception.DaoXmlException;
import catalogContacts.model.Group;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DaoGroupDOM extends DaoGroup {

    private NodeList getNodeListDocByCondition(Document doc, String expression) throws DaoXmlException {
        try {
            XPathExpression xPathExpression = xpath.compile(expression);
            NodeList node = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);

            return node;

        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Ошибка при получении списока групп." + e.getMessage());
        }

    }

    public Group getObject(int id) throws DaoXmlException {
        Document doc = getDocumentParse();
        Node node = getNodeDocByCondition(doc, "/groupList/group[id='" + id + "']");
        List<Group> groupList = null;
        groupList = getGroupList((NodeList) node);
        if (groupList.size() > 0) {
            return groupList.get(0);
        }
        return null;
    }

    public List<Group> getAll() throws DaoXmlException {
        return xmlToListObject();
    }

    public int toFormANewId() throws DaoXmlException {
        Document doc;
        doc = getDocumentParse();
        NodeList nodeList = getNodeListDocByCondition(doc, "//idGroup");
        int max = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            try {
                int value = Integer.parseInt(nodeList.item(i).getTextContent());
                max = max < value ? value : max;

            } catch (Exception ignored) {
            }

        }
        return max + 1;
    }

    public List<Group> xmlToListObject() throws DaoXmlException {
        List<Group> groupList;
        Document doc = getDocumentParse();

        try {
            XPathExpression xPathExpression = xpath.compile("/groupList/group");
            NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
            groupList = getGroupList(nodeList);
            return groupList;
        } catch (XPathExpressionException e) {
            throw new DaoXmlException("Ошибка при обработке списока контактов." + e.getMessage());
        }
    }

    private List<Group> getGroupList(NodeList nodeList) throws DaoXmlException {
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node instanceof Element) {

                String idStr = null;
                try {
                    idStr = (String) xpath.compile("./idGroup").evaluate(node, XPathConstants.STRING);
                    int id = 0;
                    try {
                        id = Integer.parseInt(idStr);
                    } catch (Exception ignored) {
                    }
                    String name = (String) xpath.compile("./nameGroup").evaluate(node, XPathConstants.STRING);
                    Group group = new Group(name, id);
                    groupList.add(group);
                } catch (XPathExpressionException e) {
                    throw new DaoXmlException("Ошибка получения списка групп");
                }
            }
        }

        return groupList;
    }
}
