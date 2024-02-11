package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.CountryCode;
import org.json.JSONObject;

public class Address implements JsonConvertible {
   private final String firstName;
   private final String middleName;
   private final String lastName;
   private final String street;
   private final String houseNumber;
   private final String houseNumberAddition;
   private final String postalCode;
   private final String city;
   private final CountryCode countryCode;

   public Address(Builder builder) {
      this.firstName = builder.firstName;
      this.middleName = builder.middleName;
      this.lastName = builder.lastName;
      this.street = builder.street;
      this.houseNumber = builder.houseNumber;
      this.houseNumberAddition = builder.houseNumberAddition;
      this.postalCode = builder.postalCode;
      this.city = builder.city;
      this.countryCode = builder.countryCode;
   }

   public JSONObject asJson() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("firstName", this.firstName);
      jsonObject.put("middleName", this.middleName);
      jsonObject.put("lastName", this.lastName);
      jsonObject.put("street", this.street);
      jsonObject.put("houseNumber", this.houseNumber);
      jsonObject.put("houseNumberAddition", this.houseNumberAddition);
      jsonObject.put("postalCode", this.postalCode);
      jsonObject.put("city", this.city);
      jsonObject.put("countryCode", this.countryCode.toString());
      return jsonObject;
   }

   public String getFirstName() {
      return this.firstName;
   }

   public String getMiddleName() {
      return this.middleName;
   }

   public String getLastName() {
      return this.lastName;
   }

   public String getStreet() {
      return this.street;
   }

   public String getHouseNumber() {
      return this.houseNumber;
   }

   public String getHouseNumberAddition() {
      return this.houseNumberAddition;
   }

   public String getPostalCode() {
      return this.postalCode;
   }

   public String getCity() {
      return this.city;
   }

   public CountryCode getCountryCode() {
      return this.countryCode;
   }

   public List<String> getSignatureData() {
      List<String> signatureData = new ArrayList();
      signatureData.add(this.firstName);
      signatureData.add(this.middleName);
      signatureData.add(this.lastName);
      signatureData.add(this.street);
      if (this.houseNumber != null) {
         signatureData.add(this.houseNumber);
      }

      if (this.houseNumberAddition != null) {
         signatureData.add(this.houseNumberAddition);
      }

      signatureData.add(this.postalCode);
      signatureData.add(this.city);
      signatureData.add(this.countryCode.name());
      return signatureData;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o == null) {
         return false;
      } else if (this.getClass() != o.getClass()) {
         return false;
      } else {
         Address address = (Address)o;
         return Objects.equals(this.firstName, address.firstName) && Objects.equals(this.middleName, address.middleName) && Objects.equals(this.lastName, address.lastName) && Objects.equals(this.street, address.street) && Objects.equals(this.houseNumber, address.houseNumber) && Objects.equals(this.houseNumberAddition, address.houseNumberAddition) && Objects.equals(this.postalCode, address.postalCode) && Objects.equals(this.city, address.city) && this.countryCode == address.countryCode;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.firstName, this.middleName, this.lastName, this.street, this.houseNumber, this.houseNumberAddition, this.postalCode, this.city, this.countryCode});
   }

   public static class Builder {
      private String firstName;
      private String middleName;
      private String lastName;
      private String street;
      private String houseNumber;
      private String houseNumberAddition;
      private String postalCode;
      private String city;
      private CountryCode countryCode;

      public Builder withFirstName(String firstName) {
         this.firstName = firstName;
         return this;
      }

      public Builder withMiddleName(String middleName) {
         this.middleName = middleName;
         return this;
      }

      public Builder withLastName(String lastName) {
         this.lastName = lastName;
         return this;
      }

      public Builder withStreet(String street) {
         this.street = street;
         return this;
      }

      public Builder withHouseNumber(String houseNumber) {
         this.houseNumber = houseNumber;
         return this;
      }

      public Builder withHouseNumberAddition(String houseNumberAddition) {
         this.houseNumberAddition = houseNumberAddition;
         return this;
      }

      public Builder withPostalCode(String postalCode) {
         this.postalCode = postalCode;
         return this;
      }

      public Builder withCity(String city) {
         this.city = city;
         return this;
      }

      public Builder withCountryCode(CountryCode countryCode) {
         this.countryCode = countryCode;
         return this;
      }

      public Address build() {
         return new Address(this);
      }
   }
}
