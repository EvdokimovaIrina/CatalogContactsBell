package catalogContacts.controller.rest.dto;

/**
 *
 */
public class ContactDTO {
    private int number;
    private String fio;
    private String userName;

    public ContactDTO(){
    }

    public ContactDTO(int number,  String fio, String userName) {
        this.number = number;
        this.userName = userName;
        this.fio = fio;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
