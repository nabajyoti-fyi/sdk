package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums;

public enum VatCategory {
   HIGH("1"),
   LOW("2"),
   ZERO("3"),
   NONE("4");

   private final String value;

   private VatCategory(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }
}
