package TMDTBoBa.BoBaEcor.API.PublicAPI.Payment.Paypal;

import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.Cart;
import TMDTBoBa.BoBaEcor.API.CustomeHttpRe.Store.CartItem;
import TMDTBoBa.BoBaEcor.Models.Store.Order;
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


    public String createPaypal(Order order, Cart cart){
        List<Item> items = new ArrayList<>();
        Integer total = 0;
        for(CartItem cartItem : cart.getCartItems()){
            Item item = new Item();
            Integer price = cartItem.getProductDetail().getProductPrice()/ 23820;
            total += price * cartItem.getQuantity();
            item.setName(cartItem.getName()).setQuantity(String.valueOf(cartItem.getQuantity())).setCurrency("USD").setPrice(String.valueOf(price));
            items.add(item);
        }
        // Adding items to itemList
        ItemList itemList = new ItemList();
        itemList.setItems(items);
        // Set payment details
        Details details = new Details();
        details.setShipping("1");
        // itemOne cost: 5 itemTwo cost 15*2. So 5+30
        details.setSubtotal(String.valueOf(total));
        details.setTax("1");

        // Payment amount
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(total+2));
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
        String guid = String.valueOf(order.getOrderId());
        // Payment cancellation url
        redirectUrls.setCancelUrl("https://bobaecor.live" + "/thanh-toan/that-bai?guid=" + guid);
        // Payment success url
        redirectUrls.setReturnUrl("https://bobaecor.live" + "/thanh-toan/thanh-cong?guid=" + guid);
//        redirectUrls.setReturnUrl("http://localhost:8060/" + "/thanh-toan/thanh-cong?guid=" + guid);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment = null;
        try {
            createdPayment = payment.create(apiContext);
            Iterator<Links> links = createdPayment.getLinks().iterator();
            while (links.hasNext()) {
                Links link = links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.out.println("error");
        }
        return "";
    }
    public Payment executePayment(String paymentId, String payerId,Integer guild) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
}