import java.awt.event.*;

public class KeyManager implements KeyListener{
   
   public Boolean keypressed = false;
   public int pressed=0;
   
   public void keyPressed(KeyEvent e) {
      int keyCode = e.getKeyCode();
      switch( keyCode ) { 
         case 72:
            pressed = 5;
            break;
         case KeyEvent.VK_UP:
            pressed = 4;
            break;
         case KeyEvent.VK_DOWN:
            pressed=3;
            break;
         case KeyEvent.VK_LEFT:
            pressed=2;
            break;
         case KeyEvent.VK_RIGHT :
            pressed = 1;
            break;
        }
   }
   
   public int tick(){
   return pressed;
   }
   
   @Override
   public void keyReleased(KeyEvent e) {
   pressed=0;

   }

   public void keyTyped(KeyEvent e) {
   }
   

}