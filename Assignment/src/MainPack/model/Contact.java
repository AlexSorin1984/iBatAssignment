package MainPack.model;

public class Contact implements Comparable<Contact>{
    private int id;
    private String firstName;
    private String lastName;
    private String telephone;

    public Contact(int id, String firstName, String lastName, String telephone) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }

    @Override
    public int compareTo(Contact contact) {
        if(this.firstName.compareTo(contact.firstName)<0){
            return -1;
        }
        else if(this.firstName.compareTo(contact.firstName)>0){
            return 1;
        }
        else return 0;
    }
}
