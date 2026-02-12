package VaLocaProject.Sockets;


public record ChatMessage(String content, String sender, String destinationUser) {

    public String getContent() {
        return content;
    }

    public String getDestinationUser() {
        return destinationUser;
    }

    public String getSender() {
        return sender;
    }
}
