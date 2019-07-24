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
        Fire[] fires=new Fire[6];//朝5个方向发子弹
        for(int i=0;i<5;i++){
            fires[i]=new Fire(x,y,i);
        }
        fires[5]=new Fire(x,y,hero.getX(),hero.getY());

        return fires;
    }

    public boolean attackBy(Heart heart){
        return heart.x<this.x&&this.x<heart.x+heart.width&&heart.y<this.y&&this.y<heart.y+heart.width;
    }
}
