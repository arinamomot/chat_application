package chat_server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends ServerSocket {
    /**
     * Instantiates a new Server.
     *
     * @param port the port
     * @throws IOException the io exception
     */
    public Server(int port) throws IOException {
        super(port);
    }
}
