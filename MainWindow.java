/*MainWindow.java
 * Authors Chris Shah, Saeyon Sivakumaran, Daniel Tang, David Wang
 * Date 22 January 2017
 * This file contains most of the GUI featured in the shared JFrames, JPanels, and JTabbedPanes. Also includes the Completed Orders JPanel.
 */

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
import javax.swing.JLabel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListDataEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import java.util.ArrayList;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ScrollPaneConstants;

class MainWindow extends JFrame{
  
  //Integers
  
  int dineInListSize;
  int takeOutListSize;
  
  //TabbedPane variables
  JTabbedPane mainTabs;
  JTabbedPane ordersTabs;
  
  //Panels in the TabbedPane
  JPanel ordersPanel;
  JPanel floorPanel;
  WaiterTab waiterPanel;
  
  //Order - specific panels
  JPanel dineInOrderPanel;
  JPanel takeOutPanel;
  JPanel completedPanel;
  
  //Completed Order Panels
  JPanel completedPanelLabels;
  JPanel completedPanelLists;
  JPanel completedPanelTotals;
  
  JLabel dineInTotal;
  JLabel takeOutTotal;
  
  JList takeOutCompletedList;
  JScrollPane takeOutCompletedScrollPane;
  
  JList dineInCompletedList;
  JScrollPane dineInCompletedScrollPane;
  
  //Order objects
  Order dineInOrderArray = new Order();
  Order takeOutOrderArray = new Order();
  
  //CompletedOrderRecord Objects
  CompletedOrderRecord completedDineInOrder = new CompletedOrderRecord();
  CompletedOrderRecord completedTakeOutOrder = new CompletedOrderRecord();
  
  OrderMenu dineInMenu = new OrderMenu();
  OrderMenu takeOutMenu = new OrderMenu();
  
  ArrayList<Tip> tips = new ArrayList<Tip>();
  
  MainWindow() throws Exception{
    super("myRestaurant");
    
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    int height = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    
    this.setSize(width, height);
    this.setResizable(true);  
    
    mainTabs = new JTabbedPane();
    ordersPanel = new JPanel(new GridLayout(1,2));
    floorPanel = new LayoutPanel(this.getWidth(), this.getHeight(), "FloorPanel");
    waiterPanel = new WaiterTab(this.getWidth() / 2, this.getHeight(), tips);
    
    ordersTabs = new JTabbedPane();
    
    //Construct order-specific panels
    dineInOrderPanel = new DineInOrderPanel(this.getWidth()/2, this.getHeight(), dineInMenu, dineInOrderArray, completedDineInOrder, tips);
    takeOutPanel = new TakeOutOrderPanel(takeOutMenu, takeOutOrderArray, completedTakeOutOrder);
    
    //Completed Order Panel GUI
    completedPanel = new JPanel();
    completedPanel.setLayout(new BorderLayout());
    
    completedPanelLabels = new JPanel(new GridLayout(1,2));
    completedPanelLists = new JPanel(new GridLayout(1,2));
    completedPanelTotals = new JPanel(new GridLayout(1,2));
    
    dineInTotal = new JLabel("Total");
    takeOutTotal = new JLabel("Total");    
    
    
    completedDineInOrder.getCompletedModel().addListDataListener(new dineInCompletedModelListener());
    dineInCompletedList = new JList(completedDineInOrder.getCompletedModel());
    dineInCompletedScrollPane = new JScrollPane(dineInCompletedList);
    
    
    completedTakeOutOrder.getCompletedModel().addListDataListener(new takeOutCompletedModelListener());
    takeOutCompletedList = new JList(completedTakeOutOrder.getCompletedModel());
    takeOutCompletedScrollPane = new JScrollPane(takeOutCompletedList); 
    
    JLabel dineInOrderLabel = new JLabel("DINE IN ORDERS");
    JLabel takeOutOrderLabel = new JLabel("TAKE OUT ORDERS");
    
    completedPanelLabels.add(dineInOrderLabel);
    completedPanelLabels.add(takeOutOrderLabel);
    
    completedPanelLists.add(dineInCompletedScrollPane);
    completedPanelLists.add(takeOutCompletedScrollPane);
    
    completedPanelTotals.add(dineInTotal);
    completedPanelTotals.add(takeOutTotal);
    
    completedPanel.add(completedPanelLabels, BorderLayout.NORTH);
    completedPanel.add(completedPanelLists);
    completedPanel.add(completedPanelTotals, BorderLayout.SOUTH);
    
    //Adding JPanels
    ordersTabs.add(dineInOrderPanel, "DINE IN");
    ordersTabs.add(takeOutPanel, "TAKE OUT");
    ordersTabs.add(completedPanel, "COMPLETED ORDERS");
    
    ordersPanel.add(ordersTabs);
    
    mainTabs.add(ordersPanel, "ORDERS");
    mainTabs.add(floorPanel, "FLOOR");
    mainTabs.add(waiterPanel, "WAITER INFO");
    
    this.add(mainTabs);
    
    this.setVisible(true);
    
  }
  
  class dineInCompletedModelListener implements ListDataListener {
    
    public void contentsChanged(ListDataEvent e){}
    
    public void intervalAdded(ListDataEvent e){
      
      double temp = completedDineInOrder.getTotal();
      
      if(Double.toString(temp).indexOf(".") == Double.toString(temp).length() - 2){
        dineInTotal.setText("Total: $" + temp + "0");
      }else{
        dineInTotal.setText("Total: $" + temp); 
      }
      
    }
    
    public void intervalRemoved(ListDataEvent e){}
    
  }
  
  class takeOutCompletedModelListener implements ListDataListener {
    
    public void contentsChanged(ListDataEvent e){
      
    }
    
    public void intervalAdded(ListDataEvent e){
      
      double temp = completedTakeOutOrder.getTotal();
      
      if(Double.toString(temp).indexOf(".") == Double.toString(temp).length() - 2){
        takeOutTotal.setText("Total: $" + temp + "0");
      }else{
        takeOutTotal.setText("Total: $" + temp); 
      }
      
    }
    
    public void intervalRemoved(ListDataEvent e){}
    
  }
  
}
