package catalogContacts.service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iren on 15.07.2017.
 */
public class SavAndRestoreData {

    public void serialize(ContactService contactService, GroupService groupService) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("objects.dat"));
        out.writeObject(contactService);
        out.writeObject(groupService);
        out.close();
    }

    public Map<String, Object> deserialize() throws IOException, ClassNotFoundException {
        Map<String, Object> mapReturn = new HashMap<>();

        File file = new File("objects.dat");
        if (file.exists()) {
            FileInputStream inpS = new FileInputStream("objects.dat");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("objects.dat"));
            //ContactService contactService = ;
            mapReturn.put("ContactService", (ContactService) in.readObject());
            mapReturn.put("GroupService", (GroupService) in.readObject());
        } else {
            mapReturn.put("ContactService", new ContactService());
            mapReturn.put("GroupService", new GroupService());
        }

        // GroupService groupService = (GroupService) in.readObject();
        return (mapReturn);
    }

}
