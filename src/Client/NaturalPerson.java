
package Client;

/**
 *Clase que se extiene de Client.
 */
public class NaturalPerson extends Client{

    public NaturalPerson() {
    }

    public NaturalPerson(String id, String name, String phone, String address, String ocupation,boolean isSubscribed) {
        super(id, name, phone, address, ocupation,isSubscribed);
    }
    
    
}
