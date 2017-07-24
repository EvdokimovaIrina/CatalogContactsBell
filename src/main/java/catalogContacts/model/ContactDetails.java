package catalogContacts.model;

import java.io.Serializable;

public class ContactDetails implements Serializable {
    private TypeContact type;
    private String value;

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
}
