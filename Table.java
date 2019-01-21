/**
 * The table class is essentially a table in the layout 
 * @author Saeyon Sivakumaran
 * @version 1.4
 * @since 2017-01-14
 */

import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

class Table{
  
  ArrayList<TableDimension> dimensions;
  int tableNum;
  int statusNum = 0;
  boolean hasWaiter = false;
  Waiter assignedWaiter;
  
  public ArrayList<TableDimension> getDimensions(){
    return this.dimensions;
  }
  
  public int getTableNum(){
    return this.tableNum;
  }
  
  public void setStatusNum(int num){
    this.statusNum = num;
  }
  
  public int getStatusNum(){
    return this.statusNum;
  }
  
  //Assigns a waiter to the table
  public void setWaiter(boolean value, Waiter waiter){
    this.hasWaiter = value;
    assignedWaiter = waiter;
  }
  
  public Waiter getAssignedWaiter(){
    return assignedWaiter;
  }
  
  //Checks if this table has a waiter or not
  public boolean getWaiter(){
    return this.hasWaiter;
  }
  
  //CONSTRUCTOR
  Table(ArrayList<TableDimension> dimensionsInput, int tableNum){
    dimensions = new ArrayList<TableDimension>();
    dimensions = dimensionsInput;
    this.tableNum = tableNum;
  }

  //Checking if the coordinates are in the table
  public boolean isSelected(int x, int y){
    boolean selected;;
    for (int count = 0; count < dimensions.size(); count++){
      selected = dimensions.get(count).isSelected(x,y);
      if (selected == true){
        return true;
      }
    }
    return false;
  }
  
  //Drawing a box around the table
  public void drawBoxAround(Graphics g, boolean value){
    if (value == true){
      g.setColor(assignedWaiter.getWaiterColor());
    } else{
      g.setColor(Color.WHITE);
    }
    for (int count = 0; count < dimensions.size(); count++){
      g.fillRect(dimensions.get(count).getX()-10, dimensions.get(count).getY()-10, dimensions.get(count).getWidth()+20, dimensions.get(count).getHeight()+20);
    }
  }
  
  //Changing the colour of the table
  public void changeColour(Graphics g){
    g.setColor(Color.GREEN);
    for (int count = 0; count < dimensions.size(); count++){
      g.fillRect(dimensions.get(count).getX(), dimensions.get(count).getY(), dimensions.get(count).getWidth(), dimensions.get(count).getHeight());
    }
  }
  
  //Changing the status of the table
  public void changeStatus(int x, int y, String original, String status, String[][] statusArray){
    if (!statusArray[x][y].equals(original) || original.equals(status)){
        //Do nothing
      } else {
        statusArray[x][y] = status;
        changeStatus(x-1, y, original, status, statusArray);       
        changeStatus(x, y+1, original, status, statusArray);
        changeStatus(x-1, y+1, original, status, statusArray);
        changeStatus(x-1, y-1, original, status, statusArray);
        changeStatus(x, y-1, original, status, statusArray);
        changeStatus(x+1, y, original, status, statusArray);
        changeStatus(x+1, y-1, original, status, statusArray);
        changeStatus(x+1, y+1, original, status, statusArray);        
      }
  }
  
}