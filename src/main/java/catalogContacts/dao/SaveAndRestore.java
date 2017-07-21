package catalogContacts.dao;

import java.io.IOException;

/**
 * Created by iren on 21.07.2017.
 */
public interface SaveAndRestore {
    void serialize() throws IOException;
    boolean deserialize() throws IOException, ClassNotFoundException;
}
