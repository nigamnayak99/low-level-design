package structural.adapter;


//Adapter
public class RazorPayAdapter implements PaymentGateway {
    RazorPayClient client;

    public RazorPayAdapter(RazorPayClient razorPayClient){
        this.client = razorPayClient;
    }

    @Override
    public void pay(double amount) {
        this.client.createPayment(amount);
    }
}
