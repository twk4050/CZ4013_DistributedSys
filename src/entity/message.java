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
    
    public String messageToString(){
        if (messageContent.contains(":")){
            return messageContent;
        }
        else{
            StringBuilder strg = new StringBuilder();
            strg.append(messageId);
            strg.append(":");
            strg.append(messageContent);
            String nmessage = strg.toString();
            setMessageContent(nmessage);
            return nmessage;
        }
    }

    public static message replymessage(){
        return new message(messageId, messageContent);
    }

    public void printMessage(){
        System.out.println("Message ID is : "+messageId+", Message Content is : "+messageContent);
    }

    public void printMessageId(){
        System.out.println("Message ID is : "+messageId);
    }
    public void printMessageContent(){
        System.out.println("Message Content is : "+messageContent);
    }
}
