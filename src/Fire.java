import java.awt.image.BufferedImage;
import java.util.Random;

public class Fire extends FlyingObject{
    private int speed=6;
    private int type;
    double xSpeed,ySpeed;

    public Fire(double fromx,double fromy,int tp){
        x=fromx;
        y=fromy;
        image=Main.fire;
        type=tp;
    }
    public Fire(double fromx,double fromy,double tox,double toy){
        x=fromx;
        y=fromy;
        image=Main.fire;
        type=-1;
        xSpeed=speed*(tox-x)/(Math.abs(tox-x)+Math.abs(toy-y));
        ySpeed=speed*(toy-y)/(Math.abs(tox-x)+Math.abs(toy-y));
    }

    @Override
    public boolean outOfBounds() {
        if(x>-width&&x<Main.WIDTH&&y>-height&&y<Main.HEIGHT){
            return false;
        }
        return true;
    }
    @Override
    public void step() {
        switch(type){
            case 0:
                xSpeed=speed;ySpeed=0;
                break;
            case 1:
                xSpeed=-speed/1.4;ySpeed=-speed/1.4;
                break;
            case 2:
                xSpeed=-speed/1.4;ySpeed=speed/1.4;
                break;
            case 3:
                xSpeed=0;ySpeed=-speed;
                break;
            case 4:
                xSpeed=speed/1.4;ySpeed=-speed/1.4;
            default:break;
        }
        x+=xSpeed;
        y+=ySpeed;
    }
}
