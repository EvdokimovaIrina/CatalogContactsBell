package catalogContacts.event;

/**
 * Created by iren on 21.07.2017.
 */
public interface Observer<T> {
    void handleEventDetailsList(T object);
    void handleEventChangedList(T object);
}
