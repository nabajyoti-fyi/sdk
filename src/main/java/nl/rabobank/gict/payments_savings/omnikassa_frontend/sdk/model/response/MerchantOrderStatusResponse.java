package nl.rabobank.gict.payments_savings.omnikassa_frontend.sdk.model.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class MerchantOrderStatusResponse extends SignedResponse {
   private final boolean moreOrderResultsAvailable;
   private final List<MerchantOrderResult> orderResults;

   public MerchantOrderStatusResponse(JSONObject jsonObject) {
      super(jsonObject);
      JSONArray jsonArray = jsonObject.getJSONArray("orderResults");
      this.orderResults = new ArrayList();

      for(int i = 0; i < jsonArray.length(); ++i) {
         MerchantOrderResult orderResult = new MerchantOrderResult(jsonArray.getJSONObject(i));
         this.orderResults.add(orderResult);
      }

      this.moreOrderResultsAvailable = jsonObject.getBoolean("moreOrderResultsAvailable");
   }

   public boolean moreOrderResultsAvailable() {
      return this.moreOrderResultsAvailable;
   }

   public List<MerchantOrderResult> getOrderResults() {
      return this.orderResults;
   }

   public List<String> getSignatureData() {
      List<String> data = new ArrayList();
      data.add(String.valueOf(this.moreOrderResultsAvailable));
      Iterator i$ = this.orderResults.iterator();

      while(i$.hasNext()) {
         MerchantOrderResult result = (MerchantOrderResult)i$.next();
         data.addAll(result.getSignatureData());
      }

      return data;
   }
}
