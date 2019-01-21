/**
 * The TableDimension class store the dimensions of a table cell
 * @author Saeyon Sivakumaran
 * @version 1.0
 * @since 2017-01-14
 */

class TableDimension{
  
  int[] dimensions;  //Array of dimensions
  
  //Getter for the x coord
  public int getX(){
    return dimensions[0];
  }
  
  //Getter for the y coord
  public int getY(){
    return dimensions[1];
  }
  
  //Getter for the width
  public int getWidth(){
    return dimensions[2];
  }
  
  //Getter for the height
  public int getHeight(){
    return dimensions[3];
  }
  
  //CONTRUCTOR
  TableDimension(int x, int y, int width, int height){
    dimensions = new int[4];
    dimensions[0] = x;
    dimensions[1] = y;
    dimensions[2] = width;
    dimensions[3] = height;
  }
  
  //Checking if the coordinates are in the dimensions
  public boolean isSelected(int x, int y){
    if (x >= dimensions[0] && x <= dimensions[0] + dimensions[2] && y >= dimensions[1] && y <= dimensions[1] + dimensions[3]){
      return true;
    } else {
      return false;
    }
  }
  
}