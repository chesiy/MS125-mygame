public class Bullet  extends FlyingObject{
    private int Speed=20;
    public double Tox,Toy;
    public double oriX,oriY;
    double xSpeed,ySpeed;

    public Bullet(double fromx,double fromy,double tox,double toy){
        oriX=this.x=fromx;
        oriY=this.y=fromy;
        this.image=Main.bullet;
        Tox=tox;Toy=toy;
    }

    @Override
    public void step(){
        xSpeed=Speed*(Tox-oriX)/(Math.abs(Tox-oriX)+Math.abs(Toy-oriY));
        ySpeed=Speed*(Toy-oriY)/(Math.abs(Tox-oriX)+Math.abs(Toy-oriY));
        x+=xSpeed;
        y+=ySpeed;
     //   System.err.println("aaaaa"+Tox+' '+Toy+' '+oriX+' '+oriY+' '+x+' '+y);
    }
    @Override
    public boolean outOfBounds(){
        if(x>-width&&x<Main.WIDTH&&y>-height&&y<Main.HEIGHT){
            return false;
        }
        return true;
    }
}
