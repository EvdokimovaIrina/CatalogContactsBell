package catalogContacts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iren on 12.07.2017.
 */
public class Group implements Serializable {
    private String name;
    private int number;

    public Group(){
    }

    public Group(String name){
        this.name = name;
    }

    public Group(String name,int number){
        this.name = name;
        this.number = number;
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
