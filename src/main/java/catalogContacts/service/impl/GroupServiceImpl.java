package catalogContacts.service.impl;

import catalogContacts.dao.CrudDAO;
import catalogContacts.dao.exception.DaoException;
import catalogContacts.event.Event;
import catalogContacts.event.Observer;
import catalogContacts.model.Group;
import catalogContacts.event.TypeEvent;
import catalogContacts.service.GroupService;
import catalogContacts.view.impl.ViewOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EvdokimovaIS on 13.07.2017.
 */
public final class GroupServiceImpl implements GroupService, Observer.Observable {
    private static GroupServiceImpl instance;
    private CrudDAO<Group> crudDAOGroup;
    private List<Observer> ObserversList = new ArrayList<>();

    // Singleton

    private GroupServiceImpl() {
    }

    public static GroupServiceImpl getInstance() {
        return GroupServiceImplHolder.instance;
    }

    private static class GroupServiceImplHolder {
        private static final GroupServiceImpl instance = new GroupServiceImpl();
    }

    //////

    //работа с наблюдателями
    public synchronized void addObserver(Observer observer) {
        if (!ObserversList.contains(observer)) {
            ObserversList.add(observer);
        }
    }

    public synchronized void removeObserver(Observer observer) {
        if (ObserversList.contains(observer)) {
            ObserversList.remove(observer);
        }
    }


    public synchronized void notifyObserver(TypeEvent typeEvent, Object mainObject, Object value) {
        for (Observer observer : ObserversList) {
            observer.handleEvent(new Event(typeEvent, mainObject, value));
        }
    }

    private synchronized void notifyObserverWithAneError(DaoException e) {
        notifyObserver(TypeEvent.ERROR, e.getMessage(), null);
    }

    ////////
    public void addGroup(String name) {
        Group group = new Group(name);

        saveGroup(group);
    }

    public void saveGroup(Group group) {

        try {
            synchronized (this) {
                crudDAOGroup.create(group);
            }
            notifyObserver(TypeEvent.showGroupList, crudDAOGroup.getAll(), null);

        } catch (DaoException e) {
            notifyObserverWithAneError(e);
        }

    }

    public void deleteGroup(int numberGroup) {
        try {
            synchronized (this) {
                crudDAOGroup.delete(numberGroup);
            }
            notifyObserver(TypeEvent.showGroupList, crudDAOGroup.getAll(), null);

        } catch (DaoException e) {
            notifyObserverWithAneError(e);
        }

    }

    public void changeGroup(int numberGroup, String value) {
        try {
            synchronized (this) {
                Group group = crudDAOGroup.getObject(numberGroup);
                group.setName(value);
                crudDAOGroup.update(group);
            }

        } catch (DaoException e) {
            notifyObserverWithAneError(e);
        }

    }

    public void showGroupList() {
        try {
            notifyObserver(TypeEvent.showGroupList, crudDAOGroup.getAll(), null);
        } catch (DaoException e) {
            notifyObserverWithAneError(e);
        }

    }


    public Group findByNumber(int number) {
        Group group = null;
        try {
            synchronized (this) {
                group = crudDAOGroup.getObject(number);
            }
        } catch (DaoException e) {
            notifyObserverWithAneError(e);
        }

        return group;
    }

    public void setCrudDAOGroup(CrudDAO<Group> crudDAOGroup) {
        this.crudDAOGroup = crudDAOGroup;
    }
}
