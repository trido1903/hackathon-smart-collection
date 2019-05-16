package com.trido.aibin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database db = new Database();
    private final String DB_NAME = "aibin.db";
    private final String CONN_DB = "jdbc:sqlite:/Users/trido/Desktop/" + DB_NAME;
    private Connection conn;

    public Database(){

    }

    public static Database getInstance(){
        return db;
    }

    //=================== OPEN CONNECTION =====================//
    public boolean open(){
        try{

            // required for creating database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(CONN_DB);
            return true;
        }catch (Exception e){
            System.out.println("Couldn't connect to database: " + e.getStackTrace());
            return false;
        }
    }
    //=================== CLOSE CONNECTION =====================//
    public void close(){
        if(conn != null){
            try {
                conn.close();
            }catch (SQLException e){
                System.out.println("Couldn't close database: " + e.getStackTrace());
            }
        }
    }

    // ========================================== USER ========================================== //

    public boolean addUser(User user){
        String sql = "INSERT INTO user (username, password) values (?, ?)";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            int affectedRow = preparedStatement.executeUpdate();
            if(affectedRow == 1){
                return true;
            }else {
                return false;
            }

        }catch (SQLException e){
            System.out.println("Some thing went wrong addUser(): " + e.getStackTrace());
            return false;
        }finally {
            try {
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.getStackTrace();
            }

        }

    }

    public User queryUser(User user){
        String sql = "SELECT id, username, password FROM USER";
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean found = false;
            User oldUser = new User();
            while(resultSet.next()) {

                int id = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String password = resultSet.getString(3);


                if(user.getUsername().equals(username) && user.getPassword().equals(password)){

                    oldUser.setId(id);
                    oldUser.setUsername(username);
                    oldUser.setPassword(password);
                    break;
                }
            }
            if(oldUser.getUsername() != null){
                return oldUser;
            }
            return null;
        }catch (SQLException e){
            System.out.println("Couldn't query garbage info: " + e.getMessage());
            return null;
        }finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.getStackTrace();
            }
        }
    }

    // ========================================== GARBAGE ========================================== //

    // ================= Get one Garbage ==================== //
    public Garbage queryGarbageInfo(){
        String sql = "SELECT * FROM garbage WHERE id = 1";
        Garbage garbage = new Garbage();
        try(Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);

            garbage.setId(resultSet.getInt(1));
            garbage.setRecycle(resultSet.getInt(2));
            garbage.setCompost(resultSet.getInt(3));
            garbage.setLandfill(resultSet.getInt(4));
            garbage.setEwaste(resultSet.getInt(5));
            garbage.setDescription(resultSet.getString(6));
            garbage.setFull(resultSet.getString(7));

            return garbage;
        }catch (SQLException e){
            System.out.println("Couldn't query garbage info: " + e.getMessage());
            return null;
        }
    }

    public List<Garbage> queryGarbageInfos(){
        String sql = "SELECT * FROM garbage";
        List<Garbage> garbages = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Garbage garbage = new Garbage();
                garbage.setId(resultSet.getInt(1));
                garbage.setRecycle(resultSet.getInt(2));
                garbage.setCompost(resultSet.getInt(3));
                garbage.setLandfill(resultSet.getInt(4));
                garbage.setEwaste(resultSet.getInt(5));
                garbage.setDescription(resultSet.getString(6));
                garbage.setFull(resultSet.getString(7));

                garbages.add(garbage);
            }
            return garbages;
        }catch (SQLException e){
            System.out.println("Couldn't query garbage info: " + e.getMessage());
            return null;
        }
    }


    public boolean updateGarbage(int quantity, String typeOfGarbage){
        // UPDATE  garbage SET recycle = 1 WHERE id = 1
        String sql = "UPDATE garbage set " + typeOfGarbage + " = ? WHERE id = 1";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quantity);
            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow == 1){
                System.out.println("Successfully updated garbage");
                return true;
            }else{
                System.out.println("Something went wrong while updating");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error from updateGarbage(): " + e.getMessage());
            return false;
        }finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }

            }catch (SQLException e){
                e.getStackTrace();
            }
        }
    }

    public boolean updateGarbageWithStatus(int quantity, String typeOfGarbage, String status){
        // UPDATE  garbage SET recycle = 1 WHERE id = 1
        if(status.equals("4/4")){
            status = "full";
        }
        String sql = "UPDATE garbage set " + typeOfGarbage + " = ?, status = ? WHERE id = 1";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setString(2, status);

            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow == 1){
                System.out.println("Successfully updated garbage");
                return true;
            }else{
                System.out.println("Something went wrong while updating");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error from updateGarbage(): " + e.getMessage());
            return false;
        }finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }

            }catch (SQLException e){
                e.getStackTrace();
            }
        }
    }

    public boolean cleanOneTrash(int quantity, String typeOfGarbage, int id, int status){
        // UPDATE  garbage SET recycle = 1 WHERE id = 1
        String sql = "UPDATE garbage set " + typeOfGarbage + " = ?, status = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, quantity);
            if(status == 0){
                preparedStatement.setString(2, "normal");
            }else{
                preparedStatement.setString(2, status + "/4");
            }

            preparedStatement.setInt(3, id);
            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow == 1){
                System.out.println("Successfully Clean one garbage");
                return true;
            }else{
                System.out.println("Something went wrong while updating");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error from updateGarbage(): " + e.getMessage());
            return false;
        }finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }

            }catch (SQLException e){
                e.getStackTrace();
            }
        }
    }

    public boolean cleanAll(int id){
        // UPDATE  garbage SET recycle = 1 WHERE id = 1
        String sql = "update garbage set recycle = 0, compost = 0, landfill = 0, ewaste = 0, status = " + "'normal'" + " WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int affectedRow = preparedStatement.executeUpdate();

            if(affectedRow == 1){
                System.out.println("Successfully Clean All garbage");
                return true;
            }else{
                System.out.println("Something went wrong while cleaning all garbage");
                return false;
            }
        }catch (SQLException e){
            System.out.println("Error from cleanAll(): " + e.getMessage());
            return false;
        }finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }

            }catch (SQLException e){
                e.getStackTrace();
            }
        }
    }
    // ========================================== PRODUCT ========================================== //
    // insert into product (title, description, address, lati, longi, date, user) values ('laptop', 'old', '1123 Main Street', 12.3, 14.2, 123213, 1)

    public boolean addProduct(Product product){
        String sql = "INSERT INTO product (title, description, address, lati, longi, date, user, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getAddress());
            preparedStatement.setDouble(4, product.getLati());
            preparedStatement.setDouble(5, product.getLongi());
            preparedStatement.setLong(6, product.getDate());
            preparedStatement.setInt(7, product.getUser_id());
            preparedStatement.setString(8, product.getImages());
            int affectedRow = preparedStatement.executeUpdate();
            if(affectedRow == 1){
                return true;
            }else {
                return false;
            }

        }catch (SQLException e){
            System.out.println("Some thing went wrong addProduct(): " + e.getStackTrace());
            return false;
        }finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.getStackTrace();
            }
        }
    }

    public List<Product> getProducts(int user_id){

        //SELECT fruit.name, user.name, user.bought, user.id from user inner join fruit on user.fruit = fruit.id WHERE user.id = ?
        String sql = "select product.id, product.title, product.description, product.address, product.lati, product.longi, product.date, product.user, product.images FROM product WHERE user = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setTitle(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setAddress(resultSet.getString(4));
                product.setLati(resultSet.getDouble(5));
                product.setLongi(resultSet.getDouble(6));
                product.setDate(resultSet.getLong(7));
                product.setUser_id(resultSet.getInt(8));
                product.setImages(resultSet.getString(9));
                products.add(product);
            }
            return products;
        }catch (SQLException e){
            System.out.println("Something went wrong getProducts(): " + e.getStackTrace());
            return null;

        }
    }

    public List<Product> getAllProducts(){

        //SELECT fruit.name, user.name, user.bought, user.id from user inner join fruit on user.fruit = fruit.id WHERE user.id = ?
        String sql = "select product.id, product.title, product.description, product.address, product.lati, product.longi, product.date, product.user, product.images FROM product";
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setTitle(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                product.setAddress(resultSet.getString(4));
                product.setLati(resultSet.getDouble(5));
                product.setLongi(resultSet.getDouble(6));
                product.setDate(resultSet.getLong(7));
                product.setUser_id(resultSet.getInt(8));
                product.setImages(resultSet.getString(9));
                products.add(product);
            }
            return products;
        }catch (SQLException e){
            System.out.println("Something went wrong getProducts(): " + e.getStackTrace());
            return null;
        }finally {
            try{
                if(preparedStatement != null){
                    preparedStatement.close();
                }
            }catch (SQLException e){
                e.getStackTrace();
            }
        }
    }
}
