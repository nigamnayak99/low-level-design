package structural.bridge;


//Abstraction
public abstract class Notification {

    NotificationSender notificationSender;

    public Notification(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void notify(String message) {
        notificationSender.notify(message);
    }
}
