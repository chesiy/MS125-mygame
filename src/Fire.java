import java.awt.image.BufferedImage;
import java.util.Random;

public class Fire extends FlyingObject{
    private int speed=10;
    private int type;
    double xSpeed,ySpeed;

    public Fire(double fromx,double fromy,int tp){
        x=fromx;
        y=fromy;
        image=Main.fire;
        type=tp;
    }

    @Override
    public boolean outOfBounds() {
        return false;
    }

    @Override
    public void step() {
        switch(type){
            case 0:
                xSpeed=speed;ySpeed=0;
                break;
            case 1:
                xSpeed=speed/1.4;ySpeed=speed/1.4;
                break;
            case 2:
                xSpeed=-speed/1.4;ySpeed=speed/1.4;
                break;
            case 3:
                xSpeed=speed/1.4;ySpeed=-speed/1.4;
                break;
            case 4:
                xSpeed=-speed/1.4;ySpeed=-speed/1.4;
                break;
            case 5:
                xSpeed=-speed;ySpeed=0;
                break;
            case 6:
                xSpeed=0;ySpeed=speed;
                break;
            case 7:
                xSpeed=0;ySpeed=-speed;
                break;
        }
        x+=xSpeed;
        y+=ySpeed;
    }


}
