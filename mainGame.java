import javax.swing.JOptionPane;

public class mainGame{
  
  
  
   public static void main(String[] args){
               int reply = JOptionPane.showConfirmDialog(null, "You are a dungeon cartographer. It is your job to go in and map the dungeons, so the real heroes of the game can win easily. \n Your goal is to get the most points possible without dying. Points are gained by uncovering tiles, defeating monsters, and opening chests. \n Are you ready for your great adventure?", "Welcome, \"hero\"", JOptionPane.YES_NO_OPTION);
         if (reply == JOptionPane.YES_OPTION) {
      }
      if(reply==JOptionPane.NO_OPTION){
      JOptionPane.showMessageDialog(null, "Too bad!");
}
Game n= new Game(800,800,"Game");
      n.start();
      
   }
}