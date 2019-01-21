/**
 * The LayoutMaker is a program to create/edit the layout file
 * @author Saeyon Sivakumaran
 * @version 1.0
 * @since 2017-01-18
 */

//Imports
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultCellEditor;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File; 
import java.io.PrintWriter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//Main class
class LayoutMaker extends JPanel{
  
  JTable layoutEditor;
  DefaultTableModel tableModel;
  String[] selections = new String[]{"TABLE", ""};
  JComboBox layoutSelector = new JComboBox(selections);
  JPanel buttonPanel;
  JButton addColumn, addRow, updateLayout, removeColumn, removeRow;
  File layoutFile = new File("layout.txt");
  JTextField blank = new JTextField();
  
  //CONSTRUCTOR
  LayoutMaker(){
    setLayout(new BorderLayout());
    //Creating the buttons panel and the buttons in it
    buttonPanel = new JPanel(new GridLayout(5,1));
    addColumn = new JButton("Add Column");
    addColumn.addActionListener(new addListener());
    addRow = new JButton("Add Row");
    addRow.addActionListener(new addListener());
    removeColumn = new JButton("Remove Selected Column");
    removeColumn.addActionListener(new removeListener());
    removeRow = new JButton("Remove Selected Row");
    removeRow.addActionListener(new removeListener());
    updateLayout = new JButton("Update Layout");
    updateLayout.addActionListener(new updateListener());
    buttonPanel.add(addColumn);
    buttonPanel.add(addRow);
    buttonPanel.add(removeColumn);
    buttonPanel.add(removeRow);
    buttonPanel.add(updateLayout);
    //Creating the table
    tableModel = new DefaultTableModel(3, 3);
    layoutEditor = new JTable(tableModel);
    blank.setEditable(false);
    //Creating editors for all the cells
    for (int count = 0; count < tableModel.getColumnCount(); count++){
      layoutEditor.getColumnModel().getColumn(count).setCellEditor(new DefaultCellEditor(layoutSelector));
    }
    //Adding components to the panel
    add(layoutEditor, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.EAST);
  }
  
  //Update listener
  class updateListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      String[][] tableData = new String[tableModel.getRowCount()][tableModel.getColumnCount()];
      //Getting table data into a string array
      for (int x = 0; x < tableModel.getRowCount(); x++){
        for (int y = 0; y < tableModel.getColumnCount(); y++){
          if (tableModel.getValueAt(x,y) == null || tableModel.getValueAt(x,y) == ""){
            tableData[x][y] = "0";
          } else if(tableModel.getValueAt(x,y) == "TABLE"){
            tableData[x][y] = "L";
          }
        }
      }  
      //Updating the layout
      try{
        updateLayoutFile(tableData);
      } catch(Exception e){}
    }
  }
  
  //Method to update the layout file
  public void updateLayoutFile(String[][] tableData) throws Exception{
    PrintWriter fileOutput = new PrintWriter(layoutFile);
    //Writing out the data to the layout file
    for (int count = 0; count < tableData[1].length+2; count++){
      fileOutput.print("0,");
    }
    fileOutput.println();
    for (int a = 0; a < tableData.length; a++){
      fileOutput.print("0,");
      for (int b = 0; b < tableData[1].length; b++){
        fileOutput.print(tableData[a][b] + ",");
      }
      fileOutput.print("0,");
      fileOutput.println();
    }
    for (int count1 = 0; count1 < tableData[1].length+2; count1++){
      fileOutput.print("0,");
    }
    fileOutput.close();
  }
  
  //Add Button listeners
  class addListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      JButton source = (JButton)event.getSource();
      //Adding columns and rows for the table
      if (source == addColumn){
        tableModel.addColumn("");
        //Creating editors for all the columns
        for (int count = 0; count < tableModel.getColumnCount(); count++){
          layoutEditor.getColumnModel().getColumn(count).setCellEditor(new DefaultCellEditor(layoutSelector));
        }
      } else {
        tableModel.addRow(new String[0]);  //Adding a blank row
      }
    }
  }
  
  //Remove button listeners
  class removeListener implements ActionListener{
    public void actionPerformed(ActionEvent event){
      JButton source = (JButton)event.getSource();
      if (source == removeColumn){  //Removing the selected column
        if(layoutEditor.getSelectedColumn() != -1){
          layoutEditor.removeColumn(layoutEditor.getColumnModel().getColumn(layoutEditor.getSelectedColumn()));
        }
      } else {  //Removing the selected row        
        if(layoutEditor.getSelectedRow() != -1){
          layoutEditor.getCellEditor(layoutEditor.getSelectedRow(), layoutEditor.getSelectedColumn()).stopCellEditing();
          tableModel.removeRow(layoutEditor.getSelectedRow());
        }
      }
      tableModel.fireTableDataChanged();
    }
  }
  
}