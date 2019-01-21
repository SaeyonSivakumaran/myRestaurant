/**
 * The LayoutPanel is a graphic of the restaurants floor layout
 * @author Saeyon Sivakumaran
 * @version 2.0
 * @since 2017-01-15
 */
 
//Imports
import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.io.PrintWriter;
import java.io.File;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

//Main class for the layout panel
class LayoutPanel extends JPanel{
  
  int rows, columns;
  int width;
  int height;
  int mouseX, mouseY;
  int totalTables = 0;
  int selectedTable = 0;
  String parentName;
  String selectedName;
  File layoutFile = new File("layout.txt");
  String[][] layout;
  String[][] tableLayout;
  int[][] tableNumLayout;
  ArrayList<Table> tables;
  ArrayList<TableDimension> tempDimensions;
  TableDimension tempDimension;  
  Color transparent = new Color(1, 2, 3, 0);
  
  //Getter for the arraylist of tables
  public ArrayList<Table> getTables(){
    return tables;
  }
  
  //Getter for the total tables
  public int getTotalTables(){
    return totalTables;
  }
  
  //Getter for the selected table
  public int getSelectedTable(){
    return selectedTable;
  }
  
  //Setter for the selected table
  public void setSelectedTable(int num){
    this.selectedTable = num;
  }
  
  //Setter for the selected name
  public void setSelectedName(String name){
    this.selectedName = name;
  }
  
  //CONSTRUCTOR
  LayoutPanel(int width, int height, String parentName) throws Exception{      
    //Setting the width and height
    this.width = width;
    this.height = height;
    this.parentName = parentName;
    selectedName = "";
    
    //Creating a scanner for the layout file
    Scanner fileInput = new Scanner(layoutFile);
    //Creating the arraylist of tables
    tables = new ArrayList<Table>();
    
    //Initializing the rows and columns
    rows = 0;
    columns = 0;
    
    //Getting the number of rows and columns
    String temp;
    while (fileInput.hasNext()){
      temp = fileInput.nextLine();
      columns = 0;
      for (int count = 0; count < temp.length(); count++){
        if (temp.substring(count, count+1).equals(",")){
          columns++;
        }
      }
      rows++;      
    }
    
    tableLayout = new String[rows][columns];
    layout = setGrid(rows, columns);     
    tableNumLayout = new int[rows][columns];    
    addMouseListener(new mouseListener());
    createAllTables();
    retrieveStatusNums();
    
    fileInput.close();
    
  }
  
  //Creating the 2D layout array with the text file
  public String[][] setGrid(int rows, int columns) throws Exception{    
    String line, word;
    String[][] layout = new String[rows][columns];
    int comma;
    
    File layoutFile = new File("layout.txt");
    Scanner fileInput = new Scanner(layoutFile);
    
    //Creating the string array
    for (int m = 0; m < rows; m++){
      line = fileInput.nextLine();
      for (int n = 0; n < columns; n++){
        comma = line.indexOf(",");
        word = line.substring(0, comma);
        layout[m][n] = word;
        if (!word.equals("0")){
          tableLayout[m][n] = "L";
        } else {
          tableLayout[m][n] = "0";
        }
        line = line.substring(line.indexOf(",")+1, line.length());
      }
    }    
    fileInput.close();
    return layout;    
  }
  
  //Creating all tables
  public void createAllTables(){
    int tableNum = 0;
    for (int x = 0; x < tableLayout.length; x++){
      for (int y = 0; y < tableLayout[1].length; y++){             
        if (tableLayout[x][y].equals("L")){  
          tempDimensions = new ArrayList<TableDimension>();
          tableNum++;   
          totalTables++;
          createTable(x, y, tableNum);   
          tables.add(new Table(tempDimensions, tableNum)); 
        }
      }
    }
    
  }
  
  //Creating tables
  public void createTable(int x, int y, int tableNum){
    if (tableLayout[x][y].equals("0") || tableLayout[x][y].equals("X")){
      //Do nothing
    } else {
      tableNumLayout[x][y] = tableNum;
      tableLayout[x][y] = "X";
      tempDimension = new TableDimension(y*(width/columns), x*((height-20)/rows), width/columns, (height-20)/rows);
      tempDimensions.add(tempDimension);      
      createTable(x-1, y, tableNum);
      createTable(x, y+1, tableNum);
      createTable(x-1, y+1, tableNum);
      createTable(x-1, y-1, tableNum);
      createTable(x, y-1, tableNum);
      createTable(x+1, y, tableNum);
      createTable(x+1, y-1, tableNum);
      createTable(x+1, y+1, tableNum);
    }
  }
  
  //Method to retrieve status numbers from layout
  public void retrieveStatusNums(){
    for (int x = 0; x < layout.length; x++){
      for (int y = 0; y < layout[1].length; y++){
        if (layout[x][y].equals("Empty") || layout[x][y].equals("L")){
          tables.get(tableNumLayout[x][y] - 1).setStatusNum(0);
        } else if (layout[x][y].equals("OrderPending")){
          tables.get(tableNumLayout[x][y] - 1).setStatusNum(1);
        } else if (layout[x][y].equals("FoodPending")){
          tables.get(tableNumLayout[x][y] - 1).setStatusNum(2);
        } else if (layout[x][y].equals("Eating")){
          tables.get(tableNumLayout[x][y] - 1).setStatusNum(3);
        } else if (layout[x][y].equals("Unavailable")){
          tables.get(tableNumLayout[x][y] - 1).setStatusNum(4);
        }
      }
    }
    
  }
  
  //Graphics paint component
  public void paintComponent(Graphics g){
    
    super.paintComponent(g);  //Calling the super class
    Table tempTable;
    TableDimension tempDimensions;
    int xCoord, yCoord;
    
    //Drawing the background
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);
    
    //Changing selection colours for the dine in panel
    if (parentName.equals("DineInOrderPanel") && selectedTable != 0){          
      //Drawing a rectangle for each part of the layout
      for (int x = 0; x < layout[1].length; x++){
        for (int y = 0; y < layout.length; y++){
          if (layout[y][x].equals("0")){
            g.setColor(transparent);
          } else {
            g.setColor(Color.GRAY);
          }
          //Drawing the table rectangles
          g.fillRect(x*(width/columns), y*((height-20)/rows), width/columns, (height-20)/rows);        
        }   
      } 
      tables.get(selectedTable-1).changeColour(g);
    } else if (parentName.equals("FloorPanel")){
      //Drawing a rectangle for each part of the layout
      for (int x = 0; x < layout[1].length; x++){
        for (int y = 0; y < layout.length; y++){
          if (layout[y][x].equals("0")){
            g.setColor(transparent);
          } else if (layout[y][x].equals("OrderPending")){
            g.setColor(Color.YELLOW);
          } else if (layout[y][x].equals("FoodPending")){
            g.setColor(Color.ORANGE);
          } else if (layout[y][x].equals("Eating")){
            g.setColor(Color.GREEN);
          } else if (layout[y][x].equals("Unavailable")){
            g.setColor(Color.RED);
          } else {
            g.setColor(Color.GRAY);
          }
          //Drawing the table rectangles
          g.fillRect(x*(width/columns), y*((height-20)/rows), width/columns, (height-20)/rows);        
        }   
      } 
    } else if (parentName.equals("WaiterTab")){ 
      //For loop for checking if a table has a waiter assigned to it
      for (int x = 0; x < tables.size(); x++){
        //If it does, call the drawBoxAround graphic
        if (tables.get(x).getAssignedWaiter() != null){
          tables.get(x).drawBoxAround(g, true);
        } else {
          tables.get(x).drawBoxAround(g, false);
        }
        repaint();
      }
      //Drawing a rectangle for each part of the layout
      for (int x = 0; x < layout[1].length; x++){
        for (int y = 0; y < layout.length; y++){
          if (layout[y][x].equals("0")){
            g.setColor(transparent);
          } else {
            g.setColor(Color.GRAY);
          }
          //Drawing the table rectangles
          g.fillRect(x*(width/columns), y*((height-20)/rows), width/columns, (height-20)/rows);        
        }   
      }
    } else {
      //Drawing a rectangle for each part of the layout
      for (int x = 0; x < layout[1].length; x++){
        for (int y = 0; y < layout.length; y++){
          if (layout[y][x].equals("0")){
            g.setColor(transparent);
          } else {
            g.setColor(Color.GRAY);
          }
          //Drawing the table rectangles
          g.fillRect(x*(width/columns), y*((height-20)/rows), width/columns, (height-20)/rows);        
        }   
      } 
    }
    
    //Setting colour and font for drawing the table numbers
    g.setColor(Color.BLACK);
    g.setFont(new Font("Arial", Font.PLAIN, 20));
    
    //Drawing the table numbers
    for (int count = 0; count < tables.size(); count++){      
      tempTable = tables.get(count);
      tempDimensions = tempTable.getDimensions().get(0);
      xCoord = tempDimensions.getX() + tempDimensions.getWidth()/2;
      yCoord = tempDimensions.getY() + (int)Math.round(tempDimensions.getHeight()/1.5);
      g.drawString(Integer.toString(tempTable.getTableNum()), xCoord, yCoord);
    }
    
  }
  
  //Mouse Listener
  class mouseListener implements MouseListener{
    public void mousePressed(MouseEvent e) {
      //Getting and outputting the cursor's coordinates when pressed
      mouseX = e.getX();
      mouseY = e.getY();
      //Finding which table has been clicked on if any
      for (int count = 0; count < tables.size(); count++){
        if (tables.get(count).isSelected(mouseX, mouseY) == true){       
          selectedTable = tables.get(count).getTableNum();
          //Changing the table status and repainting the floor
          for (int x = 0; x < tableNumLayout.length; x++){
            for (int y = 0; y < tableNumLayout[1].length; y++){
              if (tableNumLayout[x][y] == tables.get(count).getTableNum()){
                //Changing colours based on the current status
                if (tables.get(count).getStatusNum() == 0){
                  tables.get(count).changeStatus(x, y, layout[x][y], "OrderPending", layout);
                  tables.get(count).setStatusNum(tables.get(count).getStatusNum()+1);
                  repaint();
                  return;
                } else if (tables.get(count).getStatusNum() == 1){
                  tables.get(count).changeStatus(x, y, layout[x][y], "FoodPending", layout);
                  tables.get(count).setStatusNum(tables.get(count).getStatusNum()+1);
                  repaint();
                  return;
                } else if (tables.get(count).getStatusNum() == 2){
                  tables.get(count).changeStatus(x, y, layout[x][y], "Eating", layout);
                  tables.get(count).setStatusNum(tables.get(count).getStatusNum()+1);
                  repaint();
                  return;
                } else if (tables.get(count).getStatusNum() == 3){
                  tables.get(count).changeStatus(x, y, layout[x][y], "Unavailable", layout);
                  tables.get(count).setStatusNum(tables.get(count).getStatusNum()+1);
                  repaint();
                  return;
                } else if (tables.get(count).getStatusNum() == 4){
                  tables.get(count).changeStatus(x, y, layout[x][y], "Empty", layout);
                  tables.get(count).setStatusNum(0);
                  repaint();
                  return;
                }
              }                
            }
          }       
        }
      }
    }    
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
  }
  
}  //End of LayoutPanel2 JPanel class