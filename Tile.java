import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile extends Entity
{
   public BufferedImage image;
   public Terrain tile;
   public boolean visible= false;
   public boolean pass = true;
   public enum Terrain
   {
      FLOOR, WALL, DOOR, CORRIDOR, UPSTAIRS, DOWNSTAIRS;
   }
    /*
   *Creates tile with picture
   */

   public Tile(int x, int y, String t){
      super(x, y);
      char z =t.charAt(0);
      try
      {
         switch(z)
         {
               case('|'): image= ImageIO.read(new File("dirtwall.png"));
               pass= false;
               break;
               case(' '): image = ImageIO.read(new File("dirtwall.png"));
               pass= false;
               break;
               case('o'): image = ImageIO.read(new File("dirtfloor.png"));
               break;
               case('0'): image = ImageIO.read(new File("stonefloor.png"));
               break;
               case('D'): image = ImageIO.read(new File("door.png"));
               break;
               case('^'): image = ImageIO.read(new File("upstairs.png"));
               break;
               case('v'): image = ImageIO.read(new File("downstairs.png"));
               break;
               
         }
      
      }
      catch (IOException e){System.out.println("Read tile error");}
   }
   
   
   public void tick()
   {
   
   }
   public boolean isPass(){
   return pass;}
    /*
   *returns true if not visible and then set visible
   */

   public boolean setVis(){
   if(!visible){
   visible=true;
   return true;}
   return false;
   }
   
   public boolean getVis(){
      return visible;
   }
   
   public void render(Graphics g)
   {
   if(visible)
   g.drawImage(image,this.x,this.y,null);   
   else{g.fillRect(this.x,this.y,20,20);}
   }
   
   public int getX(){
      return x;
   }
   
   public int getY(){
      return y;
   }
   public Terrain getTerr(){
      return tile;
   }

}