package chat_server;

import chat_server.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

public class Main {
    private static int size = 0;
    public static final Logger LOGGER = Logger.getGlobal();
    public static EntityManager em;
    //HashMap with token and id of active users.
    public static HashMap<String, Integer> active_users = new HashMap<>();
    public static void main(String[] args) throws ClassNotFoundException {
        //Connection to the database
        Class.forName("org.postgresql.Driver");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("chatik");
        em = emf.createEntityManager();
        LOGGER.info("Connected successfully");
        //Creation of server
        Server server = null;
        try {
            server = new Server(9000);
        } catch (IOException e) {
            LOGGER.severe("Server couldn't launch");
            System.exit(-1);
        }

        Socket client;
        LOGGER.info("Listening...");

        try {
            while((client = server.accept()) != null) {
                LOGGER.info(":Client connected: " + size);
                Client newClient = new Client(client.getInputStream(), client.getOutputStream(), size, client);
                size++;
                new Thread(newClient).start();
            }
        } catch (IOException e) {
            LOGGER.severe("Client cannot connect");
        }
    }
}
