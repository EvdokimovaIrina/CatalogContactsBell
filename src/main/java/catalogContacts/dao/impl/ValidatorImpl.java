package catalogContacts.dao.impl;

import catalogContacts.dao.Validator123;
import catalogContacts.dao.exception.DaoXmlException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 *
 */
public class ValidatorImpl implements Validator123 {
    public boolean isXmlCorrect() {
        try {
            if (checkXMLforXSD("ContactList.xml", "src\\main\\java\\catalogContacts\\resources\\ContactSchema.xsd") && checkXMLforXSD("GroupList.xml", "src\\main\\java\\catalogContacts\\resources\\GroupSchema.xsd")) {
                return true;
            }
            return false;
        } catch (DaoXmlException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    /**
     * @param pathXml - путь к XML
     * @param pathXsd - путь к XSD
     */
    private boolean checkXMLforXSD(String pathXml, String pathXsd) throws DaoXmlException {
        File xml = new File(pathXml);
        File xsd = new File(pathXsd);

        if (!xml.exists()) {
            return true;
        }

        if (!xsd.exists()) {
            throw new DaoXmlException("Не найден XSD " + pathXsd);
        }

        if (!xml.exists() || !xsd.exists()) {
            return false;
        }

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(pathXsd));
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(pathXml));

        } catch (IOException e) {
            throw new DaoXmlException("Ошибка чтения файла " + e.getMessage());
        } catch (IllegalArgumentException | SAXException e) {
            throw new DaoXmlException("xml не соответствует схеме " + e.getMessage());
        }

        return true;

    }

}
