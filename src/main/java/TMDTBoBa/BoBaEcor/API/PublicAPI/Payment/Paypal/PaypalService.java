package TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;


    public String createPaypal(){
        Item itemOne = new Item();
        itemOne.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
        Item itemTwo = new Item();
        itemTwo.setName("Cat collor").setQuantity("2").setCurrency("USD").setPrice("15");


        // Adding item to our list
        List<Item> items = new ArrayList<>();
        items.add(itemOne);
        items.add(itemTwo);

        // Adding items to itemList
        ItemList itemList = new ItemList();
        itemList.setItems(items);

        // Set payment details
        Details details = new Details();
        details.setShipping("1");
        // itemOne cost: 5 itemTwo cost 15*2. So 5+30
        details.setSubtotal("35");
        details.setTax("1");

        // Payment amount
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("37");
        amount.setDetails(details);


        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("This is the payment transaction description.");

        transaction.setItemList(itemList);


        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // Add payment details
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        // payment.setRedirectUrls(redirectUrls);
        payment.setTransactions(transactions);

        // ###Redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        String guid = UUID.randomUUID().toString().replaceAll("-", ""); // Not necessary, just demonstrating how we can add the order/user id as a param.
        // Payment cancellation url
        redirectUrls.setCancelUrl("http://localhost:8060" + "/thanh-toan/that-bai?guid=" + guid);
        // Payment success url
        redirectUrls.setReturnUrl("http://localhost:8060" + "/thanh-toan/thanh-cong?guid=" + guid);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment = null;
        try {
            createdPayment = payment.create(apiContext);
            Iterator<Links> links = createdPayment.getLinks().iterator();
            while (links.hasNext()) {
                Links link = links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.out.println("error");
        }
        return "";
    }
    public Payment executePayment(String paymentId, String payerId,String guild) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}
