package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;
import org.json.JSONObject;

public final class MerchantOrderResult {
   private final int pointOfInteractionId;
   private final String merchantOrderId;
   private final String omnikassaOrderId;
   private final String orderStatus;
   private final String errorCode;
   private final String orderStatusDateTime;
   private final Money paidAmount;
   private final Money totalAmount;

   MerchantOrderResult(JSONObject jsonObject) {
      this.pointOfInteractionId = jsonObject.getInt("poiId");
      this.merchantOrderId = jsonObject.getString("merchantOrderId");
      this.omnikassaOrderId = jsonObject.getString("omnikassaOrderId");
      this.orderStatus = jsonObject.getString("orderStatus");
      this.orderStatusDateTime = jsonObject.getString("orderStatusDateTime");
      this.errorCode = jsonObject.getString("errorCode");
      this.paidAmount = Money.fromJson(jsonObject.getJSONObject("paidAmount"));
      this.totalAmount = Money.fromJson(jsonObject.getJSONObject("totalAmount"));
   }

   public int getPointOfInteractionId() {
      return this.pointOfInteractionId;
   }

   public String getMerchantOrderId() {
      return this.merchantOrderId;
   }

   public String getOmnikassaOrderId() {
      return this.omnikassaOrderId;
   }

   public String getOrderStatus() {
      return this.orderStatus;
   }

   public String getErrorCode() {
      return this.errorCode;
   }

   public Calendar getOrderStatusDateTime() {
      return CalendarUtils.stringToCalendar(this.orderStatusDateTime);
   }

   public Money getPaidAmount() {
      return this.paidAmount;
   }

   public Money getTotalAmount() {
      return this.totalAmount;
   }

   public List<String> getSignatureData() {
      return Arrays.asList(this.merchantOrderId, this.omnikassaOrderId, String.valueOf(this.pointOfInteractionId), this.orderStatus, this.orderStatusDateTime, this.errorCode, this.paidAmount.getCurrency().name(), String.valueOf(this.paidAmount.getAmountInCents()), this.totalAmount.getCurrency().name(), String.valueOf(this.totalAmount.getAmountInCents()));
   }
}
