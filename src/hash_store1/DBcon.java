/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hash_store1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ab o da
 */
public class DBcon {
     Connection con = null ;
     Statement st = null;
     ResultSet rs,r,rr;
     PreparedStatement pst;
     public DBcon(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stocks?useUnicode=true&characterEncoding=utf-8","root","");
           st = (Statement) con.createStatement();
          // st.close();
//           Class.forName("org.sqlite.JDBC");
//           con = DriverManager.getConnection("jdbc:sqlite:);
//           st = (Statement) con.createStatement();
        } catch (Exception e){
            System.out.println("Error:" + e);
        }
         
}
     
}