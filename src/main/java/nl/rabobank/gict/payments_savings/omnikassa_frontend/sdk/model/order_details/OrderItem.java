package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import java.util.ArrayList;
import java.util.List;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.Money;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.ItemCategory;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.VatCategory;
import org.json.JSONObject;

public final class OrderItem implements JsonConvertible {
   private final int quantity;
   private final String id;
   private final String name;
   private final String description;
   private final Money amount;
   private final Money tax;
   private final ItemCategory category;
   private final VatCategory vatCategory;

   /** @deprecated */
   @Deprecated
   public OrderItem(int quantity, String name, String description, Money amount, Money tax, ItemCategory category) {
      this.quantity = quantity;
      this.name = name;
      this.description = description;
      this.amount = amount;
      this.tax = tax;
      this.category = category;
      this.id = null;
      this.vatCategory = null;
   }

   public OrderItem(Builder builder) {
      this.id = builder.id;
      this.quantity = builder.quantity;
      this.name = builder.name;
      this.description = builder.description;
      this.amount = builder.amount;
      this.tax = builder.tax;
      this.category = builder.category;
      this.vatCategory = builder.vatCategory;
   }

   public String getId() {
      return this.id;
   }

   public ItemCategory getCategory() {
      return this.category;
   }

   public Money getTax() {
      return this.tax;
   }

   public Money getAmount() {
      return this.amount;
   }

   public String getDescription() {
      return this.description;
   }

   public String getName() {
      return this.name;
   }

   public int getQuantity() {
      return this.quantity;
   }

   public VatCategory getVatCategory() {
      return this.vatCategory;
   }

   public List<String> getSignatureData() {
      List<String> signatureData = new ArrayList();
      if (this.id != null) {
         signatureData.add(this.id);
      }

      signatureData.add(this.name);
      signatureData.add(this.description);
      signatureData.add(String.valueOf(this.quantity));
      signatureData.add(this.getAmount().getCurrency().name());
      signatureData.add(String.valueOf(this.getAmount().getAmountInCents()));
      signatureData.add(this.getTax().getCurrency().name());
      signatureData.add(String.valueOf(this.getTax().getAmountInCents()));
      signatureData.add(this.category.name());
      if (this.vatCategory != null) {
         signatureData.add(this.vatCategory.getValue());
      }

      return signatureData;
   }

   public JSONObject asJson() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("id", this.id);
      jsonObject.put("quantity", this.quantity);
      jsonObject.put("name", this.name);
      jsonObject.put("description", this.description);
      jsonObject.put("amount", this.amount.asJson());
      jsonObject.put("tax", this.tax.asJson());
      jsonObject.put("category", this.category.name());
      jsonObject.put("vatCategory", this.getNullSafe(this.vatCategory));
      return jsonObject;
   }

   private String getNullSafe(VatCategory vatCategory) {
      return vatCategory != null ? vatCategory.getValue() : null;
   }

   public static class Builder {
      private int quantity;
      private String id;
      private String name;
      private Money amount;
      private Money tax;
      private ItemCategory category;
      private VatCategory vatCategory;
      private String description;

      public Builder withId(String id) {
         this.id = id;
         return this;
      }

      public Builder withQuantity(int quantity) {
         this.quantity = quantity;
         return this;
      }

      public Builder withName(String name) {
         this.name = name;
         return this;
      }

      public Builder withDescription(String description) {
         this.description = description;
         return this;
      }

      public Builder withAmount(Money amount) {
         this.amount = amount;
         return this;
      }

      public Builder withTax(Money tax) {
         this.tax = tax;
         return this;
      }

      public Builder withItemCategory(ItemCategory category) {
         this.category = category;
         return this;
      }

      public Builder withVatCategory(VatCategory vatCategory) {
         this.vatCategory = vatCategory;
         return this;
      }

      public OrderItem build() {
         return new OrderItem(this);
      }
   }
}
