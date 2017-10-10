import java.awt.Graphics;
public abstract class Entity{

   protected int x, y;

   public Entity(int x, int y){
      this.x = x;
      this.y = y;
   }
   
   public abstract void tick();
   
   public abstract void render(Graphics g);
   
   public void move(int x, int y){
   this.x=x;
   this.y=y;
   }
   public int getXP(){
   return x;
   }
   
   public int getYP(){
   return y;
   }
   
}