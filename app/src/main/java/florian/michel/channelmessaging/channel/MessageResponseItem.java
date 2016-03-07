package florian.michel.channelmessaging.channel;

/**
 * Created by Flo on 01/03/2016.
 */
public class MessageResponseItem {

    private Integer userID;
    private String message;
    private String date;
    private String imageUrl;
    private String username;

    public String getUsername() {
        return username;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
