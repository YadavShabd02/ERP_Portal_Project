package edu.univ.erp.auth.edu.univ.erp.data;
import java.sql.*;

public class ERP_DB_Connection {
    private static final String url="jdbc:mysql://127.0.0.1:3306/ERP_DB";
    private static final String username ="root" ;
    private static final String password ="thook";
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
         try{
            Connection connection = DriverManager.getConnection(url,username,password);
            String query = "";
             PreparedStatement prestatement = connection.prepareStatement(query);
             prestatement.setString(1,"Jhonny bro ");
             prestatement.setInt(2,1);

//
//            Statement statement = connection.createStatement();
//           String query1 ="UPDATE users set name = 'Raman' WHERE id = 1";

             int rowsAffected = prestatement.executeUpdate();
             if(rowsAffected!=0){
                 System.out.println("Data inserted");
             }
             else {
                 System.out.println("Data not inserted");
             }

         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }

    }


}
