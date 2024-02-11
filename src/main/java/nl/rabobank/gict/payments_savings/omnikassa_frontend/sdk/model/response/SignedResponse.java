package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalSignatureException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Signable;
import org.json.JSONObject;

public abstract class SignedResponse extends Signable {
   SignedResponse(JSONObject jsonObject) {
      this.setSignature(jsonObject.getString("signature"));
   }

   SignedResponse(String signature) {
      this.setSignature(signature);
   }

   public void validateSignature(byte[] signingKey) throws RabobankSdkException {
      String calculatedSignature = calculateSignature(this.getSignatureData(), signingKey);
      if (!calculatedSignature.equals(this.getSignature())) {
         throw new IllegalSignatureException();
      }
   }
}
