/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;


import java.util.regex.Matcher;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.regex.Pattern;


/**
 *
 * @author ASUS
 */
public class TextFileValidation {
    
    public static boolean isTextFieldNotEmpty(TextField tf){
        boolean b = false;
        if(tf.getText().length() !=0 || !tf.getText().isEmpty())
            b = true;
            return b;       
    }
    
    public  static boolean isTextFieldNotEmpty(TextField tf,Label label , String error_message){
        boolean b = true;
        String mes = null;
        if(!isTextFieldNotEmpty(tf)){
            b = false;
            mes = error_message;
        }
        label.setText(mes);
        return b;
    }
    
    public static boolean isTextFieldNumber(TextField tf){
        boolean b = false;
        if(tf.getText().matches("([0-9]+(\\.[0-9]+)?)+"))
        b = true;
        return b;
    }
    
    public  static boolean isTextFieldNumber(TextField tf,Label lable,String error_message){
        boolean b =true;
        String mes = null;
        if(!isTextFieldNumber(tf)){
            b = true;
            mes = error_message;
        }
        lable.setText(mes);
        return b;
    }
    public static int getSpecialCharacterCount(String s)
    {
        if (s.equals("")) 
        {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Không được để trống dữ liệu!!!");
                alert.show();
                return 1;
        }
        Pattern special = Pattern.compile("[!@#$%^&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(s);
        boolean b = hasSpecial.find();
        if ( b == true ) 
        {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("");
                alert.setContentText("Không được nhập ký tự đặc biệt ");
                alert.show();
        }   
        return 1;
        
    }
}
