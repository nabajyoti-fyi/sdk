package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public final class ApiNotification extends SignedResponse {
   private int poiId;
   private String authentication;
   private String expiry;
   private String eventName;

   public ApiNotification() {
      super((String)null);
   }

   public ApiNotification(int poiId, String authentication, String expiry, String eventName, String signature) {
      super(signature);
      this.poiId = poiId;
      this.authentication = authentication;
      this.expiry = expiry;
      this.eventName = eventName;
   }

   public ApiNotification(JSONObject jsonObject) {
      super(jsonObject);
      this.poiId = jsonObject.getInt("poiId");
      this.authentication = jsonObject.getString("authentication");
      this.expiry = jsonObject.getString("expiry");
      this.eventName = jsonObject.getString("eventName");
   }

   public int getPoiId() {
      return this.poiId;
   }

   public String getAuthentication() {
      return this.authentication;
   }

   public String getExpiry() {
      return this.expiry;
   }

   public String getEventName() {
      return this.eventName;
   }

   public List<String> getSignatureData() {
      return Arrays.asList(this.authentication, this.expiry, this.eventName, String.valueOf(this.poiId));
   }
}
