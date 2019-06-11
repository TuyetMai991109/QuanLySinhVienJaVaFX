/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.clsConnect;
/**
 * FXML Controller class
 *
 * @author WIN64BIT
 */
public class DangNhapController implements Initializable {

    @FXML
    private TextField txt_TenDN;
    @FXML
    private TextField txt_MatKhau;
    @FXML
    private Button btn_DangNhap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    void changeScene(ActionEvent e,String view) throws IOException
    {
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        //Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/views/"+view));
        Parent root = loader.load();
        Scene scene = new Scene(root);       
        stage.setScene(scene);
        stage.setMaximized(true);
        //stage.show();
    }
    
    @FXML
    void btn_login(ActionEvent e) throws IOException, ClassNotFoundException, SQLException
    {
        clsConnect connect = new clsConnect();
        connect.getConnect();
        String sql="select * from TAIKHOAN where TENDANGNHAP='"+txt_TenDN.getText().toString()+"' AND MATKHAU='"+txt_MatKhau.getText().toString()+"'";
        if(connect.queryScara(sql)>0)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Đăng nhập thành công");
            alert.show();
            changeScene(e,"MainScreen.fxml");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Đăng nhập không thành công");
            alert.show();
        }
        connect.disconnect();
    }
    
}
