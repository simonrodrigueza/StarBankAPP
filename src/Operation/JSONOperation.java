
package Operation;


import java.util.ArrayList;

/**
 *Interfaz que contiene métodos sobre el json para las operaciones.
 */
public interface JSONOperation {

    public abstract ArrayList readJson();

    public abstract void addToJson(Operation o);

}
