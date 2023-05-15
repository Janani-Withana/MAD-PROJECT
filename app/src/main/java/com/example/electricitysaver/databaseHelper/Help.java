//package com.example.electricitysaver.databaseHelper;
//
//import android.widget.Toast;
//
//import com.stripe.android.PaymentConfiguration;
//import com.stripe.android.paymentsheet.PaymentSheet;
//import com.stripe.android.paymentsheet.PaymentSheetResult;
//
//import org.json.JSONObject;
//
//
//public class Help {
//
//    PaymentSheet.CustomerConfiguration config;
//    private void onResult(final PaymentSheetResult paymentSheetResult){
//        if (paymentSheetResult instanceof PaymentSheetResult.Canceled){
//            Toast.makeText(this, "Cancled", Toast.LENGTH_SHORT).show();
//        }
//        if (paymentSheetResult instanceof PaymentSheetResult.Failed){
//            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
//        }
//        if (paymentSheetResult instanceof PaymentSheetResult.Completed){
//            Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    RequestQueue queue = Volley.newRequestQueue(this);
//    String url ="https://projects.vishnusivadas.com/testing/write.php";
//
//    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    textView.setText("Response is: "+ response);
//                    JSONObject jsonObject = new JSONObject( response);
//                    config = new PaymentSheet.CustomerConfiguration(
//                            jsonObject.getString('customer')
//                            jsonObject.getString('ephemeralKey')
//                    );
//                    secrect = jsonObject.getString('paymentIntent');
//                    PaymentConfiguration.init(getApplictionContext(),jsonObject.getString('publishableKey'));
//
//                }
//            }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            textView.setText("That didn't work!");
//        }
//    }){
//        protected Map<String, String> getParams(){
//            Map<String, String> paramV = new HashMap<>();
//            paramV.put("param", "abc");
//            return paramV;
//        }
//    };
//        queue.add(stringRequest);
//
//        p
//}
