package com.trido.aibin;

import java.util.List;

public class FetchData {

    private static FetchData fetchData = new FetchData();

    private FetchData(){
        Database.getInstance().open();
    }

    public static FetchData getInstance(){
        return fetchData;
    }

    public Garbage getGarbage(){
        return Database.getInstance().queryGarbageInfo();
    }

    // ================= Classify the garbage ================= //
    public void updateGarbageQuantity(String typeOfGarbage, int quantity){
        Garbage garbage = getGarbage();
        int count = 0;
        if(garbage.getRecycle() == 20){
            count++;
        }
        if(garbage.getCompost() == 20){
            count++;
        }
        if(garbage.getLandfill() == 20){
            count++;
        }
        if(garbage.getEwaste() == 20){
            count++;
        }
        System.out.println("Count: " + count);
        if(typeOfGarbage.equals("recycle")){
            // get the quantity in database and add to the new one
            int numOfRecycle = garbage.getRecycle();

            if(numOfRecycle == 19) {
                Database.getInstance().updateGarbageWithStatus(numOfRecycle + 1, typeOfGarbage, count + 1 + "/4");
            }else if(numOfRecycle < 19){
                numOfRecycle = numOfRecycle + quantity;
                Database.getInstance().updateGarbage(numOfRecycle, typeOfGarbage);
            }

        }else if(typeOfGarbage.equals("compost")){
            // get the quantity in database and add to the new one
            int numOfCompost = garbage.getCompost();

            if(numOfCompost == 19){
                Database.getInstance().updateGarbageWithStatus(numOfCompost + 1, typeOfGarbage, count + 1 + "/4");
            }else if(numOfCompost < 19){
                numOfCompost = numOfCompost + quantity;
                Database.getInstance().updateGarbage(numOfCompost, typeOfGarbage);
            }

        }else if(typeOfGarbage.equals("landfill")){
            // get the quantity in database and add to the new one
            int numOfLandfill = garbage.getLandfill() + quantity;
            if(numOfLandfill == 19){
                Database.getInstance().updateGarbageWithStatus(numOfLandfill + 1, typeOfGarbage, count + 1 + "/4");
            }else if(numOfLandfill < 19){
                Database.getInstance().updateGarbage(numOfLandfill, typeOfGarbage);
            }

        }else if(typeOfGarbage.equals("ewaste")){
            int numOfEwaste = garbage.getEwaste() + quantity;
            if(numOfEwaste == 19){
                Database.getInstance().updateGarbageWithStatus(numOfEwaste + 1, typeOfGarbage, count + 1 + "/4");
            }else if(numOfEwaste < 19){
                Database.getInstance().updateGarbage(numOfEwaste, typeOfGarbage);
            }
        }
        // ====================== Original ====================== //
//        Garbage garbage = getGarbage();
//        int count = 0;
//        if(garbage.getRecycle() == 5){
//            count++;
//        }
//        if(garbage.getCompost() == 5){
//            count++;
//        }
//        if(garbage.getLandfill() == 5){
//            count++;
//        }
//        if(garbage.getEwaste() == 5){
//            count++;
//        }
//        System.out.println("Count: " + count);
//        if(typeOfGarbage.equals("recycle")){
//            // get the quantity in database and add to the new one
//            int numOfRecycle = garbage.getRecycle();
//
//            if(numOfRecycle == 4) {
//                Database.getInstance().updateGarbageWithStatus(numOfRecycle + 1, typeOfGarbage, count + 1 + "/4");
//            }else if(numOfRecycle < 4){
//                numOfRecycle = numOfRecycle + quantity;
//                Database.getInstance().updateGarbage(numOfRecycle, typeOfGarbage);
//            }
//
//        }else if(typeOfGarbage.equals("compost")){
//            // get the quantity in database and add to the new one
//            int numOfCompost = garbage.getCompost();
//
//            if(numOfCompost == 4){
//                Database.getInstance().updateGarbageWithStatus(numOfCompost + 1, typeOfGarbage, count + 1 + "/4");
//            }else if(numOfCompost < 4){
//                numOfCompost = numOfCompost + quantity;
//                Database.getInstance().updateGarbage(numOfCompost, typeOfGarbage);
//            }
//
//        }else if(typeOfGarbage.equals("landfill")){
//            // get the quantity in database and add to the new one
//            int numOfLandfill = garbage.getLandfill() + quantity;
//            if(numOfLandfill == 4){
//                Database.getInstance().updateGarbageWithStatus(numOfLandfill + 1, typeOfGarbage, count + 1 + "/4");
//            }else if(numOfLandfill < 4){
//                Database.getInstance().updateGarbage(numOfLandfill, typeOfGarbage);
//            }
//
//        }else if(typeOfGarbage.equals("ewaste")){
//            int numOfEwaste = garbage.getEwaste() + quantity;
//            if(numOfEwaste == 4){
//                Database.getInstance().updateGarbageWithStatus(numOfEwaste + 1, typeOfGarbage, count + 1 + "/4");
//            }else if(numOfEwaste < 4){
//                Database.getInstance().updateGarbage(numOfEwaste, typeOfGarbage);
//            }
//        }
    }

    public List<Garbage> getGarbageInfos(){
        return Database.getInstance().queryGarbageInfos();
    }

    public void cleanOneTrash(String typeOfTrash, int id){
        Garbage garbage = getGarbage();
        int recycle = garbage.getRecycle();
        int compost = garbage.getCompost();
        int landfill = garbage.getLandfill();
        int ewaste = garbage.getEwaste();
        int count = 0;
        if(recycle == 20){
            count++;
        }
        if(compost == 20){
            count++;
        }
        if(landfill == 20){
            count++;
        }

        if(ewaste == 20){
            count++;
        }

        if(count <= 19 && count > 0){
            count--;
        }

        if(Database.getInstance().cleanOneTrash(0, typeOfTrash, id, count)){
            System.out.println("Clean one trash successfully");
        }else{
            System.out.println("Cannot clean the trash");
        }
    }

    public void cleanAll(int id){
        if(Database.getInstance().cleanAll(id)){
            System.out.println("Clean all trash successfully");
        }else{
            System.out.println("Cannot clean all the trash");
        }
    }

    // =================== Return CUSTOMIZE JSON ====================//
    public CustomizeJSON<Product> login(User user){
        User queryUser = Database.getInstance().queryUser(user);

        System.out.println(queryUser.getId());
        if(queryUser.getUsername() != null){
            CustomizeJSON<Product> productCustomizeJSON = new CustomizeJSON<>();
            productCustomizeJSON.setUser_id(queryUser.getId());
            productCustomizeJSON.setProducts(Database.getInstance().getProducts(queryUser.getId()));
            return productCustomizeJSON;
        }else{
            return null;
        }
    }

    public boolean addProduct(Product product){
        if(Database.getInstance().addProduct(product)){
            System.out.println("Successfully add product");
            return true;
        }else{
            return false;
        }
    }

    public List<Product> getProducts(){
        return Database.getInstance().getAllProducts();
    }

    public String register(User user){
        String text = "";
        if(Database.getInstance().addUser(user)){
            text = "Successfully";
        }else{
            text = "Failed";
        }
        return text;
    }

}
