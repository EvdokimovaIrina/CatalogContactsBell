package catalogContacts.event;

import catalogContacts.model.Contact;
import catalogContacts.model.TypeEvent;

import java.util.List;

/**
 * Created by iren on 21.07.2017.
 */
public interface Observer {
    void handleEvent(Event event);
}
