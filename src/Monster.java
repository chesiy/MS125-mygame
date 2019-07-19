public class Monster extends Figure {
    private int speed =-2;//怪是往左走的

    public Monster(){
        this.image=Main.monster;
        width=image.getWidth();
        height=image.getHeight();
        y=Main.HEIGHT-height-20;
        x=Main.WIDTH-width-20;
    }

    @Override
    public boolean outOfBound(){
        return x<-width;
    }
    @Override
    public void step(){
        x+=speed;
    }

    public Fire[] shoot(Hero hero){
        Fire[] fires=new Fire[5];//朝5个方向发子弹
        for(int i=0;i<4;i++){
            fires[i]=new Fire(x,y,i);
        }
        fires[4]=new Fire(x,y,hero.getX(),hero.getY());
        return fires;
    }

    public boolean attackBy(Heart heart){
        return this.x<heart.x&&heart.x<this.x+width&&this.y<heart.y&&heart.y<this.y+height;
    }
}
