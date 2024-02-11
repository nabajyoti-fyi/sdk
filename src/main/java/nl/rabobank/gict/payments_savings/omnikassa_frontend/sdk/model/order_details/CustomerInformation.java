package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.order_details;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.JsonConvertible;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.enums.Gender;
import org.json.JSONObject;

public final class CustomerInformation implements JsonConvertible {
   private final String emailAddress;
   private final String dateOfBirth;
   private final String initials;
   private final String telephoneNumber;
   private final Gender gender;

   CustomerInformation(Builder builder) {
      this.emailAddress = builder.emailAddress;
      this.dateOfBirth = builder.dateOfBirth;
      this.gender = builder.gender;
      this.initials = builder.initials;
      this.telephoneNumber = builder.telephoneNumber;
   }

   public String getEmailAddress() {
      return this.emailAddress;
   }

   public Gender getGender() {
      return this.gender;
   }

   public String getInitials() {
      return this.initials;
   }

   public String getTelephoneNumber() {
      return this.telephoneNumber;
   }

   public String getDateOfBirth() {
      return this.dateOfBirth;
   }

   public List<String> getSignatureData() {
      return Arrays.asList(this.emailAddress, this.dateOfBirth, this.getNullSafe(this.gender), this.initials, this.telephoneNumber);
   }

   public JSONObject asJson() {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("emailAddress", this.emailAddress);
      jsonObject.put("dateOfBirth", this.dateOfBirth);
      jsonObject.put("gender", this.getNullSafe(this.gender));
      jsonObject.put("initials", this.initials);
      jsonObject.put("telephoneNumber", this.telephoneNumber);
      return jsonObject;
   }

   private String getNullSafe(Enum value) {
      return value == null ? null : value.name();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof CustomerInformation)) {
         return false;
      } else {
         CustomerInformation that = (CustomerInformation)o;
         return Objects.equals(this.emailAddress, that.emailAddress) && Objects.equals(this.dateOfBirth, that.dateOfBirth) && Objects.equals(this.initials, that.initials) && Objects.equals(this.telephoneNumber, that.telephoneNumber) && this.gender == that.gender;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.emailAddress, this.dateOfBirth, this.initials, this.telephoneNumber, this.gender});
   }

   public static class Builder {
      private String emailAddress;
      private Gender gender;
      private String initials;
      private String telephoneNumber;
      private String dateOfBirth;

      public Builder withEmailAddress(String emailAddress) {
         this.emailAddress = emailAddress;
         return this;
      }

      public Builder withGender(Gender gender) {
         this.gender = gender;
         return this;
      }

      public Builder withInitials(String initials) {
         this.initials = initials;
         return this;
      }

      public Builder withTelephoneNumber(String telephoneNumber) {
         this.telephoneNumber = telephoneNumber;
         return this;
      }

      public Builder withDateOfBirth(String dateOfBirth) {
         this.dateOfBirth = dateOfBirth;
         return this;
      }

      public CustomerInformation build() {
         return new CustomerInformation(this);
      }
   }
}
