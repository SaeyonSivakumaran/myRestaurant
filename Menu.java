/**
 * Menu.java
 * Menu for the restaurant
 * @author Saeyon Sivakumaran
 * @version 1.0
 */

//Importing array lists
import java.util.ArrayList;  

//Importing for file writing
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

class Menu{
  
  private ArrayList <Food> menu;  //Menu array list
  Food tempItem;  //Temporary menu item used for methods
  
  //CONSTRUCTOR
  Menu() throws Exception{
    menu = new ArrayList <Food>();
    retrieveSavedItems();  //Retrieving the current menu
  }
  
  //Add method
  public void add(String name, double price, String type) throws Exception{
    tempItem = new Food(name, price, type);
    menu.add(tempItem);
    rewrite();
  }
  
  //Remove method
  public void remove(String name) throws Exception{
    for (int count = 0; count < menu.size(); count++){
      if (((menu.get(count)).getName()).equals(name)){
        menu.remove(count);
        rewrite();
      }
    }
  }
  
  //Clear menu method
  public void clear() throws Exception{
    menu.clear();
    rewrite();
  }
  
  //Get info method
  public String[][] getInfo(){
    String[][] names = new String[menu.size()][3];
    String info;
    for (int count = 0; count < menu.size(); count++){
      for (int count1 = 0; count1 < 3; count1++){
        if (count1 == 0){
          names[count][count1] = menu.get(count).getType();
        } else if (count1 == 1){
          names[count][count1] = menu.get(count).getName();
        } else {
          names[count][count1] = Double.toString(menu.get(count).getPrice());
        }
      }
    }
    return names;
  }
  
  //Update information method
  public void update(String[][] info) throws Exception{
    int size = info.length;
    clear();
    for (int count = 0; count < size; count++){
      tempItem = new Food(info[count][1], Double.parseDouble(info[count][2]), info[count][0]);
      menu.add(tempItem);
    }
    rewrite();
  }
  
  //File rewrite method
  public void rewrite() throws Exception{
    //Creating a print writer
    PrintWriter fileOutput = new PrintWriter(new File("menu.txt"));
    //Writing the information of each item per line
    for (int count = 0; count < menu.size(); count++){
      tempItem = menu.get(count);
      fileOutput.println(tempItem.getType() + "/" + tempItem.getName() + "/" + tempItem.getPrice());
    }
    fileOutput.close();
  }
  
  //File reading method
  public void retrieveSavedItems() throws Exception{
    //Creating a scanner for the file
    Scanner fileInput = new Scanner(new File("menu.txt"));
    String line, name, type;
    double price;
    //Reading the file
    while (fileInput.hasNext()){
      line = fileInput.nextLine();
      type = line.substring(0, line.indexOf("/"));  //Getting the type
      line = line.substring(line.indexOf("/")+1, line.length());
      name = line.substring(0, line.indexOf("/"));  //Getting the name
      price = Double.parseDouble(line.substring(line.indexOf("/")+1, line.length()));  //Getting the price
      tempItem = new Food(name, price, type);  
      menu.add(tempItem);
    }
    fileInput.close();
  }
  
}  //End of Menu class