package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import java.math.BigDecimal;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Currency;
import org.json.JSONObject;

public final class Money implements JsonConvertible {
   private final Currency currency;
   private final BigDecimal amount;

   private Money(Currency currency, BigDecimal amount) {
      this.currency = currency;
      this.amount = amount;
   }

   public Currency getCurrency() {
      return this.currency;
   }

   public BigDecimal getAmount() {
      return this.amount;
   }

   public static Money fromEuros(Currency currency, BigDecimal amount) {
      checkAmount(amount);
      return new Money(currency, amount);
   }

   public static Money fromJson(JSONObject jsonObject) {
      Currency currency = (Currency)jsonObject.getEnum(Currency.class, "currency");
      BigDecimal amount = parseAmount(jsonObject.getString("amount"));
      return new Money(currency, amount);
   }

   private static void checkAmount(BigDecimal amount) {
      if (getNumberOfDecimalPlaces(amount) > 2) {
         throw new IllegalArgumentException("amount must have at most 2 decimal places, and must be a valid number");
      }
   }

   private static int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
      return Math.max(0, bigDecimal.stripTrailingZeros().scale());
   }

   private static BigDecimal parseAmount(String amountString) {
      BigDecimal amountBigDecimal = new BigDecimal(amountString);
      amountBigDecimal = amountBigDecimal.movePointLeft(2);
      checkAmount(amountBigDecimal);
      return amountBigDecimal;
   }

   public long getAmountInCents() {
      BigDecimal hundred = new BigDecimal(100);
      BigDecimal rounded = this.amount.setScale(2, 7);
      return rounded.multiply(hundred).longValueExact();
   }

   public JSONObject asJson() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("currency", this.currency.toString());
      jsonObject.put("amount", String.valueOf(this.getAmountInCents()));
      return jsonObject;
   }
}
