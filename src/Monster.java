public class Monster extends Figure {
    private int speed =-3;//怪是往左走的

    public Monster(){
        this.image=Main.monster;
        width=image.getWidth();
        height=image.getHeight();
        y=Main.HEIGHT-height-20;
        x=Main.WIDTH-width-20;
    }

    @Override
    public boolean outOfBound(){
        return x<0;
    }
    @Override
    public void step(){
        x+=speed;
    }
}
