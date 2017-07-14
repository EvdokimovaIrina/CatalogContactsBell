package catalogContacts.model;

/**
 * Created by iren on 12.07.2017.
 */
public class Group {
    String name;
    int number;

    public Group(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
