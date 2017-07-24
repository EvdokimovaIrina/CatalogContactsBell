package catalogContacts.dao;

import catalogContacts.model.PhoneBook;

import java.io.*;

/**
 * Created by iren on 15.07.2017.
 */
public class SavAndRestoreDataImpl implements SaveAndRestore{

    public void serialize() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("objects.dat"));
        out.writeObject(PhoneBook.getPhoneBook());
        out.close();
    }


    public boolean deserialize() throws IOException, ClassNotFoundException {
        File file = new File("objects.dat");
        if (file.exists()) {
            FileInputStream inpS = new FileInputStream("objects.dat");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("objects.dat"));
            PhoneBook phoneBookDeserialize = (PhoneBook) in.readObject();
            PhoneBook phoneBook = PhoneBook.getPhoneBook();
            phoneBook.setContactsList(phoneBookDeserialize.getContactsList());
            phoneBook.setGroupsList(phoneBookDeserialize.getGroupsList());
        } else {
           return false;
        }

        return true;
    }

    /*public void serialize(ContactService contactService, GroupService groupService) throws IOException {
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
            mapReturn.put("ContactService", (ContactServiceImplOld) in.readObject());
            mapReturn.put("GroupService", (GroupServiceImpl) in.readObject());
        } else {
            mapReturn.put("ContactService", new ContactServiceImplOld());
            mapReturn.put("GroupService", new GroupServiceImpl());
        }

        // GroupService groupService = (GroupService) in.readObject();
        return (mapReturn);
    }*/





}
