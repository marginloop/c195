/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import Model.Customer;
import Model.DAO.AppointmentDOA;
import Model.DAO.CustomerDAO;
import Utils.DBConnection;
import Utils.Login;
import Utils.Scheduler;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author CDuffy
 */
public class ModifyAppointmentViewController implements Initializable {

    @FXML private TextField TextFieldAppointmentID;
    @FXML private TextField TextFieldUser;
    @FXML private TextField TextFieldTitle;
    @FXML private TextField TextFieldLocation;
    @FXML private TextField TextFieldContact;
    @FXML private TextField TextFieldType;
    //@FXML private TextField TextFieldClient;
    @FXML private TextField TextFieldUrl;
    
    @FXML private DatePicker DatePickerStart;
    @FXML private DatePicker DatePickerEnd;
    
    @FXML private MenuButton MenuButtonClient;
    @FXML private MenuButton MenuButtonStartHour, MenuButtonStartMinute;
    @FXML private MenuButton MenuButtonEndHour, MenuButtonEndMinute;
    
    @FXML private TextArea TextAreaDescription;
    
    @FXML private Button ButtonSave;
    @FXML private Button ButtonCancel;
    
    @FXML private Label LabelAppointmentID,
            LabelUserID,
            LabelClient,
            LabelTitle,
            LabelLocation,
            LabelContact,
            LabelType,
            LabelStart,
            LabelEnd,
            LabelDescription;

    private AppointmentDOA appointmentDOA = new AppointmentDOA(DBConnection.getConnection());
    public static Appointment appointment;
    
    private CustomerDAO customerDOA = new CustomerDAO(DBConnection.getConnection());
    private ObservableList<Customer> Clients = customerDOA.findAll();
    
    public void loadCalendarView(ActionEvent event)throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarView.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void clickButtonSave(ActionEvent event) throws IOException{
        
        
        //setup date/time information
        int startHour = Integer.parseInt(MenuButtonStartHour.getText());
        int startMinute = Integer.parseInt(MenuButtonStartMinute.getText()); 
        int endHour = Integer.parseInt(MenuButtonEndHour.getText());
        int endMinute = Integer.parseInt(MenuButtonEndMinute.getText());
        
        Timestamp startDate = Timestamp.valueOf(DatePickerStart.getValue().atTime(startHour, startMinute));
        Timestamp endDate = Timestamp.valueOf(DatePickerEnd.getValue().atTime(endHour, endMinute));
        
        boolean NoScheduleErrorsExist = Scheduler.checkForScheduleErrors(startDate, endDate);
        boolean NoOverlap = Scheduler.CheckForScheduleOverLap(startDate, endDate);
 
        if(NoScheduleErrorsExist && NoOverlap){
            
            appointment.setUserId(Integer.parseInt(TextFieldUser.getText()));
            appointment.setTitle(TextFieldTitle.getText());
            appointment.setLocation(TextFieldLocation.getText());
            appointment.setContact(TextFieldContact.getText());
            appointment.setType(TextFieldType.getText());
            appointment.setCustomerId(Integer.parseInt(MenuButtonClient.getId()));
            appointment.setUrl(TextFieldUrl.getText());
            appointment.setDescription(TextAreaDescription.getText());  
            appointment.setStartTime(startDate);
            appointment.setEndTime(endDate);
            appointment.setLastUpdateBy(Login.getUserName());
            appointmentDOA.update(appointment);
            loadCalendarView(event);
            
        }
        
    }
    
    
    public void clickButtonCancel(ActionEvent event) throws IOException{
        
        loadCalendarView(event);
        
    }
    
    public void LoadClientMenuItems(){
        for(int i=0; i<  Clients.size(); i++){
            MenuItem ClientMenuItem = new MenuItem(Clients.get(i).getCustomerName());
            ClientMenuItem.setId(Integer.toString(Clients.get(i).getCustomerId()));
            
            //Lambda Expression: create the menu items for each client in the database make the menu item selectable
            ClientMenuItem.setOnAction((event)->{
                MenuButtonClient.setText(ClientMenuItem.getText());
                MenuButtonClient.setId(ClientMenuItem.getId());
            });
                        
            MenuButtonClient.getItems().addAll(ClientMenuItem);
            
        }
    }
    
    public void LoadDateTimeMenuItems(){
        
        int startHour = appointment.getStartTime().toLocalDateTime().getHour();
        int startMinute = appointment.getStartTime().toLocalDateTime().getMinute();
        DatePickerStart.setValue(appointment.getStartTime().toLocalDateTime().toLocalDate());
        MenuButtonStartHour.setText(Integer.toString(startHour));
        MenuButtonStartMinute.setText(Integer.toString(startMinute));

        
        int endHour = appointment.getEndTime().toLocalDateTime().getHour();
        int endMinute = appointment.getEndTime().toLocalDateTime().getMinute();
        DatePickerEnd.setValue(appointment.getEndTime().toLocalDateTime().toLocalDate());
        MenuButtonEndHour.setText(Integer.toString(endHour));
        MenuButtonEndMinute.setText(Integer.toString(endMinute));
        
        
        int[] interval = new int[]{00,
            15,
            30,
            45};
        
        //set the minute
        for (int j : interval) {
            
            //start times
            MenuItem StartTime = new MenuItem();
            StartTime.setText(String.format("%d", j));
            
            StartTime.setOnAction((event)->{
                MenuButtonStartMinute.setText(StartTime.getText());
            });
            
            MenuButtonStartMinute.getItems().addAll(StartTime);
            
            
            //end times
            MenuItem EndTime = new MenuItem();
            EndTime.setText(String.format("%d", j));
            
            EndTime.setOnAction((event)->{
                MenuButtonEndMinute.setText(EndTime.getText());  
            });
            
            MenuButtonEndMinute.getItems().addAll(EndTime);
        }
        
        //set the hour
        for (int i=0; i < 23; i++){  

                
                MenuItem StartMenuItem = new MenuItem();
                StartMenuItem.setText(String.format("%d", i));
                
                //Lambda Expression: Add Menu options for start times
                StartMenuItem.setOnAction((event)->{
                    MenuButtonStartHour.setText(StartMenuItem.getText());
                });
                
                MenuItem EndMenuItem = new MenuItem();
                EndMenuItem.setText(String.format("%d", i));
                
                //Lambda Expression: Add Menu options for end times
                EndMenuItem.setOnAction((event)->{
                    MenuButtonEndHour.setText(EndMenuItem.getText());  
                });
                
                MenuButtonStartHour.getItems().addAll(StartMenuItem);
                MenuButtonEndHour.getItems().addAll(EndMenuItem);

            
        }
    }
    
    public void LoadAppointment(){
        
        // load the object to the fields for editing
        TextFieldAppointmentID.setText(Integer.toString(appointment.getAppointmentId()));
        TextFieldUser.setText(Integer.toString(appointment.getUserId()));
        
        MenuButtonClient.setText(Clients.get(appointment.getCustomerId()-1).getCustomerName());
        MenuButtonClient.setId(Integer.toString(appointment.getCustomerId()));
        TextFieldTitle.setText(appointment.getTitle());
        TextFieldLocation.setText(appointment.getLocation());
        TextFieldContact.setText(appointment.getContact()); 
        TextFieldType.setText(appointment.getType());
        TextFieldUrl.setText(appointment.getUrl());

        TextAreaDescription.setText(appointment.getDescription());
    }
    
    public void LoadLocales(ResourceBundle rb){
        
        System.out.println(LabelAppointmentID.getText());
        LabelAppointmentID.setText(rb.getString(LabelAppointmentID.getText()));
        LabelUserID.setText(rb.getString(LabelUserID.getText()));
        LabelClient.setText(rb.getString(LabelClient.getText()));
        LabelTitle.setText(rb.getString(LabelTitle.getText()));
        LabelType.setText(rb.getString(LabelType.getText()));
        LabelLocation.setText(rb.getString(LabelLocation.getText()));
        LabelContact.setText(rb.getString(LabelContact.getText()));
        LabelStart.setText(rb.getString(LabelStart.getText()));
        LabelEnd.setText(rb.getString(LabelEnd.getText()));
        LabelDescription.setText(rb.getString(LabelDescription.getText()));
        

    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(!(Locale.getDefault()==Locale.US)){
            rb = ResourceBundle.getBundle("locale/c195", Locale.getDefault());
            LoadLocales(rb); 
        }
        
        LoadClientMenuItems();
        LoadDateTimeMenuItems();
        LoadAppointment();
    }    

}
