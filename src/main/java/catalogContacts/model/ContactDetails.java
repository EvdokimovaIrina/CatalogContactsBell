package catalogContacts.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;


public class ContactDetails implements Serializable {
    @JacksonXmlProperty(localName = "type")
    private int id;
    private TypeContact type;
    @JacksonXmlProperty(localName = "value")
    private String value;

    public ContactDetails(int id,TypeContact type, String value){
        this.id = id;
        this.type = type;
        this.value = value;
    }
    public ContactDetails(TypeContact type, String value){
        this.type = type;
        this.value = value;
    }

    public ContactDetails() {
    }

    public TypeContact getType() {
        return type;
    }

    public void setType(TypeContact type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
