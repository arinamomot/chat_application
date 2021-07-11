package chat_client.exception;

public class SendError extends Exception {
    /**
     * Instantiates a new Send error.
     */
    public SendError() {
        super("Unable to send request");
    }
}
