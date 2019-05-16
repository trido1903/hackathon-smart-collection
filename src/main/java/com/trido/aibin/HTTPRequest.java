package com.trido.aibin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HTTPRequest {
    private static final String TARGET_URL =
            "https://exp.host/--/api/v2/push/send";
    private static final String TOKEN = "ExponentPushToken[eP3XVlMskNy5xuDZy551-T]";

    private static HTTPRequest instance = new HTTPRequest();

    private HTTPRequest(){
        Database.getInstance().open();
    }

    public static HTTPRequest requestInstance(){
        return instance;
    }

    public void configRequest() {
        try {
            URL url = new URL(TARGET_URL);
            try{

                URLConnection urlConnection = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json, charset=UTF-8" );
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                String input = "{\"to\":\"" + TOKEN + "\", \"body\":\"successfully\"}";

                OutputStream os = httpURLConnection.getOutputStream();
                os.write(input.getBytes("UTF-8"));
                os.flush();
                os.close();

//                if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//                    throw new RuntimeException("Failed : HTTP error code : "
//                            + httpURLConnection.getResponseCode());
//                }
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
//                bufferedWriter.write("{\"requests\": [{" +
//                        "\"image\": {\"content\": \"" + encodedImage + "\"}," +
//                        "\"features\": " +
//                        "[{\"type\": \"LABEL_DETECTION\", \"maxResults\": 20}]}]}");
//                bufferedWriter.close();
//
//                String response = httpURLConnection.getResponseMessage();
//                if (httpURLConnection.getInputStream() == null) {
//                    System.out.println("No stream");
//                }
//                Scanner httpResponseScanner = new Scanner (httpURLConnection.getInputStream());
//                String resp = "";
//
//                while (httpResponseScanner.hasNext()) {
//                    String line = httpResponseScanner.nextLine();
//                    resp += line;
//                    System.out.println(line);
//                }
//
//                httpResponseScanner.close();
//
//                //**************** Json Parser ******************//
//                JsonParser parser = new JsonParser();
//                JsonObject rootObj = parser.parse(resp).getAsJsonObject();
//                JsonObject reponsesObj = rootObj.getAsJsonArray("responses").get(0).getAsJsonObject();
//                JsonArray labelArr = reponsesObj.getAsJsonArray("labelAnnotations");
//
//                String typeOfGarBage = "";
//                for(int i = 0; i < labelArr.size(); i++){
//                    String type = labelArr.get(i).getAsJsonObject().get("description").getAsString();
//
//                    //======================= COMPOST (FOOD) =======================//
//                    if(type.equals("Vegetable") || type.equals("Food") || type.equals("Fruit") || type.equals("Plant")){
//                        System.out.println("COMPOST");
//
//                        typeOfGarBage = "compost";
//                        break;
//
//                    //======================= RECYCLING (FOOD) =======================//
//                    }else if(type.equals("Drink") || type.equals("Beer") || type.equals("Alcoholic beverage") || type.equals("Bottle")
//                        || type.equals("Glass bottle")|| type.equals("Alcohol") || type.equals("Liqueur")){
//                        System.out.println("RECYCLING");
//                        typeOfGarBage = "recycle";
//                        break;
//                    }else{
//                        typeOfGarBage = "decompost";
//                        break;
//                    }
//                }
//                System.out.println(typeOfGarBage);
//                updateGarbageQuantity(typeOfGarBage, 1);
//
            }catch (IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }catch (MalformedURLException malioe){
            System.out.println(malioe.getMessage());
        }
    }

    // ===================== UPDATE THE GARBAGE QUANTITY===================== //
//    public void updateGarbageQuantity(String typeOfGarbage, int quantity){
//        Garbage garbage = getGarbage();
//        if(typeOfGarbage.equals("recycle")){
//            // get the quantity in database and add to the new one
//            int numOfRecycle = garbage.getRecycle() + quantity;
//            Database.getInstance().updateGarbage(numOfRecycle, typeOfGarbage);
//        }else if(typeOfGarbage.equals("compost")){
//            // get the quantity in database and add to the new one
//            int numOfCompost = garbage.getCompost() + quantity;
//            Database.getInstance().updateGarbage(numOfCompost, typeOfGarbage);
//
//        }else if(typeOfGarbage.equals("decompost")){
//            // get the quantity in database and add to the new one
//            int numOfDecompose= garbage.getLandfill() + quantity;
//            Database.getInstance().updateGarbage(numOfDecompose, typeOfGarbage);
//        }
//    }

    // ===================== QUERY GARBAGE BEFORE UPDATE ===================== //
//    private Garbage getGarbage(){
//        return Database.getInstance().queryGarbageInfo();
//    }
}
