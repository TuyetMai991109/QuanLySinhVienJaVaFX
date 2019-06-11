/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.clsMonHoc;
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
import javafx.scene.layout.VBox;
import models.clsConnect;

/**
 * FXML Controller class
 *
 * @author WIN64BIT
 */
public class MonHocController implements Initializable {

    @FXML
    private TextField txt_MaMH;
    @FXML
    private TextField txt_TenMH;
    @FXML
    private TextField txt_soTinChi;
    @FXML
    private TableView<clsMonHoc> tab_MonHoc;
    @FXML
    private TableColumn<clsMonHoc, String> col_MaMH;
    @FXML
    private TableColumn<clsMonHoc, String> col_TenMH;
    @FXML
    private TableColumn<clsMonHoc, String> col_soTinChi;
    @FXML
    private Label error_TenMH,error_SoTinChi;
    @FXML
    private Button btn_them;
    @FXML
    private Button btn_ThemMoi;
    @FXML
    private Button btn_xoa;
    @FXML
    private Button btn_sua;
    @FXML
    private Button btn_luu;

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
                btn_sua.setDisable(true);
                btn_luu.setDisable(true);
                break;
            case "THEM":
                btn_ThemMoi.setDisable(false);
                btn_them.setDisable(true);
                btn_xoa.setDisable(false);
                btn_sua.setDisable(false);
                btn_luu.setDisable(true);
                break;
            case "SUA":
                btn_ThemMoi.setDisable(true);
                btn_them.setDisable(true);
                btn_xoa.setDisable(true);
                btn_sua.setDisable(true);
                btn_luu.setDisable(false);
                break;
            case "LUU":
                btn_ThemMoi.setDisable(false);
                btn_them.setDisable(true);
                btn_xoa.setDisable(false);
                btn_sua.setDisable(false);
                btn_luu.setDisable(true);
                break;
           case "TABLE":
                btn_ThemMoi.setDisable(false);
                btn_them.setDisable(true);
                btn_xoa.setDisable(false);
                btn_sua.setDisable(false);
                btn_luu.setDisable(true);
                break; 
        }
    }
                
    clsConnect connect = new clsConnect();
    ObservableList<clsMonHoc> list;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    @FXML
    void clickTable(MouseEvent e)
    {
        trangThaiNut("TABLE");
        default1_(true);
        default_(true);
        if(e.getClickCount()>0){
            clsMonHoc selected = tab_MonHoc.getSelectionModel().getSelectedItem();
            txt_MaMH.setText(selected.getMAMONHOC());
            txt_TenMH.setText(selected.getTENMONHOC());
            txt_soTinChi.setText(selected.getSOTINCHI());
        }
    }
    
    
    @FXML
    void btn_ThemMoi() throws ClassNotFoundException, SQLException{    
        trangThaiNut("THEMMOI");
        txt_MaMH.setText("");
        txt_TenMH.setText("");
        txt_soTinChi.setText("");
        default1_(true);
        default_(false);

       
    }
    @FXML
    void btn_Them() throws ClassNotFoundException, SQLException{        
        trangThaiNut("THEM");
        default_(true);
        default1_(true);
        boolean isTENMHEmpty = Validation.TextFileValidation.isTextFieldNotEmpty(txt_TenMH, error_TenMH, "Nhập Tên Môn Học");
        boolean isSOTCNumber = Validation.TextFileValidation.isTextFieldNumber(txt_soTinChi, error_SoTinChi, "Sai dữ liệu");
        if(isTENMHEmpty && isSOTCNumber){
            String MAMH = txt_MaMH.getText().toString();
            String TENMH = txt_TenMH.getText().toString();
            String SOTINCHI = txt_soTinChi.getText().toString();

            String sql = "insert into MONHOC(TENMH,SOTINCHI) values ('"+TENMH+"','"+SOTINCHI+"')";

            connect.getConnect();
            int result = connect.nonQuery(sql);          
            if(result > 0)
            {
                list.add(new  clsMonHoc((list.size()+1)+"", TENMH, SOTINCHI));
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Thêm Thành Công !!!");
                ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait();
            }
            else
            {
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Thêm Không Thành Công !!!");
                ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait();  
            }
            clearTextField();
            connect.disconnect();
        }
    }
    @FXML
    void btn_Xoa() throws ClassNotFoundException, SQLException
    {
        default_(true) ;
        clsMonHoc mh = tab_MonHoc.getSelectionModel().getSelectedItem();    
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn muốn xóa môn học này chứ ??");
        ButtonType buttonTypeYes = new ButtonType("OK",ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== buttonTypeYes){          
            //xoa han trong sql
            String ma = mh.getMAMONHOC();
            String sql = "delete MONHOC where MAMH ='"+ma+"'";
            connect.getConnect();
            int result2 = connect.nonQuery(sql);///lay du lieu ra su dung nonquery
            if(result2>0)
            {
                list.remove(mh);//xoa o table
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
        clsMonHoc monhoc = tab_MonHoc.getSelectionModel().getSelectedItem(); 
        if(monhoc==null)
            return;
        trangThaiNut("SUA");
        default1_(true);
        default_(false) ;      
        txt_MaMH.setText(monhoc.getMAMONHOC());
        txt_TenMH.setText(monhoc.getTENMONHOC());
        txt_soTinChi.setText(monhoc.getSOTINCHI());  
      
    }
    @FXML
    void btn_Luu() throws ClassNotFoundException, SQLException{
        trangThaiNut("LUU");
        boolean isTENMHEmpty = Validation.TextFileValidation.isTextFieldNotEmpty(txt_TenMH, error_TenMH, "Nhập Tên Môn Học");
        boolean isSOTCNumber = Validation.TextFileValidation.isTextFieldNumber(txt_soTinChi, error_SoTinChi, "Nhập Số Tín Chỉ");
        if(isTENMHEmpty && isSOTCNumber){
            String MAMH = txt_MaMH.getText().toString();
            String TENMH = txt_TenMH.getText().toString();
            String SOTINCHI = txt_soTinChi.getText().toString();

            String sql ="update MONHOC SET TENMH = '"+TENMH+"',SOTINCHI = '"+SOTINCHI+"'where MAMH = '"+MAMH+"' ";
            connect.getConnect();
            int result = connect.nonQuery(sql);
            clearTextField();
            connect.disconnect();

            //list.add(new clsMonHoc(MAMH, TENMH, SOTINCHI));
            if(result > 0)
            {
                load();
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Lưu Thành Công !!!");
                ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                alert.showAndWait();
            }
        }
        else {
            alert.setTitle("Thông Báo");
            alert.setHeaderText("Lưu Không Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait();
        }
    }
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
    ////////////
    void default1_(boolean value)
    {
        txt_MaMH.setDisable(value);
    }
    void default_(boolean value)
    {
        txt_TenMH.setDisable(value);
        txt_soTinChi.setDisable(value);
    }
        void clearTextField(){
        txt_MaMH.clear();
        txt_TenMH.clear();
        txt_soTinChi.clear();
    }
        
    void load()
    {
        default1_(true);
        default_(true);
        try {
            list.clear();          
            connect.getConnect();
            
            String sql = "select * from MONHOC";
            ResultSet listMH = connect.query(sql);
            while(listMH.next())
            {
                String maMH,tenMH,soTinChi;
                maMH = listMH.getString("MAMH");
                tenMH = listMH.getString("TENMH");
                soTinChi = listMH.getString("SOTINCHI");
                list.add(new clsMonHoc(maMH, tenMH, soTinChi));
            }
            tab_MonHoc.setItems(list);
            clearTextField();
            connect.disconnect();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MonHocController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MonHocController.class.getName()).log(Level.SEVERE, null, ex);
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
            col_MaMH.setCellValueFactory(new PropertyValueFactory<clsMonHoc,String>("MAMONHOC"));
            col_TenMH.setCellValueFactory(new PropertyValueFactory<clsMonHoc,String>("TENMONHOC"));
            col_soTinChi.setCellValueFactory(new PropertyValueFactory<clsMonHoc,String>("SOTINCHI"));
            String sql = "select * from MONHOC";
            ResultSet listMH = connect.query(sql);
            while(listMH.next())
            {
                String maMH,tenMH,soTinChi;
                maMH = listMH.getString("MAMH");
                tenMH = listMH.getString("TENMH");
                soTinChi = listMH.getString("SOTINCHI");
                list.add(new clsMonHoc(maMH, tenMH, soTinChi));
            }
            tab_MonHoc.setItems(list);
            clearTextField();
            connect.disconnect();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MonHocController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MonHocController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    
}
