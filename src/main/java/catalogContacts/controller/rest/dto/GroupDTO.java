package catalogContacts.controller.rest.dto;

/**
 *
 */
public class GroupDTO {
    private int number;
    private String name;
    private String userName;

    public GroupDTO(){}

    public GroupDTO(int number, String name, String userName) {
        this.number = number;
        this.name = name;
        this.userName = userName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
