package catalogContacts.dao.entityForJackson;

import catalogContacts.model.Contact;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 *
 */

@JacksonXmlRootElement(localName = "contactList")
public class ContactListJackson {
    @JacksonXmlElementWrapper(localName = "contact", useWrapping = false)
    private List<ContactJackson> contact;

    public ContactListJackson() {
    }
    public ContactListJackson(List<ContactJackson> contact) {
        this.contact = contact;
    }


    public List<ContactJackson> getContact() {
        return contact;
    }

    public void setContactList(List<ContactJackson> contact) {
        this.contact = contact;
    }
}
