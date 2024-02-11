package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import java.util.Calendar;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.utils.CalendarUtils;
import org.apache.commons.lang3.StringUtils;

public abstract class TokenProvider {
   private AccessToken accessToken;

   public final String getAccessToken() {
      if (this.accessToken == null && this.hasAccessToken()) {
         this.accessToken = this.reCreateAccessToken();
      }

      return this.accessToken != null && this.accessToken.isNotExpired() ? this.accessToken.getToken() : null;
   }

   public final boolean hasNoValidAccessToken() {
      return this.getAccessToken() == null;
   }

   public final String getRefreshToken() {
      return this.getValue(TokenProvider.FieldName.REFRESH_TOKEN);
   }

   public final void setAccessToken(AccessToken accessToken) {
      this.accessToken = accessToken;
      this.setValue(TokenProvider.FieldName.ACCESS_TOKEN, accessToken.getToken());
      this.setValue(TokenProvider.FieldName.ACCESS_TOKEN_VALID_UNTIL, CalendarUtils.calendarToString(accessToken.getValidUntil()));
      this.setValue(TokenProvider.FieldName.ACCESS_TOKEN_DURATION, String.valueOf(accessToken.getDurationInMillis()));
   }

   private AccessToken reCreateAccessToken() {
      String token = this.getValue(TokenProvider.FieldName.ACCESS_TOKEN);
      String validUntil = this.getValue(TokenProvider.FieldName.ACCESS_TOKEN_VALID_UNTIL);
      String durationInMillis = this.getValue(TokenProvider.FieldName.ACCESS_TOKEN_DURATION);
      return new AccessToken(token, CalendarUtils.stringToCalendar(validUntil), Integer.parseInt(durationInMillis));
   }

   private boolean hasAccessToken() {
      String token = this.getValue(TokenProvider.FieldName.ACCESS_TOKEN);
      String validUntil = this.getValue(TokenProvider.FieldName.ACCESS_TOKEN_VALID_UNTIL);
      Calendar validUntilCalendar = CalendarUtils.stringToCalendar(validUntil);
      String durationInMillis = this.getValue(TokenProvider.FieldName.ACCESS_TOKEN_DURATION);
      return StringUtils.isNotBlank(token) && StringUtils.isNotBlank(validUntil) && StringUtils.isNotBlank(durationInMillis) && validUntilCalendar != null;
   }

   protected abstract String getValue(TokenProvider.FieldName var1);

   protected abstract void setValue(TokenProvider.FieldName var1, String var2);

   public static enum FieldName {
      REFRESH_TOKEN,
      ACCESS_TOKEN,
      ACCESS_TOKEN_VALID_UNTIL,
      ACCESS_TOKEN_DURATION;
   }
}
