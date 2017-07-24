package catalogContacts.event;

/**
 * Created by iren on 21.07.2017.
 */
public interface Observer {
    void handleEvent(Event event);

    /**
     * Created by iren on 21.07.2017.
     */
    interface Observable {

        void addObserver(Observer observer);

        void removeObserver(Observer observer);

        void notifyObserver(TypeEvent typeEvent, Object mainObject, Object value);

    }
}
