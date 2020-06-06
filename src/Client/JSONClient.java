
package Client;

import Client.Client;
import java.util.ArrayList;

/**
 *Interfaz que tiene los métodos para
 * usar sobre el JSON.
 */
public interface JSONClient {
    
    public abstract ArrayList readJson();
    public abstract void addToJson(Client o);
    public abstract void editJson(Client o);
        
    
}
