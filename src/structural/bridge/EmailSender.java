package structural.bridge;

//Concrete Implementor
public class EmailSender implements NotificationSender {
    @Override
    public void notify(String message) {
        System.out.println("Notified via Email: "+message);
    }
}
