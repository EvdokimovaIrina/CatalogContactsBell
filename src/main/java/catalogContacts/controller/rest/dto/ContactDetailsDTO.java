package catalogContacts.controller.rest.dto;

import catalogContacts.model.TypeContact;

/**
 *
 */
public class ContactDetailsDTO {
    private int id;
    private TypeContact type;
    private String value;
    private int contactId;

    public ContactDetailsDTO(){

    }
    public ContactDetailsDTO(int id, TypeContact type, String value, int contactId) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.contactId = contactId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
