package VaLocaProject.Sockets;


public record ChatMessage(String message, String sender) {



    public String getSender() {
        return sender;
    }
}
