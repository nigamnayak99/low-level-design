// Strategy
interface PaymentStrategy {
    void pay(double amount);
}

// Concrete strategies
class CardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " by card");
    }
}

class UpiPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid ₹" + amount + " by UPI");
    }
}

// Context
class Checkout {
    private PaymentStrategy paymentStrategy;

    Checkout(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    void pay(double amount) {
        paymentStrategy.pay(amount);
    }
}

// Client
public class Strategy {
    public static void main(String[] args) {
        Checkout checkout = new Checkout(new UpiPayment());
        checkout.pay(500);

        checkout.setPaymentStrategy(new CardPayment());
        checkout.pay(1200);
    }
}