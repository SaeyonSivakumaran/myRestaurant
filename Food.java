/* 
 * [Food.java]
 * Description: This class creates the object Food that has all variables Foods have in common
 * @author Chris Shah
 * @version 1.0
 * January 22, 2017
 */

class Food {

  //Declares all the variables that all foods should have
  private String name;
  private double price;
  private String type;
  
  //Constructor
  Food(String name, double price, String type){
    
    //Initializes the common variables
    this.name = name;
    this.price = price;
    this.type = type;
    
  }
  
  //Getters and setters
  
  public String getName(){
    
    return name;
    
  }
  
  public void setName(String name){
    
    this.name = name;
    
  }  
  
  public double getPrice(){
    
    return price;
    
  }
  
  public void setPrice(double price){
    
    this.price = price;
    
  }
  
   public String getType(){
    
    return type;
    
  }
  
  public void setType(String type){
    
    this.type = type;
    
  }  

}