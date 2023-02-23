package entity;

public class message {
    
    private static int messageId;
    private static String messageContent;

    public message(int messageId, String messageContent){
        message.messageId = messageId;
        message.messageContent = messageContent;
    }

    public int getMessageId(){
        return message.messageId;
    }

    public String getMessageContent(){
        return message.messageContent;
    }

    public void setMessageId(int messageId){
        message.messageId = messageId;
    }
    public void setMessageContent(String messageContent){
        message.messageContent = messageContent;
    }
    

}
