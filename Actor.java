public abstract class Actor extends Entity{
   protected int health;
   protected int maxHealth;
   protected int defense=1;

   public Actor(int x, int y, int health){
      super(x,y);    
      this.health=health;
      this.maxHealth = health;
      }
  
   protected int getHealth(){
   return health;
   }
   public void attacked(int z){
   health = health+defense-z;}
   public void changeHealth(int h){
   health= health+h;}
   
    /*
   *Checks if actor is within 1 space of other actor
   */

   public boolean inRange(Actor e){
   if(this.yAway(e)+this.xAway(e)<=1)
      return true;
    return false;}
   
   public int getYP(){
   return y;
   }
   
   public int getX(){
   return y/20;}
   
   public int getY(){
   return x/20;}
   
   public void addArmor(int i){
   defense+=i;}
    
      public int defense(){
   return defense;
   }
 
   
   public int xAway(Actor e){
      return Math.abs(this.getX()-e.getX());

   }
   
   public int yAway(Actor e){
   return Math.abs(this.getY()-e.getY());
   }

}      
