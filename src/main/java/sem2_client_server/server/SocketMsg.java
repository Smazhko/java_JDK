package sem2_client_server.server;

public class SocketMsg {
    public enum MsgType{
        LOGIN,
        LOGOUT,
        TEXT
    };

    private MsgType type;
    private User user;
    private String msgText;

    public SocketMsg(MsgType type, User fromUser, String msgText) {
        this.type = type;
        this.user = fromUser;
        this.msgText = msgText;
    }

    public MsgType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public String getMsgText() {
        return msgText;
    }
}
