package structural.bridge;

//Concrete abstraction
public class PushNotification extends Notification {

    public PushNotification(NotificationSender notificationSender) {
        super(notificationSender);
    }

    @Override
    public void notify(String message) {
        super.notify(message);
    }
}
