/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Keo-Mo
 */
public class MainScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    void changeScene(ActionEvent e,String view) throws IOException
    {
        //Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/views/"+view));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void exit()
    {
        System.exit(1);
    }
    
    
    @FXML
    void btn_managementStudent(ActionEvent e) throws IOException
    {
        changeScene(e,"SinhVien.fxml");
    }
    @FXML
    void btn_managerTranning(ActionEvent e) throws IOException
    {
        changeScene(e,"HeDaoTao.fxml");
        //System.out.println(getClass().getResource("/resources/views/HeDaoTao.fxml"));
    }
    @FXML
    void btn_manager_course(ActionEvent e) throws IOException
    {
        changeScene(e,"KhoaHoc.fxml");
    }
    
    @FXML
    void btn_managerFaculty(ActionEvent e) throws IOException
    {
        changeScene(e,"Khoa.fxml");
    }
    @FXML
    void btn_managerClass(ActionEvent e) throws IOException
    {
        changeScene(e,"Lop.fxml");
    }
    @FXML
    void btn_managerPoint(ActionEvent e) throws IOException
    {
        changeScene(e,"Diem.fxml");
    }
    @FXML
    void btn_managerSubject(ActionEvent e) throws IOException
    {
        changeScene(e,"MonHoc.fxml");
    }
    
}
