
package Client;

/**
 *Clase abstracta que contiene los datos del cliente.
 */
public abstract class Client {
    private String id;
    private String name;
    private String phone;
    private String address;
    private String ocupation;
    private boolean isSubscribed;
    private int numberOfAccounts;

    

    public Client() {
    }

    public Client(String id, String name, String phone, String address, String ocupation, boolean isSubscribed) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.ocupation = ocupation;
        this.isSubscribed = isSubscribed;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOcupation() {
        return ocupation;
    }

    public void setOcupation(String ocupation) {
        this.ocupation = ocupation;
    }

    public boolean isIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", phone=" + phone + ", address=" + address + ", ocupation=" + ocupation + ", isSubscribed=" + isSubscribed  + '}';
    }


    public int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public void setNumberOfAccounts(int numberOfAccounts) {
        this.numberOfAccounts = numberOfAccounts;
    }
    
    
}
