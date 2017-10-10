import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class HealthPot extends Item
{
   protected int amountAdded;
      private Clip clip;
   private AudioInputStream audioInputStream;

   public HealthPot()
   {
      amountAdded = 30;
      //Or make a random amount
   }
   
   public void use(Character ch)
   {
   try{
      audioInputStream =
         AudioSystem.getAudioInputStream(
            this.getClass().getResource("swallo.wav"));
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
}catch(Exception e){}
      ch.changeHealth(amountAdded);
      System.out.println("Used health potion.");
   }
}