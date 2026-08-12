package structural.adapter;

public class Main {
    void main()  {

        //This is legacy system.
        DefaultLegacyGateway defaultGateway = new DefaultLegacyGateway();
        OrderService orderService = new OrderService(defaultGateway);
        orderService.makePayment(20D);

        //Now wants to integrate razor pay which has different methods e.g createPayment() to make payment,
        // but I want my structure to be stable.
        RazorPayAdapter razorPayAdapter = new RazorPayAdapter(new RazorPayClient());
        OrderService orderService1 = new OrderService(razorPayAdapter);
        orderService1.makePayment(500);

    }

}
