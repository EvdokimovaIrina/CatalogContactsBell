package catalogContacts.controller;

import catalogContacts.TypeContact;

/**
 * Created by iren on 16.07.2017.
 */
public interface Valid {
        public int actionValid(String strReader);
        public Boolean valueContactDetailsValid(TypeContact type);
}
