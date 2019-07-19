import java.awt.image.BufferedImage;

public class Heart {
    protected int x;
    protected int y;
    protected int width;//宽
    protected int height;//高
    protected BufferedImage image;

    public Heart(Hero hero){
        x=hero.getX();
        y=hero.getY();
        image=Main.heart;
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
        x=hero.getX()-100;
        y=hero.getY()-50;
    }
}
