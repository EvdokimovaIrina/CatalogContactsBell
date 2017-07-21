package catalogContacts.event;

import catalogContacts.view.ViewInput;

/**
 * Created by iren on 21.07.2017.
 */
public interface ObserverForViewImpl {
    void handleEventMenuSelection(int item);
    void handleEventAddContact(String name);
}
