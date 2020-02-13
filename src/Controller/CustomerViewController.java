/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import Model.DAO.CustomerDAO;
import Model.DAO.UserDAO;
import Model.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cjd
 */
public class CustomerViewController implements Initializable {
    
    @FXML private TextField TextFieldCustomerID;
    @FXML private TextField TextFieldCustomerName;
    
    @FXML private Button ButtonGoBack;
    @FXML private Button ButtonAddModify;
    @FXML private Button ButtonRemove;
    @FXML private Button ButtonCancel;
    
    @FXML private MenuButton MenuButtonAddress;
    
    @FXML private MenuButton MenuButtonActive;
    @FXML private MenuItem MenuActiveItemActive;
    @FXML private MenuItem MenuActiveItemInactive;
    
    @FXML private TableView TableViewCustomer;
    @FXML private TableColumn TableCustomerColumnCutomerID;
    @FXML private TableColumn TableCustomerColumnCustomerName;
    @FXML private TableColumn TableCustomerColumnAddress;
    @FXML private TableColumn TableCustomerColumnActive;
    
    private int addressId = 1;
    private int Active = 1;
    private int userId = 1;
    
    public void clickButtonGoBack(ActionEvent event) throws IOException{
        
            Parent root = FXMLLoader.load(getClass().getResource("/View/CalendarView.fxml"));
            Scene scene = new Scene(root);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            
    }
    
    public void clickButtonAddModify(ActionEvent event) throws IOException, SQLException, Exception{
        //todo: add if no customer record is selected
        
        if(true){
            
            CustomerDAO customerDAO = new CustomerDAO(DBConnection.getConnection());
            Customer customer = new Customer();
            LocalDateTime lastUpdate = LocalDateTime.now();
            LocalDateTime createDate = LocalDateTime.now();

            customer.setCustomerName(TextFieldCustomerName.getText().toString());
            customer.setAddressId(addressId);
            customer.setActive(Active);
            customer.setCreateDate(createDate);
            customer.setCreatedBy(UserDAO.getUserId());
            customer.setLastUpdate(lastUpdate);
            customer.setLastUpdateBy(UserDAO.getUserId());

            customerDAO.create(customer);
            
        }else if (false){
            
            //todo: modify if customer record is selected
            
        }
    }
    
    public void clickButtonRemove(ActionEvent event) throws IOException{
        //todo: remove record if customer record is selected
        
    }        
    
    public void clickButtonCancel(ActionEvent event) throws IOException{
        //todo: deselect if customer record is selected
        
    }
    
    public void clickMenuActiveItemActive(ActionEvent event) throws IOException{
        
        //todo: set the Customer record to Active
        
    }
    
    public void clickMenuActiveItemInactive(ActionEvent event) throws IOException{
        
        //todo: set the Customer record to Inactive
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: load the Address Choices to the MenuButtonAddress
 
    }    
    
}
