import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class World{

   private Map map;
   private int hi;
   private Character cha;
   private List<Monster> enemies= new ArrayList<Monster>();
   private List<Chest> chests = new ArrayList<Chest>();
   private boolean newWorld=false;
   private int numberEnemies;
   private int numberChests;
   public World(int z){


   hi=z;
   }
//Fills floors with enemies and treasures.
   public void populateMap(){
      numberEnemies = getRand((int)((cha.getLevel()*2)+10)/2, (int)((cha.getLevel()*2)+16)/2)+3;
      numberChests = getRand(numberEnemies-2, numberEnemies+2)+3;
      for(int i=0; i< numberEnemies; i++){
         int x = getRand(0, map.rowLength());
         int y = getRand(0, map.colLength());
         while(!map.tileAt(y, x).isPass())
         {
            x = getRand(0, map.rowLength());
            y = getRand(0, map.colLength());
         }
         //System.out.println("New monster at" + x + ", " + y);
         enemies.add(new Monster(x*20,y*20,(int)(cha.getLevel()*1.5 + 15),map,cha));
      }
      for(int j=0; j< numberChests; j++){
         int a = getRand(0, map.rowLength());
         int b = getRand(0, map.colLength());
         while(!map.tileAt(b, a).isPass())
         {
            a = getRand(0, map.rowLength());
            b = getRand(0, map.colLength());
         }
         chests.add(new Chest(a*20,b*20,map,cha));
      }
      
   }
   
   public int getRand(int min, int max)
   {
      return (int)(min + (Math.random()*(max-min)));
   }

   public void tick(int pressed,Graphics g){
            cha.tick(pressed);


      for(Monster m: enemies){
         m.tick();
      }
      for(Chest c: chests){
         c.tick();
      }

            render(g);   
   }
   public void render(Graphics g){
              map.render(g);
       for(Chest c: chests){
         c.render(g);
      }

     for(Monster m: enemies){
         m.render(g);
      }
      cha.render(g);
      if(cha.newWorld()){
         start(g);}   

}
    /*
    *Starts new floor or game, makes new map and moves character to start
    */  
   public void start(Graphics g){
      this.map = new Map(hi/20,hi/20);
      map.drawMap(g);
      enemies = new ArrayList<Monster>();
      chests = new ArrayList<Chest>();

      if(cha==null)
         cha = new Character(map.startY()*20,map.startX()*20,100,map, enemies);
      else{
         cha.move(map.startY()*20,map.startX()*20);   
         cha.setMap(map);
         cha.setEnemies(enemies);
         cha.noNewWorld();}
               populateMap();

      System.gc();
      this.render(g);
   
   }
   public Character getChar(){
      return cha;}
 
}