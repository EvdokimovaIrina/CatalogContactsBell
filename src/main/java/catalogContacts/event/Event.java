package catalogContacts.event;

import catalogContacts.model.TypeEvent;

/**
 * Created by iren on 22.07.2017.
 */
public class Event {
    private TypeEvent eventType;
    private Object mainObject;
    private Object value;

    public Event(TypeEvent eventType, Object mainObject, Object value) {
        this.eventType = eventType;
        this.mainObject = mainObject;
        this.value = value;
    }

    public TypeEvent getEventType() {
        return eventType;
    }

    public Object getMainObject() {
        return mainObject;
    }

    public Object getValue() {
        return value;
    }
}
