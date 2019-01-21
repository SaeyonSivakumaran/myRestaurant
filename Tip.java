class Tip{
  
  private Double tip;
  private int tableNumber;
  
  Tip(Double tip, int tableNumber){
  
    this.tip = tip;
    this.tableNumber = tableNumber;
  
  }
  
  public int getTip(){
    
    return tip.intValue();
    
  }
  
  public void setTip(double tip){
    
    this.tip = tip;
    
  }
  
  public int getTableNumber(){
    
    return tableNumber;
    
  }
  
  public void setTableNumber(int tableNumber){
    
    this.tableNumber = tableNumber;
    
  }
  
  
}