package catalogContacts.event;

import catalogContacts.model.TypeEvent;

/**
 * Created by iren on 21.07.2017.
 */
public interface Observable {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver(TypeEvent typeEvent, Object mainObject, Object value);

}
