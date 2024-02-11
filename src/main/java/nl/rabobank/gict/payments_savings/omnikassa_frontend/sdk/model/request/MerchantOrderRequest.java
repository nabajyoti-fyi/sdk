package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Signable;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

public class MerchantOrderRequest extends Signable implements JsonConvertible {
   private final MerchantOrder merchantOrder;
   private String timestamp;

   public MerchantOrderRequest(MerchantOrder merchantOrder, byte[] signingKey) throws RabobankSdkException {
      this.merchantOrder = merchantOrder;
      this.timestamp = DateTime.now().toString();
      this.calculateSignature(signingKey);
   }

   public List<String> getSignatureData() {
      List<String> data = new ArrayList();
      data.add(this.timestamp);
      data.add(this.merchantOrder.getMerchantOrderId());
      data.add(this.merchantOrder.getAmount().getCurrency().name());
      data.add(String.valueOf(this.merchantOrder.getAmount().getAmountInCents()));
      data.add(this.merchantOrder.getLanguage().name());
      data.add(this.merchantOrder.getDescription());
      data.add(this.merchantOrder.getMerchantReturnURL());
      Iterator i$ = this.merchantOrder.getOrderItems().iterator();

      while(i$.hasNext()) {
         OrderItem orderItem = (OrderItem)i$.next();
         data.addAll(orderItem.getSignatureData());
      }

      Address shippingDetails = this.merchantOrder.getShippingDetail();
      if (shippingDetails != null) {
         data.addAll(shippingDetails.getSignatureData());
      }

      PaymentBrand paymentBrand = this.merchantOrder.getPaymentBrand();
      if (paymentBrand != null) {
         data.add(paymentBrand.name());
      }

      PaymentBrandForce paymentBrandForce = this.merchantOrder.getPaymentBrandForce();
      if (paymentBrandForce != null) {
         data.add(paymentBrandForce.name());
      }

      CustomerInformation customerInformation = this.merchantOrder.getCustomerInformation();
      if (customerInformation != null) {
         data.addAll(customerInformation.getSignatureData());
      }

      Address billingDetails = this.merchantOrder.getBillingDetail();
      if (billingDetails != null) {
         data.addAll(billingDetails.getSignatureData());
      }

      return data;
   }

   public JSONObject asJson() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("timestamp", this.timestamp);
      jsonObject.put("merchantOrderId", this.merchantOrder.getMerchantOrderId());
      jsonObject.put("amount", this.merchantOrder.getAmount().asJson());
      jsonObject.put("language", this.merchantOrder.getLanguage().toString());
      jsonObject.put("description", this.merchantOrder.getDescription());
      jsonObject.put("merchantReturnURL", this.merchantOrder.getMerchantReturnURL());
      jsonObject.put("orderItems", this.getOrderItemsAsJson());
      Address shippingDetails = this.merchantOrder.getShippingDetail();
      if (shippingDetails != null) {
         jsonObject.put("shippingDetail", shippingDetails.asJson());
      }

      PaymentBrand paymentBrand = this.merchantOrder.getPaymentBrand();
      if (paymentBrand != null) {
         jsonObject.put("paymentBrand", paymentBrand.name());
      }

      PaymentBrandForce paymentBrandForce = this.merchantOrder.getPaymentBrandForce();
      if (paymentBrandForce != null) {
         jsonObject.put("paymentBrandForce", paymentBrandForce.name());
      }

      CustomerInformation customerInformation = this.merchantOrder.getCustomerInformation();
      if (customerInformation != null) {
         jsonObject.put("customerInformation", customerInformation.asJson());
      }

      Address billingDetails = this.merchantOrder.getBillingDetail();
      if (billingDetails != null) {
         jsonObject.put("billingDetail", billingDetails.asJson());
      }

      jsonObject.put("signature", this.getSignature());
      return jsonObject;
   }

   private JSONArray getOrderItemsAsJson() {
      JSONArray jsonArray = new JSONArray();
      Iterator i$ = this.merchantOrder.getOrderItems().iterator();

      while(i$.hasNext()) {
         OrderItem orderItem = (OrderItem)i$.next();
         jsonArray.put(orderItem.asJson());
      }

      return jsonArray;
   }

   private void calculateSignature(byte[] signingKey) throws RabobankSdkException {
      String calculatedSignature = Signable.calculateSignature(this.getSignatureData(), signingKey);
      this.setSignature(calculatedSignature);
   }
}
