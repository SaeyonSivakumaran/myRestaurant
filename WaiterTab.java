/**
 * The WaiterTab is an object that is represents waiters made by the users
 * author Daniel Tang
 * version 1.0
 * since 2017-01-22
 */

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JColorChooser;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import java.util.ArrayList;

//Main Class
class WaiterTab extends JPanel {
  
  //List of public variables needed for the entire tab
  LayoutPanel tableLayout;
  
  ArrayList <Tip> tipsArray;
  ArrayList <Table> tables;
  
  int mouseX, mouseY;
  int listSize = 0;
  
  String selectedName; 
  
  JButton tipWaiters;
  JButton editWaiters;
  JButton removeWaiters;
  JButton viewEditWaiters;
  JButton addWaiters;
  JButton editCloseWaiters;
  JButton viewWaiters;
  
  JTextField editNameField;
  JTextField editColorField;
  JTextField waiterNameField;
  JTextField waiterColorField;
  
  JLabel editWaiterNameLabel;
  JLabel editWaiterColorLabel;
  JLabel blank;
  JLabel waiterNameLabel;
  JLabel waiterName;
  JLabel waiterColor;
  JLabel waiterTip;
  
  JList waiterList;
  JList colorList;
  
  JColorChooser colorChooser;
  DefaultColorSelectionModel colorModel;
  
  DefaultListModel model;
  ListSelectionModel listSelectionModel;
  JScrollPane viewScroll;
  
  WaiterList waiters;
  ArrayList <Color> colors;
  
  JFrame editWindow;
  
  JPanel waiterInfo;
  JPanel editPanel;
  JPanel addPanel;
  JPanel viewPanel;
  JPanel colourPanel;
  JPanel legendPanel;
  JPanel buttonPanel;
  JPanel tipPanel;
  
  Waiter newWaiter;
  WaiterInfo infoTab;
  
  //CONSTRUCTOR
  WaiterTab(int w,int h, ArrayList<Tip> tipsArray) throws Exception {
    this.setLayout(new GridLayout(1,2));
    tableLayout = new LayoutPanel(w,h, "WaiterTab");
    tables = tableLayout.getTables();
    tableLayout.addMouseListener(new waiterMouseListener());
    this.tipsArray = tipsArray;
    infoTab = new WaiterInfo();
    this.add(tableLayout);
    this.add(infoTab);
    revalidate();
    repaint();
  }
  
  //Mouse Listener
  class waiterMouseListener implements MouseListener{
    public void mousePressed(MouseEvent e) {
      if (waiterList.getSelectedIndex() != -1){
        //Getting and outputting the cursor's coordinates when pressed
        mouseX = e.getX();
        mouseY = e.getY();
        //Finding which table has been clicked on if any
        for (int count = 0; count < tables.size(); count++){
          if (tables.get(count).isSelected(mouseX, mouseY) == true){
            //If the table has a waiter then assign a waiter to it
            if (tables.get(count).getWaiter() == false){
              tables.get(count).setWaiter(true, waiters.viewWaiter(waiterList.getSelectedIndex()));
              //Otherwise take away the assigned waiter from the table
            } else {
              tables.get(count).setWaiter(false, null);
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
  
  //Class: All non-table-layout components
  class WaiterInfo extends JPanel {
    
    //CONSTRUCTOR
    WaiterInfo(){
      this.setLayout(new GridLayout(1,2));
      
      buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(4,1));
      
      waiters = new WaiterList();
      
      model = new DefaultListModel();
      
      //Declaring and setting up the colour chooser
      colorModel = new DefaultColorSelectionModel();
      colorChooser = new JColorChooser(colorModel);
      colorChooser.setPreviewPanel(new JPanel());
      colorChooser.getSelectionModel().addChangeListener(new changeListener());
      colourPanel = new JPanel();
      
      //Panel used for displaying the assigned waiter's color
      legendPanel = new JPanel();
      
      //Buttons on the WaiterInfo Panel
      addWaiters = new JButton("Add Waiters");
      addWaiters.addActionListener(new editButtonListener());
      addWaiters.setFont(new Font("Arial", Font.PLAIN, 20));
      viewWaiters = new JButton("View Waiter");
      viewWaiters.addActionListener(new viewEditButtonListener());
      viewWaiters.setFont(new Font("Arial", Font.PLAIN, 20));
      removeWaiters = new JButton("Remove Waiter");
      removeWaiters.addActionListener(new removeButtonListener());
      removeWaiters.setFont(new Font("Arial", Font.PLAIN, 20));
      tipWaiters = new JButton ("View Tips");
      tipWaiters.addActionListener(new tipButtonListener());
      tipWaiters.setFont(new Font("Arial", Font.PLAIN, 20));
      
      waiterNameLabel = new JLabel("Waiter List: ");
      waiterNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
      
      //Initiallizing the JList and adding a list selection listener to it
      waiterList = new JList(model);
      waiterList.addListSelectionListener(new listSelectionListener());
      viewScroll = new JScrollPane(waiterList);
      waiterList.setListData(waiters.turnArray());
      listSize = waiters.getListSize();
      waiterList.setFont(new Font("Arial", Font.PLAIN, 20));
      
      buttonPanel.add(addWaiters);
      buttonPanel.add(viewWaiters);
      buttonPanel.add(removeWaiters);
      buttonPanel.add(tipWaiters);
      
      //Adding all of the components to the panel
      this.add(waiterNameLabel);
      this.add(viewScroll);
      this.add(buttonPanel);
    }
    
    //Sets a panel so that the user can see the colour they selected
    class changeListener implements ChangeListener{
      public void stateChanged(ChangeEvent e) {
        colourPanel.setBackground(colorModel.getSelectedColor());
      }
    }
    
    //Sets the selection so that it corresponds with the selected waiter's colour
    class listSelectionListener implements ListSelectionListener{
      public void valueChanged(ListSelectionEvent e){
        if (waiterList.getSelectedIndex() >= 0){  
          waiterList.setSelectionBackground(waiters.viewWaiter(waiterList.getSelectedIndex()).getWaiterColor());
          selectedName = (String)waiterList.getSelectedValue();
          tableLayout.setSelectedName(selectedName);
        }
      }
    }
    
    //Adds tips to waiters that are assigned to tables
    class tipButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent event){
        if (waiterList.getSelectedIndex() != -1){
          //Getting a new window to pop up when the button is clicked
          editWindow = new JFrame("Edit Waiters");
          editWindow.setSize(300, 300);
          editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          editWindow.setResizable(true);
         
          //Clearing all the tip values so that they can be readded
          for(int count = count = 0; count < tables.size(); count++){
            if(tables.get(count).getAssignedWaiter() != null){
              
              tables.get(count).getAssignedWaiter().setWaiterTip(0);
              
            }          
          }
          
          //Going through tables to check if their is a waiter assigned to it and adds necessary tips
          for (int count = 0; count < tables.size(); count++){
            for(int count2 = 0; count2 < tipsArray.size(); count2++){
              if (tables.get(count).getAssignedWaiter() != null){
                if(tipsArray.get(count2).getTableNumber() == tables.get(count).getTableNum()){
                  tables.get(count).getAssignedWaiter().addWaiterTip(tipsArray.get(count2).getTip());
                }
              }
            }
          }
          
          //Adds the label to the panel
          String tipString = Double.toString(waiters.viewWaiter(waiterList.getSelectedIndex()).getWaiterTip());
          if(tipString.indexOf(".") == tipString.length() - 2){
            tipString += "0";
          }
          waiterTip = new JLabel("Tips: $" + tipString);
          tipPanel = new JPanel();
          
          tipPanel.add(waiterTip);
          
          //Adds panel to the window
          editWindow.add(tipPanel);
          editWindow.setVisible(true);
        }
      }
    }
    
    //A button listener for adding new waiters
    class editButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent event) {
        //Getting a new window to pop up when the button is clicked
        editWindow = new JFrame("Edit Waiters");
        editWindow.setSize(900, 700);
        editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editWindow.setResizable(true);
        
        //Initializing Panel components
        viewPanel = new JPanel();
        colourPanel = new JPanel();
        colourPanel.setBackground(colorModel.getSelectedColor());
        
        //Initializing GUI components
        addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(3,2));
        waiterName = new JLabel("Name:");
        waiterName.setFont(new Font("Arial", Font.PLAIN, 20));
        waiterNameField = new JTextField(10);
        waiterNameField.setFont(new Font("Arial", Font.PLAIN, 20));
        waiterColor = new JLabel("Color:");
        waiterColor.setFont(new Font("Arial", Font.PLAIN, 20));
        waiterColorField = new JTextField(10);
        addWaiters = new JButton("Add");
        addWaiters.setFont(new Font("Arial", Font.PLAIN, 20));
        addWaiters.addActionListener(new addButtonListener());
        
        //Adding components to panel
        addPanel.add(waiterName);
        addPanel.add(waiterNameField);
        addPanel.add(waiterColor);
        addPanel.add(colourPanel);
        addPanel.add(colorChooser);
        addPanel.add(addWaiters);
        
        //Adding panels to JFrame
        editWindow.add(addPanel);
        editWindow.setVisible(true);
        
      }
    }
    //Creates a new waiter class when the add button is clicked
    class addButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent event) {
        String tempName, tempColor;
        tempName = waiterNameField.getText();
        tempColor = waiterColorField.getText();
        newWaiter = new Waiter(tempName,colorModel.getSelectedColor());
        System.out.println(newWaiter.getWaiterTip());
        waiters.addWaiter(newWaiter);
        waiterList.setListData(waiters.turnArray());
        editWindow.dispose();
      }
    }
    
    //Removes the waiter selected completely and gets rid of any tables it was assigned to
    class removeButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent event) {
        if (waiterList.getSelectedIndex() != -1){
          //Checking which table the waiter was assigned to and setting it back to an empty table
          for (int count = 0; count < tables.size(); count++){
            if (tables.get(count).getAssignedWaiter() != null){
              if (tables.get(count).getAssignedWaiter().getWaiterName().equals((String)waiterList.getSelectedValue())){
                tables.get(count).setWaiter(false, null);
              }
            }
          }
          waiters.removeWaiter(waiterList.getSelectedIndex());
          waiterList.setListData(waiters.turnArray());
          editWindow.dispose();
        }
      }
    }
    
    //A button listener for editing waiters
    class viewEditButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent event) {
        if (waiterList.getSelectedIndex() != -1){
          //Getting a new window to pop up when the button is clicked
          editWindow = new JFrame("Edit Waiters");
          editWindow.setSize(900, 700);
          editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
          editWindow.setResizable(true);
          
          //Initializing Panel components
          editPanel = new JPanel();
          editPanel.setLayout(new GridLayout(3,2));
          
          //Initializing GUI components
          viewEditWaiters = new JButton("View");
          viewEditWaiters.addActionListener(new viewEditButtonListener());
          viewEditWaiters.setFont(new Font("Arial", Font.PLAIN, 20));
          editWaiterNameLabel = new JLabel("Name");
          editWaiterNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
          editWaiterColorLabel = new JLabel("Color");
          editWaiterColorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
          editNameField = new JTextField(waiters.viewWaiter(waiterList.getSelectedIndex()).getWaiterName());
          editNameField.setFont(new Font("Arial", Font.PLAIN, 20));
          editCloseWaiters = new JButton("Edit/Close");
          editCloseWaiters.addActionListener(new editCloseButtonListener());
          editCloseWaiters.setFont(new Font("Arial", Font.PLAIN, 20));
          
          legendPanel.setBackground(waiters.viewWaiter(waiterList.getSelectedIndex()).getWaiterColor());
          
          //Adding components to panel
          editPanel.add(editWaiterNameLabel);
          editPanel.add(editNameField);
          editPanel.add(editWaiterColorLabel);
          editPanel.add(colourPanel);
          editPanel.add(colorChooser);
          editPanel.add(editCloseWaiters);
          
          //Adding panels to JFrame
          editWindow.add(editPanel);
          editWindow.setVisible(true);
        }
      }
    }
    
    //Button listener for setting the new changes to a waiter class
    class editCloseButtonListener implements ActionListener{
      public void actionPerformed(ActionEvent event) {
        //Setting the new information for the waiter object
        waiters.viewWaiter(waiterList.getSelectedIndex()).setWaiterName(editNameField.getText());
        waiters.viewWaiter(waiterList.getSelectedIndex()).setWaiterColor(colorModel.getSelectedColor());
        waiterList.setListData(waiters.turnArray());
        editWindow.dispose();
      }
    }
  }
}
