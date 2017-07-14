package catalogContacts.model;

import catalogContacts.TypeContact;
import catalogContacts.model.Contact;

public class ContactDetails{
    private Contact owner;
    private TypeContact type;
    private String value;

    public ContactDetails(Contact owner, TypeContact type, String value){
        this.owner = owner;
        this.type = type;
        this.value = value;
    }

    public ContactDetails() {
    }

    public Contact getOwner() {
        return owner;
    }

    public void setOwner(Contact owner) {
        this.owner = owner;
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
