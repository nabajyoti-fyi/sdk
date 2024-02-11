package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk;

import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.ApiConnector;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.connector.TokenProvider;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.InvalidAccessTokenException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.AccessToken;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.MerchantOrder;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.request.MerchantOrderRequest;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.ApiNotification;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response.MerchantOrderStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Endpoint {
   private static final Logger LOGGER = LoggerFactory.getLogger(Endpoint.class);
   private final ApiConnector connector;
   private final TokenProvider tokenProvider;
   private final byte[] signingKey;

   Endpoint(ApiConnector connector, TokenProvider tokenProvider, byte[] signingKey) {
      this.connector = connector;
      this.tokenProvider = tokenProvider;
      this.signingKey = signingKey;
   }

   public static Endpoint createInstance(String baseURL, byte[] signingKey, TokenProvider tokenProvider) {
      ApiConnector connector = new ApiConnector(baseURL, signingKey);
      return new Endpoint(connector, tokenProvider, signingKey);
   }

   public String announceMerchantOrder(MerchantOrder merchantOrder) throws RabobankSdkException {
      this.validateAccessToken();
      MerchantOrderRequest merchantOrderRequest = new MerchantOrderRequest(merchantOrder, this.signingKey);

      try {
         return this.doAnnounceMerchantOrder(merchantOrderRequest);
      } catch (InvalidAccessTokenException var4) {
         LOGGER.warn("The token which we assumed to be valid was not accepted, forcing a new access token and retrying", var4);
         this.retrieveNewToken();
         return this.doAnnounceMerchantOrder(merchantOrderRequest);
      }
   }

   private String doAnnounceMerchantOrder(MerchantOrderRequest merchantOrderRequest) throws RabobankSdkException {
      return this.connector.announceMerchantOrder(merchantOrderRequest, this.tokenProvider.getAccessToken()).getRedirectUrl();
   }

   public MerchantOrderStatusResponse retrieveAnnouncement(ApiNotification apiNotification) throws RabobankSdkException {
      apiNotification.validateSignature(this.signingKey);
      return this.connector.getAnnouncementData(apiNotification);
   }

   private void validateAccessToken() throws RabobankSdkException {
      if (this.tokenProvider.hasNoValidAccessToken()) {
         this.retrieveNewToken();
      }

   }

   private void retrieveNewToken() throws RabobankSdkException {
      LOGGER.debug("Trying to retrieve new AccessToken from the Rabobank");
      AccessToken retrievedToken = this.connector.retrieveNewToken(this.tokenProvider.getRefreshToken());
      this.tokenProvider.setAccessToken(retrievedToken);
   }
}
