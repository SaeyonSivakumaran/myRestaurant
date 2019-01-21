/**
 * The Waiter is an object that is represents waiters made by the users
 * author Daniel Tang
 * version 1.0
 * since 2017-01-22
 */

import java.awt.Color;
import java.util.ArrayList;

//Main Class
class Waiter{
  //Variables needed for Waiter
  String waiterName;
  Color waiterColor;
  double tipTotal;
  
  //CONTRUCTOR
  Waiter(String waiterName, Color waiterColor){
    this.waiterName = waiterName;
    this.waiterColor = waiterColor;
  }
  
  //METHOD: Retrieves the waiter's name
  public String getWaiterName() {
    return waiterName;
  }
  
  //METHOD: Sets the waiter's name
  public void setWaiterName(String waiterName) {
    this.waiterName = waiterName;
  }
  
  //METHOD: Retrieves the waiter's color
  public Color getWaiterColor() {
    return waiterColor;
  }
  
  //METHOD: Sets the waiter's color
  public void setWaiterColor(Color waiterColor){
    this.waiterColor = waiterColor;
  }
  
  //METHOD: Sets the waiter's tip
  public void setWaiterTip(int tip){
    tipTotal = tip; 
  }
  
  //METHOD: Adds to the waiter's tip
  public void addWaiterTip(int tip){
    tipTotal += tip; 
  }
  
  //METHOD: Sets the waiter's tip
  public double getWaiterTip(){
    return tipTotal;
  }

}