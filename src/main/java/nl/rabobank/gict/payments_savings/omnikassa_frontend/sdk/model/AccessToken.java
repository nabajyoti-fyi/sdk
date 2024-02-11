package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import java.util.Calendar;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;
import org.json.JSONObject;

public final class AccessToken {
   private static final double ACCESS_TOKEN_EXPIRATION_MARGIN = 0.01D;
   private final String token;
   private final Calendar validUntil;
   private final int durationInMillis;

   public AccessToken(JSONObject jsonObject) {
      this.token = jsonObject.getString("token");
      this.validUntil = CalendarUtils.stringToCalendar(jsonObject.getString("validUntil"));
      this.durationInMillis = jsonObject.getInt("durationInMillis");
   }

   public AccessToken(String token, Calendar validUntil, int durationInMillis) {
      this.validateArguments(token, validUntil, durationInMillis);
      this.token = token;
      this.validUntil = validUntil;
      this.durationInMillis = durationInMillis;
   }

   private void validateArguments(String token, Calendar validUntil, int durationInMillis) {
      if (token != null && !"".equals(token)) {
         if (validUntil == null) {
            throw new IllegalArgumentException("Valid until cannot be empty");
         } else if (durationInMillis == 0) {
            throw new IllegalArgumentException("Duration in milliseconds cannot be zero");
         }
      } else {
         throw new IllegalArgumentException("Token cannot be empty");
      }
   }

   public String getToken() {
      return this.token;
   }

   public Calendar getValidUntil() {
      return this.validUntil;
   }

   public int getDurationInMillis() {
      return this.durationInMillis;
   }

   public boolean isNotExpired() {
      return !this.isExpired();
   }

   boolean isExpired() {
      Calendar currentDate = Calendar.getInstance();
      double timeLeft = (double)(this.getValidUntil().getTimeInMillis() - currentDate.getTimeInMillis());
      return timeLeft / (double)this.getDurationInMillis() < 0.01D;
   }
}
