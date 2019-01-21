/* 
 * [OrderMenu.java]
 * Description: This class creates all variables that are involved with the menu
 * @author Chris Shah
 * @version 1.0
 * January 22, 2017
 */

import java.util.ArrayList;
import java.io.File; 
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

class OrderMenu{
  
  //Declares all variables an OrderMenu has
  private ArrayList<Food> tempMenu;  
  
  private DefaultListModel foodAppetizerModel;
  private DefaultListModel foodMainModel;
  private DefaultListModel foodBeverageModel;
  private DefaultListModel foodDessertModel;
  
  private JList appsList;
  private JList mainsList;
  private JList drinksList;
  private JList dessertsList;
  
  private JScrollPane appScrollPane;
  private JScrollPane mainScrollPane;
  private JScrollPane drinkScrollPane;
  private JScrollPane dessertScrollPane;
  
  //Constructor
  OrderMenu() throws Exception{
    
    //Declares the ArrayList of Foods which will contain the menu items
    tempMenu = new ArrayList<Food>();
    
    //Reads the text from menu.txt and adds foods that are stored in the file to the menu ArrayList
    File menuFile = new File("menu.txt");
    Scanner menuScanner = new Scanner(menuFile);
    String tempText;
    String tempType;
    String tempName;
    Double tempPrice;
    
    while(menuScanner.hasNext()){
      
      tempText = menuScanner.nextLine();
      
      tempType = tempText.substring(0, tempText.indexOf("/"));      
      tempText = tempText.substring(tempText.indexOf("/") + 1);  
      
      tempName = tempText.substring(0, tempText.indexOf("/"));
      tempText = tempText.substring(tempText.indexOf("/") + 1);
      
      tempPrice = Double.parseDouble(tempText);
      
      tempMenu.add(new Food(tempName, tempPrice, tempType));
      
    }
    
    //Decalares the DefaultListModels that used to display the menu
    foodAppetizerModel = new DefaultListModel();
    foodMainModel  = new DefaultListModel();
    foodBeverageModel  = new DefaultListModel();
    foodDessertModel  = new DefaultListModel();
    
    //Loops through the menu ArrayList and adds items to their corresponding DefaultListModel based on what type of food they are
    for(int i = 0; i < tempMenu.size(); i++){

      if(tempMenu.get(i).getType().equals("APPETIZER")){
        
        foodAppetizerModel.addElement(tempMenu.get(i).getName());
        
      }else if(tempMenu.get(i).getType().equals("MAIN")){
        
        foodMainModel.addElement(tempMenu.get(i).getName());
        
      }else if(tempMenu.get(i).getType().equals("BEVERAGE")){
        
        foodBeverageModel.addElement(tempMenu.get(i).getName());
        
      }else{
        
        foodDessertModel.addElement(tempMenu.get(i).getName());
        
      }      
      
    }
    
    //Creates the JLists and JScrollPanes of all the items
    appsList = new JList(foodAppetizerModel);
    mainsList = new JList(foodMainModel);
    drinksList = new JList(foodBeverageModel);
    dessertsList = new JList(foodDessertModel);
    
    appScrollPane = new JScrollPane(appsList);
    mainScrollPane = new JScrollPane(mainsList);
    drinkScrollPane = new JScrollPane(drinksList);
    dessertScrollPane = new JScrollPane(dessertsList);
    
  }
  
  //Getters and setters
  public ArrayList<Food> getTempMenu(){
    
    return this.tempMenu;
    
  }
  
  public void setTempMenu(ArrayList<Food> tempMenu){
    
    this.tempMenu = tempMenu;
    
  }
  
  public DefaultListModel getFoodAppetizerModel(){
    
    return this.foodAppetizerModel;
    
  }
  
  public void setFoodAppetizerModel(DefaultListModel foodAppetizerModel){
    
    this.foodAppetizerModel = foodAppetizerModel;
    
  }
  
  public DefaultListModel getFoodMainModel(){
    
    return this.foodMainModel;
    
  }
  
  public void setFoodMainModel(DefaultListModel foodMainModel){
    
    this.foodMainModel = foodMainModel;
    
  }
  
  public DefaultListModel getFoodBeverageModel(){
    
    return this.foodBeverageModel;
    
  }
  
  public void setFoodBeverageModel(DefaultListModel foodBeverageModel){
    
    this.foodBeverageModel = foodBeverageModel;
    
  }
  
  public DefaultListModel getFoodDessertModel(){
    
    return this.foodDessertModel;
    
  }
  
  public void setFoodDessertModel(DefaultListModel foodDessertModel){
    
    this.foodDessertModel = foodDessertModel;
    
  }
  
  public JList getAppsList(){
    
    return appsList;
    
  }
  
  public void setAppsList(JList appsList){
    
    this.appsList = appsList;
    
  }
  
  public JList getMainsList(){
    
    return mainsList;
    
  }
  
  public void setMainsList(JList mainsList){
    
    this.mainsList = mainsList;
    
  }
  
  public JList getDrinksList(){
    
    return drinksList;
    
  }
  
  public void setDrinksList(JList drinksList){
    
    this.drinksList = drinksList;
    
  }
  
  public JList getDessertsList(){
    
    return dessertsList;
    
  }
  
  public void setDessertsList(JList dessertsList){
    
    this.dessertsList = dessertsList;
    
  }
  
  public JScrollPane getAppScrollPane(){
    
    return appScrollPane;
    
  }
  
  public void setAppScrollPane(JScrollPane appScrollPane){
    
    this.appScrollPane = appScrollPane;
    
  }
  
  public JScrollPane getMainScrollPane(){
    
    return mainScrollPane;
    
  }
  
  public void setMainScrollPane(JScrollPane mainScrollPane){
    
    this.mainScrollPane = mainScrollPane;
    
  }
  
  public JScrollPane getDrinkScrollPane(){
    
    return drinkScrollPane;
    
  }
  
  public void setDrinkScrollPane(JScrollPane drinkScrollPane){
    
    this.drinkScrollPane = drinkScrollPane;
    
  }
  
  public JScrollPane getDessertScrollPane(){
    
    return dessertScrollPane;
    
  }
  
  public void setDessertScrollPane(JScrollPane dessertScrollPane){
    
    this.dessertScrollPane = dessertScrollPane;
    
  }
  
}
