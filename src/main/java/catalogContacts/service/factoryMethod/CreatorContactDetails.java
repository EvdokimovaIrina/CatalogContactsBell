package catalogContacts.service.factoryMethod;

import catalogContacts.TypeContact;
import catalogContacts.model.Details;

/**
 * Created by iren on 21.07.2017.
 */
public interface CreatorContactDetails {
    Details creatDetails(TypeContact typeContact);
}
