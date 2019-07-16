public class Bullet  extends FlyingObject{
    private int speed=5;
    public Bullet(int x,int y){
        this.x=x;
        this.y=y;
        this.image=Main.bullet;
    }

    @Override
    public void step(){
        x+=speed;//平飞
    }

    @Override
    public boolean outOfBounds(){
        return x>width;
    }
}
