package catalogContacts.controller;

import catalogContacts.TypeContact;

/**
 * Created by iren on 16.07.2017.
 */
public interface ValidController {
        public Boolean valueContactDetailsValid(TypeContact type);

        int actionValid(String strReader);
}
