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

    public Fire[] shoot(){
        Fire[] fires=new Fire[8];//朝8个方向发子弹
        for(int i=0;i<8;i++){
            fires[i]=new Fire(x,y,i);
        }
        return fires;
    }
}
