import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Character extends Actor{

   private String message;
   private BufferedImage image;
   private BufferedImage imgd;
   private BufferedImage imgl;
   private BufferedImage imgr;
   private BufferedImage imgu;
   private int score = 0;
   private int eyesight=2;
   private List<Item> inventory = new ArrayList<Item>(30);
   private List<Monster> enemies;
   private Sword weapon;
   private Clip clip;
   private AudioInputStream audioInputStream;
   private int attack = 1;
   private int defense=1;
   private int healthpots=1;
   private Map map;   
   int m=3;
   private int level = 1;
   private int oldHealth;
   private boolean newWorld=false;
   private boolean moved = false;
   
   public Character(int x, int y, int health, Map m, List<Monster> mons){
      super(x,y,health);
      try{
         audioInputStream =
            AudioSystem.getAudioInputStream(
            this.getClass().getResource("attack.wav"));
         clip = AudioSystem.getClip();
         clip.open(audioInputStream);
      
      }
      catch(Exception ex)
      {
         System.out.println(ex);
      }
   
      enemies = mons;
      for(int i=0; i< healthpots; i++)
         inventory.add(new HealthPot());
         
      inventory.add(new Sword());
      this.map=m;
      try{
         imgu= ImageIO.read(new File("char.png"));
         imgl= ImageIO.read(new File("charl.png"));
         imgr= ImageIO.read(new File("charr.png"));
         imgd= ImageIO.read(new File("chard.png"));
      
      }
      catch (IOException e){System.out.println("Read character image error");}
   
   }
   @Override
   public void tick(){}
   /*on each tick
   *moves based on input
   *if there is an enemy in the space it is attempting to move to it will attack it
   *and it will make all new tiles in a 3*3 square visible.
   */
   public void tick(int moveCode){
      if(m<=0){
         m=3;
         this.message=" ";}
      else{m--;}
      moved = false;
      try{
      
         switch(moveCode){
            case 1:
               moveRight();
               break;
            case 2:
               moveLeft();
               break;
            case 3:
               moveDown();
               break;
            case 4:
               moveUp();
               break;
            case 5:
               {
                  for(Item a : inventory)
                  {
                     if(a instanceof HealthPot)
                     {
                        a.use(this);
                        inventory.remove(a);
                        healthpots--;
                     
                        message = "You have " + healthpots + " potions left.";
                        if(health>maxHealth)
                           health = maxHealth;
                        break;
                     }
                  }
               }
         }}
      catch (Exception e){System.out.println(e);}
      //Makes all squares within eyesight visible
      for(int i = 1;i<=this.eyesight;i++){
         try{
         if(map.tileAt(this.getX()+i,this.getY()+i).setVis()) score+=1*level;
         if(map.tileAt(this.getX()+i,this.getY()).setVis()) score+=1*level;
         if(map.tileAt(this.getX(),this.getY()+i).setVis()) score+=1*level;
         if(map.tileAt(this.getX()-i,this.getY()-i).setVis()) score+=1*level;
         if(map.tileAt(this.getX()-i,this.getY()).setVis()) score+=1*level;
         if(map.tileAt(this.getX(),this.getY()-i).setVis()) score+=1*level;
         if(map.tileAt(this.getX()+i,this.getY()-i).setVis()) score+=1*level;
         if(map.tileAt(this.getX()-i,this.getY()+i).setVis()) score+=1*level;
         }catch(Exception x){}
      }
      if(health!= oldHealth)
      {
         oldHealth = health;
      }
      if(health<=0){
         JOptionPane.showMessageDialog(null, "Your score is: "+String.valueOf(score));
         System.exit(0);
      }
   }
   
   public boolean attackMonster(int x, int y)
   {
      //Damage = 5(base) + weapon damage.
      for(Monster m : enemies)
      {
         if(m.getX() == x && m.getY() == y && m.alive==true){
            m.changeHealth(-(5+attack));
            try{
               clip.start();
               clip.setFramePosition(0);
            }
            catch(Exception e){}
            message="You dealt " + (5+attack)+ " damage to the monster.";
                        
            moved = true;
                   
               
         
            return true;
         }           }
      return false;
   }
  
   public void moveUp()
   {
      if (this.attackMonster(this.getX()-1, this.getY())==false && !moved)
         if (map.tileAt(this.getX()-1, this.getY()).isPass())
            y-=20;
      image=imgu;
   }
   
   public void moveDown()
   {
      if (this.attackMonster(this.getX()+1,this.getY())==false && !moved)
         if(map.tileAt(this.getX()+1,this.getY()).isPass())
            y+=20;
      image=imgd;
   }
   
   public void moveLeft()
   {
      if (this.attackMonster(this.getX(),this.getY()-1)==false && !moved)
         if(map.tileAt(this.getX(),this.getY()-1).isPass())
            x-=20;
      image=imgl;
   }
   
   public void moveRight()
   {
      if (this.attackMonster(this.getX(),this.getY()+1)==false && !moved)
         if(map.tileAt(this.getX(),this.getY()+1).isPass())
            x+=20;
      image=imgr;
   }
   
   public void addInventory(Item item)
   {
      this.inventory.add(item);
      if(item instanceof HealthPot)
         healthpots++;
   }
   public void addSword(int increase){
      attack+=increase;}
   @Override
   public void render(Graphics g){
   
   
      g.drawImage(image,x,y,null);
      if(this.getX()==map.endX()&&this.getY()==map.endY()){
         int reply = JOptionPane.showConfirmDialog(null, "Enter new level?", "Y/N", JOptionPane.YES_NO_OPTION);
         if (reply == JOptionPane.YES_OPTION){
            level++;
            newWorld=true;
            System.gc();
            System.out.println("Entering level " + level);
         }
         else{return;}
      }
   }
   
   public int healthPots(){
      return healthpots;
   }
   
   public int attack(){
      return attack;
   }
   
   public int getFloor(){
      return level;}
   public String getMessage(){
      return message;
   }
   public int getScore(){
      return score;}

   public void setMap(Map m){
      this.map = m;}
   
   public void setMessage(String s){
      this.message=s;}
   
   public void setEnemies(List<Monster> a){
      this.enemies=a;
   } 

   public void addScore(int i){
      score+=i;
   }
   public int getEyesight(){
   return eyesight;}
   public void increaseEyesight(){
   eyesight++;
   }
   public void noNewWorld(){
      newWorld=false;}
   public int getLevel(){
      return level;
   }
   
   public boolean newWorld(){
      return newWorld;} 
}      
