package catalogContacts.event;

/**
 * Created by iren on 21.07.2017.
 */
public interface ObservableViewInput {
    void addObserver(ObserverForViewImpl observer);

    void removeObserver(ObserverForViewImpl observer);

    void notifyMenuSelection();

    void notifyAddContact(String name);
}
