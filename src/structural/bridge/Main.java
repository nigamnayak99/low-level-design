package structural.bridge;

public class Main {

    void main() {

        //Email alert
        Notification notification = new ReminderNotification(new EmailSender());
        notification.notify("Alert Message");

        //SMS Reminder
        Notification notification1 = new ReminderNotification(new SMSSender());
        notification1.notify("SMS Message");
    }
}
