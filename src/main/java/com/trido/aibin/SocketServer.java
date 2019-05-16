package com.trido.aibin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@javax.websocket.server.ServerEndpoint("/update")
public class SocketServer {
    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void handleOpen(Session session){
        clients.add(session);
        System.out.println("Client connect to server");
    }

    @OnClose
    public void handleClose(Session session){
        clients.remove(session);
        System.out.println("Client is disconnected !!!");
    }

    @OnError
    public void handleError(Throwable t){
        t.printStackTrace();
    }

    @OnMessage(maxMessageSize = 5242880)
    public void handleMessage(String message, Session session) throws IOException {
        synchronized(clients){

            boolean isJson = false;

            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parse(message).getAsJsonObject();
            String word = rootObj.get("message").getAsString();
            System.out.println(word);

            int id = rootObj.get("id").getAsInt();

            if (word.equals("list") && id == -1){
                String json = FetchData.getInstance().getGarbageInfos().toString();


            }else if(word.equals("list") && id == -2){
                String json = FetchData.getInstance().getProducts().toString();
                for(Session client : clients){
                    client.getBasicRemote().sendText(json);
                }
                // clean most one
            }else if(word.equals("clear")){
                String type = getTrashToClean();
                FetchData.getInstance().cleanOneTrash(type, id);

                // clean
            }else if(word.equals("clear all")){
                FetchData.getInstance().cleanAll(id);

            }else if(word.equals("recycle") || word.equals("compost") || word.equals("landfill") || word.equals("ewaste")){
                FetchData.getInstance().updateGarbageQuantity(word, 1);
            }else{
                // add product
                JsonParser parserMessage = new JsonParser();
                JsonObject rootMessage = parserMessage.parse(word).getAsJsonObject();

                String title = rootMessage.get("title").getAsString();
                String address = rootMessage.get("address").getAsString();
                String description = rootMessage.get("description").getAsString();
                double lati = rootMessage.get("lati").getAsDouble();
                double longi = rootMessage.get("longi").getAsDouble();
                long date = rootMessage.get("date").getAsLong();
                int user = rootMessage.get("user").getAsInt();
                String images = rootMessage.get("images").getAsString();

                Product product = new Product();
                product.setTitle(title);
                product.setAddress(address);
                product.setDescription(description);
                product.setLati(lati);
                product.setLongi(longi);
                product.setDate(date);
                product.setUser_id(user);
                product.setImages(images);
                FetchData.getInstance().addProduct(product);
                HTTPRequest.requestInstance().configRequest();
                isJson = true;
            }
//
            String jsonAll = "";


            if(isJson){
                jsonAll = FetchData.getInstance().getProducts().toString();
            }else{
                jsonAll = FetchData.getInstance().getGarbageInfos().toString();
            }
            // {message: ...., id: ......}
            for(Session client : clients){
                if(client.equals(session) && isJson){
                    client.getBasicRemote().sendText(jsonAll);
                }else {
                    client.getBasicRemote().sendText(jsonAll);
                }

            }
        }
    }

    public String getTrashToClean(){
        Garbage garbage = FetchData.getInstance().getGarbage();
        int recycle = garbage.getRecycle();
        int compost = garbage.getCompost();
        int landfill = garbage.getLandfill();
        int ewaste = garbage.getEwaste();
        int max = recycle;
        String type = "recycle";

        if(max < compost){
            max = compost;
            type = "compost";
        }
        if(max < landfill){
            max = landfill;
            type = "landfill";
        }
        if(max < ewaste){
            max = ewaste;
            type = "ewaste";
        }
        return type;
    }
//    public List<Product> sendAllProducts(){
//
//    }
}
