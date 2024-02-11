package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

public final class MerchantOrderResponse extends SignedResponse {
   private final String redirectUrl;

   public MerchantOrderResponse(JSONObject jsonObject) {
      super(jsonObject);
      this.redirectUrl = jsonObject.getString("redirectUrl");
   }

   public String getRedirectUrl() {
      return this.redirectUrl;
   }

   protected List<String> getSignatureData() {
      return Collections.singletonList(this.redirectUrl);
   }
}
