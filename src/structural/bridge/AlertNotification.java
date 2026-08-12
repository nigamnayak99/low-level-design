package structural.bridge;

//Concrete abstraction
public class AlertNotification extends Notification {

    public AlertNotification(NotificationSender notificationSender) {
        super(notificationSender);
    }

    @Override
    public void notify(String message) {
        super.notify(message);
    }
}
