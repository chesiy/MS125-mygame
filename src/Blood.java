import java.awt.image.BufferedImage;

public class Blood {
    protected int x;
    protected int y;
    protected int width;//宽
    protected int height;//高
    protected BufferedImage image;

    Blood(Boss boss){
        x=boss.getX();
        y=boss.getY();
        image=Main.blood;
        width=image.getWidth();
        height=image.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void step(Boss boss){
        x=boss.getX();
        y=boss.getY()-20;
        if(boss.getBlood()>0) {
            image = new BufferedImage(boss.getBlood() * width / 100, height, BufferedImage.TYPE_INT_RGB);
        }
        if(boss.getBlood()==0){
            image = null;
        }
    }
}
