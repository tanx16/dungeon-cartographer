public class Sword extends Item
{
   private int attk;
   public Sword()
   {
      attk = 5;
   }
   
   public int getAttk()
   {
      return attk;
   }
   
   public void use(Character ch)
   {
      //ch.weapon = this;
   }
}