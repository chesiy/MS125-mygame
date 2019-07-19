import java.awt.image.BufferedImage;

public class Defenceobj {
    protected int x;
    protected int y;
    protected int width;//宽
    protected int height;//高
    protected BufferedImage image;

    public Defenceobj(Hero hero){
        x=hero.getX();
        y=hero.getY();
        image=Main.shield;
        width=image.getWidth();
        height=image.getHeight();
    }
    public boolean outOfBound(){return false;}

    public BufferedImage getImage() {
        return image;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void step(Hero hero){
        x=hero.getX()+20;
        y=hero.getY();
    }

}
