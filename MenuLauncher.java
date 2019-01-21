/**
 * MenuLauncher.java
 * The MenuLauncher starts the menu program that links to other programs via buttons
 * @author Saeyon Sivakumaran
 * @version 1.0
 */

//Imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import java.awt.Desktop;
import java.io.File;
import java.awt.Color;

//Launcher for the Menu Launcher
class MenuLauncher{
  public static void main(String[] args) throws Exception{
    MenuProgram mainWindow = new MenuProgram();
  }
}

//Main JFrame class for the menu launcher
class MenuProgram extends JFrame{
  
  MenuEditor menuEditor;
  MenuPanel menuPanel;
  JButton editMenu;
  JButton instructionsButton;
  JButton editLayoutButton;
  JButton startProgramButton;
  
  //CONSTRUCTOR
  MenuProgram() throws Exception{
    super("MyRestaurant");
    setSize(java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width, java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
    setResizable(false);
    setLayout(new BorderLayout());;
    //setForeground(Color.WHITE);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    menuPanel = new MenuPanel(getWidth(), getHeight());
    add(menuPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }
  
  //Panel sub-class
  class MenuPanel extends JPanel{
    
    JComponent[][] menuComponents = new JComponent[7][3];
    
    //CONSTRUCTOR
    MenuPanel(int w, int h){
      setLayout(new GridLayout(7,3));   
      //Adding blank labels to the components
      for (int x = 0; x < 7; x++){
        for (int y = 0; y < 3; y++){
          menuComponents[x][y] = new JLabel();
          menuComponents[x][y].setOpaque(true);
          menuComponents[x][y].setBackground(Color.WHITE);
        }
      }
      //Creating menu buttons
      editMenu = new JButton("Create/Edit Menu");
      editMenu.addActionListener(new selectListener());
      instructionsButton = new JButton("Instructions");
      instructionsButton.addActionListener(new selectListener());
      
      editLayoutButton = new JButton("Edit Floor Layout");
      editLayoutButton.addActionListener(new editLayoutButtonListener());
      
      startProgramButton = new JButton("Start myRestaurant");
      startProgramButton.addActionListener(new startProgramButtonListener());
      
      //this generates an image file
      ImageIcon icon = new ImageIcon("logo.png"); 

      //Adding buttons to the components 
      menuComponents[0][1]  = new JLabel(icon);
      menuComponents[0][1].setOpaque(true);
      menuComponents[0][1].setBackground(Color.WHITE);
      menuComponents[2][1] = startProgramButton;
      menuComponents[3][1] = editLayoutButton;
      menuComponents[4][1] = editMenu;
      menuComponents[5][1] = instructionsButton;      
      //Adding components
      for (int a = 0; a < 7; a++){
        for (int b = 0; b < 3; b++){
          add(menuComponents[a][b]);          
        }
      } 
    }
    
  }  //End of MenuPanel
  
  //Button Listener: Edit Button
  class selectListener implements ActionListener{
    JButton source;
    public void actionPerformed(ActionEvent event){     
      source = (JButton)event.getSource();
      if (source == instructionsButton){   
        try{
        Desktop.getDesktop().open(new File("Instructions.txt"));
        }catch (Exception e){}       
      } else if (source == editMenu){
        try{        
          menuEditor = new MenuEditor(500, 500);     
        } catch (Exception e){}        
        revalidate();
        repaint();
      }
    }
  }  //End of selectListener
  
  //Listener to open the layout maker
  class editLayoutButtonListener implements ActionListener{    
    public void actionPerformed(ActionEvent event){      
      JFrame main = new JFrame();
      main.setSize(800, 600);
      main.setLayout(new GridLayout(1,1));
      main.add(new LayoutMaker());
      main.setVisible(true);      
    }    
  }
  
  //Listener to start the myRestaurant main program
  class startProgramButtonListener implements  ActionListener{    
    public void actionPerformed(ActionEvent event){      
      try{        
        JFrame myRestaurant = new MainWindow();      
      } catch (Exception e){}      
    }    
  }
  
}  //End of MenuProgram