package model.da;

import model.domain.*;
import java.sql.*;
import java.util.ArrayList;

public final class ProductCategoryDA{
    private String host = "jdbc:derby://localhost:1527/MCD";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "PRODUCT_CATEGORY";
    private Connection conn;
    private PreparedStatement stmt;
    
    public ProductCategoryDA() throws SQLException{
        try{
            createConnection();
        }catch(SQLException ex){
            throw ex;
        }
    }
    
    public ProductCategoryDA(String user, String password, String tableName) throws SQLException{
        this.user = user;
        this.password = password;
        this.tableName = tableName;
        
        try{
            createConnection();
        }catch(SQLException ex){
            throw ex;
        }
    }
    
    
    
    public ProductCategory getProductCategory(String categoryID) throws SQLException {
        String queryStr = "SELECT * FROM " + tableName + " WHERE CATEGORY_ID = ?";
        ProductCategory productCategory = null;
        try {
            //bind query and parameter
            stmt = conn.prepareStatement(queryStr);
            stmt.setString(1, categoryID);
            //execute query
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                productCategory = new ProductCategory(categoryID, rs.getString("CATEGORY_NAME"));
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException ex) {
            throw ex;
        }
        
        return productCategory;
    }
    
    public ArrayList<ProductCategory> getAllProductCategory() throws SQLException {
        String queryStr = "SELECT * FROM " + tableName ;
        ProductCategory productCategory = null;
        ArrayList<ProductCategory> productCategoryList = new ArrayList<>();
        
        try {
            stmt = conn.prepareStatement(queryStr);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                productCategory = new ProductCategory(rs.getString("CATEGORY_ID"), rs.getString("CATEGORY_NAME"));
                
                productCategoryList.add(productCategory);
            }
        } catch (SQLException ex) {
            throw ex;
        }
        
        return productCategoryList;
    }

    
    public int insertNewProductCategory(ProductCategory productCategory) throws SQLException{
        String sqlStr= "INSERT INTO PRODUCT_CATEGORY VALUES(?,?)";
        int affectedRows = 0;
        
        try{
            stmt = conn.prepareStatement(sqlStr);
            
            stmt.setString(1, productCategory.getCategoryID());
            stmt.setString(2, productCategory.getCategoryName());
            
            affectedRows = stmt.executeUpdate();
            
            stmt.close();
            
        }catch(SQLException ex){
            throw ex;
        }
        
        return affectedRows;
    }
    
    
    public int updateProductCategory(ProductCategory productCategory) throws SQLException{
        
        String sqlStr = "UPDATE STUDENT SET CATEGORY_ID=?, CATEGORY_NAME=? WHERE CATEGORY_ID=?";
        int affectedRows= 0;
        
        try{
            stmt = conn.prepareStatement(sqlStr);
            
            stmt.setString(1, productCategory.getCategoryID());
            stmt.setString(2, productCategory.getCategoryName());
            stmt.setString(3, productCategory.getCategoryID());
            affectedRows = stmt.executeUpdate();
            
            stmt.close();
            
        }catch(SQLException ex){
            throw ex;
        }
        
        return affectedRows;
    }
    
    public int deleteProductCategory(String categoryID) throws SQLException{
        String sqlStr= "DELETE FROM PRODUCT_CATEGORY WHERE CATEGORY_ID = ?";
        int affectedRows =0;
        
        try{
            stmt = conn.prepareStatement(sqlStr);
            
            stmt.setString(1, categoryID);
            
            affectedRows = stmt.executeUpdate();
            
            stmt.close();
            
        }catch(SQLException ex){
            throw ex;
        }
        
        return affectedRows;
    }
    
    //Print exception message
    public void printStackTrace(SQLException ex){
        StackTraceElement[] elements = ex.getStackTrace();
            for(StackTraceElement e: elements){
                System.out.println("File Name: " + e.getFileName());
                System.out.println("Class Name: " + e.getClassName());
                System.out.println("Method Name: " + e.getMethodName());
                System.out.println("Line Number: " + e.getLineNumber());
                System.out.println("");
            }   
    }
    
    
    //create connection object
    private void createConnection() throws SQLException{
        try {
            conn = DriverManager.getConnection(host, user, password);
            System.out.println("***TRACE: Connection established.");
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    //close connection object
    private void shutDown() throws SQLException{
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
}

