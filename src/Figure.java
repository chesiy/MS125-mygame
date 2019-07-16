import java.awt.image.BufferedImage;

public abstract class Figure {
    protected int x;
    protected int y;
    protected int width;//宽
    protected int height;//高
    protected BufferedImage image;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public abstract boolean outOfBound();//是否出界

    public abstract void step();//人物移动一步

    public boolean shootBy(Bullet bul){//检查是否被子弹击中
        return this.x<bul.x&&bul.x<this.x+width&&this.y<bul.y&&bul.y<this.y+height;
    }

}
