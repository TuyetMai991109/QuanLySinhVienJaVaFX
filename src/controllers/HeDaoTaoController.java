/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.clsHeDT;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.clsConnect;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class HeDaoTaoController implements Initializable {

    @FXML
    private TextField txt_MaHDT;
    @FXML
    private TextField txt_TenHDT;
    @FXML
    private TableView<clsHeDT> tab_HĐT;
    @FXML
    private TableColumn<clsHeDT, String> col_MaHĐT;
    @FXML
    private TableColumn<clsHeDT, String> col_TenHĐT;
    @FXML
    private Button btn_ThemMoi;
    @FXML
    private Button btn_them;
    @FXML
    private Button btn_xoa;
    @FXML
    private Button btn_sửa;
    @FXML
    private Button btn_lưu;
    @FXML
    private Button thoát;
    @FXML
    private Label error_MAHDT;
    @FXML
    private Label error_TENHDT;
    /**
     * Initializes the controller class.
     */
    
    void trangThaiNut(String value)
    {
        
        switch(value)
        {
            case "THEMMOI":
                btn_ThemMoi.setDisable(true);
                btn_them.setDisable(false);
                btn_xoa.setDisable(true);
                btn_sửa.setDisable(true);
                btn_lưu.setDisable(true);
                break;
            case "THEM":
                btn_ThemMoi.setDisable(false);
                btn_them.setDisable(true);
                btn_xoa.setDisable(false);
                btn_sửa.setDisable(false);
                btn_lưu.setDisable(true);
                break;
            case "SUA":
                btn_ThemMoi.setDisable(true);
                btn_them.setDisable(true);
                btn_xoa.setDisable(true);
                btn_sửa.setDisable(true);
                btn_lưu.setDisable(false);
                break;
            case "LUU":
                btn_ThemMoi.setDisable(false);
                btn_them.setDisable(true);
                btn_xoa.setDisable(false);
                btn_sửa.setDisable(false);
                btn_lưu.setDisable(true);
                break;
           case "TABLE":
                btn_ThemMoi.setDisable(false);
                btn_them.setDisable(true);
                btn_xoa.setDisable(false);
                btn_sửa.setDisable(false);
                btn_lưu.setDisable(true);
                break; 
        }
    }
    
    clsConnect connect = new clsConnect();
    
    ObservableList<clsHeDT> list;
    
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    @FXML
    void btn_Thoat(ActionEvent e){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn có muốn thoát khỏi chương trình ??");
        ButtonType buttonTypeYes = new ButtonType("OK",ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== buttonTypeYes){    
            System.exit(1);
        }
        //alert.show();
    }
    @FXML
    void clickTable(MouseEvent e)
    {           
        trangThaiNut("TABLE");
        default1_(true);
        default_(true);
        if(e.getClickCount() > 0)
        {
            clsHeDT selected =tab_HĐT.getSelectionModel().getSelectedItem();
            txt_MaHDT.setText(selected.getMAHEDT());
            txt_TenHDT.setText(selected.getTENHEDT());
        }
    }
    
    @FXML
    void btn_ThemMoi() throws ClassNotFoundException, SQLException
    {   
        trangThaiNut("THEMMOI");
        default1_(true);
        default_(false);
        txt_MaHDT.setText("");
        txt_TenHDT.setText("");
    }
    
    @FXML
    void btn_Them() throws ClassNotFoundException, SQLException
    {   
       int check = Validation.TextFileValidation.getSpecialCharacterCount(txt_TenHDT.getText());
        if (check == 1) 
        {           
            return;           
        }
        trangThaiNut("THEM");
        default1_(true);
        default_(true);
        boolean isMAHDTNumber = Validation.TextFileValidation.isTextFieldNumber(txt_MaHDT, error_MAHDT, " ");
        boolean isTENHDTEmpty = Validation.TextFileValidation.isTextFieldNotEmpty(txt_TenHDT, error_TENHDT, " ");
        if(isMAHDTNumber&& isTENHDTEmpty){
            
            String MAHDT = txt_MaHDT.getText().toString();
            String TENHDT = txt_TenHDT.getText().toString();   
            
            String sql="insert into HEDT(TENHEDT) VALUES ('"+TENHDT+"')";
            connect.getConnect();   
            connect.nonQuery(sql);   
            connect.disconnect(); 
            clearTextField();
            
            load();
            
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Thêm Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait();          
        }
        else{
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Thêm Không Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait();      
        }
        
    }
    
    @FXML
    void btn_Xoa() throws ClassNotFoundException, SQLException
    {   
        default1_(true);
        default_(true) ;
        clsHeDT HDT =tab_HĐT.getSelectionModel().getSelectedItem();
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn muốn xóa hệ đào tạo này chứ ??");
        ButtonType buttonTypeYes = new ButtonType("OK",ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== buttonTypeYes){
            
            //xoa han trong sql
            String ma = HDT.getMAHEDT();
            String sql = "delete HEDT where MAHEDT ='"+ma+"'";
            connect.getConnect();
            int result2 = connect.nonQuery(sql);///lay du lieu ra su dung nonquery
            if(result2>0)
            {
                list.remove(HDT);//xoa o table
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Xóa Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
            }
            else
            {
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Xóa Không Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
            }
            clearTextField();
            
            connect.disconnect();
        }     
        else if((result.get()== buttonTypeCancel)){
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Xóa Không Thành Công !!!");
            ButtonType buttonTypeCancel2 = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel2);
            alert.showAndWait();
        }
        
    }
    
    @FXML
    void btn_Sua()
    {
        trangThaiNut("SUA");
        clsHeDT HDT = tab_HĐT.getSelectionModel().getSelectedItem();
        if(HDT==null)
            return;
        default1_(true);
        default_(false) ;       
        txt_MaHDT.setText(HDT.getMAHEDT());
        txt_TenHDT.setText(HDT.getTENHEDT());
       
    }
    
    @FXML
    void btn_Luu() throws ClassNotFoundException, SQLException
    {
        trangThaiNut("LUU");
        default1_(true);
        default_(true);
        boolean isTENHDTEmpty = Validation.TextFileValidation.isTextFieldNotEmpty(txt_TenHDT, error_TENHDT, "nhập tên hệ đào tạo");
        if(isTENHDTEmpty){
            String MAHDT = txt_MaHDT.getText().toString();
            String TENHDT = txt_TenHDT.getText().toString();      

            String sql="update HEDT set TENHEDT = '"+TENHDT+"' where MAHEDT = '"+MAHDT+"'";      

            connect.getConnect();   
            int reuslt = connect.nonQuery(sql);  
            clearTextField();
            connect.disconnect();      
            
            if(reuslt>0)
            {
                load();
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Lưu Thành Công !!!");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait();
            }
        }
        else {
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Luu Không Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait();
        }
    }
    void default1_(boolean value)
    {
        txt_MaHDT.setDisable(value);   
    }   
    void default_(boolean value)
    {
        txt_TenHDT.setDisable(value);       
    }
    void clearTextField(){
        txt_MaHDT.clear();
        txt_TenHDT.clear();
    }
    
    void load()
    {
        default1_(true);
        try {
            list.clear();
            connect.getConnect();          
            String sql = "select * from HEDT";
            ResultSet listHDT = connect.query(sql);
            while(listHDT.next())
            {
                String MAHDT = listHDT.getString("MAHEDT");
                String TENHDT = listHDT.getString("TENHEDT");               
                list.add(new clsHeDT(MAHDT, TENHDT));                
            }
            tab_HĐT.setItems(list);
            connect.disconnect();    
            clearTextField();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        trangThaiNut("TABLE");
        default1_(true);
        try {
            list = FXCollections.observableArrayList();        
            connect.getConnect();
            
            col_MaHĐT.setCellValueFactory(new PropertyValueFactory<clsHeDT,String>("MAHEDT"));
            col_TenHĐT.setCellValueFactory(new PropertyValueFactory<clsHeDT,String>("TENHEDT"));
            
            String sql = "select * from HEDT";
            ResultSet listHDT = connect.query(sql);
            while(listHDT.next())
            {
                String MAHDT = listHDT.getString("MAHEDT");
                String TENHDT = listHDT.getString("TENHEDT");               
                list.add(new clsHeDT(MAHDT, TENHDT));                
            }
            tab_HĐT.setItems(list);
            connect.disconnect();    
            clearTextField();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }    
}


