/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.clsDiem;
import dao.clsHeDT;
import dao.clsKhoa;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
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
public class KhoaController implements Initializable {

    @FXML
    private TextField txt_MaKhoa;
    @FXML
    private TextField txt_TenKhoa;
    @FXML
    private TextField txt_DiaChi;
    @FXML
    private TextField txt_SĐT;
    @FXML
    private TableView<clsKhoa> tab_Khoa;
    @FXML
    private TableColumn<clsKhoa, String> col_MaKhoa;
    @FXML
    private TableColumn<clsKhoa, String> col_TenKhoa;
    @FXML
    private TableColumn<clsKhoa, String> col_DiaChi;
    @FXML
    private TableColumn<clsKhoa, String> col_SĐT;
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
    private Label lab_mk,lab_tenkhoa,lab_diachi,lab_sdt;

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
    ObservableList<clsKhoa> list;
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
            clsKhoa selected =tab_Khoa.getSelectionModel().getSelectedItem();
            txt_MaKhoa.setText(selected.getMAKHOA());
            txt_TenKhoa.setText(selected.getTENKHOA());
            txt_DiaChi.setText(selected.getDIACHI());
            txt_SĐT.setText(selected.getDIENTHOAI());
        }
    }
    
    @FXML
    void btn_ThemMoi() throws ClassNotFoundException, SQLException
    {   
        trangThaiNut("THEMMOI");
        default1_(true);
        default_(false);
        txt_MaKhoa.setText("");
        txt_TenKhoa.setText("");
        txt_DiaChi.setText("");
        txt_SĐT.setText("");
    }
    
    @FXML
    void btn_Thêm() throws ClassNotFoundException, SQLException{

        boolean isTenKhoa = Validation.TextFileValidation.isTextFieldNotEmpty(txt_SĐT, lab_tenkhoa, "Bạn chưa nhập Tên Khoa");
        boolean isDienThoai = Validation.TextFileValidation.isTextFieldNumber(txt_SĐT, lab_sdt, "Lỗi nhập SĐT");
        boolean isDiaChi = Validation.TextFileValidation.isTextFieldNotEmpty(txt_DiaChi, lab_diachi, "Bạn chưa nhập Địa Chỉ");       
        if(isTenKhoa && isDienThoai && isDiaChi){
            String MAKHOA = txt_MaKhoa.getText().toString();
            String TENKHOA = txt_TenKhoa.getText().toString();
            String DIENTHOAI = txt_SĐT.getText().toString();
            String DIACHI = txt_DiaChi.getText().toString();
            if(!DIENTHOAI.matches(".*\\d.*"))
            {
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Thêm Không Thành Công !!! \n SDT bạn nhập không đúng");
                ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait(); 
                return;
            }
            String sql = "insert into KHOA(TENKHOA,DIENTHOAI,DIACHI) values('"+TENKHOA+"','"+DIENTHOAI+"','"+DIACHI+"')";
            System.out.println(sql);
            connect.getConnect();   
            connect.nonQuery(sql);
            ClearTextField();
            connect.disconnect();
            load();
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Thêm Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait();   
            trangThaiNut("THEM");
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
        default_(true) ;
        clsKhoa KHOA =tab_Khoa.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn muốn xóa sinh viên chứ !!");
        ButtonType buttonTypeYes = new ButtonType("Đồng Ý",ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);       
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get()== buttonTypeYes){          
            //xoa han trong sql
            String ma = KHOA.getMAKHOA();
            String sql = "delete KHOA where MAKHOA ='"+ma+"'";
            connect.getConnect();
            
            int result2 = connect.nonQuery(sql);
            if(result2 > 2){
                list.remove(KHOA);//xoa o table           
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Xóa Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
            }
            else{
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Xóa Không Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
            }
            ClearTextField();
            connect.disconnect();                      
        }
        else if((result.get()!= buttonTypeYes)){
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
        clsKhoa KHOA = tab_Khoa.getSelectionModel().getSelectedItem();       
        if(KHOA==null)
            return;
        trangThaiNut("SUA");
        default_(false) ;
        default1_(true);
        txt_MaKhoa.setText(KHOA.getMAKHOA());
        txt_TenKhoa.setText(KHOA.getTENKHOA());
        txt_DiaChi.setText(KHOA.getDIACHI());
        txt_SĐT.setText(KHOA.getDIENTHOAI());
        System.out.println(KHOA.getDIENTHOAI()+"1");
    }
    
    @FXML
    void btn_Luu() throws ClassNotFoundException, SQLException
    {
        trangThaiNut("LUU");
        default1_(true);
        default_(true);
        boolean isTenKhoa = Validation.TextFileValidation.isTextFieldNotEmpty(txt_SĐT, lab_tenkhoa, "Nhập Tên Khoa");
        boolean isDienThoai = Validation.TextFileValidation.isTextFieldNumber(txt_SĐT, lab_sdt, "Nhập SĐT");
        if(isTenKhoa && isDienThoai){
            String MAKHOA = txt_MaKhoa.getText().toString();
            String TENKHOA = txt_TenKhoa.getText().toString();
            String DIENTHOAI = txt_SĐT.getText().toString();
            String DIACHI = txt_DiaChi.getText().toString();
            
            if(!DIENTHOAI.matches(".*\\d.*"))
            {
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Thêm Không Thành Công !!! \n SDT bạn nhập không đúng");
                ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait(); 
                return;
            }

            String sql = "update KHOA set TENKHOA='"+TENKHOA+"',DIACHI='"+DIACHI+"',DIENTHOAI='"+DIENTHOAI+"' where MAKHOA='"+MAKHOA+"' ";
            connect.getConnect(); 
            
            if(connect.nonQuery(sql)>0)
            {
                ClearTextField();

                load();

                alert.setTitle("Thông Báo");
                alert.setHeaderText("Lưu Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
            }
            connect.disconnect();
        }
        else {
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Luu Không Thành Công !!!");
            ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel2);
            alert.showAndWait();
        }
    }
    
    void load()
    {
        default1_(true);
        default_(true);
        try {
            list.clear();
            connect.getConnect();
            
            String sql = "select * from KHOA";
            ResultSet listKhoa = connect.query(sql);
            while(listKhoa.next())
            {
                String MAKHOA = listKhoa.getString("MAKHOA");
                String TENKHOA = listKhoa.getString("TENKHOA");
                String DIACHI = listKhoa.getString("DIACHI");
                String DIENTHOAI = listKhoa.getString("DIENTHOAI");               
                list.add(new clsKhoa(MAKHOA, TENKHOA, DIACHI, DIENTHOAI));                
            }
            tab_Khoa.setItems(list);
            connect.disconnect();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    void default1_(boolean value)
    {
        txt_MaKhoa.setDisable(value);
    }
    void default_(boolean value)
    {
        txt_TenKhoa.setDisable(value);   
        txt_DiaChi.setDisable(value);
        txt_SĐT.setDisable(value); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
        trangThaiNut("TABLE");
        default1_(true);
        default_(true);
        try {
            list = FXCollections.observableArrayList();
            connect.getConnect();
            
            col_MaKhoa.setCellValueFactory(new PropertyValueFactory<clsKhoa,String>("MAKHOA"));
            col_TenKhoa.setCellValueFactory(new PropertyValueFactory<clsKhoa,String>("TENKHOA"));
            col_DiaChi.setCellValueFactory(new PropertyValueFactory<clsKhoa,String>("DIACHI"));
            col_SĐT.setCellValueFactory(new PropertyValueFactory<clsKhoa,String>("DIENTHOAI"));
            
            String sql = "select * from KHOA";
            ResultSet listKhoa = connect.query(sql);
            while(listKhoa.next())
            {
                String MAKHOA = listKhoa.getString("MAKHOA");
                String TENKHOA = listKhoa.getString("TENKHOA");
                String DIACHI = listKhoa.getString("DIACHI");
                String DIENTHOAI = listKhoa.getString("DIENTHOAI");  
                System.out.println(DIENTHOAI+".");
                list.add(new clsKhoa(MAKHOA, TENKHOA, DIACHI, DIENTHOAI));                
            }
            tab_Khoa.setItems(list);
            connect.disconnect();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }  
    void ClearTextField(){
        txt_MaKhoa.clear();
        txt_TenKhoa.clear();
        txt_DiaChi.clear();
        txt_SĐT.clear();
    }
}


