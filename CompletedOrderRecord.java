/* 
 * [CompletedOrderRecord.java]
 * Description: This class creates the object CompletedOrderRecord
 * @author Chris Shah
 * @version 1.0
 * January 22, 2017
 */

import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.util.ArrayList;

class CompletedOrderRecord {
  
  //Creates variables that all CompletedOrderRecords have
  private ArrayList<Food> completedList;
  private DefaultListModel completedModel;
  private double total;
  
  //Constructor
  CompletedOrderRecord(){
    
    //Creates new ArrayList and DefaultListModel when a CompletedOrderRecord is made
    completedList = new ArrayList<Food>();
    completedModel = new DefaultListModel();
    
  }
  
  //Constructor
  CompletedOrderRecord(ArrayList<Food> completedList, DefaultListModel completedModel){
    
    //Sets the ArrayList and DefaultListModel to the ones that are added in the constructors
    this.completedList = completedList;
    this.completedModel = completedModel;
    
  }
  
  //Getters and setters
  public ArrayList<Food> getCompletedList(){
    
    return this.completedList;
    
  }
  
  public void setCompletedList(ArrayList<Food> completedList){
    
    this.completedList = completedList;
    
    //Calculates the total price every time a new list is made, and recreates the DefaultListModel
    total = 0;
    completedModel.removeAllElements();
    
    for(int i = 0; i < completedList.size(); i++){
      
      completedModel.addElement(((Food)completedList.get(i)).getName());
      total += completedList.get(i).getPrice();
      
    }
    
  }
  
  public DefaultListModel getCompletedModel(){
    
    return completedModel;
    
  }
  
  public void setCompletedModel(DefaultListModel completedModel){
    
    this.completedList = completedList;
    
  }
  
  public double getTotal(){
    
    total = 0;
    
    for(int i = 0; i < completedList.size(); i++){
     
      total += completedList.get(i).getPrice();
        
    }
    
    return total;
    
  }
  
  public void setTotal(double total){
    
    this.total = total;
    
  }
  
  /**
   * saveCompletedOrders
   * This method clears the DefaultListModel, clears the total, recreates the DefaultListModel, and calculates the new total
   * @param n/a
   * @return void
   */
  public void saveCompletedOrders (){
    
    completedModel.clear();
    total = 0;
    
    for(int i = 0; i < completedList.size(); i++){
     
      completedModel.addElement(((Food)completedList.get(i)).getName());
      total += completedList.get(i).getPrice();
        
    }
    
  }
  
  
}


