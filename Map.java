import javax.swing.JPanel;
import javax.swing.JFrame;
import java.util.Arrays;
import java.awt.Graphics;

public class Map
{
   
   private String[][] map;
   private int width;
   private int height;
   private Tile[][] tiles;
public enum Terrain
   {
      FLOOR, WALL, DOOR, CORRIDOR, UPSTAIRS, DOWNSTAIRS;
   }
   private Boolean newWorld = false;
   private int startX;
   private int startY;
      private int endX;
   private int endY;
   private Character cha;
   private static String border = "|";
   private static String wall = " ";
   private static String corridor = "o";
   private static String floor = "0";
   private static String door = "D";
   private static String up = "^";
   private static String down = "v";
   private int objects = 10;
   private static int chanceRoom = 90;
   private static int chanceCorridor = 50;
   
   public Map(int wid, int hei)
   {
      map = populateMap(new String[wid][hei]);
      tiles=new Tile[wid][hei];
      width = wid;
      height = hei;
      
      this.createDungeon(50);

   }
   
   public String[][] populateMap(String[][] m)
   {
      for(int x = 0; x<m.length; x++)
         for(int y= 0; y<m[x].length; y++)
         {
            m[x][y]= wall;
         }
      return m; 
   }   
   
   public void setRect(int x, int y, int w, int h, String type)
   {
      for(int i=x-w/2; i<x+w/2; i++)
      {
         for(int j=y-h/2; j<y+h/2; j++)
         {
            if(i<=width&&i>=0&&j<=height&&j>=0)
               map[i][j] = type;
         }
      }
   }
   /** Makes a corridor with a random length
   @param x the x-coord of the start
   @param y the y-coord of the start
   @param direction the direction of the corridor, with 0-3 as west to north counterclockwise
   @param length the max length of the corridor
   */
   public boolean makeCorridor(int x, int y, int length, int direction)
   {
      int len = getRand(2, length);
      int dir = 0;
      if(direction > 0 && direction < 4)
         dir = direction;
   
      int xtemp = 0;
      int ytemp = 0;
   
      switch(dir)
      {
         case 0:
            {
               if(x < 0 || x > width)
                  return false;
               else xtemp = x;
            
               for(ytemp = y; ytemp > (y-len); ytemp--)
               {
                  if(ytemp < 0 || ytemp > height)
                     return false;
                  if(map[xtemp][ytemp] != wall)
                     return false;
               }
            
               for(ytemp = y; ytemp > (y - len); ytemp--)
               {
                  map[xtemp][ytemp] = corridor;
               }
               break;
            
            }
         case 1:
            {
               if(y < 0 || y > height) 
                  return false;
               else ytemp = y;
            
               for(xtemp = x; xtemp < (x + len); xtemp++)
               {
                  if(xtemp < 0 || xtemp > width) 
                     return false;
                  if(map[xtemp][ytemp] != wall)
                     return false;
               }
            
               for(xtemp = x; xtemp < (x + len); xtemp++)
               {
                  map[xtemp][ytemp] = corridor;
               }
               break;
            }
         case 2:
            {
               if(x < 0 || x > width) 
                  return false;
               else xtemp = x;
            
               for(ytemp = y; ytemp < (y + len); ytemp++)
               {
                  if(ytemp < 0 || ytemp > height) 
                     return false;
                  if(map[xtemp][ytemp] != wall) 
                     return false;
               }
               for (ytemp = y; ytemp < (y+len); ytemp++){
                  map[xtemp][ytemp] = corridor;
               }
               break;
            }
         case 3:
            {
               if (y < 0 || y > height)
                  return false;
               else ytemp = y;
            
               for (xtemp = x; xtemp > (x-len); xtemp--){
                  if (xtemp < 0 || xtemp > width)
                     return false;
                  if (map[xtemp][ytemp] != wall)
                     return false;
               }
            
               for (xtemp = x; xtemp > (x-len); xtemp--){
                  map[xtemp][ytemp] = corridor;
               }
               break;
            }
      }
      return true;
   }
   //Direction 0-3 is west to north also
   boolean makeRoom(int x, int y, int xlength, int ylength, int direction)
   {
   	//define the dimensions of the room, it should be at least 4x4 tiles (2x2 for walking on, the rest is walls)
      int xlen = getRand(4, xlength);
      int ylen = getRand(4, ylength);
   	//the tile type it's going to be filled with
      String roomFloor = floor;
      String roomWall = wall;
   	//choose the way it's pointing at
      int dir = 0;
      if (direction > 0 && direction < 4) dir = direction;
   
      switch(dir){
         case 0:
         //north
         //Check if there's enough space left for it
            for (int ytemp = y; ytemp > (y-ylen); ytemp--){
               if (ytemp < 0 || ytemp > height) 
                  return false;
               for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
                  if (xtemp < 0 || xtemp > width) 
                     return false;
                  if (map[xtemp][ytemp] != roomWall) 
                     return false; //no space left...
               }
            }
         
         //If all conditions are correct
            for (int ytemp = y; ytemp > (y-ylen); ytemp--){
               for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
               //start with the walls
                  if (xtemp == (x-xlen/2) || xtemp == (x+(xlen-1)/2) || ytemp == y || ytemp == (y-ylen+1))
                     map[xtemp][ytemp] = roomWall;
                  //and then fill with the floor
                  else 
                     map[xtemp][ytemp] = roomFloor;
               }
            }
            break;
         case 1:
         //east
            for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
               if (ytemp < 0 || ytemp > height) 
                  return false;
               for (int xtemp = x; xtemp < (x+xlen); xtemp++){
                  if (xtemp < 0 || xtemp > width) 
                     return false;
                  if (map[xtemp][ytemp] != wall) 
                     return false;
               }
            }
         
            for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
               for (int xtemp = x; xtemp < (x+xlen); xtemp++){
               
                  if(xtemp == x || xtemp == (x+xlen-1) || ytemp == (y-ylen/2) || ytemp == (y+(ylen-1)/2))
                     map[xtemp][ytemp] = roomWall;
                  else
                     map[xtemp][ytemp] = roomFloor;
               }
            }
            break;
         case 2:
         //south
            for (int ytemp = y; ytemp < (y+ylen); ytemp++){
               if (ytemp < 0 || ytemp > height) 
                  return false;
               for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
                  if (xtemp < 0 || xtemp > width) 
                     return false;
                  if (map[xtemp][ytemp] != wall) 
                     return false;
               }
            }
         
            for (int ytemp = y; ytemp < (y+ylen); ytemp++){
               for (int xtemp = (x-xlen/2); xtemp < (x+(xlen+1)/2); xtemp++){
               
                  if (xtemp == (x-xlen/2) || xtemp == (x+(xlen-1)/2) || ytemp == y || ytemp == (y+ylen-1))
                     map[xtemp][ytemp] = roomWall;
                  
                  else
                     map[xtemp][ytemp] = roomFloor;
               }
            }
            break;
         case 3:
         //west
            for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
               if (ytemp < 0 || ytemp > height) 
                  return false;
               for (int xtemp = x; xtemp > (x-xlen); xtemp--){
                  if (xtemp < 0 || xtemp > width) 
                     return false;
                  if (map[xtemp][ytemp] != wall) 
                     return false;
               }
            }
         
            for (int ytemp = (y-ylen/2); ytemp < (y+(ylen+1)/2); ytemp++){
               for (int xtemp = x; xtemp > (x-xlen); xtemp--){
               
                  if (xtemp == x || xtemp == (x-xlen+1) || ytemp == (y-ylen/2) || ytemp == (y+(ylen-1)/2))
                     map[xtemp][ytemp] = roomWall;
                  
                  else
                     map[xtemp][ytemp] = roomFloor;
               }
            }
            break;
      }
   
      return true;
   }
   
   public boolean createDungeon(int features)
   {
      if (features < 1) 
         objects = 3;
      else
         objects = features;
         
      for (int y = 0; y < height; y++){
         for (int x = 0; x < width; x++){
         	//ie, making the borders of unwalkable walls
            if (y <2 || y > height-3 || x < 2 || x > width-3)
               map[x][y] = border;
            
            //and fill the rest with dirt
            else
               map[x][y] = wall;
         }
      }
      
      makeRoom(width/2, height/2, 8, 6, getRand(0,3));
      int currentFeatures = 1; //+1 for the first room we just made
      for (int countingTries = 0; countingTries < 1000; countingTries++){
      	//check if we've reached our quota
         if (currentFeatures == objects){
            break;
         }
      
      	//start with a random wall
         int newx = 0;
         int xmod = 0;
         int newy = 0;
         int ymod = 0;
         int validTile = -1;
      	//1000 chances to find a suitable object (room or corridor)
         for (int testing = 0; testing < 1000; testing++){
            //Finds a random coordinate in the map
            newx = getRand(1, width-1);
            newy = getRand(1, height-1);
            validTile = -1;
            
            if (map[newx][newy] == wall || map[newx][newy] == corridor){
            	//check if we can reach the place
               if (map[newx][newy+1] == floor || map[newx][newy+1] == corridor){
                  validTile = 0; //
                  xmod = 0;
                  ymod = -1;
               }
               else if (map[newx-1][newy] == floor || map[newx-1][newy] == corridor){
                  validTile = 1; //
                  xmod = +1;
                  ymod = 0;
               }
               else if (map[newx][newy-1] == floor || map[newx][newy-1] == corridor){
                  validTile = 2; //
                  xmod = 0;
                  ymod = +1;
               }
               else if (map[newx+1][newy] == floor || map[newx+1][newy] == corridor){
                  validTile = 3; //
                  xmod = -1;
                  ymod = 0;
               }
            
            	//check that we haven't got another door nearby, so we won't get alot of openings besides
            	//each other
               if (validTile > -1)
                  if (map[newx][newy+1] == door || map[newx-1][newy] == door || map[newx][newy-1] == door || map[newx+1][newy] == door)
                     validTile = -1;
            
            	//if we can, jump out of the loop and continue with the rest
               if (validTile > -1) 
                  break;
            }
         }
         if (validTile > -1){
         	//choose what to build now at our newly found place, and at what direction
            int feature = getRand(0, 100);
            if (feature <= chanceRoom){ //a new room
               if (makeRoom((newx+xmod), (newy+ymod), 12, 8, validTile)){
                  currentFeatures++; //add to our quota
               
               	//then we mark the wall opening with a door
                  map[newx][newy] = door;
               
               	//clean up infront of the door so we can reach it
                  map[newx+xmod][newy+ymod] = floor;
               }
            }
            else if (feature >= chanceRoom){ //new corridor
               if (makeCorridor((newx+xmod), (newy+ymod), 6, validTile)){
               	//same thing here, add to the quota and a door
                  currentFeatures++;
               
                  map[newx][newy] = door;
               }
            }
         }
      }
      //sprinkle out the bonusstuff (stairs, chests etc.) over the map
      int newx = 0;
      int newy = 0;
      int ways = 0; //from how many directions we can reach the random spot from
      int state = 0; //the state the loop is in, start with the stairs
      while (state != 10){
         for (int testing = 0; testing < 1000; testing++){
            newx = getRand(1, width-1);
            newy = getRand(1, height-2); //cheap bugfix, pulls down newy to 0<y<24, from 0<y<25
         
         	//System.out.println("x: " + newx + "\ty: " + newy);
            ways = 4; //the lower the better
         
         	//check if we can reach the spot
            if (map[newx][newy+1] == floor || map[newx][newy+1] == corridor){
            //north
               if (map[newx][newy+1] != door)
                  ways--;
            }
            if (map[newx-1][newy] == floor || map[newx-1][newy] == corridor){
            //east
               if (map[newx-1][newy] != door)
                  ways--;
            }
            if (map[newx][newy-1] == floor || map[newx][newy-1] == corridor){
            //south
               if (map[newx][newy-1] != door)
                  ways--;
            }
            if (map[newx+1][newy] == floor || map[newx+1][newy] == corridor){
            //west
               if (map[newx+1][newy] != door)
                  ways--;
            }
         
            if (state == 0){
               if (ways == 0){
               //we're in state 0, let's place a "upstairs" thing
                  map[newx][newy] = up;
                  startX=newx;
                  startY=newy;
                  state = 1;
                  break;
               }
            }
            else if (state == 1){
               if (ways == 0){
               //state 1, place a "downstairs"
                  map[newx][newy] = down;
                  endX=newx;
                  endY=newy;
                  state = 10;
                  break;
               }
            }
         }
      }
      return true;
   }
   
   public int getRand(int min, int max)
   {
      return (int)(min + (Math.random()*(max-min)));
   }
   
   public void addActors(Actor z, int x, int y)   
   {
      map[x][y]= z.toString();
   }
   


   public String lineToString(int r){
      String str= "";
      for(int i= 0;i<map[r].length;i++){
         str+=map[r][i];
         str+=" ";}
      return str;
   }

   public int rowLength(){
      return map.length;
   }
   public int colLength(){
      return map[0].length;
   }
   
   @Override public String toString() {
      String ret = "";
      for(int i=0; i<map.length; i++)
      {
         for(int j=0; j<map[0].length; j++)
         {
            ret+=map[i][j];
         }
         ret += "\n";
      }
      return ret;
   }

   public Tile tileAt(int x,int y){
   return tiles[x][y];
   }
   
   public void drawMap(Graphics g){
   int xtem= 0;
   int ytem = 0;
     for(int xi = 0; xi<map.length;xi++){
         for(int yi=0;yi<map[xi].length;yi++){
          
           tiles[xi][yi] =new Tile(xtem,ytem,map[xi][yi]);
            
           
               xtem+=20;
               }
            ytem+=20;
            xtem=0;
            }
      render(g);      
   }
   public int startX(){
   return startX;}
   public int startY(){
   return startY;}
   public int endX(){
   return endX;}
   public int endY(){
   return endY;}

   public void render(Graphics g){
   for(Tile[] t: tiles){
      for(Tile x: t){
      x.render(g);}
      }
      }
      

}