/*TakeOutOrderPanel.java
 * Author David Wang
 * Date 22 January 2017
 * This file manages the GUI and operations of the Take Out Orders tab
 */
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridLayout;

import java.util.ArrayList;

import java.awt.Choice;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;


class TakeOutOrderPanel extends JPanel{
  
  JPanel takeOutListPanel;
  
  ArrayList<Food> tempMenu;              //ArrayList of all foods
  
  DefaultListModel takeOutListModel;     //Model of all foods
  JList takeOutOrderJList;
  JScrollPane takeOutOrderScrollPane;
  
  Choice takeOutOrders;                  //Dropdown list of orders

  ArrayList<String> orderNumber;         //ArrayList of other information
  ArrayList<String> customerName;
  ArrayList<String> orderTime;
  
  Order takeOutOrderArray;               //ArrayList to connect to completed orders
  ArrayList<Food> allTakeOutOrder;
  
  CompletedOrderRecord completedTakeOutOrder;
  
  
  //GUI Variables
  JPanel takeOutButtonPanel;
  
  JButton takeOutViewOrder;
  JButton takeOutAddOrder;
  JButton takeOutEditOrder;
  JButton takeOutRemoveOrder;
  JButton takeOutCompleteOrder;
  
  JFrame takeOutAddFrame = new JFrame ("Add Take Out Order");
  JFrame takeOutEditFrame = new JFrame ("Edit Take Out Order");
  
  JTabbedPane foodTypes;
  JPanel foodAppetizer;
  JPanel foodMain;
  JPanel foodBeverage;
  JPanel foodDessert;
  
  JPanel takeOutAddInfoPanel;
  JPanel takeOutAddInfoLeftPanel;
  
  JLabel takeOutAddOrderNumber;
  JTextField takeOutAddCustomerName;
  JLabel takeOutAddTime;
  
  Calendar cal;
  SimpleDateFormat sdf;
  
  JButton addFoodAppetizer;
  JButton addFoodMain;
  JButton addFoodBeverage;
  JButton addFoodDessert;
  
  JButton removeFood;
  JButton saveOrder;
  int orderCounter = 1;
  
  DefaultListModel currentOrderModel;
  JList currentOrder;
  JScrollPane currentOrderScrollPane;
  
  OrderMenu menu;
  
  TakeOutOrderPanel(OrderMenu menu, Order takeOutOrderArray, CompletedOrderRecord completedTakeOutOrder){
    
    this.menu = menu;
    tempMenu = menu.getTempMenu();
    this.takeOutOrderArray = takeOutOrderArray;
    allTakeOutOrder = takeOutOrderArray.getOrderArray();
    this.completedTakeOutOrder = completedTakeOutOrder;
    
    orderNumber = new ArrayList<String>();
    customerName = new ArrayList<String>();
    orderTime = new ArrayList<String>();
    
    //PANEL GUI
    this.setLayout(new GridLayout(1,2));
    
    takeOutListPanel = new JPanel();
    takeOutListPanel.setLayout(new GridLayout(2,1));    
    
    takeOutOrders = new Choice();                         //Initialize drop down menu
    
    takeOutOrders.add("View All Orders");
    
    takeOutListPanel.add(takeOutOrders);    
    
    takeOutListModel = new DefaultListModel();
    takeOutOrderJList = new JList(takeOutListModel);
    takeOutOrderScrollPane = new JScrollPane(takeOutOrderJList);
    
    takeOutListPanel.add(takeOutOrderScrollPane);
    
    takeOutButtonPanel = new JPanel();
    takeOutButtonPanel.setLayout(new GridLayout(5,1));
    
    takeOutViewOrder = new JButton("View Order");
    takeOutViewOrder.addActionListener(new TakeOutViewButtonListener());
    
    takeOutAddOrder = new JButton("Add Order");
    takeOutAddOrder.addActionListener(new TakeOutAddButtonListener());
    
    takeOutEditOrder = new JButton("Edit Order");
    takeOutEditOrder.addActionListener(new TakeOutEditButtonListener());
    
    takeOutRemoveOrder = new JButton("Remove Order");
    takeOutRemoveOrder.addActionListener(new TakeOutRemoveButtonListener());
    
    takeOutCompleteOrder = new JButton("Complete Order");
    takeOutCompleteOrder.addActionListener(new TakeOutCompleteButtonListener());
    
    //Add take out order frame/ GUI
    takeOutAddFrame.setSize(500,500);
    takeOutAddFrame.setLayout(new GridLayout(3,1));
    
    JPanel takeOutAddPanel = new JPanel();
    takeOutAddPanel.setLayout(new GridLayout(1,2));
    
    JPanel takeOutAddButtonPanel = new JPanel();
    takeOutAddButtonPanel.setLayout(new GridLayout(2,1));
    
    foodTypes = new JTabbedPane();
    
    foodAppetizer = new JPanel();
    foodAppetizer.setLayout(new GridLayout(1,2));
    
    foodMain = new JPanel();
    foodMain.setLayout(new GridLayout(1,2));
    
    foodBeverage = new JPanel();
    foodBeverage.setLayout(new GridLayout(1,2));
    
    foodDessert = new JPanel();
    foodDessert.setLayout(new GridLayout(1,2));
    
    foodAppetizer.add(menu.getAppScrollPane());
    foodMain.add(menu.getMainScrollPane());
    foodBeverage.add(menu.getDrinkScrollPane());
    foodDessert.add(menu.getDessertScrollPane());
    
    foodTypes.addTab("APPS", foodAppetizer);
    foodTypes.addTab("MAIN", foodMain);
    foodTypes.addTab("BEVERAGES", foodBeverage);
    foodTypes.addTab("DESSERTS", foodDessert);
    
    currentOrderModel = new DefaultListModel();
    currentOrder = new JList(currentOrderModel);
    currentOrderScrollPane = new JScrollPane(currentOrder);
    
    addFoodAppetizer = new JButton("Add Appetizer");
    addFoodAppetizer.addActionListener(new AddFoodButtonListener());
    
    addFoodMain = new JButton("Add Main");
    addFoodMain.addActionListener(new AddFoodButtonListener());
    
    addFoodBeverage = new JButton("Add Beverage");
    addFoodBeverage.addActionListener(new AddFoodButtonListener());
    
    addFoodDessert = new JButton("Add Dessert");
    addFoodDessert.addActionListener(new AddFoodButtonListener());
    
    removeFood = new JButton("Remove Selected Food");
    removeFood.addActionListener(new RemoveFoodButtonListener());
    
    saveOrder = new JButton("Save Order");
    saveOrder.addActionListener(new SaveFoodButtonListener());
    
    foodAppetizer.add(addFoodAppetizer);
    foodMain.add(addFoodMain);
    foodBeverage.add(addFoodBeverage);
    foodDessert.add(addFoodDessert);
    
    takeOutAddFrame.add(foodTypes);
    takeOutAddButtonPanel.add(removeFood);
    takeOutAddButtonPanel.add(saveOrder);
    
    takeOutAddPanel.add(currentOrderScrollPane);
    
    takeOutAddInfoPanel = new JPanel();
    takeOutAddInfoPanel.setLayout(new GridLayout(1,2));
    
    takeOutAddOrderNumber = new JLabel("Order : " + Integer.toString(orderCounter));       //Real-time order tracking
    takeOutAddCustomerName = new JTextField("Enter Customer Name");
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    takeOutAddTime = new JLabel("Sample Time");
    
    takeOutAddInfoLeftPanel = new JPanel();
    takeOutAddInfoLeftPanel.setLayout(new GridLayout(3,1));
    
    takeOutAddInfoLeftPanel.add(takeOutAddOrderNumber);
    takeOutAddInfoLeftPanel.add(takeOutAddCustomerName);
    takeOutAddInfoLeftPanel.add(takeOutAddTime);
    
    takeOutAddInfoPanel.add(takeOutAddInfoLeftPanel);
    takeOutAddInfoPanel.add(takeOutAddButtonPanel);
    
    takeOutAddFrame.add(takeOutAddPanel);
    takeOutAddFrame.add(takeOutAddInfoPanel);
    

    
    //Adding everything
    
    takeOutButtonPanel.add(takeOutViewOrder);
    takeOutButtonPanel.add(takeOutAddOrder);
    takeOutButtonPanel.add(takeOutEditOrder);
    takeOutButtonPanel.add(takeOutRemoveOrder);
    takeOutButtonPanel.add(takeOutCompleteOrder);
    
    this.add(takeOutListPanel);
    this.add(takeOutButtonPanel);
    
    
  }
  
  
  //ACTION LISTENERS//
  
  //VIEW AN ORDER
  class TakeOutViewButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      if(takeOutOrders.getSelectedIndex() == 0){                                            //If view all orders is selected, prints all the orders
        //print jlist
        takeOutListModel.removeAllElements();
        
        for(int k = 0; k < orderNumber.size(); k ++){                                       //Goes through all order numbers
          
          takeOutListModel.addElement(orderNumber.get(k));                                  //Each order number, customer name, order time is added 
          takeOutListModel.addElement(customerName.get(k));
          takeOutListModel.addElement(orderTime.get(k));
          
          for(int l = 0; l < allTakeOutOrder.size(); l++){                                  //Then goes through every food order 
            
            String tempPrintOrderNumber;                     
            
            tempPrintOrderNumber = ((TakeOutOrder)allTakeOutOrder.get(l)).getOrderNumber(); 
            
            if(tempPrintOrderNumber.equals(orderNumber.get(k))){                            //Checks to see if order number matches with order number on food, adds to list
              
              takeOutListModel.addElement(allTakeOutOrder.get(l).getName());
            }
          }
          
          takeOutListModel.addElement("=====================================================================");
          
        }
        
      }else{                                                                                //Else, deletes model, searches array for matching order number
        takeOutListModel.removeAllElements();
        
        double price = 0;
        int temp = 0;
        
        temp = takeOutOrders.getSelectedIndex() - 1;                                        //Has to consider -1 as View All Orders takes up first index
        
        String tempOrderNumber;
        
        tempOrderNumber = orderNumber.get(temp);
        
        for(int i = 0; i < orderNumber.size(); i++){                                        //Goes through order numbers, prints only the matching order number and other info
          
          if(orderNumber.get(i).equals(tempOrderNumber)){
            
            takeOutListModel.addElement(orderNumber.get(i));
            takeOutListModel.addElement(customerName.get(i));
            takeOutListModel.addElement(orderTime.get(i));
            
            for(int j = 0; j < allTakeOutOrder.size(); j++){
              
              String tempOrderNumber2;
              
              tempOrderNumber2 = ((TakeOutOrder)allTakeOutOrder.get(j)).getOrderNumber();
              
              if(tempOrderNumber2.equals(orderNumber.get(i))){                             //Goes through all foods to see that order number is matching, adds to model  
                
                takeOutListModel.addElement(allTakeOutOrder.get(j).getName());
                
                price = price + allTakeOutOrder.get(j).getPrice();                         //Price counter
                
              }
            }
          }  
          
        }
        
        takeOutListModel.addElement("Subtotal : " + price * 1.00);                                            //View price here
        takeOutListModel.addElement("Tax : " + (Math.round(price * 0.13 * 100))/100.00);
        takeOutListModel.addElement("Total : " + (Math.round(price * 1.13 * 100))/100.00);
        
      }
    }
  }
  
  //ADD AN ORDER
  class TakeOutAddButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      takeOutAddFrame.setTitle("Add Order");                                       //Sets title as edit and add use same frame, keep correct title
      
      takeOutAddFrame.setVisible(true);                                            //Set up live time tracking
      cal = Calendar.getInstance();
      sdf = new SimpleDateFormat("HH:mm:ss");
      
      takeOutAddTime.setText(sdf.format(cal.getTime()));
      
    }
  }
  
  class TakeOutEditButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      takeOutAddFrame.setTitle("Edit Order");
      
      int temp = 0;
      ArrayList<Food> tempRemove = new ArrayList<Food>();
      
      if((takeOutOrders.getSelectedIndex() - 1) != -1){                           //Ensures that the order chosen is not View All Orders
        temp = takeOutOrders.getSelectedIndex() - 1;
        
        takeOutAddOrderNumber.setText(orderNumber.get(temp));                     //Sets all information to previously entered information
        takeOutAddCustomerName.setText(customerName.get(temp));
        takeOutAddTime.setText(orderTime.get(temp));
        
        for(int i = 0; i < allTakeOutOrder.size(); i++){
          
          if(((TakeOutOrder)allTakeOutOrder.get(i)).getOrderNumber().equals(orderNumber.get(temp))){
            
            currentOrderModel.addElement(allTakeOutOrder.get(i).getName());       //Loads all entered food into the model
            
          }
          
        }
        
        
        for(int j = 0; j < allTakeOutOrder.size(); j ++){
          
          if(((TakeOutOrder)allTakeOutOrder.get(j)).getOrderNumber().equals(orderNumber.get(temp))){
            
            tempRemove.add(allTakeOutOrder.get(j));                                            //Removes food as will be rewritten afterwards
            
          }
          
        }
        
        for(int count = 0; count < tempRemove.size(); count++){
          
          allTakeOutOrder.remove(tempRemove.get(count));
          
        }
        
        takeOutOrders.remove(temp + 1);                                           //Removes all previous information, however is saved 
        orderNumber.remove(temp);
        customerName.remove(temp);
        orderTime.remove(temp);
        
        takeOutAddFrame.setVisible(true);
        
        
      }
      
    }
    
  }
  
  //REMOVE AN ORDER
  class TakeOutRemoveButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      int temp = 0;
      int foodCounter = 0;
      
      if((takeOutOrders.getSelectedIndex() - 1) != -1){                                              //Check that index is applicable
        
        temp = takeOutOrders.getSelectedIndex() - 1;
        
        String tempOrderNumber;
        
        tempOrderNumber = orderNumber.get(temp);
        
        for(int i = 0; i < allTakeOutOrder.size(); i++){                                                             
          
          if(((TakeOutOrder)allTakeOutOrder.get(i)).getOrderNumber().equals(tempOrderNumber)){       //If order number is matching, food is removed
            
            allTakeOutOrder.remove(i);
            foodCounter = foodCounter + 1;
          }
        }
        
        allTakeOutOrder.remove(tempOrderNumber);                                                     //Removes other information, reprints JList 
        orderNumber.remove(temp);
        customerName.remove(temp);
        orderTime.remove(temp);
        takeOutOrders.remove(temp + 1);
        
        
        //print jlist
        takeOutListModel.removeAllElements();
        
        for(int k = 0; k < orderNumber.size(); k ++){
          
          takeOutListModel.addElement(orderNumber.get(k));
          takeOutListModel.addElement(customerName.get(k));
          takeOutListModel.addElement(orderTime.get(k));
          
          for(int l = 0; l < allTakeOutOrder.size(); l++){
            
            String tempPrintOrderNumber;
            
            tempPrintOrderNumber = ((TakeOutOrder)allTakeOutOrder.get(l)).getOrderNumber();
            
            if(tempPrintOrderNumber.equals(orderNumber.get(k))){
              
              takeOutListModel.addElement(allTakeOutOrder.get(l).getName());
            }
          }
          
          takeOutListModel.addElement("=====================================================================");
          
        }
        
      }
      
    }
  }
  
  //COMPLETE AN ORDER
  class TakeOutCompleteButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      int temp = 0;

      if((takeOutOrders.getSelectedIndex() - 1) != -1){                                    //Checks to see if applicable index
        temp = takeOutOrders.getSelectedIndex() - 1;
        
        String tempOrderNumber;
        
        tempOrderNumber = orderNumber.get(temp);
        
        ArrayList<Food> tempRemove = new ArrayList<Food>();
     
        for(int i = 0; i < allTakeOutOrder.size(); i++){                                   //If order number matches, it is send to completed list

          if(((TakeOutOrder)allTakeOutOrder.get(i)).getOrderNumber().equals(tempOrderNumber)){

            completedTakeOutOrder.getCompletedList().add(allTakeOutOrder.get(i));

            tempRemove.add(allTakeOutOrder.get(i));
          }
    
        }
        
        for(int count = 0; count < tempRemove.size(); count++){
          
          allTakeOutOrder.remove(tempRemove.get(count));
          
        }
        
        completedTakeOutOrder.saveCompletedOrders(); 
        
        allTakeOutOrder.remove(tempOrderNumber);                                            //Remove info and reprint
        orderNumber.remove(temp);
        customerName.remove(temp);
        orderTime.remove(temp);
        takeOutOrders.remove(temp + 1);
        
        
        //print jlist
        takeOutListModel.removeAllElements();
        
        for(int k = 0; k < orderNumber.size(); k ++){
          
          takeOutListModel.addElement(orderNumber.get(k));
          takeOutListModel.addElement(customerName.get(k));
          takeOutListModel.addElement(orderTime.get(k));
          
          for(int l = 0; l < allTakeOutOrder.size(); l++){
            
            String tempPrintOrderNumber;
            
            tempPrintOrderNumber = ((TakeOutOrder)allTakeOutOrder.get(l)).getOrderNumber();
            
            if(tempPrintOrderNumber.equals(orderNumber.get(k))){
              
              takeOutListModel.addElement(allTakeOutOrder.get(l).getName());
            }
          }
          
          takeOutListModel.addElement("=====================================================================");
          
        }
        
      }
      
    }
    
  }
  
  
  
  //ADD ORDER EVENT LISTENERS
  
  //ADD FOOD BUTTON
  class AddFoodButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      String temp;
      
      if(event.getSource() == addFoodAppetizer){                               //Checks which button, and which type of food is being entered, adds it
        
        temp = menu.getAppsList().getSelectedValue().toString();
        
        currentOrderModel.addElement(temp);
        
      }else if(event.getSource() == addFoodMain){
        
        temp = menu.getMainsList().getSelectedValue().toString();
        
        currentOrderModel.addElement(temp);
        
      }else if(event.getSource() == addFoodBeverage){
        
        temp = menu.getDrinksList().getSelectedValue().toString();
        
        currentOrderModel.addElement(temp);
        
      }else if(event.getSource() == addFoodDessert){
        
        temp = menu.getDessertsList().getSelectedValue().toString();
        
        currentOrderModel.addElement(temp);
        
      }
      
    }
  }
  
  //REMOVE A FOOD
  class RemoveFoodButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
     if((currentOrder.getSelectedIndex() - 1) != -1  || currentOrder.getSelectedIndex() != -1){    //determine that something is being clicked instead of empty field
        int temp = 0;
        temp = currentOrder.getSelectedIndex();
        currentOrderModel.removeElementAt(temp);
      }else{
      }
    }
  }
  
  //SAVE A FOOD
  class SaveFoodButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      
      double orderPriceTemp = 0;
      
      for(int i = 0; i < currentOrderModel.getSize(); i++){                   //goes through all food entries
        
        String temp;
        
        temp = currentOrderModel.getElementAt(i).toString();
        
        for(int j = 0; j < tempMenu.size(); j ++){                            //goes through all food
          
          if (temp.equals(tempMenu.get(j).getName())){                        //if it is equal, temporary order is initialized and created
                        
            TakeOutOrder tempOrder;
            
            tempOrder = new TakeOutOrder(tempMenu.get(j).getName(), tempMenu.get(j).getPrice(), tempMenu.get(j).getType(), takeOutAddOrderNumber.getText(), takeOutAddCustomerName.getText(), takeOutAddTime.getText(), completedTakeOutOrder);
            
            allTakeOutOrder.add(tempOrder);
            orderPriceTemp = orderPriceTemp + tempOrder.getPrice();
            
          }
        }
      }
      
      orderNumber.add(takeOutAddOrderNumber.getText());                        //Save order information
      customerName.add(takeOutAddCustomerName.getText());
      orderTime.add(takeOutAddTime.getText());
      
      takeOutOrders.add(takeOutAddOrderNumber.getText());
      
      //print jlist                                                            //Reprint menu
      takeOutListModel.removeAllElements();
      
      for(int k = 0; k < orderNumber.size(); k ++){
        
        takeOutListModel.addElement(orderNumber.get(k));
        takeOutListModel.addElement(customerName.get(k));
        takeOutListModel.addElement(orderTime.get(k));
        
        for(int l = 0; l < allTakeOutOrder.size(); l++){
          
          String tempOrderNumber;
          
          tempOrderNumber = ((TakeOutOrder)allTakeOutOrder.get(l)).getOrderNumber();
          
          if(tempOrderNumber.equals(orderNumber.get(k))){
            
            takeOutListModel.addElement(allTakeOutOrder.get(l).getName());
          }
        }
        
        takeOutListModel.addElement("=====================================================================");
        
      }
      
      orderCounter = orderCounter + 1;
      takeOutAddFrame.setVisible(false);
      currentOrderModel.removeAllElements();
      takeOutAddOrderNumber.setText("Order : " + Integer.toString(orderCounter));
      takeOutAddCustomerName.setText("Enter Customer Name");
      
      
    }
  }
  
  
}


