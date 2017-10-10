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


public class Monster extends Actor
{
   public boolean alive= true;
   public Boolean visible = false;  
   public boolean moved = false;
   public Map map;
   private Clip clip;
   private AudioInputStream audioInputStream;
   private Character chara;
   public BufferedImage mon;
   public BufferedImage mondead;
   public int attk;
   public Monster(int x, int y, int health, Map m, Character chara){
      super(x,y,health);
      try{
      audioInputStream =
         AudioSystem.getAudioInputStream(
            this.getClass().getResource("died.wav"));
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
   
      this.map=m;
      this.chara = chara;
      attk = (int)Math.log(chara.getLevel())*10 + 5;
         mon= ImageIO.read(new File("monster.png"));
         mondead= ImageIO.read(new File("monsterdead.png"));
      }
      catch (Exception e){
         System.out.println("Read monster picture error");
      }
   
   }
    /*
   *Each tick:
   *makes sure alive
   *if it has been seen, it tries to move to player
      *then tries to attack
   *if not it moves randomly
   */
   @Override
   public void tick()
   {
      moved = false;
      //System.out.println(health+"");
      if(health <= 0&&alive){
      try{
              clip.start();
              clip.setFramePosition(0);
            }
            catch(Exception e){}

         chara.setMessage("You killed the monster");
         chara.addScore(20);
         alive=false;
         mon=mondead;}
     
      if(alive){
         if(this.map.tileAt(this.getX(), this.getY()).getVis())
         {
            visible= true;
            this.attackPlayer();
            if(chara.getHealth()<=0){
           javax.swing.JOptionPane.showMessageDialog(null, "Your score is: "+String.valueOf(chara.getScore()));
         System.exit(0);}

            if(moved == false)
               this.moveToPlayer();
         }
         else{
            moveRandomly();
            moved = true;
         }
        
      }
   }
   
   @Override
   public void render(Graphics g){
      if(visible) g.drawImage(mon,x,y,null);
   }
   
   public void attackPlayer()
   {
      //If next to player
      if(this.inRange(chara))
      {
         chara.attacked(attk);
         chara.setMessage("Monster hit you for "+(attk-chara.defense())+" damage.");
         moved = true;
      }
   }

   public boolean alive(){
      return alive;}
   
   public void moveToPlayer()
   {
      int cx=chara.getX();
      int cy=chara.getY();
     //Checks if Y is right
      if(!this.inRange(chara)){
         if(cx>this.getX()&&map.tileAt(this.getX()+1,this.getY()).isPass())
            this.y+=20;
         else if(cx<this.getX()&&map.tileAt(this.getX()-1,this.getY()).isPass())
            this.y-=20;
         else if(cy<this.getY()&&map.tileAt(this.getX(),this.getY()-1).isPass())         
            this.x-=20;
         else if(cy>this.getY()&&map.tileAt(this.getX(),this.getY()+1).isPass())       
            this.x+=20;
         else if(cx>this.getX()&&map.tileAt(this.getX()+1,this.getY()).isPass())
            this.y+=20;
         else if(map.tileAt(this.getX()-1,this.getY()).isPass())
            this.y-=20;
         else if(map.tileAt(this.getX(),this.getY()+1).isPass())       
            this.x+=20;
         else if(map.tileAt(this.getX(),this.getY()-1).isPass())         
            this.x-=20;
         else if(map.tileAt(this.getX()+1, this.getY()).isPass())
            this.y+=20;     
      }
   
       
   }
   
   public void moveRandomly()
   {
      int dir = (int)(1 + (Math.random()*(3)));
      if(map.tileAt(this.getX()+1,this.getY()).isPass() && dir == 4)
         this.y+=20;
      else if(map.tileAt(this.getX()-1,this.getY()).isPass() && dir == 3)
         this.y-=20;
      else if(map.tileAt(this.getX(),this.getY()-1).isPass() && dir == 2)
         this.x-=20;
      else if(map.tileAt(this.getX()+1,this.getY()).isPass() && dir == 1)
         this.x+=20;
   }
   
   public int getX(){
      return y/20;}
   
   public int getY(){
      return x/20;}
   
}      
