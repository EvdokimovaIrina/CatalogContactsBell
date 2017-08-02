package catalogContacts.dao.entityForJackson;

import catalogContacts.model.ContactDetails;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

/**
 *
 */
public class ContactDetailsListJackson {
    @JacksonXmlElementWrapper(localName = "contactDetails", useWrapping = false)
    private List<ContactDetails> contactDetails;

    public ContactDetailsListJackson() {
    }

    public ContactDetailsListJackson(List<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<ContactDetails> getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(List<ContactDetails> contactDetails) {
        this.contactDetails = contactDetails;
    }
}
