import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import java.awt.Canvas;
import java.awt.Dimension;

public class Display{

   private JFrame window;
   private int width, height;
   private String title;
   private Canvas canvas;
   private JLabel floor = new JLabel("FLOOR 1");

   private Box box= Box.createHorizontalBox();
   private JProgressBar healthBar = new JProgressBar(0, 100);
   private JLabel healthpots = new JLabel("Potions: 1");
   private Box stats = Box.createVerticalBox();
   private JLabel Attack=new JLabel("Damage: 1",JLabel.CENTER);
      private JLabel pScore=new JLabel("Score: 0",JLabel.CENTER);
   private JLabel message= new JLabel(" ",JLabel.CENTER);

   private JLabel Armor= new JLabel("Defense: 1",JLabel.CENTER);
   private int healthpotsa=10;
   private int attack=1;
   private int defense=1;
   private int flooar=1;
   private int pscorea=0;
   private int health=100;
   private String messages=" ";
   
   public Display(int width, int height, String title){
      this.width=width;
      this.height=height;
      this.title=title;
      setUpFrameandPanel();
   }
 /*
   *Creates a box layout that holds two horizontal boxes above one canvas, above one horizontal box
   */

   public void setUpFrameandPanel(){
      window= new JFrame(title);
   
      window.setSize(width, height);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setResizable(false);
      window.setLocationRelativeTo(null);
   
      window.setLayout(new BoxLayout(window.getContentPane(),BoxLayout.Y_AXIS));
      canvas = new Canvas();
      canvas.setPreferredSize(new Dimension(width, height));
      canvas.setMaximumSize(new Dimension(width, height));
      canvas.setMinimumSize(new Dimension(width, height));
      canvas.setFocusable(false);
      Box panel=Box.createHorizontalBox();
      panel.add(floor);
      Box score=Box.createHorizontalBox();
      score.add(pScore);
      Box msgs=Box.createHorizontalBox();
      msgs.add(message);

      setUpBox();
      window.getContentPane().add(panel);
      window.getContentPane().add(score);
      window.add(canvas);
      window.add(box);
            window.getContentPane().add(msgs);
      window.pack();
      window.setVisible(true);
   
   }
   public void setUpBox(){
      box.add(box.createHorizontalStrut(15));
   
      box.add(healthpots);
      box.add(box.createHorizontalStrut(15));
      box.add(healthBar);
      healthBar.setValue(100);
   
      box.add(box.createHorizontalStrut(15));
      stats.add(Attack);
      stats.add(Armor);
   
      box.add(stats);
      box.add(box.createHorizontalStrut(15));
   
   }      
         
    /*
   *Set the JLabel to the value it gets.
   */

   public void setPotions(int i){
      if(i!=healthpotsa){
         healthpots.setText("Potions: "+String.valueOf(i));
         healthpotsa=i;}
   }
   public void setAttack(int i){
      if(i!=attack){
         Attack.setText("Attack: "+String.valueOf(i));
         attack=i;}
   }

   public void setArmor(int i){
      if(i!=defense){
         Armor.setText("Defense: "+String.valueOf(i));
         defense=i;}
   }
   public void setFloor(int i){
      if(i!=flooar){
         floor.setText("Floor "+String.valueOf(i));
         flooar=i;}
   }
   public void setMessage(String s){
      if(messages!=s){
         message.setText(s);
         messages=s;}
   }

public void setScore(int i){
      if(i!=pscorea){
         pScore.setText("Score: "+String.valueOf(i));
         pscorea=i;}
   }


   public void setHealth(int i){
      if(i!=health){
         healthBar.setValue(i);
         health=i;
      }}
   public Canvas getCanvas(){
      return canvas;
   }
   
   public JFrame getWindow(){
      return window;
   }
}