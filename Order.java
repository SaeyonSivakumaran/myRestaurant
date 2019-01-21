/* 
 * [Order.java]
 * Description: This class creates the object Order which is an ArrayList and therefore extends it
 * @author Chris Shah
 * @version 1.0
 * January 22, 2017
 */

import java.util.ArrayList;

class Order extends ArrayList<Food>{
  
  //Declares the ArrayList
  private ArrayList<Food> orderArray;
  
  //Constructor
  Order(){
    
    //Initializes the ArrayList
    this.orderArray = new ArrayList<Food>();
    
  }
  
  //Getters and setters
  
  public ArrayList<Food> getOrderArray(){
    
    return this.orderArray;
    
  }
  
  public void setOrderArray(ArrayList<Food> orderArray){
    
    this.orderArray = orderArray;
    
  }

  
}