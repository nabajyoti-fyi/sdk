package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions;

public class InvalidAccessTokenException extends IllegalApiResponseException {
   public static final int INVALID_AUTHORIZATION_ERROR_CODE = 5001;

   public InvalidAccessTokenException(String message) {
      super(5001, message);
   }

   InvalidAccessTokenException() {
      super(5001);
   }
}
