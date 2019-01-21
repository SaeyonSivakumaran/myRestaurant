/*TakeOutOrder.java
 * Authors Chris Shah, Saeyon Sivakumaran, Daniel Tang, David Wang
 * Date 22 January 2017
 * This file manages the setting and getting of information related to take out orders
 */

class TakeOutOrder extends Food{
  
  private String orderNumber;
  private String customer;
  private String timeOrdered;
  private CompletedOrderRecord completedOrderRecord;
  
  TakeOutOrder(String name, double price, String type, String orderNumber, String customer, String timeOrdered, CompletedOrderRecord completedOrderRecord){
    
    super(name, price, type);
    this.orderNumber = orderNumber;
    this.customer = customer;
    this.timeOrdered = timeOrdered;
    this.completedOrderRecord = completedOrderRecord;
    
  }
  
  /*
   * getOrderNumber()
   * this method gets the order number
   */
  String getOrderNumber(){
    
    return orderNumber;
    
  }
  
   /*
   * setOrderNumber()
   * this method sets the order number
   */
  void setOrderNumber(String orderNumber){
    
    this.orderNumber = orderNumber;
    
  }
  
   /*
   * getCustomer()
   * this method gets the customer name
   */
  String getCustomer(){
    
    return customer;
    
  }
  
  /*
   * setCustomer()
   * this method sets the customer name
   */
  void setCustomer(String customer){
    
    this.customer = customer;
    
  }
  
  /*
   * getTimeOrdered()
   * this method gets the order time
   */
  String getTimeOrdered(){
    
    return timeOrdered;
  
  }
  
  /*
   * setTimeOrdered()
   * this method sets the order time
   */
  public void setTimeOrdered(String timeOrdered){
    
    this.timeOrdered = timeOrdered;
    
  }
  
  /*
   * getCompletedOrderRecord()
   * this method gets the completed order record
   */
  public CompletedOrderRecord getCompletedOrderRecord(){
    
    return this.completedOrderRecord;
    
  }
  
  /*
   * setCompletedOrderRecord()
   * this method sets the completed order record
   */
  public void setCompletedOrderRecord(CompletedOrderRecord completedOrderRecord){
    
    this.completedOrderRecord = completedOrderRecord;
    
  }
  
}


