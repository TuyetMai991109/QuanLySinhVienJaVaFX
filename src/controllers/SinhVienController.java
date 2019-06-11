/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.clsSinhVien;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.clsConnect;

/**
 * FXML Controller class
 *
 * @author WIN64BIT
 */
public class SinhVienController implements Initializable {

    @FXML
    private TextField txt_MaSV;
    @FXML
    private TextField txt_TenSV;
    @FXML
    private ComboBox<String> cob_GioiTinh;
    @FXML
    private DatePicker txt_NgaySinh;
    @FXML
    private TextField txt_DiaChi;
    @FXML
    private TextField txt_tenLop;
    @FXML
    private TextField txt_TimKiem;
    @FXML
    private TableView<clsSinhVien> tab_SinhVien;
    @FXML
    private TableColumn<clsSinhVien,String> col_MaSV;
    @FXML
    private TableColumn<clsSinhVien, String> col_TenSV;
    @FXML
    private TableColumn<clsSinhVien, String> col_GioiTinh;
    @FXML
    private TableColumn<clsSinhVien, Date> col_NgaySinh;
    @FXML
    private TableColumn<clsSinhVien, String> col_DiaChi;
    @FXML
    private TableColumn<clsSinhVien, String> col_tenLop;
    @FXML
    private Button btn_ThemMoi;
    @FXML
    private Button btn_them;
    @FXML
    private Button btn_xoa;
    @FXML
    private Button btn_sua;
    @FXML
    private Button btn_luu;
    /**
     * Initializes the controller class.
     */
            
    
    clsConnect connect = new clsConnect();
    ObservableList<clsSinhVien> list;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
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
        default_(true);
        if(e.getClickCount() > 0)
        {
            trangThaiNut("TABLE");
            clsSinhVien selected = tab_SinhVien.getSelectionModel().getSelectedItem();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            txt_MaSV.setText(selected.getMASV());
            txt_TenSV.setText(selected.getTENSV());
            cob_GioiTinh.setValue(selected.getGIOITINH());
            txt_NgaySinh.setValue(selected.getNGAYSINH().toLocalDate());
            txt_DiaChi.setText(selected.getQUEQUAN());
            txt_tenLop.setText(selected.getTENLOP());
        }
    }
    
    @FXML
    void btn_ThemMới() throws ClassNotFoundException, SQLException
    {   
        trangThaiNut("THEMMOI");
        default_(false);
        txt_MaSV.setText("");
        txt_TenSV.setText("");
        txt_DiaChi.setText("");
        txt_tenLop.setText("");
        txt_NgaySinh.setValue(LocalDate.now());
        
        
    }
    @FXML
    void btn_Thêm() throws ClassNotFoundException, SQLException{
        trangThaiNut("THEM");
        default_(true);
         
        Date ngaysinh = Date.valueOf(txt_NgaySinh.getValue());
        String tensv = txt_TenSV.getText();                    
        String gioitinh = !cob_GioiTinh.getSelectionModel().isEmpty()?cob_GioiTinh.getValue():"";
        String quequan = txt_DiaChi.getText();
        String tenlop = txt_tenLop.getText();      
        int gt = gioitinh.equals("NAM")?0:1;
        
        if(tensv.equals("") || quequan.equals("") || tenlop.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Chưa nhập đủ thông tin!!!");
            alert.show();
            return;
        }
        
        String sql2 = "select MALOP from LOP where TENLOP='"+tenlop+"'";
        
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        cal.setTime(ngaysinh);
        if(cal.get(Calendar.YEAR)>today.get(Calendar.YEAR))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Người này chưa sinh ra!!!");
            alert.show();
            return;
        }
        int age = today.get(Calendar.YEAR)-cal.get(Calendar.YEAR);
        if(age<18)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Chưa đủ tuổi để nhập học!!!");
            alert.show();
            return;
        }
        connect.getConnect(); 
        ResultSet rs = connect.query(sql2);
        if(rs.next())
        {
            String malop = rs.getString("MALOP");
            String sql="insert into SINHVIEN(MALOP,TENSV,GIOITINH,NGAYSINH,QUEQUAN) VALUES ('"+malop+"','"+tensv+"',"+gt+",'"+ngaysinh+"','"+quequan+"')";
            if(connect.nonQuery(sql)>0)
            {
                //list.add(new clsSinhVien((list.size()+1)+"", tensv, gioitinh, ngaysinh, quequan, tenlop));
                LoadDataFormDatabase();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm Sinh Viên thành công công!!!");
                alert.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Thêm Sinh Viên thất bại!!!");
                alert.show();
            }
        }
        else
        {
            connect.disconnect();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Lớp bạn nhập không tồn tại!!!");
            alert.show();

            return;
        }        
        connect.disconnect();
        default_(true);
    }
/////////////////
    @FXML
    void btn_TimKiem() throws SQLException, ClassNotFoundException
    {
        if(txt_TimKiem.getText().equals(""))
        {
            connect.getConnect();
            LoadDataFormDatabase();
            connect.disconnect();
            return;
        }
        list.clear();
        String sql = "select sv.*,l.TENLOP from SINHVIEN sv,LOP l where sv.MALOP=l.MALOP AND sv.MASV="+txt_TimKiem.getText();
        connect.getConnect();
        ResultSet listSV = connect.query(sql);
        while(listSV.next())
        {
            String masv,tensv,gioitinh,quequan,tenlop;
            Date ngaysinh;
            masv = listSV.getString("MASV");
            tensv = listSV.getNString("TENSV");
            gioitinh = listSV.getString("GIOITINH").toString().equals("0")?"NAM":"Nữ";
            ngaysinh = listSV.getDate("NGAYSINH");
            quequan = listSV.getNString("QUEQUAN");
            tenlop = listSV.getString("TENLOP");
            list.add(new clsSinhVien(masv, tensv, gioitinh, ngaysinh, quequan, tenlop));
        }
        tab_SinhVien.setItems(list);
        connect.disconnect();
    }
    
    @FXML
    void btn_Xoa() throws ClassNotFoundException, SQLException
    { 
        
        default_(true) ;
        clsSinhVien sv = tab_SinhVien.getSelectionModel().getSelectedItem();     
        
        
        alert.setTitle("Thông báo");
        alert.setHeaderText("Bạn muốn xóa sinh viên chứ !!");
        ButtonType buttonTypeYes = new ButtonType("Đồng Ý",ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()== buttonTypeYes){     
        //xoa han trong sql
            String ma = sv.getMASV();
            String sql = "delete SINHVIEN where MASV ='"+ma+"'";
            connect.getConnect();
            int result2 = connect.nonQuery(sql);///lay du lieu ra su dung nonquery
            if(result2>0)
            {
                list.remove(sv);//xoa o table   
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Xóa Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
                connect.disconnect();
            }
            else
            {
                alert.setTitle("Thông Báo");
                alert.setHeaderText("Xóa Không Thành Công !!!");
                ButtonType buttonTypeCancel2 = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel2);
                alert.showAndWait();
                connect.disconnect();
            }
            
        }
        
    }
///////////////////
    @FXML
    void btn_Sua()
    {
        trangThaiNut("SUA");
        clsSinhVien sv = tab_SinhVien.getSelectionModel().getSelectedItem(); 
        default_(false) ;
       
        txt_MaSV.setText(sv.getMASV());
        txt_TenSV.setText(sv.getTENSV());
        cob_GioiTinh.setValue(sv.getGIOITINH());
        txt_DiaChi.setText(sv.getQUEQUAN());
        txt_tenLop.setText(sv.getTENLOP());   
      
    }
/////////////////    
    @FXML
    void btn_Luu() throws ClassNotFoundException, SQLException
    {
        trangThaiNut("LUU");
        default_(false);
        
        clsSinhVien sv = tab_SinhVien.getSelectionModel().getSelectedItem();       
        String masv = txt_MaSV.getText().toString();
        String tensv = txt_TenSV.getText().toString();     
        
        Date ngaysinh = Date.valueOf(txt_NgaySinh.getValue());
        String gioitinh = cob_GioiTinh.getValue().toString();
        String quequan = txt_DiaChi.getText().toString();
        String tenlop = txt_tenLop.getText().toString();      
        int gt = gioitinh.equals("NAM")?0:1;
        String sql2 = "select MALOP from LOP where TENLOP='"+tenlop+"'";
        
        connect.getConnect(); 
        ResultSet rs = connect.query(sql2);
        if(rs.next())
        {
            String malop=rs.getString("MALOP");
            String sql="UPDATE SINHVIEN SET TENSV='"+tensv+"',GIOITINH="+gt+",NGAYSINH='"+ngaysinh+"',QUEQUAN='"+quequan+"'"+",MALOP="+malop+" where MASV="+masv;
            //System.out.println(sql);
            if(connect.nonQuery(sql)>0)
            {
                LoadDataFormDatabase();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Sửa Sinh Viên thành công!!!");
                alert.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Sửa Sinh Viên thất bại!!!");
                alert.show();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("");
            alert.setContentText("Lớp bạn nhập không tồn tại");
            alert.show();
        }
        connect.disconnect();
        default_(true);
        //list.add(new clsSinhVien(masv, tensv, gioitinh, ngaysinh, quequan, tenlop));
        
    }
//////////////////
    void combobox()
    {
        cob_GioiTinh.getItems().addAll("NAM","Nữ");
        cob_GioiTinh.setValue("NAM");
    }
/////////////////   
    
    

    void default_(boolean value)
    {
        txt_MaSV.setDisable(true);
        txt_DiaChi.setDisable(value);
        txt_NgaySinh.setDisable(value);
        txt_TenSV.setDisable(value);
        cob_GioiTinh.setDisable(value);
        txt_tenLop.setDisable(value);
    }
////////////////   
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        txt_TimKiem.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    btn_TimKiem();
                } catch (SQLException ex) {
                    Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        trangThaiNut("TABLE");
        combobox();
        default_(true);
        try {
            list = FXCollections.observableArrayList();

            connect.getConnect();
            setCellTextField();
            LoadDataFormDatabase();
            clearTaxtField();
            connect.disconnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SinhVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }    
    void clearTaxtField(){
        txt_MaSV.clear();
        txt_TenSV.clear();
        txt_DiaChi.clear();
        //txt_NgaySinh.
        txt_tenLop.clear();
    }
    void setCellTextField(){
            col_MaSV.setCellValueFactory(new PropertyValueFactory<clsSinhVien,String>("MASV"));
            col_TenSV.setCellValueFactory(new PropertyValueFactory<clsSinhVien,String>("TENSV"));
            col_GioiTinh.setCellValueFactory(new PropertyValueFactory<clsSinhVien,String>("GIOITINH"));
            col_NgaySinh.setCellValueFactory(new PropertyValueFactory<clsSinhVien,Date>("NGAYSINH"));
            col_DiaChi.setCellValueFactory(new PropertyValueFactory<clsSinhVien,String>("QUEQUAN"));
            col_tenLop.setCellValueFactory(new PropertyValueFactory<clsSinhVien,String>("TENLOP"));
    }
    void LoadDataFormDatabase() throws SQLException{
            list.clear();
            String sql = "select sv.*,l.TENLOP from SINHVIEN sv,LOP l where sv.MALOP=l.MALOP";
            ResultSet listSV = connect.query(sql);
            while(listSV.next())
            {
                String masv,tensv,gioitinh,quequan,tenlop;
                Date ngaysinh;
                masv = listSV.getString("MASV");
                tensv = listSV.getNString("TENSV");
                gioitinh = listSV.getString("GIOITINH").toString().equals("0")?"NAM":"Nữ";
                ngaysinh = listSV.getDate("NGAYSINH");
                quequan = listSV.getNString("QUEQUAN");
                tenlop = listSV.getString("TENLOP");
                list.add(new clsSinhVien(masv, tensv, gioitinh, ngaysinh, quequan, tenlop));
            }
            tab_SinhVien.setItems(list);
    }

      
}
