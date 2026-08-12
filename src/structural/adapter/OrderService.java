package structural.adapter;


//Client
public class OrderService {

    PaymentGateway paymentGateway;

    public OrderService (PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    void makePayment(double amount) {
        this.paymentGateway.pay(amount);
    }
}
