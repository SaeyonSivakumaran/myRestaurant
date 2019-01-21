/**
 * The WaiterList is and arraylist that stores and saves the list of waiters made by the user
 * author Daniel Tang
 * version 1.0
 * since 2017-01-22
 */

import java.util.ArrayList;

//Main Class
class WaiterList{
  
  //Variables needed for WaiterList
  ArrayList<Waiter> list;
  String [] nameList;
  
  //CONSTRUCTOR
  WaiterList() {
    list = new ArrayList<Waiter>();
  }
  
  //METHOD: Turns the arraylist into an array for storing in a JList
  public String [] turnArray (){
    nameList = new String [list.size()];
    for (int j = 0; j< list.size(); j++){
      nameList [j] = list.get(j).getWaiterName();
    }
    return nameList;
  }
  
  //METHOD: Adds a waiter to the arraylist
  public void addWaiter (Waiter waiter){
    list.add(waiter);
  }
  
  //METHOD: Removes a waiter from the arraylist
  public void removeWaiter(int index){
    list.remove(index);
  }
  
  //METHOD: Retrieves the selected waiter
  public Waiter viewWaiter(int selected){
    return list.get(selected);
  }
  
  //METHOD: Retrieves the size of the arraylist
  public int getListSize (){
    return list.size();
  }
}