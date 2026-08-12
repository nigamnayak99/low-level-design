package structural.bridge;

//Concrete abstraction
public class ReminderNotification extends Notification {

    public ReminderNotification(NotificationSender notificationSender) {
        super(notificationSender);
    }

    @Override
    public void notify(String message) {
        super.notify(message);
    }

}
