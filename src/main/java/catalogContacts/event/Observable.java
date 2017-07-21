package catalogContacts.event;

/**
 * Created by iren on 21.07.2017.
 */
public interface Observable {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserverChangedlist();

    void notifyObserverDetails();


}
