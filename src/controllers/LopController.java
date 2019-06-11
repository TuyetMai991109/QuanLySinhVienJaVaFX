/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.clsLop;
import dao.clsSinhVien;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
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
public class LopController implements Initializable {

    @FXML
    private TextField txt_MaLop;
    @FXML
    private ComboBox cbx_MaKhoaHoc;
    @FXML
    private ComboBox cbx_MaDaoTao;
    @FXML
    private ComboBox cbx_khoa;
    @FXML
    private TextField txt_TenLop;
    @FXML
    private TableView<clsLop> tab_Lop;
    @FXML
    private TableColumn<clsLop, String> col_MaLop;
    @FXML
    private TableColumn<clsLop, String> col_TenLop;
    @FXML
    private TableColumn<clsLop, String> col_TenKhoa;
    @FXML
    private TableColumn<clsLop, String> col_TenHDT;
    @FXML
    private TableColumn<clsLop, String> col_TenKH;
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
    @FXML
    private Button thoát;

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
    
    ObservableList<clsLop> list;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    int row = 0;
    
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
            clsLop selected = tab_Lop.getSelectionModel().getSelectedItem();
            txt_MaLop.setText(selected.getMALOP());
            cbx_MaKhoaHoc.setValue(selected.getMAKH());
            cbx_MaDaoTao.setValue(selected.getMAHDT());
            cbx_khoa.setValue(selected.getMAKHOA());
            txt_TenLop.setText(selected.getTENLOP());
        }
    }
    
    @FXML
    void btn_ThemMoi() throws ClassNotFoundException, SQLException
    {   
        trangThaiNut("THEMMOI");
        default1_(true);
        default_(false);
        clearTextfield();
    }
    @FXML
    void btn_Them() throws ClassNotFoundException, SQLException{
        trangThaiNut("THEM");
        default1_(true);
        default_(true);
        String MALOP = "null";
        String MAKHOAHOC = !cbx_MaKhoaHoc.getSelectionModel().isEmpty()?cbx_MaKhoaHoc.getValue().toString():"";
        String MAKHOA = !cbx_khoa.getSelectionModel().isEmpty()?cbx_khoa.getValue().toString():"";
        String MADT = !cbx_MaDaoTao.getSelectionModel().isEmpty()?cbx_MaDaoTao.getValue().toString():"";
        String TENKH = txt_TenLop.getText().toString();
        if(MAKHOAHOC.equals("") || MAKHOA.equals("") || TENKH.equals("") || MADT.equals(""))
        {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Thông báo");
            alert2.setHeaderText("Bạn chưa chọn đủ thông tin");
            alert2.showAndWait();
            return;
        }
        String sql_check_name_class = "select MALOP from LOP where TENLOP='"+TENKH+"'";       
        String sql="insert Lop(MAKHOAHOC,MAHEDT,MAKHOA,TENLOP) values("+MAKHOAHOC+","+MADT+","+MAKHOA+",'"+TENKH+"')";
        System.out.println(sql);
        clsConnect connect = new clsConnect();
        connect.getConnect();
        
        if(connect.queryScara(sql_check_name_class)>0)//kiem tra ten lop da ton tai hay chua
        {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Thông báo");
            alert2.setHeaderText("Tên lớp này đã tồn tại.");
            alert2.showAndWait();
            return;
        }
        
        int result = connect.nonQuery(sql);      
        if(result>0)//them thanh cong vao sql
        {
            load();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Thông báo");
            alert2.setHeaderText("Thành công");
            alert2.showAndWait();
        }
        else
        {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Thông báo");
            alert2.setHeaderText("Thất bại");
            alert2.showAndWait();
        }
        connect.disconnect();
    }
    
    @FXML
    void btn_Xoa() throws ClassNotFoundException, SQLException
    {
        default_(true) ;
        clsLop lop = tab_Lop.getSelectionModel().getSelectedItem();       
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn muốn xóa lớp? \n Hành động này sẽ xóa hết tất cả sinh viên đang học tại lớp này, bao gồm cả điểm sinh viên!!!");
        ButtonType buttonTypeYes = new ButtonType("Ðồng Ý",ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("OK",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== buttonTypeYes){           
            //xoa han trong sql
            String ma = lop.getMALOP();
            String sql_del_diem = "delete DIEM where DIEM.MASV IN (SELECT MASV from SINHVIEN where MALOP="+ma+")";
            String sql2="delete SINHVIEN where MALOP="+ma;
            String sql = "delete LOP where MALOP ="+ma+"";
            System.out.println(sql2);
            clsConnect connect = new clsConnect();
            connect.getConnect();
            connect.nonQuery(sql_del_diem);
            int result_del_sv = connect.nonQuery(sql2);
            int result_del = connect.nonQuery(sql);///lay du lieu ra su dung nonquery
            if(result_del > 0)
            {
                load();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Thông báo");
                alert2.setHeaderText("Thành công");
                alert2.getButtonTypes().setAll(buttonTypeYes);
                alert2.showAndWait();
            }
            connect.disconnect();
        }
    }
    @FXML
    void btn_Sua()
    {       
        clsLop lop = tab_Lop.getSelectionModel().getSelectedItem(); 
        if(lop==null)
            return;
        trangThaiNut("SUA");
        default_(false) ;      
        txt_MaLop.setText(lop.getMALOP());
        cbx_MaKhoaHoc.setValue(lop.getMAKH());
        cbx_MaDaoTao.setValue(lop.getMAHDT());
        cbx_khoa.setValue(lop.getMAKHOA());
        txt_TenLop.setText(lop.getTENLOP());   
      
    }
    
    @FXML
    void btn_Luu() throws ClassNotFoundException, SQLException
    {
        trangThaiNut("LUU");
        default1_(true);
        default_(true);
        String MALOP = txt_MaLop.getText().toString();
        String MaKH = cbx_MaKhoaHoc.getValue().toString();
        String MaDaoTao = cbx_MaDaoTao.getValue().toString();
        String MaKhoa = cbx_khoa.getValue().toString();
        String TenLop = txt_TenLop.getText().toString();
        
        String sql="update LOP set MAKHOAHOC="+MaKH+",MAHEDT="+MaDaoTao+",MAKHOA="+MaKhoa+",TENLOP='"+TenLop+"' where MALOP="+MALOP;
        
        clsConnect connect = new clsConnect();
        connect.getConnect();       
        int result = connect.nonQuery(sql); 
        connect.disconnect();
        if(result > 0)
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
            alert.setHeaderText("Lưu Không Thành Công !!!");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeCancel);
            alert.showAndWait(); 
        }
        
    }
    void default1_(boolean value)
    {
        txt_MaLop.setDisable(value);
    }
    void default_(boolean value)
    {
        cbx_MaKhoaHoc.setDisable(value);
        cbx_MaDaoTao.setDisable(value);
        txt_TenLop.setDisable(value);
        cbx_khoa.setDisable(value);
    }
    void clearTextfield(){
        txt_MaLop.clear();
        //txt_MaKH.clear();
        //txt_MaKhoa.clear();
        txt_TenLop.clear();
        //txt_MaDaoTao.clear();
    }
    
    void load_maKhoa() throws ClassNotFoundException, SQLException
    {
        clsConnect connect = new clsConnect();
        connect.getConnect();
        String sql = "select MAKHOA from KHOA";
        ResultSet rs = connect.query(sql);
        cbx_khoa.getItems().clear();
        while(rs.next())
        {
            cbx_khoa.getItems().add(rs.getString("MAKHOA"));
        }
        connect.disconnect();
    }
    
    void load_maKhoaHoc() throws ClassNotFoundException, SQLException
    {
        clsConnect connect = new clsConnect();
        connect.getConnect();
        String sql = "select MAKHOAHOC from KHOAHOC";
        ResultSet rs = connect.query(sql);
        cbx_MaKhoaHoc.getItems().clear();
        while(rs.next())
        {
            cbx_MaKhoaHoc.getItems().add(rs.getString("MAKHOAHOC"));
        }
        connect.disconnect();
    }
    
    void load_cbx_MaDaoTao() throws ClassNotFoundException, SQLException
    {
        clsConnect connect = new clsConnect();
        connect.getConnect();
        String sql = "select MAHEDT from HEDT";
        ResultSet rs = connect.query(sql);
        cbx_MaDaoTao.getItems().clear();
        while(rs.next())
        {
            cbx_MaDaoTao.getItems().add(rs.getString("MAHEDT"));
        }
        connect.disconnect();
    }
    
    void load() throws ClassNotFoundException, SQLException
    {
        default1_(true);
        default_(true);
        load_maKhoa();
        load_maKhoaHoc();
        load_cbx_MaDaoTao();
        try {
            list.clear();
            
            clsConnect connect = new clsConnect();
            connect.getConnect();
            String sql = "select * from LOP";
            ResultSet listLop = connect.query(sql);
            while(listLop.next())
            {
                String MALOP = listLop.getString("MALOP");
                String MAKHOAHOC = listLop.getString("MAKHOAHOC");
                String MAHEDT = listLop.getString("MAHEDT");
                String MAKHOA = listLop.getString("MAKHOA");
                String TENLOP = listLop.getString("TENLOP");
                list.add(new clsLop(MALOP, MAKHOAHOC, MAHEDT, MAKHOA, TENLOP));
            }
            tab_Lop.setItems(list);
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
            load_maKhoa();
            load_maKhoaHoc();
            load_cbx_MaDaoTao();
            list = FXCollections.observableArrayList();
            col_MaLop.setCellValueFactory(new PropertyValueFactory<clsLop,String>("MALOP"));
            col_TenLop.setCellValueFactory(new PropertyValueFactory<clsLop,String>("MAKH"));
            col_TenKhoa.setCellValueFactory(new PropertyValueFactory<clsLop,String>("MAHDT"));
            col_TenHDT.setCellValueFactory(new PropertyValueFactory<clsLop,String>("MAKHOA"));
            col_TenKH.setCellValueFactory(new PropertyValueFactory<clsLop,String>("TENLOP"));
            
            clsConnect connect = new clsConnect();
            connect.getConnect();
            String sql = "select * from LOP";
            ResultSet listLop = connect.query(sql);
            while(listLop.next())
            {
                String MALOP = listLop.getString("MALOP");
                String MAKHOAHOC = listLop.getString("MAKHOAHOC");
                String MAHEDT = listLop.getString("MAHEDT");
                String MAKHOA = listLop.getString("MAKHOA");
                String TENLOP = listLop.getString("TENLOP");
                list.add(new clsLop(MALOP, MAKHOAHOC, MAHEDT, MAKHOA, TENLOP));
            }
            tab_Lop.setItems(list);
            connect.disconnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }    

      
}
