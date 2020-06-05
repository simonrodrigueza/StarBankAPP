/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.Client;
import Operation.Operation;
import java.util.ArrayList;

/**
 *
 * @author SIMON
 */
public interface JSONClient {
    
    public abstract ArrayList readJson();
    public abstract void addToJson(Client o);
    public abstract void editJson(Client o);
        
    
}
