package catalogContacts.service.factoryMethod;

import catalogContacts.TypeContact;
import catalogContacts.model.ContactDetails;
import catalogContacts.model.Details;

/**
 * Created by iren on 21.07.2017.
 */
public class CreatorContactDetailsImpl implements CreatorContactDetails {

    public Details creatDetails(TypeContact typeContact) {
        if (typeContact.equals(TypeContact.Phone)){
            return new ContactDetails();
        }
        return null;
    }
}
