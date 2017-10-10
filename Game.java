import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Game implements Runnable{

   private Display display;
   private int width, height;
   private String title;
   private boolean test = false;
   private Boolean run=false;
   private BufferStrategy bs;
   private Map map;
   private Thread thread;
   private KeyManager keymanager= new KeyManager();
   private int pressed=0;
   private Graphics g;
   private World world; 
   
   public Game(int width, int height, String title){
    
      try{
         AudioInputStream audioInputStream =
            AudioSystem.getAudioInputStream(
            this.getClass().getResource("music.wav"));
         Clip clip = AudioSystem.getClip();
         clip.open(audioInputStream);
         clip.loop(Clip.LOOP_CONTINUOUSLY);
      }
      catch(Exception ex)
      {
         System.out.println(ex);
      }
   
      this.width=width;
      this.height=height;
      this.title=title;
   
      world = new World(width);
   
      
   }
   /*
   *Sets up display and gamescreen
   */
   public void init(){
      display=new Display(width, height, title);
      display.getWindow().addKeyListener(keymanager);
      world.start(display.getCanvas().getGraphics());
      
   }

   @Override
   public void run(){
      init();
      pressed=2;
      ftick();
      pressed=4;
      ftick();
   
      while(run){
         tick();
      }
      stop();   
   }
   
   public int getPressed(){
      return pressed;
   }
    /*
   *force a tick, without user input
   */

   public void ftick(){
      if(pressed!=0){
         bs = display.getCanvas().getBufferStrategy();
         if(bs == null){
            display.getCanvas().createBufferStrategy(2);
            return;
         }
         g = bs.getDrawGraphics();
         g.clearRect(0, 0, width, height);
         world.tick(pressed, g);
      
         bs.show();
         g.dispose();
      }
      
   }   

   /*
   *buffered strategy keeps from screen flashing
   */

   public void tick(){
      int tempressed = keymanager.tick();
      if(tempressed!=pressed){
         pressed=tempressed;
         if(pressed!=0){
            bs = display.getCanvas().getBufferStrategy();
            if(bs == null){
               display.getCanvas().createBufferStrategy(2);
               return;
            }
            g = bs.getDrawGraphics();
            g.clearRect(0, 0, width, height);
            world.tick(pressed, g);
         
            bs.show();
            g.dispose();
            display.setPotions(world.getChar().healthPots());
            display.setAttack(world.getChar().attack());
            display.setArmor(world.getChar().defense());
            display.setHealth(world.getChar().getHealth());
            display.setFloor(world.getChar().getFloor());
            display.setScore(world.getChar().getScore());
         
            display.setMessage(world.getChar().getMessage());
         
         
         }
      }
   }   
   
      
   public synchronized void start(){
      if(run)
         return;
      run = true;
      thread = new Thread(this);
      thread.start();
   }

   public synchronized void stop(){
      if(!run)
         return;
      run = false;
      try {
         thread.join();
      } 
      catch (InterruptedException e) {
         e.printStackTrace();
      }   
      
   }
   
}