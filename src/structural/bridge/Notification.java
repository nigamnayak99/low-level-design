package structural.bridge;


//1. Abstraction : Defines the high-level functionality that the client uses.
public abstract class Notification {

    NotificationSender notificationSender;

    public Notification(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void notify(String message) {
        notificationSender.notify(message);
    }
}
