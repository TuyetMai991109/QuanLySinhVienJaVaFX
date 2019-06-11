/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.clsDiem;
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
public class DiemController implements Initializable {

    @FXML
    private ComboBox cbx_maSV;
    @FXML
    private ComboBox cbx_maMH;
    @FXML
    private TextField txt_STT;
    @FXML
    private TextField txt_DiemHK;
    @FXML
    private TextField txt_DiemLan1;
    @FXML
    private TextField txt_DiemLan2;
    @FXML
    private TableView<clsDiem> tab_Diem;
    @FXML
    private TableColumn<clsDiem, String> col_TenSV;
    @FXML
    private TableColumn<clsDiem, String> col_TenMH;
    @FXML
    private TableColumn<clsDiem, String> col_STT;
    @FXML
    private TableColumn<clsDiem, Float> col_DiemHK;
    @FXML
    private TableColumn<clsDiem, Float> col_DiemLan1;
    @FXML
    private TableColumn<clsDiem, Float> col_DiemLan2;
    @FXML
    private Button btn_HienThi;
    @FXML
    private Button btn_Them;
    @FXML
    private Button btn_themmoi;
    @FXML
    private Button btn_Xoa;
    @FXML
    private Button btn_Sua;
    @FXML
    private Button btn_Luu;
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
                btn_themmoi.setDisable(true);
                btn_Them.setDisable(false);
                btn_Xoa.setDisable(true);
                btn_Sua.setDisable(true);
                btn_Luu.setDisable(true);
                break;
            case "THEM":
                btn_themmoi.setDisable(false);
                btn_Them.setDisable(true);
                btn_Xoa.setDisable(false);
                btn_Sua.setDisable(false);
                btn_Luu.setDisable(true);
                break;
            case "SUA":
                btn_themmoi.setDisable(true);
                btn_Them.setDisable(true);
                btn_Xoa.setDisable(true);
                btn_Sua.setDisable(true);
                btn_Luu.setDisable(false);
                break;
            case "LUU":
                btn_themmoi.setDisable(false);
                btn_Them.setDisable(true);
                btn_Xoa.setDisable(false);
                btn_Sua.setDisable(false);
                btn_Luu.setDisable(true);
                break;
           case "TABLE":
                btn_themmoi.setDisable(false);
                btn_Them.setDisable(true);
                btn_Xoa.setDisable(false);
                btn_Sua.setDisable(false);
                btn_Luu.setDisable(true);
                break; 
        }
    }
    
    ObservableList<clsDiem> list;
    
    @FXML
    void clickTable(MouseEvent e)
    {
        trangThaiNut("TABLE");
        default_(true);
        if(e.getClickCount() > 0)
        {
            clsDiem selected = tab_Diem.getSelectionModel().getSelectedItem();          
            cbx_maSV.setValue(selected.getMASV());
            cbx_maMH.setValue(selected.getMAMH());
            txt_STT.setText(selected.getSTT());
            txt_DiemHK.setText(Float.toString(selected.getHOCKY()));
            txt_DiemLan1.setText(Float.toString(selected.getDIEMLAN1()));
            txt_DiemLan2.setText(Float.toString(selected.getDIEMLAN2()));
        }
    }
    
    @FXML
    void btn_themmoi()
    {
        trangThaiNut("THEMMOI");
        default_(false);
    }
    
    @FXML
    void btn_Them()
    {   
       
        try {            
             if(txt_DiemHK.getText().equals(""))
            {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Điểm học kỳ không được để trống!!!");
                alert.show();
                return;
            }
              if(txt_DiemLan1.getText().equals(""))
             {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Điểm lần 1 không được để trống!!!");
                alert.show();
                return;
             }
              if(txt_DiemLan2.getText().equals(""))
             {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Điểm lần 2 không được để trống!!!");
                alert.show();
                return;
             }
            String masv = cbx_maSV.getValue().toString();
            String tenmh = cbx_maMH.getValue().toString();
            float hocky = Float.parseFloat(txt_DiemHK.getText());
            float diemlan1 = Float.parseFloat(txt_DiemLan1.getText().toString());
            float diemlan2 = Float.parseFloat(txt_DiemLan2.getText().toString());
            if(hocky > 10 || diemlan1 > 10 || diemlan2 > 10 || hocky < 0 || diemlan1 < 0 || diemlan2 < 0)
            {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm thất bại!!! Điểm không được lớn hơn 10 và bé hơn 0");
                alert.show();
                return;
            }
            
            String sql="insert into DIEM(MASV,MAMH,HOCKY,DIEMLAN1,DIEMLAN2) values("+masv+","+tenmh+","+hocky+","+diemlan1+","+diemlan2+")";
            System.out.println(sql);
            clsConnect connect = new clsConnect();
            connect.getConnect();
            
            if(connect.nonQuery(sql) >0)
            {
                loadData();
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm thành công!!!");
                alert.show();
            }else
            {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm thất bại!!!");
                alert.show();
            }
            connect.disconnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DiemController.class.getName()).log(Level.SEVERE, null, ex);
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm thất bại!!!");
                alert.show();
        } catch (SQLException ex) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm thất bại!!!");
                alert.show();
        } catch(Exception e)
        {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm thất bại!!!");
                alert.show();
        }finally
        {
            trangThaiNut("THEM");
            default_(true); 
        }
        
    }
    @FXML
    void btn_Xoa() throws ClassNotFoundException, SQLException
    {
        default_(true) ;
        clsDiem diem = tab_Diem.getSelectionModel().getSelectedItem();       
        if(diem==null)
            return;
        //xoa han trong sql
        String stt = diem.getSTT();
        String sql = "delete DIEM where STT ='"+stt+"'";
        clsConnect connect = new clsConnect();
        connect.getConnect();
        if(connect.nonQuery(sql)>0)///lay du lieu ra su dung nonquery
        {
            list.remove(diem);//xoa o table
        }
        
        connect.disconnect();
    }
    @FXML
    void btn_Sua()
    {
        
        clsDiem diem = tab_Diem.getSelectionModel().getSelectedItem(); 
        if(diem == null)
        {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Có gì đâu để mà sửa :))!! \n Vui lòng chọn 1 sinh viên trong bảng để sửa!!");
            alert.show();
            return;
        }
        trangThaiNut("SUA");
        default_(false) ;
       
        
        cbx_maSV.setValue(diem.getMASV());
        cbx_maMH.setValue(diem.getMAMH());
        txt_STT.setText(diem.getSTT());
        txt_DiemHK.setText(Float.toString(diem.getHOCKY()));
        txt_DiemLan1.setText(Float.toString(diem.getDIEMLAN1()));   
        txt_DiemLan2.setText(Float.toString(diem.getDIEMLAN2()));   
    }
    
    @FXML
    void btn_Luu() throws ClassNotFoundException, SQLException
    {           
        
        String masv = cbx_maSV.getValue().toString();
        String mamh = cbx_maMH.getValue().toString();
        String stt = txt_STT.getText().toString();  
        float hocky = Float.parseFloat(txt_DiemHK.getText());
        float diemlan1 = Float.parseFloat(txt_DiemLan1.getText().toString());
        float diemlan2 = Float.parseFloat(txt_DiemLan2.getText().toString());
        if(hocky > 10 || diemlan1 > 10 || diemlan2 > 10 || hocky < 0 || diemlan1 < 0 || diemlan2 < 0)
        {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Thêm thất bại!!! Điểm không được lớn hơn 10 và bé hơn 0");
            alert.show();
            return;
        }
        
        trangThaiNut("LUU");
        String sql="UPDATE DIEM SET MASV="+masv+",MAMH="+mamh+",HOCKY="+hocky+",DIEMLAN1="+diemlan1+",DIEMLAN2="+diemlan2+" where STT="+stt;
        clsConnect connect = new clsConnect();
        connect.getConnect();   
          
        if(connect.nonQuery(sql)>0)
        {
            loadData();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Sửa điểm thành công!!!");
            alert.show();
        }
        connect.disconnect();
    }
   
    @FXML
    void btnThoat(ActionEvent actionEvent)
    {
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
    
    void loadData()
    {
        default_(true);
        try {
            Init_cbx_maMH();
            Init_cbx_maSV();
            list.clear();
            clsConnect connect = new clsConnect();
            connect.getConnect();
            String sql = "select * from DIEM";
            ResultSet listDiem = connect.query(sql);
            while(listDiem.next())
            {
                String masv = listDiem.getString("MASV");
                String mamh = listDiem.getString("MAMH");
                String stt = listDiem.getString("STT");
                float hocky = listDiem.getFloat("HOCKY");
                float diemlan1 = listDiem.getFloat("DIEMLAN1");
                float diemlan2 = listDiem.getFloat("DIEMLAN2");
                
                list.add(new clsDiem(stt, hocky, diemlan1, diemlan2, masv, mamh));
            }
            tab_Diem.setItems(list);
            connect.disconnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    void default_(boolean value)
    {
        cbx_maMH.setDisable(value);
        cbx_maSV.setDisable(value);
        txt_STT.setDisable(true);
        txt_DiemHK.setDisable(value);
        txt_DiemLan1.setDisable(value);
        txt_DiemLan2.setDisable(value);
        
    }
    
    void Init_cbx_maMH() throws ClassNotFoundException, SQLException
    {
        cbx_maMH.getItems().clear();
        clsConnect conn = new clsConnect();
        String sql = "select MAMH from MONHOC";
        conn.getConnect();
        ResultSet rs = conn.query(sql);
        while(rs.next())
        {
            cbx_maMH.getItems().add(rs.getString("MAMH"));
        }
        
        conn.disconnect();
    }
    
    void Init_cbx_maSV() throws ClassNotFoundException, SQLException
    {
        cbx_maSV.getItems().clear();
        clsConnect conn = new clsConnect();
        String sql = "select MASV from SINHVIEN";
        conn.getConnect();
        ResultSet rs = conn.query(sql);
        while(rs.next())
        {
            cbx_maSV.getItems().add(rs.getString("MASV"));
        }
        
        conn.disconnect();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trangThaiNut("TABLE");
        default_(true);        
        try {
            Init_cbx_maMH();
            Init_cbx_maSV();
            list = FXCollections.observableArrayList();
            col_TenSV.setCellValueFactory(new PropertyValueFactory<clsDiem,String>("MASV"));
            col_TenMH.setCellValueFactory(new PropertyValueFactory<clsDiem,String>("MAMH"));
            col_STT.setCellValueFactory(new PropertyValueFactory<clsDiem,String>("STT"));
            col_DiemHK.setCellValueFactory(new PropertyValueFactory<clsDiem,Float>("HOCKY"));
            col_DiemLan1.setCellValueFactory(new PropertyValueFactory<clsDiem,Float>("DIEMLAN1"));
            col_DiemLan2.setCellValueFactory(new PropertyValueFactory<clsDiem,Float>("DIEMLAN2"));
            
            clsConnect connect = new clsConnect();
            connect.getConnect();
            String sql = "select * from DIEM";
            ResultSet listDiem = connect.query(sql);
            while(listDiem.next())
            {
                String masv = listDiem.getString("MASV");
                String mamh = listDiem.getString("MAMH");
                String stt = listDiem.getString("STT");
                float hocky = listDiem.getFloat("HOCKY");
                float diemlan1 = listDiem.getFloat("DIEMLAN1");
                float diemlan2 = listDiem.getFloat("DIEMLAN2");
                
                list.add(new clsDiem(stt, hocky, diemlan1, diemlan2, masv, mamh));
            }
            tab_Diem.setItems(list);
            connect.disconnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }    

      
}
