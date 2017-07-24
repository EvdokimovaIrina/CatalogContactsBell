package catalogContacts.dao.impl;

import catalogContacts.dao.SaveAndRestore;
import catalogContacts.model.PhoneBook;

import java.io.*;

/**
 * Created by iren on 15.07.2017.
 */
public class SavAndRestoreDataImpl implements SaveAndRestore {

    public void serialize() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("objects.dat"));
        out.writeObject(PhoneBook.getPhoneBook());
        out.close();
    }


    public boolean deserialize() throws IOException, ClassNotFoundException {
        File file = new File("objects.dat");
        if (file.exists()) {
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


}
