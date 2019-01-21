/* 
 * DineInOrder.java]
 * Description: This class creates the object DineInOrder that is a Food and therefore extends it
 * @author Chris Shah
 * @version 1.0
 * January 22, 2017
 */

import java.util.ArrayList;

class DineInOrder extends Food{
 
  //Creates the variables that are specific to a DineInOrder
  private int tableNumber;
  private int customerNumber;
  private CompletedOrderRecord completedOrderRecord;
  
  //Constructor
  DineInOrder(String name, double price, String type, int tableNumber, int customerNumber, CompletedOrderRecord compeletedOrderRecord){
    
    //Initializes all DineInOrder information
    super (name, price, type);
    this.tableNumber = tableNumber;
    this.customerNumber = customerNumber;
    this.completedOrderRecord = completedOrderRecord;
    
  }
  
  //Getters and setters
  public int getTableNumber(){
    
    return tableNumber;
    
  }
  
  public void setTableNumber(int tableNumber){
    
    this.tableNumber = tableNumber;
    
  }
  
  
  public int getCustomerNumber(){
    
    return customerNumber;
    
  }
  
  public void setCustomerNumber(int customerNumber){
    
    this.customerNumber = customerNumber;
    
  }
  
  public CompletedOrderRecord getCompletedOrderRecord(){
    
    return this.completedOrderRecord;
    
  }
  
  public void setCompletedOrderRecord(CompletedOrderRecord completedOrderRecord){
    
    this.completedOrderRecord = completedOrderRecord;
    
  }
  
}