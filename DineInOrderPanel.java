/* 
 * [DineInOrderPanel.java]
 * Description: This class a custom JPanel to be added to the MainWindow
 * @author Chris Shah
 * @version 1.0
 * January 22, 2017
 */

//Import statements
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.util.ArrayList;

import java.util.Scanner;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.File; 

class DineInOrderPanel extends JPanel{
  
  //Creates all the class variables that are used in separate methods or inner classes
  JPanel dineInOrderPanel;
  JPanel dineLeftPanel;
  JPanel dineRightPanel;
  
  JList dineInOrders;
  DefaultListModel listModel;
  JComboBox tableOrders;
  ArrayList<Food> dineInOrderArray;
  ArrayList<Food> tempMenu;
  
  CompletedOrderRecord completedDineInOrderRecord;
  ArrayList<Food> completedDineInOrders;
  
  JTextArea currentOrder;
  JTextField customerNum; 
  
  JButton addAppButton;
  JButton addMainButton;
  JButton addDrinkButton; 
  JButton addDessertButton; 
  
  JFrame menuFrame;
  
  int numCustomers;
  int tempCustomer;
  
  JButton addDineInOrder;
  JButton editDineInOrder;
  
  JButton orderButton;
  
  String dashes;
  String dots;
  Boolean enterCustomer = true;
  
  double totalPrice;
  int mouseX, mouseY;
  
  LayoutPanel layoutPanel;
  
  int selectedTable;
  
  CompletedOrderRecord completedDineInOrder;
  
  OrderMenu menu;

  ArrayList<Tip> tipsArray;
  
  //Constructor
  DineInOrderPanel(int frameWidth, int frameHeight, OrderMenu menu, Order dineInOrderArray, CompletedOrderRecord completedDineInOrderRecord, ArrayList<Tip> tipsArray) throws Exception{
    
    //Sets the layout and intializes class variables from the constructor
    this.setLayout((new GridLayout(1,2)));
    this.menu = menu;
    tempMenu = menu.getTempMenu();
    this.dineInOrderArray = dineInOrderArray.getOrderArray();
    this.completedDineInOrderRecord = completedDineInOrderRecord;
    this.completedDineInOrders = (ArrayList<Food>)completedDineInOrderRecord.getCompletedList();
    this.tipsArray = tipsArray;
    
    //Creates new panels
    dineInOrderPanel = new JPanel(new GridLayout(1,2));
    dineLeftPanel = new JPanel(new GridLayout(3,1));
    dineRightPanel = new JPanel(new GridLayout(4,3));

    //Creates a LayoutPanel object 
    layoutPanel = new LayoutPanel(frameWidth, frameHeight, "DineInOrderPanel");
    
    //Creates an array of strings that hold table numbers, calculated based on the total number of tables
    String[] tableArray = new String[layoutPanel.getTotalTables()];
    
    for(int i = 0; i < tableArray.length; i++){
      tableArray[i] = "Table " + (i + 1);
    }
    
    //Creates a JComboBox of the tables and adds an action listener
    tableOrders = new JComboBox(tableArray);
    tableOrders.addActionListener(new clickTableListener());
    
    //Adds the JComboBox to the left panel
    dineLeftPanel.add(tableOrders);
    
    //Creates a new DefaultListModel, JList, and JScrollPane which will contain the orders the user enters
    listModel = new DefaultListModel();    
    dineInOrders = new JList(listModel);
    JScrollPane dineInOrdersScrollPane = new JScrollPane(dineInOrders);
    
    //Adds the JScrollPane of orders to the left panel
    dineLeftPanel.add(dineInOrdersScrollPane);
    
    //Creates buttons each with their own action listeners
    addDineInOrder = new JButton("ADD NEW ORDER");
    addDineInOrder.addActionListener(new addAndEditOrderListener());
    
    editDineInOrder = new JButton("EDIT ORDER");
    editDineInOrder.addActionListener(new addAndEditOrderListener());
    
    JButton removeDineInOrder = new JButton("REMOVE ORDER");
    removeDineInOrder.addActionListener(new removeOrderListener());
    
    JButton completeDineInOrder = new JButton("COMPLETE ORDER");
    completeDineInOrder.addActionListener(new completeOrderListener());
    
    //Adds all the buttons to the right panel
    dineRightPanel.add(addDineInOrder);
    dineRightPanel.add(editDineInOrder);
    dineRightPanel.add(removeDineInOrder);
    dineRightPanel.add(completeDineInOrder);
    
    //Adds a mouse listener to the custom JPanel, LayoutPanel 
    layoutPanel.addMouseListener(new dineInOrderMouseListener());    
    
    //Adds the left and the right panels to the dineInOrderPanel
    dineInOrderPanel.add(dineLeftPanel);
    dineInOrderPanel.add(dineRightPanel);
    
    //Adds both the layout panel and the dine in order panel to the main window
    this.add(layoutPanel);
    this.add(dineInOrderPanel);
    
  }  
 
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  //Beginning of inner classes
  
  /**
   * clickTableListener
   * Description: This class creates the action listener for the JComboBox of table numbers which displays which orders correspond to the table number
   */
    
  class clickTableListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event){
      
      //Calls the method to refresh the orders based on the table number
      refreshOrders();
      
      //Sets the selected table as the table in the JComboBox in order for it to highlight green so the user can see which table they selected on the floor layout
      layoutPanel.setSelectedTable(tableOrders.getSelectedIndex() + 1);
      
      layoutPanel.repaint();
      
    }
    
  }
  
  /**
   * addAndEditOrderListener
   * Description: This class creates the action listener for the "Add Order" and "Edit Order" buttons which creates a new JFrame that contains the menu, 
   * and adds different action listeners to the buttons on the menu, based on which button they originally pressed
   */
    
  class addAndEditOrderListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event){
      
      if(event.getSource() == addDineInOrder){
        //Allows the user to enter which customer they want to add the order to
        enterCustomer = true;
        JFrame menuFrame = new Menu();
        orderButton.addActionListener(new addOrderListener());
        
      }else{
        
        //Makes sure the user clicked something in the JList to edit
        if(dineInOrders.getSelectedIndex() != -1){
          
          //Doesn't allow the user to enter which customer they want to add the order to, but instead defaults to the one that they selected while clicking the edit button
          enterCustomer = false;
          JFrame menuFrame = new Menu();
          orderButton.addActionListener(new editOrderListener());

          //Creates a temporary ArrayList of Foods
          ArrayList<Food> dineInOrderArrayEdit = new ArrayList<Food>();
          
          //Finds which customer the user is selecting
          updateTempCustomer();
          
          //Adds food to the temporary ArrayList that have the same customer number as the one selected
          for(int i = 0; i < dineInOrderArray.size(); i++){
            
            if(tempCustomer == ((DineInOrder)dineInOrderArray.get(i)).getCustomerNumber() && ((DineInOrder)dineInOrderArray.get(i)).getTableNumber() == (tableOrders.getSelectedIndex() + 1)){
              
              dineInOrderArrayEdit.add(dineInOrderArray.get(i));
              
            }
            
          }
          
          //Adds the food from the temporary ArrayList to the currentOrder JTextArea so that the user can see which food they have already added while editing
          for(int i = 0; i < dineInOrderArrayEdit.size(); i++){
            
            dineInOrderArray.remove(dineInOrderArrayEdit.get(i));
            currentOrder.append("\n" + dineInOrderArrayEdit.get(i).getName() + "  ");
            
          }
     
        }
        
      }
      
    }
    
  }
  
  /*
   * completeOrderListener
   * Description: This class creates the action listener for the complete order button which sends all the food from the selected table to the completed order ArrayList
   */
  
  class completeOrderListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event){      
      
      //Creates a temporary ArrayList
      ArrayList<Food> dineInOrderArrayRemove = new ArrayList<Food>();
      
      //Shows an option to the user to select if the tip was paid in dollars or percent of the bill
      Object[] options = { "Dollars ($)", "Percent (%)" };
      int option = JOptionPane.showOptionDialog(null, "Was the tip paid in dollars ($) or percent (%)?", "Tip",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);    
      
      //Prompts the user to enter how much they got in tips
      String tipText = "";
      
      if(option == 0){
        
        tipText = JOptionPane.showInputDialog(null, "Please enter the tip ($)");
        
      }else if(option == 1){
        
        tipText = JOptionPane.showInputDialog(null, "Please enter the tip (%)");
        
      }
      
      double tip = 0;
      
      //If the user did not enter anything, or clicked the "X" button, or the cancel button, the tip defaults to 0
      if(tipText != null && !tipText.equals("")){
        
        //If the user enters the tip in dollars, tries to parse the text into a double, and if they did not enter a double, defaults the tip to 0
        if(option == 0){
          
          try{
            
            tip = Double.parseDouble(tipText);
            
          }catch(NumberFormatException e){}
          
          //If the user enters the tip in %, calculates the tip based on the total bill
        }else if(option == 1){
          
          //If the user did not enter a double, defaults the tip to 0
          try{
            
            tip = Math.round((Double.parseDouble(tipText)*100)/100);
            
          }catch(NumberFormatException e){}      
     
          tip = (Math.round((totalPrice*(tip/100))*100))/100.00;
          
        }
          
        //Adds the tip and the table to an ArrayList of Tip to be used in the Waiter Panel
          tipsArray.add(new Tip(tip, tableOrders.getSelectedIndex() + 1));
          
        }

      //Adds the items that need to be removed to a temporary ArrayList so that they can be removed from the ArrayList of all orders
      for(int i = 0; i < dineInOrderArray.size(); i++){
        
        if(((DineInOrder)dineInOrderArray.get(i)).getTableNumber() == (tableOrders.getSelectedIndex() + 1)){
          dineInOrderArrayRemove.add(dineInOrderArray.get(i));      
        }
        
      }
      
      //Removes the items that were "completed" from the ArrayList of orders
      for(int i = 0; i < dineInOrderArrayRemove.size(); i++){
        
        completedDineInOrders.add(dineInOrderArrayRemove.get(i));
        dineInOrderArray.remove(dineInOrderArrayRemove.get(i));
        
      }
      
      //Saves the ArrayList of completed orders, which will add it to a list model, and then add it to the completed orders tab
      completedDineInOrderRecord.saveCompletedOrders();
      
      //Refreshes the orders to remove all the completed ones from the JList
      refreshOrders();   
      
    }
    
  }
  
  /**
   * clickMenuListener
   * Description: This class creates the action listener to 4 buttons that adds food from the JLists that the user selected to a JTextArea of all the orders being added 
   */
  
  class clickMenuListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event){
      
      //Checks which button was pressed to know which list model to go through, and if the user selected an item from one of the JLists, adds it to the JTextArea of all the orders being added
      
      if(event.getSource() == addAppButton){
        
        if(menu.getAppsList().getSelectedIndex() != -1){
          
          currentOrder.append("\n" + ((DefaultListModel)menu.getFoodAppetizerModel()).getElementAt(menu.getAppsList().getSelectedIndex()) + "  ");
          
        }
        
      }else if(event.getSource() == addMainButton){
        
        if(menu.getMainsList().getSelectedIndex() != -1){
          
          currentOrder.append("\n" + ((DefaultListModel)menu.getFoodMainModel()).getElementAt(menu.getMainsList().getSelectedIndex()) + "  ");
          
        }
        
      }else if(event.getSource() == addDrinkButton){
        
        if(menu.getDrinksList().getSelectedIndex() != -1){
          
          currentOrder.append("\n" + ((DefaultListModel)menu.getFoodBeverageModel()).getElementAt(menu.getDrinksList().getSelectedIndex()) + "  ");
          
        }
        
      }else{
        
        if(menu.getDessertsList().getSelectedIndex() != -1){
          
          currentOrder.append("\n" + ((DefaultListModel)menu.getFoodDessertModel()).getElementAt(menu.getDessertsList().getSelectedIndex()) + "  ");
          
        }
        
      }
      
    }
    
  }
  
  /*
   * addOrderListener
   * Description: Adds an actionlistener to the complete order button on the menu, which is only added if the user orginally clicked add order, and not edit order
   */
  
  class addOrderListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event){
      
      //Gets the text from the JTextArea of the orders being added, and removes all the unneccesary text
      String currentOrderText = currentOrder.getText();
      
      currentOrderText = currentOrderText.substring(currentOrderText.indexOf("- ") + 2);
      
      String tempFood;
      boolean enteredInt = true;
      
      //Tries to parse the text in the Enter Customer Number field, and will only continue through the code if they entered an int
      try {
        Integer.parseInt(customerNum.getText());
      } catch(NumberFormatException e) {
        enteredInt = false;
      }
      
      if(currentOrderText.length() > 1 && enteredInt){
        
        //Loops through the whole string that contains the JTextArea of the orders being added, gets each individual food by getting rid of unnecessary text and spaces, 
        //then finds that food in the menu and adds all of its information to an ArrayList of current orders
        do{
          tempFood = currentOrderText.substring(0, currentOrderText.indexOf("  "));
          tempFood = tempFood.trim();
          
          for(int i = 0; i < tempMenu.size(); i++){
            
            if(tempFood.equals(tempMenu.get(i).getName())){
              
              dineInOrderArray.add(new DineInOrder(tempMenu.get(i).getName(), tempMenu.get(i).getPrice(), tempMenu.get(i).getType(), tableOrders.getSelectedIndex() + 1, Integer.parseInt(customerNum.getText()), completedDineInOrder));
              
            }
            
          }
          
          currentOrderText = currentOrderText.substring(currentOrderText.indexOf("  ") + 1);
          
        }while(currentOrderText.length() > 1);
        
        //Refreshes the orders after adding to display the added food
        refreshOrders();
        
        //Closes the menu frame
        menuFrame.dispose();
        
      }
      
    }
    
  }
  
  /*
   * editOrderListener
   * Description: Adds an action listener to the edit button, which is identical to the addOrderListener above, except when adding to the ArrayList of current orders, 
   * it automatically adds the orders to the customer that the user was selecting when clicking the edit order button
   */
  
  class editOrderListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event){
      
      if(dineInOrders.getSelectedIndex() != -1){
        
        String currentOrderText = currentOrder.getText();
        
        currentOrderText = currentOrderText.substring(currentOrderText.indexOf("- ") + 2);
        
        String tempFood;
        
        do{
          tempFood = currentOrderText.substring(0, currentOrderText.indexOf("  "));
          tempFood = tempFood.trim();
          
          for(int i = 0; i < tempMenu.size(); i++){
            
            if(tempFood.equals(tempMenu.get(i).getName())){
              
              dineInOrderArray.add(new DineInOrder(tempMenu.get(i).getName(), tempMenu.get(i).getPrice(), tempMenu.get(i).getType(), tableOrders.getSelectedIndex() + 1, tempCustomer, completedDineInOrder));
              
            }
            
          }
          
          currentOrderText = currentOrderText.substring(currentOrderText.indexOf("  ") + 1);
          
        }while(currentOrderText.length() > 1);
        
        refreshOrders();
        menuFrame.dispose();
        
      }
      
    }
    
  }
  
  /*
   * removeOrderListener
   * Description: Adds an action listener to the remove order button, which either removes the item that the user selects from the ArrayList and JList, 
   * or removes all items from the ArrayList and Jlist of one customer, if the user is clicking on the customer number when pressing the remove button
   */
    
  class removeOrderListener implements ActionListener {
    
    public void actionPerformed(ActionEvent event){
      
      String tempName;
      
      //Only runs if the user selected something and if they did not try to remove the separators between orders (the dashes)
      if(dineInOrders.getSelectedIndex() != -1 && !listModel.elementAt(dineInOrders.getSelectedIndex()).equals(dashes)){

        //Gets the element that the user selected, and if they did not select a whole customer, gets rid of unnecessary spaces and finds which customer's food they selected
        tempName = (String)listModel.elementAt(dineInOrders.getSelectedIndex());
        
        if(tempName.indexOf("Customer") == -1){
          tempName = tempName.substring(0, tempName.indexOf("  "));
          
          updateTempCustomer();
          
          //Finds the corresponding food in the ArrayList of current orders to the one that the user selected based on name and customer number, refreshes the orders to remove the removed order, 
          //then sets the temporary customer to 0 so that no more food is removed
          for(int i = 0; i < dineInOrderArray.size(); i++){
            
            if(tempName.equals(dineInOrderArray.get(i).getName()) && tempCustomer == ((DineInOrder)dineInOrderArray.get(i)).getCustomerNumber()){
              dineInOrderArray.remove(i);
              refreshOrders();
              tempCustomer = 0;
            }
            
          }
          
        //If the user clicked on a whole customer
        }else{
          
          //Finds which customer they clicked
          updateTempCustomer();
          
          //Creates a temporary ArrayList
          ArrayList<Food> dineInOrderArrayRemove = new ArrayList<Food>();
          
          //Adds the items to be removed from the order Array to the temporary ArrayList
          for(int i = 0; i < dineInOrderArray.size(); i++){
            
            if(tempCustomer == ((DineInOrder)dineInOrderArray.get(i)).getCustomerNumber() && ((DineInOrder)dineInOrderArray.get(i)).getTableNumber() == (tableOrders.getSelectedIndex() + 1)){
              dineInOrderArrayRemove.add(dineInOrderArray.get(i));      
            }
            
          }
          
          //Removes the items from the order Array that correspond to the temporary Arraylist's items
          for(int i = 0; i < dineInOrderArrayRemove.size(); i++){
            
            dineInOrderArray.remove(dineInOrderArrayRemove.get(i));
            
          }
          
        }
        
        //Refreshes the orders to remove the removed items
        refreshOrders();
        
      }
      
    }
    
  }
  
  /*
   * dineInOrderMouseListener
   * Description: Adds a mouse listener to the layout panel, which detects which table was pressed based on mouse coordinates, and then sets the corresponding table in the JComboBox of table numbers to the same table
   */
  
  class dineInOrderMouseListener implements MouseListener {    
    public void mousePressed(MouseEvent e) {
      //Getting the cursor's coordinates when pressed
      mouseX = e.getX();
      mouseY = e.getY();
      //Finding which table has been clicked on if any
      for (int count = 0; count < (layoutPanel.getTables()).size(); count++){
        if (layoutPanel.getTables().get(count).isSelected(mouseX, mouseY) == true){       
          selectedTable = layoutPanel.getTables().get(count).getTableNum();   
          tableOrders.setSelectedIndex(selectedTable - 1);  
        }
        
      }
      
    }
    
    //Overrides all methods 
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    
  }
  
   
  /**
   * Menu
   * Description: Creates a new JFrame for the menu where the user can select items to add to the current orders for a table
   */
  
  class Menu extends JFrame{
    
    Menu(){
      
      //Sets the name to menu, and sets the size
      menuFrame = new JFrame("Menu");
      menuFrame.setSize(500,500);
      
      //Creates all the JPanels, JTabbedPane
      JPanel backgroundPanel = new JPanel(new GridLayout(2,0));
      JPanel menuFramePanel = new JPanel(new GridLayout());
      JPanel orderPanel = new JPanel(new GridLayout(1,2));
      
      JTabbedPane menuFrameTabs = new JTabbedPane();
      
      JPanel appsPanel = new JPanel(new GridLayout(0,2));
      JPanel appsMenuPanel = new JPanel(new GridLayout(1,1));
      JPanel appsSidePanel = new JPanel(new GridLayout(5,1));
      
      JPanel mainsPanel = new JPanel(new GridLayout(0,2));
      JPanel mainsMenuPanel = new JPanel(new GridLayout(1,1));
      JPanel mainsSidePanel = new JPanel(new GridLayout(5,1));
      
      JPanel drinksPanel = new JPanel(new GridLayout(0,2));
      JPanel drinksMenuPanel = new JPanel(new GridLayout(1,1));
      JPanel drinksSidePanel = new JPanel(new GridLayout(5,1));
      
      JPanel dessertsPanel = new JPanel(new GridLayout(0,2));
      JPanel dessertsMenuPanel = new JPanel(new GridLayout(1,1));
      JPanel dessertsSidePanel = new JPanel(new GridLayout(5,1));

      //Adds the ScrollPane of the JLists of all the menu items to their corresponding panels
      appsMenuPanel.add(menu.getAppScrollPane());      
      mainsMenuPanel.add(menu.getMainScrollPane());
      drinksMenuPanel.add(menu.getDrinkScrollPane());
      dessertsMenuPanel.add(menu.getDessertScrollPane());
      
      //Creates a non editable JTextArea that will contain all the current orders the user is entering
      currentOrder = new JTextArea("       Current Order\n------------------------------ ");
      currentOrder.setEditable(false);
      
      //Creates and adds a JScrollPane of the current order to the order panel
      JScrollPane currentOrderScrollPane = new JScrollPane(currentOrder);
      
      orderPanel.add(currentOrderScrollPane);
      
      //Creates buttons for all types of food, adds their corresponding action listener, and adds them to their corresponding panel
      addAppButton = new JButton("Add Appetizer");
      addAppButton.addActionListener(new clickMenuListener());
      appsSidePanel.add(addAppButton);
      
      addMainButton = new JButton("Add Main");
      addMainButton.addActionListener(new clickMenuListener());
      mainsSidePanel.add(addMainButton); 
      
      addDrinkButton = new JButton("Add Beverage");
      addDrinkButton.addActionListener(new clickMenuListener());
      drinksSidePanel.add(addDrinkButton);
      
      addDessertButton = new JButton("Add Dessert");
      addDessertButton.addActionListener(new clickMenuListener());
      dessertsSidePanel.add(addDessertButton);
      
      //Adds an Enter Customer Number JTextField to the panel if the user has clicked add order instead of edit order
      if(enterCustomer){
        customerNum = new JTextField("Enter Customer Number");      
        orderPanel.add(customerNum);
      }
      
      //Creates the complete order button
      orderButton = new JButton("Complete Order");
      
      //Adds all buttons and food specific panels to their corresponding panel
      orderPanel.add(orderButton);
      
      appsPanel.add(appsMenuPanel);
      appsPanel.add(appsSidePanel);
      
      mainsPanel.add(mainsMenuPanel);
      mainsPanel.add(mainsSidePanel);
      
      drinksPanel.add(drinksMenuPanel);
      drinksPanel.add(drinksSidePanel);
      
      dessertsPanel.add(dessertsMenuPanel);
      dessertsPanel.add(dessertsSidePanel);
      
      //Creates the tabs for all the specific foods
      menuFrameTabs.add("APPS", appsPanel);
      menuFrameTabs.add("MAINS", mainsPanel);
      menuFrameTabs.add("DRINKS", drinksPanel);
      menuFrameTabs.add("DESSERTS", dessertsPanel);
      
      //Adds the tabs to the menuFramePanel and adds the menuFramePanel and an orderPanel to the backgroundPanel
      menuFramePanel.add(menuFrameTabs);
      backgroundPanel.add(menuFramePanel);
      backgroundPanel.add(orderPanel);
      
      //Finally, adds the backgroundPanel to the frame and sets the frame to visible
      menuFrame.add(backgroundPanel);
      
      menuFrame.setVisible(true);      
      
    }
    
  }
  
  //End of inner classes
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * refreshOrders
   * This method refreshes all orders in the JList to display the corresponding orders from the JComboBox's table number
   * @param n/a
   * @return void
   */
    
  public void refreshOrders(){
    
    int panelWidth = dineLeftPanel.getWidth();
    
    //Clears all elements from the list model
    listModel.clear();
    
    //Creates a temporary ArrayList
    ArrayList<Food> tempOrders = new ArrayList<Food>();
    
    //Adds all the food that corresponds to the table number from the order Array to the temporary ArrayList
    for(int i = 0; i < dineInOrderArray.size(); i++){
      
      if(((DineInOrder)dineInOrderArray.get(i)).getTableNumber() == (tableOrders.getSelectedIndex() + 1)){
        
        tempOrders.add(new DineInOrder(dineInOrderArray.get(i).getName(), dineInOrderArray.get(i).getPrice(), dineInOrderArray.get(i).getType(), ((DineInOrder)dineInOrderArray.get(i)).getTableNumber(), ((DineInOrder)dineInOrderArray.get(i)).getCustomerNumber(), completedDineInOrder));
        
      }
      
    }
    
    numCustomers = 0;
    
    //Finds the maximum number of customers 
    for(int i = 0; i < tempOrders.size(); i++){
      
      if(numCustomers < ((DineInOrder)tempOrders.get(i)).getCustomerNumber()){
        
        numCustomers = ((DineInOrder)tempOrders.get(i)).getCustomerNumber();
        
      }
      
    }
    
    boolean addCustomerText;
    //Starts at 1 because all tables will have at least 1 customer, and if not then this shouldn't run
    for(int i = 1; i < numCustomers + 1; i++){
      
      addCustomerText = true;
      
      //Loops through the temporary ArrayList containing the specific table's orders
      for(int j = 0; j < tempOrders.size(); j++){
        
        //If the customer number of the element in the ArrayList is the first one for that customer, adds a string that contains what customer number they are to the list model
        if(((DineInOrder)tempOrders.get(j)).getCustomerNumber() == i){
          if(addCustomerText){
            
            listModel.addElement("Customer " + i);
            
            addCustomerText = false;
            
          }

          listModel.addElement(tempOrders.get(j).getName() + "  ");
          
        }
        
      }
      
      //Calculates how many dashes to add (to separate different customers) to the list model
      dashes = "";
      
      for(int j = 0; j < panelWidth/4.2; j++){
        
        dashes += "-";
        
      }

      //Adds the dashes to separate customers 
      if(listModel.size() != 0){
        
        if(!listModel.elementAt(listModel.size() - 1).equals(dashes)){
          
          listModel.addElement(dashes);
          
        }
        
      }
      
    }
    
    //Calculates the total price for the table's orders
    calculateTotal();
                  
    //Calculates how many dots are needed (the ones that separate the price from the name) based on the width of the panel
    dots = "  ";
    
    for(int k = 0; k < panelWidth/5; k++){
      
      dots += ".";
      
    }
    
    //Displays the total amount that the table's orders cost
    if(listModel.size() != 0){
      if(Double.toString(totalPrice).indexOf(".") == Double.toString(totalPrice).length() - 2){
        listModel.addElement("Total" + dots + " $" + totalPrice + "0");
      }else{
      listModel.addElement("Total" + dots + " $" + totalPrice);
      }
    }
    
  }
  
  /**
   * calculateTotal
   * Description: Calculates the total price of the selected table's orders
   * @param n/a
   * @return void
   */
  
  
  public void calculateTotal(){
    
    totalPrice = 0;
    
    if(listModel.size() != 0){
      
      String tempElement;
      
      //Goes through every element of the list model, and if the element is a food, gets rid of any unnecessary text, and adds it's price to a totalPrice variable
      for(int j = 0; j < listModel.size(); j++){
        
        tempElement = (String)listModel.elementAt(j);
        
        if(!tempElement.equals(dashes) && !tempElement.substring(0,tempElement.indexOf(" ")).equals("Customer") && tempElement.indexOf("$") == -1){
          
          tempElement = tempElement.substring(0, tempElement.indexOf("  "));
          
          for(int i = 0; i < tempMenu.size(); i++){
            
            if(tempMenu.get(i).getName().equals(tempElement)){
              
              totalPrice += tempMenu.get(i).getPrice();
              
            }
            
          }
          
          //Rounds the price to 2 decimal places
          totalPrice = Math.round(totalPrice*100)/100.00;
          
        }
        
      }
      
      
    }
    
  }
  
  /**
   * updateTempCustomer
   * Description: Finds which customer the user has selected from the JList of orders
   * @param n/a
   * @return void
   */
  
  public void updateTempCustomer() {
    
    tempCustomer = 0;
    int i = dineInOrders.getSelectedIndex();
    String customerText;
    
    //Loops from the element that the user selected until a customer number is found
    do{
      
      //Takes the element of the list model, and checks if it equals customer
      customerText = (String)listModel.getElementAt(i);
      
      if(customerText.indexOf(" ") != -1){
        
        if(customerText.substring(0, customerText.indexOf(" ")).equals("Customer")){
          
          //If the user selected a customer, parses the customer number into an integer, and sets it as the temporary customer
          tempCustomer = Integer.parseInt(customerText.substring(customerText.indexOf(" ") + 1, customerText.length()));
          
        }
        
      }
      
      i--;
      
    }while(tempCustomer == 0);
    
  }
  
}