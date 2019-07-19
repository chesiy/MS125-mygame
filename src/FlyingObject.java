import java.awt.image.BufferedImage;

//果子在天上飞（任意飞），主人公释放3技能打中才可以吃到果子
//子弹均为平飞，主人公释放1技能打出3发子弹，2技能打出6发子弹（每发子弹的伤害——减怪物血10滴）
public abstract class FlyingObject {//果子和子弹
    protected double x;    //x坐标
    protected double y;    //y坐标
    protected int width;    //宽
    protected int height;   //高
    protected BufferedImage image;   //图片

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
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

    public boolean shootBy(Bullet bul){//检查是否被子弹击中
        return this.x<bul.x&&bul.x<this.x+width&&this.y<bul.y&&bul.y<this.y+height;
    }

    public boolean shootBy(Fire fir){
        return this.x<fir.x&&fir.x<this.x+width&&this.y<fir.y&&fir.y<this.y+height;
    }


    //检查是否出界
    public abstract boolean outOfBounds();

    //移动一步
    public abstract void step();

    //检查是否被打中（吃果子也用子弹射）
 /*   public boolean shootBy(Bullet bullet){
        int x = bullet.x;  //子弹横坐标
        int y = bullet.y;  //子弹纵坐标
        return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
    }
*/
}
