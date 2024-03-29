/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Utils.DataTransferObject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author CDuffy
 */
public class Customer implements DataTransferObject {
    private int customerId;
    private String customerName; //varchar 45
    private int addressId;
    private int active; // tiny int 1
    private LocalDateTime createDate;
    private LocalDateTime  lastUpdate;
    private String createdBy;
    private String lastUpdateBy;
    
    @Override
    public long getId() {
        return customerId;
    }
    
    public int getCustomerId() {
        return customerId;
    }

    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    
    public void setCreateDate(Timestamp createDate) {
        
        this.createDate = createDate.toLocalDateTime();
        
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate.toLocalDateTime();
    }
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

}
