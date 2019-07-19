public class Missile extends FlyingObject {
   private int speed;
   private double toX,toY;
   private Hero hero;
   private Boss boss;
   private double xSpeed,ySpeed;

   public Missile(Hero hero,Boss boss){
       x=hero.getX();y=hero.getY();
       toX=boss.getX();toY=boss.getY();
       this.hero=hero;this.boss=boss;
       this.image=Main.missile;
       width=image.getWidth();height=image.getHeight();
       speed=20;
   }

    @Override
    public boolean outOfBounds(){
        if(x>-width&&x<Main.WIDTH&&y>-height&&y<Main.HEIGHT){
            return false;
        }
        return true;
    }

    @Override
    public void step(){
        toX=boss.getX();toY=boss.getY();
        xSpeed=speed*(toX-x)/(Math.abs(toX-x)+Math.abs(toY-y));
        ySpeed=speed*(toY-y)/(Math.abs(toX-x)+Math.abs(toY-y));
        x+=xSpeed;
        y+=ySpeed;
    }
}
