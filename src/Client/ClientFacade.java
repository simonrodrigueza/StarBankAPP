
package Client;

import java.util.ArrayList;

/**
 * Clase fachada para el cliente, la cual busca, edita y añade
 * clientes en el JSON, usa Singleton.
 */
public class ClientFacade {

    JSONClient clientPersist;
    private static ClientFacade clientFacadeInstance;

   /**
    * Busca el cliente mediante el id y el tipo y retorna un objeto
    * de tipo cliente.
    * @param id
    * @param clientType
    * @return 
    */
    public Client searchClient(String id, String clientType) {
        if (clientType.equalsIgnoreCase("natural")) {
            clientPersist = new NaturalPersonJSON();
            ArrayList<NaturalPerson> clients = clientPersist.readJson();
            for (Client client : clients) {
                if (client.getId().equalsIgnoreCase(id)) {
                    System.out.println("Encontrado");
                    return client;
                }
            }
        } else if (clientType.equalsIgnoreCase("compañia")) {
            clientPersist = new CompanyJSON();
            ArrayList<NaturalPerson> company = clientPersist.readJson();
            for (Client client : company) {
                if (client.getId().equalsIgnoreCase(id)) {
                    System.out.println("Encontrado");
                    return client;
                }
            }
        }

        return null;

    }

    /**
     * Añade un objeto tipo cliente al json según sea el caso, persona
     * natural o compañia.
     * @param client 
     */
    public void addClient(Client client) {
        if (client instanceof NaturalPerson) {
            clientPersist = new NaturalPersonJSON();
            clientPersist.addToJson(client);
        } else if (client instanceof Company) {
            clientPersist = new CompanyJSON();
            clientPersist.addToJson(client);
        }

    }

    /**
     * Edita la información del cliente buscado y lo vuelve a 
     * guardar en el JSON.
     * @param client 
     */
    public void editClient(Client client) {
        if (client instanceof NaturalPerson) {
            clientPersist = new NaturalPersonJSON();
            clientPersist.editJson(client);
        } else if (client instanceof Company) {
            clientPersist = new CompanyJSON();
            clientPersist.editJson(client);
        }
    }
    /**
     * Método para obtener la instancia 
     * de la clase (singleton).
     * @return 
     */
    public static ClientFacade getInstance() {
        if (clientFacadeInstance == null) {
            clientFacadeInstance = new ClientFacade();
        }
        return clientFacadeInstance;
    }

}
