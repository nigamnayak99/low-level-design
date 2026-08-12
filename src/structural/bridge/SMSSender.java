package structural.bridge;


//Concrete Implementor
public class SMSSender implements NotificationSender {

    @Override
    public void notify(String message) {
        System.out.println("Notified via SMS: "+message);
    }
}
