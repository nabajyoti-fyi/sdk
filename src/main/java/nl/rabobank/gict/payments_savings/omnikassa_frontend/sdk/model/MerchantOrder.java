package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Language;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrand;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.PaymentBrandForce;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.Address;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.CustomerInformation;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details.OrderItem;

public final class MerchantOrder {
   private final String merchantOrderId;
   private final Money amount;
   private final Language language;
   private final String description;
   private final String merchantReturnURL;
   private final List<OrderItem> orderItems;
   private final Address shippingDetails;
   private final Address billingDetails;
   private final CustomerInformation customerInformation;
   private final PaymentBrand paymentBrand;
   private final PaymentBrandForce paymentBrandForce;

   private MerchantOrder(Builder builder) {
      this.merchantOrderId = builder.merchantOrderId;
      this.amount = builder.amount;
      this.language = builder.language;
      this.description = builder.description;
      this.merchantReturnURL = builder.merchantReturnURL;
      this.orderItems = Collections.unmodifiableList(builder.orderItems);
      this.shippingDetails = builder.shippingDetails;
      this.billingDetails = builder.billingDetails;
      this.customerInformation = builder.customerInformation;
      this.paymentBrand = builder.paymentBrand;
      this.paymentBrandForce = builder.paymentBrandForce;
   }

   public String getMerchantOrderId() {
      return this.merchantOrderId;
   }

   public Money getAmount() {
      return this.amount;
   }

   public Language getLanguage() {
      return this.language;
   }

   public String getDescription() {
      return this.description;
   }

   public String getMerchantReturnURL() {
      return this.merchantReturnURL;
   }

   public List<OrderItem> getOrderItems() {
      return this.orderItems;
   }

   public Address getShippingDetail() {
      return this.shippingDetails;
   }

   public Address getBillingDetail() {
      return this.billingDetails;
   }

   public PaymentBrand getPaymentBrand() {
      return this.paymentBrand;
   }

   public PaymentBrandForce getPaymentBrandForce() {
      return this.paymentBrandForce;
   }

   public CustomerInformation getCustomerInformation() {
      return this.customerInformation;
   }

   // $FF: synthetic method
   MerchantOrder(Builder x0, Object x1) {
      this(x0);
   }

   public static class Builder {
      private String merchantOrderId;
      private Money amount;
      private Language language;
      private String description;
      private String merchantReturnURL;
      private List<OrderItem> orderItems = new ArrayList();
      private Address shippingDetails;
      private Address billingDetails;
      private CustomerInformation customerInformation;
      private PaymentBrand paymentBrand;
      private PaymentBrandForce paymentBrandForce;

      public Builder withMerchantOrderId(String merchantOrderId) {
         this.merchantOrderId = merchantOrderId;
         return this;
      }

      public Builder withAmount(Money amount) {
         this.amount = amount;
         return this;
      }

      public Builder withLanguage(Language language) {
         this.language = language;
         return this;
      }

      public Builder withDescription(String description) {
         this.description = description;
         return this;
      }

      public Builder withMerchantReturnURL(String merchantReturnURL) {
         this.merchantReturnURL = merchantReturnURL;
         return this;
      }

      public Builder withOrderItems(List<OrderItem> orderItems) {
         this.orderItems = orderItems;
         return this;
      }

      public Builder withShippingDetail(Address shippingDetails) {
         this.shippingDetails = shippingDetails;
         return this;
      }

      public Builder withBillingDetail(Address billingDetails) {
         this.billingDetails = billingDetails;
         return this;
      }

      public Builder withPaymentBrand(PaymentBrand paymentBrand) {
         this.paymentBrand = paymentBrand;
         return this;
      }

      public Builder withPaymentBrandForce(PaymentBrandForce paymentBrandForce) {
         this.paymentBrandForce = paymentBrandForce;
         return this;
      }

      public Builder withCustomerInformation(CustomerInformation customerInformation) {
         this.customerInformation = customerInformation;
         return this;
      }

      public MerchantOrder build() {
         return new MerchantOrder(this);
      }
   }
}
