import java.util.Random;

public class Fruit extends FlyingObject{
    private int xSpeed;
    private int ySpeed;
    private int awardType;//1-奖励money；2-奖励life；3-奖励火力

    public Fruit(){

        this.image=Main.fruit;
        width= image.getWidth();
        height = image.getHeight();

        Random rand = new Random();
        xSpeed=rand.nextInt(4);
        ySpeed=rand.nextInt(4);//先随机初始化一个速度
        x = rand.nextInt(Main.WIDTH - width);
        y=rand.nextInt(Main.HEIGHT-height);

        awardType = rand.nextInt(3);   //初始化时给奖励,三种类型
    }

    public int getAwardType(){return awardType;}

    @Override
    public boolean outOfBounds() {
        return y>Main.HEIGHT||y<0||x>Main.WIDTH||x<0;
    }

    @Override
    public void step(){
        x+=xSpeed;
        y+=ySpeed;

        Random rand =new Random();
        xSpeed=rand.nextInt(4);
        ySpeed=rand.nextInt(4);
        boolean direction=rand.nextBoolean();
        if(direction==false||x>Main.WIDTH-width||y>Main.HEIGHT-height){
            xSpeed=-xSpeed;
            ySpeed=-ySpeed;
        }
    }



}
