package catalogContacts.view.impl;

import catalogContacts.view.ValidView;

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
