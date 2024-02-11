package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector;

import com.mashape.unirest.http.exceptions.UnirestException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.IllegalApiResponseException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.SignedResponse;
import org.json.JSONObject;

public class ApiConnector {
   private final byte[] signingKey;
   private final UnirestJSONTemplate jsonTemplate;

   ApiConnector(UnirestJSONTemplate jsonTemplate, byte[] signingKey) {
      this.jsonTemplate = jsonTemplate;
      this.signingKey = signingKey;
   }

   public ApiConnector(String baseURL, byte[] signingKey) {
      this(new UnirestJSONTemplate(baseURL), signingKey);
   }

   public MerchantOrderResponse announceMerchantOrder(final MerchantOrderRequest order, final String token) throws RabobankSdkException {
      return (MerchantOrderResponse)(new ApiConnector.SignedRequestTemplate<MerchantOrderResponse>() {
         JSONObject fetch() throws UnirestException {
            return ApiConnector.this.jsonTemplate.post("order/server/api/order", order, token);
         }

         MerchantOrderResponse convert(JSONObject result) {
            return new MerchantOrderResponse(result);
         }
      }).execute();
   }

   public MerchantOrderStatusResponse getAnnouncementData(final ApiNotification apiNotification) throws RabobankSdkException {
      return (MerchantOrderStatusResponse)(new ApiConnector.SignedRequestTemplate<MerchantOrderStatusResponse>() {
         JSONObject fetch() throws UnirestException {
            return ApiConnector.this.jsonTemplate.get("order/server/api/events/results/" + apiNotification.getEventName(), apiNotification.getAuthentication());
         }

         MerchantOrderStatusResponse convert(JSONObject result) {
            return new MerchantOrderStatusResponse(result);
         }
      }).execute();
   }

   public AccessToken retrieveNewToken(final String refreshToken) throws RabobankSdkException {
      return (AccessToken)(new ApiConnector.RequestTemplate<AccessToken>() {
         JSONObject fetch() throws UnirestException {
            return ApiConnector.this.jsonTemplate.get("gatekeeper/refresh", refreshToken);
         }

         AccessToken convert(JSONObject result) {
            return new AccessToken(result);
         }
      }).execute();
   }

   private abstract static class RequestTemplate<T> {
      private RequestTemplate() {
      }

      final T execute() throws RabobankSdkException {
         return this.fetchValidateAndConvert();
      }

      T fetchValidateAndConvert() throws RabobankSdkException {
         try {
            JSONObject jsonResponse = this.fetch();
            this.checkedForErrorsInResponse(jsonResponse);
            return this.convert(jsonResponse);
         } catch (UnirestException var2) {
            throw new RabobankSdkException(var2);
         }
      }

      private void checkedForErrorsInResponse(JSONObject jsonObject) throws IllegalApiResponseException {
         if (jsonObject.has("errorCode")) {
            throw IllegalApiResponseException.of(jsonObject);
         }
      }

      abstract JSONObject fetch() throws UnirestException;

      abstract T convert(JSONObject var1);

      // $FF: synthetic method
      RequestTemplate(Object x0) {
         this();
      }
   }

   private abstract class SignedRequestTemplate<T extends SignedResponse> extends ApiConnector.RequestTemplate<T> {
      private SignedRequestTemplate() {
         super(null);
      }

      final T fetchValidateAndConvert() throws RabobankSdkException {
         T converted = (T) super.fetchValidateAndConvert();
         converted.validateSignature(ApiConnector.this.signingKey);
         return converted;
      }

      // $FF: synthetic method
      SignedRequestTemplate(Object x1) {
         this();
      }
   }
}
