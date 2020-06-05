/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operation;

import Client.Client;
import java.util.ArrayList;

/**
 *
 * @author SIMON
 */
public interface JSONOperation {

    public abstract ArrayList readJson();

    public abstract void addToJson(Operation o);

}
