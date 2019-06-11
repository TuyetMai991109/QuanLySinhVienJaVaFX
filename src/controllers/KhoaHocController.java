/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.clsHeDT;
import dao.clsKhoaHoc;
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
public class KhoaHocController implements Initializable {

    @FXML
    private TextField txt_MaKH;
    @FXML
    private TextField txt_TenKH;
    @FXML
    private TableView<clsKhoaHoc> tab_KhoaHoc;
    @FXML
    private TableColumn<clsKhoaHoc, String> col_MaKH;
    @FXML
    private TableColumn<clsKhoaHoc, String> col_TenKH;
    @FXML
    private Button btn_hienthi;
    @FXML
    private Button btn_them;
    @FXML
    private Button btn_ThemMoi;
    @FXML
    private Button btn_xoa;
    @FXML
    private Button btn_sửa;
    @FXML
    private Button btn_lưu;
    @FXML
    private Button thoát;
    @FXML
    private Label error_TenKH;

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
    ObservableList<clsKhoaHoc> list;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    @FXML
    void btn_Thoat(ActionEvent e){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn có muốn thoát khỏi chuong trình !!");
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
            clsKhoaHoc selected =tab_KhoaHoc.getSelectionModel().getSelectedItem();
            txt_MaKH.setText(selected.getMAKHOAHOC());
            txt_TenKH.setText(selected.getTENKHOAHOC());
        }       
    }
    
    @FXML
    void btn_ThemMoi() throws ClassNotFoundException, SQLException
    {   
        trangThaiNut("THEMMOI");
        default1_(true);
        default_(false);
        txt_MaKH.setText("");
        txt_TenKH.setText("");
    }
    @FXML
    void btn_Them() throws ClassNotFoundException, SQLException
    {   
        trangThaiNut("THEM");
        default1_(true);
        default_(true);
        boolean isTenKHEmpty = Validation.TextFileValidation.isTextFieldNotEmpty(txt_TenKH, error_TenKH, "Nhập Tên Khóa Học");
        if(isTenKHEmpty){
            String MAKH = txt_MaKH.getText().toString();
            String TENKH = txt_TenKH.getText().toString();

            String sql = "insert into KHOAHOC(TENKHOAHOC) values ('"+TENKH+"')";

            connect.getConnect();
            connect.nonQuery(sql);
            clearTextField();
            connect.disconnect();
            //list.add(new clsKhoaHoc((list.size()+1)+"", TENKH));
            load();
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Thêm Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait();
        }
        else{
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Thêm Không Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait();  
        }
    }
    @FXML
    void btn_Xoa() throws ClassNotFoundException, SQLException
    {
        default1_(true);
        default_(true) ;
        clsKhoaHoc KHOAHOC =tab_KhoaHoc.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn muốn xóa khóa hoc !!");
        ButtonType buttonTypeYes = new ButtonType("Đồng Ý",ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);     
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get()== buttonTypeYes){           
            //xoa han trong sql
            String ma = KHOAHOC.getMAKHOAHOC();
            String sql = "delete KHOAHOC where MAKHOAHOC ='"+ma+"'";
            clsConnect connect = new clsConnect();
            connect.getConnect();
            int result2 = connect.nonQuery(sql);///lay du lieu ra su dung nonquery
            if(result2 > 0)
            {
               list.remove(KHOAHOC);//xoa o table 
               alert.setTitle("Thông Báo");
               alert.setHeaderText("Xóa Thành Công !!!");
               ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
               alert.getButtonTypes().setAll(buttonTypeCancel2);
               alert.showAndWait();
            }
            else
            {
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Xóa Không Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
            }
            clearTextField();
            connect.disconnect();
            
        }
         else if((result.get()== buttonTypeCancel)){
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Xóa Không Thành Công !!!");
            ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel2);
            alert.showAndWait();
        }
    }
    @FXML
    void btn_Sua()
    {       
        clsKhoaHoc KHOAHOC = tab_KhoaHoc.getSelectionModel().getSelectedItem();
        if(KHOAHOC==null)
            return;
        trangThaiNut("SUA");
        default_(false) ;
        default1_(true);
        txt_MaKH.setText(KHOAHOC.getMAKHOAHOC());
        txt_TenKH.setText(KHOAHOC.getTENKHOAHOC());
       
    }
    
    @FXML
    void btn_Luu() throws ClassNotFoundException, SQLException
    {
        trangThaiNut("LUU");
        default1_(true);
        default_(true);
        boolean isTenKHEmpty = Validation.TextFileValidation.isTextFieldNotEmpty(txt_TenKH, error_TenKH, "Nhập Tên Khóa Học");
        if(isTenKHEmpty){
            String MAKH = txt_MaKH.getText().toString();
            String TENKH = txt_TenKH.getText().toString();

            //list.add(new clsKhoaHoc(MAKH, TENKH));

            String sql="UPDATE KHOAHOC SET TENKHOAHOC = '"+TENKH+"'WHERE MAKHOAHOC = '"+MAKH+"'";

            clsConnect connect = new clsConnect();
            connect.getConnect();   
            int result = connect.nonQuery(sql);  
            clearTextField();
            connect.disconnect(); 
            if(result != 0)
            {
                load();
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Lưu Thành Công !!!");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait();
            }
            else
            {
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Luu Không Thành Công !!!");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait();
            }
        }       
    }
    void default1_(boolean value)
    {
        txt_MaKH.setDisable(value);      
    }
    void default_(boolean value)
    {
        txt_TenKH.setDisable(value);       
    }
    void clearTextField(){
        txt_MaKH.clear();
        txt_TenKH.clear();
    }
    
    void load()
    {
        default1_(true);
        default_(true);
        try {
            list.clear();
            connect.getConnect();

            
            String sql = "select * from KHOAHOC";
            ResultSet listKhoaHoc = connect.query(sql);
            while(listKhoaHoc.next())
            {
                String MAKH = listKhoaHoc.getString("MAKHOAHOC");
                String TENKH = listKhoaHoc.getString("TENKHOAHOC");
                
                list.add(new clsKhoaHoc(MAKH, TENKH));
                
            }
            tab_KhoaHoc.setItems(list);
            
            connect.disconnect();
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
        default_(true);
        try {
            list = FXCollections.observableArrayList();
            connect.getConnect();
            
            col_MaKH.setCellValueFactory(new PropertyValueFactory<clsKhoaHoc,String>("MAKHOAHOC"));
            col_TenKH.setCellValueFactory(new PropertyValueFactory<clsKhoaHoc,String>("TENKHOAHOC"));
            
            String sql = "select * from KHOAHOC";
            ResultSet listKhoaHoc = connect.query(sql);
            while(listKhoaHoc.next())
            {
                String MAKH = listKhoaHoc.getString("MAKHOAHOC");
                String TENKH = listKhoaHoc.getString("TENKHOAHOC");
                
                list.add(new clsKhoaHoc(MAKH, TENKH));
                
            }
            tab_KhoaHoc.setItems(list);
            
            connect.disconnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    } 

}



