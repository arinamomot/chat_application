package chat_client.controller.request;

import chat_client.controller.Request;
import chat_server.model.GroupEntity;
import chat_server.model.MessageEntity;

public class SendMessageRequest extends Request {
    public SendMessageRequest() {
        this.action = "sendMessage";
        this.token = null;
        this.data = new SendMessageData();
    }
    public void setText(String text) {
        ((SendMessageRequest.SendMessageData)this.data).setText(text);
    }
    public void setRecipient(Integer recipient) {
        ((SendMessageRequest.SendMessageData)this.data).setRecipient(recipient);
    }

    public static class SendMessageData{
        private String text;
        private Integer recipient;

        public MessageEntity getMessage() {
            return message;
        }

        public void setMessage(MessageEntity message) {
            this.message = message;
        }

        private MessageEntity message = new MessageEntity();

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getRecipient() {
            return recipient;
        }

        public void setRecipient(Integer recipient) {
            this.recipient = recipient;
        }


    }
}
