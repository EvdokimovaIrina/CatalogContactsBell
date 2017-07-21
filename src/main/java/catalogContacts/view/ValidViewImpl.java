package catalogContacts.view;

import catalogContacts.TypeContact;
import catalogContacts.controller.ValidController;

/**
 * Created by iren on 16.07.2017.
 */
public class ValidViewImpl implements ValidView {
    public int actionValid(String strReader) {
        int selectedAction =-1;
        try {
            selectedAction = Integer.parseInt(strReader);
        } catch (Exception e) {
            return -1;
        }
        return selectedAction;
    }

}
