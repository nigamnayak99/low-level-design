package structural.adapter;

public class DefaultLegacyGateway implements PaymentGateway {

    @Override
    public void pay(double amount) {
        System.out.println(amount+ " paid via legacy bank gateway.");
    }
}
