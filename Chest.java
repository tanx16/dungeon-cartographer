import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Chest extends Entity
{
   public BufferedImage chest;
   public BufferedImage openchest;
   public Map map;
   public Character ch;
   public boolean visible = false;
   public boolean used = false;
   public Chest(int x, int y, Map m, Character chara){
      super(x,y);
      map = m;
      ch = chara;
      try{
         chest= ImageIO.read(new File("chest.png"));
         openchest= ImageIO.read(new File("openchest.png"));
      }
      catch (IOException e){
         System.out.println("Read chest picture error");
      }
   
   }
   
   public void tick()
   {
      if(this.map.tileAt(this.getXP(), this.getYP()).getVis())
         visible= true;
      if(ch.getX() == this.getXP() && ch.getY() == this.getYP() && used == false)
      {
         try{
            AudioInputStream audioInputStream =
               AudioSystem.getAudioInputStream(
               this.getClass().getResource("chest.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
         }
         catch(Exception ex)
         {
            System.out.println(ex);
         }
      
         double chance = Math.random();
         if(chance <0.15){
            ch.setMessage("The chest was empty.");
         }
         else if(chance>=0.15 && chance<0.2){
            ch.increaseEyesight();
            ch.setMessage("You have received a potion of sight! +1 Sight");
         }
         else if(chance>=0.2 && chance<=0.4){
            ch.addArmor(1);
            ch.setMessage("You have received a new piece of Armor! +1 Defense");
         }
         
         else if(chance>=0.4 && chance<=0.6){
            ch.addSword(1);
            ch.setMessage("You have received a new sword. +1 Attack!");
         }
         else if(chance>0.6){
            ch.addInventory(new HealthPot());
            ch.setMessage("You have received a health potion.");
         }
         used = true;
      }
   }
   
   public void render(Graphics g)
   {
      if(visible && used==false)
         g.drawImage(chest,x,y,null);
      else if(visible && used == true)
         g.drawImage(openchest, x, y, null);
   }
   
   public int getXP(){
      return y/20;
   }
   
   public int getYP(){
      return x/20;
   }
}