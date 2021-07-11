package chat_client.controller.code;

import chat_client.Main;

/**
 * The type Code status.
 */
//Fabric method
public class CodeStatus {
    public boolean status;
    public int code;
    public String message;

    /**
     * Instantiates a new Code status.
     *
     * @param code    the code
     * @param message the message
     */
    public CodeStatus(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = code / 100 == 2;
    }

    @Override
    public String toString() {
        return code +
                ": " + message;
    }

    /**
     * Get code status.
     *
     * @param code the code
     * @return the code status
     */
    public static CodeStatus get(int code) {
        switch (code) {
            case 200:
                return new CodeStatus(200, "OK");
            case 201:
                return new CodeStatus(201, "Created");
            case 204:
                return new CodeStatus(204, "Successfully deleted");
            case 400 :
                return new CodeStatus(400, "Bad Request. An apparent client error");
            case 401:
                return new CodeStatus(401, "Unauthorized. Token is not valid");
            case 403:
                return new CodeStatus(403, "Forbidden. You don't have permission to access / on this server");
            case 408:
                return new CodeStatus(408, "This email address is already taken");
            case 409:
                return new CodeStatus(409, "Mail or password is wrong");
            case 410:
                return new CodeStatus(410, "Such a group already exists");
            case 411:
                return new CodeStatus(411, "Operation failed");
            case 412:
                return new CodeStatus(412, "Group does not exist");
            case 413:
                return new CodeStatus(413, "You are not creator of this group");
            case 414:
                return new CodeStatus(414, "Invalid characters");
            case 415:
                return new CodeStatus(415, "Password length must be at least 8 chars");
            case 416:
                return new CodeStatus(416, "Current password is incorrect");
            case 417:
                return new CodeStatus(416, "Operation failed. Try again");
            case 418:
                return new CodeStatus(418, "Invalid characters or length. Password must contain at least 8 characters");
            case 419:
                return new CodeStatus(419, "Error, group not found");
            case 420:
                return new CodeStatus(420, "This user is already a member of this group");
            case 421:
                return new CodeStatus(421, "Forbidden. You don't have permission to invite a new member");
            case 422:
                return new CodeStatus(422, "Forbidden. You don't have permission to delete members from group");
            case 423:
                return new CodeStatus(423, "Message was not sent. Try again");
            case 424:
                return new CodeStatus(424, "The group is not found");
            case 425:
                return new CodeStatus(425, "Message can not be empty");
            case 426:
                return new CodeStatus(426, "No messages found");
            case 427:
                return new CodeStatus(427, "Forbidden. You don't have permission to change rights");
            case 428:
                return new CodeStatus(428, "Rights can not be empty");
            case 429:
                return new CodeStatus(429, "Membership was not found");
            case 430:
                return new CodeStatus(430, "User was not found");
            default:
                return new CodeStatus(404, "Not Found. Bad syntax");
        }
    }

}
