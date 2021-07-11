package chat_client.exception;

public class ConnectionError extends Exception {
    /**
     * Instantiates a new Connection error.
     */
    public ConnectionError() {
        super("Connection was not found");
    }
}
