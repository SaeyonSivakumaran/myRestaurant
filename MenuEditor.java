/**
 * MenuEditor.java
 * The MenuEditor allows the user to create a menu and edit it
 * @author Saeyon Sivakumaran
 * @version 1.0
 */

//Imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultCellEditor;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;


//Main JFrame class for the menu editor
class MenuEditor extends JFrame{
  
  //Menu JTable variables
  JPanel tablePanel;
  JTable menuTable;
  JTableHeader tableHeader;
  String[] headers;
  String[] typesList = new String[]{"APPETIZER", "MAIN", "SIDE", "BEVERAGE", "DESSERT"};
  JComboBox types = new JComboBox(typesList);
  TableColumn typesColumn;
  DefaultTableModel tableModel;
  String[] blank = new String[]{"APPETIZER", "Insert Item Name Here", "Insert Item Price Here"};
  int selectedRow;
  
  //Menu variables
  Menu menu;
  String[][] menuInfo;
  
  //Buttons panel
  JPanel buttonPanel;
  JButton updateButton;
  JButton addButton;
  JButton removeButton;
  
  //CONSTRUCTOR
  MenuEditor(int w, int h) throws Exception{
    super("Menu Editor");
    setSize(w, h);    
    setResizable(false);
    setLayout(new BorderLayout());
    
    //Creating the menu and getting the item names
    menu = new Menu();
    menuInfo = menu.getInfo();
    
    //Creating the JTable and the table panel
    tablePanel = new JPanel();
    tablePanel.setLayout(new BorderLayout());
    headers = new String[]{"Type", "Name", "Price"};
    tableModel = new DefaultTableModel(menuInfo, headers);
    menuTable = new JTable(tableModel);  
    menuTable.setFont(new Font("Arial", Font.PLAIN, 14));
    tableHeader = menuTable.getTableHeader();
    tableHeader.setFont(new Font("Arial", Font.BOLD, 18));
    typesColumn = menuTable.getColumnModel().getColumn(0);
    typesColumn.setCellEditor(new DefaultCellEditor(types));
    
    //Adding components to the table panel
    tablePanel.add(tableHeader, BorderLayout.NORTH);
    tablePanel.add(menuTable, BorderLayout.CENTER);
    
    //Buttons
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3,1));
    updateButton = new JButton("Save Menu");
    updateButton.addActionListener(new updateListener());
    addButton = new JButton("Add Item");
    addButton.addActionListener(new addListener());
    removeButton = new JButton("Remove Selected Item");
    removeButton.addActionListener(new removeListener());
    
    //Adding components to the button panel
    buttonPanel.add(addButton);
    buttonPanel.add(removeButton);
    buttonPanel.add(updateButton);
    
    //Adding components to the main panel
    add(tablePanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.EAST);

    setVisible(true);
  }
  
  //Button Listener: Update Button
  class updateListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      String[][] temp = new String[menuTable.getRowCount()][menuTable.getColumnCount()];
      boolean doubleParseable;
      double tempNum;
      //Updating the menu with the table information
      for (int x = 0; x < menuTable.getRowCount(); x++){
        for (int y = 0; y < menuTable.getColumnCount(); y++){
          if (y == 2){
            doubleParseable = true;
            //Checking if the double can be converted to a string
            try{  
              Double.parseDouble(temp[x][y]);
            }catch(NumberFormatException e){
              doubleParseable = false;
            }catch(NullPointerException e){
              doubleParseable = false;
            }
            if (doubleParseable = true){
              tempNum = Double.parseDouble((String)tableModel.getValueAt(x,y));
              tempNum = Math.round(tempNum * 100.00)/100.00;
              temp[x][y] = String.valueOf(tempNum); 
            } 
          }else {
            temp[x][y] = tableModel.getValueAt(x,y).toString();
          }
        }
      }
      menuInfo = temp;
      tableModel.fireTableDataChanged();
      //Updating the menu
      try{
        menu.update(menuInfo);
      } catch (Exception e){}
    }
  }
  
  //Button Listener: Add Item Button
  class addListener implements ActionListener{
    public void actionPerformed(ActionEvent event){     
      //Adding a blank row
      tableModel.addRow(blank);
    }
  }
  
  //Button Listener: Remove Item Button
  class removeListener implements ActionListener{
    public void actionPerformed(ActionEvent event){ 
      selectedRow = menuTable.getSelectedRow();
      //Removing the row
      if (selectedRow != -1){
        tableModel.removeRow(selectedRow);
      }
    }
  }
  
}