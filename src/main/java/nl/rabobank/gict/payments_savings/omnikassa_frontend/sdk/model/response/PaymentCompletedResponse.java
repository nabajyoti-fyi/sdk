package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import java.util.Arrays;
import java.util.List;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;

public class PaymentCompletedResponse extends SignedResponse {
   private final String orderId;
   private final String status;

   /** @deprecated */
   @Deprecated
   public PaymentCompletedResponse(String orderId, String status, String signature) {
      super(signature);
      this.orderId = orderId;
      this.status = status;
   }

   public static PaymentCompletedResponse newPaymentCompletedResponse(String orderId, String status, String signature, byte[] signingKey) throws RabobankSdkException {
      PaymentCompletedResponse response = new PaymentCompletedResponse(orderId, status, signature);
      response.validateSignature(signingKey);
      return response;
   }

   public String getStatus() {
      return this.status;
   }

   public String getOrderID() {
      return this.orderId;
   }

   public List<String> getSignatureData() {
      return Arrays.asList(this.orderId, this.status);
   }
}
