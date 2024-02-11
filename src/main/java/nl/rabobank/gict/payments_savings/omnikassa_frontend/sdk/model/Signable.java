package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model;

import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.exceptions.RabobankSdkException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

public abstract class Signable {
   private static final String HASH_ALGORITHM = "HmacSHA512";
   private String signature;

   protected abstract List<String> getSignatureData();

   public final void setSignature(String signature) {
      this.signature = signature;
   }

   protected String getSignature() {
      return this.signature;
   }

   public static String calculateSignature(List<String> parts, byte[] signingKey) throws RabobankSdkException {
      String payload = StringUtils.join(parts, ",");

      try {
         Mac instance = Mac.getInstance("HmacSHA512");
         SecretKeySpec secretKey = new SecretKeySpec(signingKey, "HmacSHA512");
         instance.init(secretKey);
         byte[] result = instance.doFinal(payload.getBytes(StandardCharsets.UTF_8));
         return Hex.encodeHexString(result);
      } catch (Exception var6) {
         throw new RabobankSdkException(var6);
      }
   }
}
