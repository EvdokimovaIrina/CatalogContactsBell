package catalogContacts.dao;

import catalogContacts.service.ContactService;
import catalogContacts.service.GroupService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by iren on 21.07.2017.
 */
public interface SavAndRestoreData {
    void serialize() throws IOException;
    boolean deserialize() throws IOException, ClassNotFoundException;
}
