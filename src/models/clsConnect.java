/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author WIN64BIT
 */
public class clsConnect {
     public Connection connection;
    
    public Connection getConnect() throws ClassNotFoundException, SQLException
    {
        String sqlInstanceName = "SQLEXPRESS";
        String hostName = "localhost";       
        String database = "QLSV";
        String userName = "sa";
        String password = "123456";
               
        return getConnect(hostName,database,userName,password,sqlInstanceName);
    }
    
    public Connection getConnect(String host,String database,String user,String pass,String sqlInstanceName) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
 
        String connectionURL = "jdbc:sqlserver://" + host + ":1433"+ ";instance=" + sqlInstanceName + ";databaseName=" + database;
 
        this.connection = DriverManager.getConnection(connectionURL, user,pass);
        return this.connection;
    }

    
    public void disconnect() throws SQLException
    {
        if(this.connection != null)
        {
            if(!this.connection.isClosed())
                this.connection.close();
        }
        this.connection=null;
    }

    //select tr? v? table
    public ResultSet query(String sql) throws SQLException
    {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(sql);
    }
    
    
    //select queryPrepared
    public int Preparedd(PreparedStatement statement) throws SQLException
    {        
        return statement.executeUpdate();
    }
    
    //select queryPrepared
    public ResultSet queryPrepared(PreparedStatement statement) throws SQLException
    {        
        return statement.executeQuery();
    }

    public ResultSet Prepared(PreparedStatement statement) throws SQLException
    {       
        return statement.executeQuery();
    }

    //insert update delete. tr? v? s? lu?ng thành công
    public int nonQuery(String sql) throws SQLException
    {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            return 0;
        }       
    }
    
    //tra ve 
    public int queryScara(String sql) throws SQLException
    {
        Statement statement = this.connection.createStatement();
        ResultSet number = statement.executeQuery(sql);
        number.next();
        
       return number.getRow();                      
    }

    
   
}
