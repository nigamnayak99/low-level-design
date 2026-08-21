package structural.bridge;


/**
 * Demonstrates the Bridge Design Pattern using a notification system.
 *
 * <p>The notification type and notification delivery channel are treated
 * as two independent dimensions of variation.</p>
 *
 * <p>Notification types represent the abstraction, for example:
 * OTP, Alert, and Marketing notifications.</p>
 *
 * <p>NotificationSender represents the implementation, with different
 * delivery channels such as Email, SMS, Push, or WhatsApp.</p>
 *
 * <p>Instead of creating separate classes for every combination
 * (e.g., OtpEmailNotification, OtpSmsNotification, AlertEmailNotification),
 * the Notification abstraction holds a reference to NotificationSender.
 * This allows both notification types and delivery channels to evolve
 * independently and avoids class explosion.</p>
 *
 * <p>In short:</p>
 * <pre>
 * Notification (Abstraction)
 *        |
 *        | uses
 *        v
 * NotificationSender (Implementation)
 *        |
 *   -------------------
 *   |        |        |
 * Email     SMS      Push
 * </pre>
 */
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
