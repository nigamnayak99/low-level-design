package structural.adapter;

//Adaptee
public class RazorPayClient {

    void createPayment(double amount) {
        System.out.println(amount + " paid via razorpay.");
    }
}
