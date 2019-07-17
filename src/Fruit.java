import java.util.Random;

public class Fruit extends FlyingObject{
    private double Speed;
    private int awardType;//1-奖励money；2-奖励life；3-奖励火力
    private double Tox,Toy;
    private double oriX,oriY;

    public Fruit(){

        this.image=Main.fruit;
        width= image.getWidth();
        height = image.getHeight();

        Random rand = new Random();
        Speed=10;
        oriX=x = rand.nextInt(Main.WIDTH - width);
        oriY=y=rand.nextInt(Main.HEIGHT-height);//随机初始化位置
        Tox=oriX+1;Toy=oriY+1;

        awardType = rand.nextInt(3);   //初始化时给奖励,三种类型
    }

    public int getAwardType(){return awardType;}

    @Override
    public boolean outOfBounds() {
        return y>Main.HEIGHT||y<0||x>Main.WIDTH||x<0;
    }

    @Override
    public void step(){
        if(x<Tox&&x+width>Tox&&y<Toy&&y+height>Toy){
            Random rand =new Random();
            Tox=rand.nextInt(20);
            Toy=rand.nextInt(20);
            oriX=x;oriY=y;
        }
        double xSpeed,ySpeed;
        xSpeed=Speed*(Tox-oriX)/(Math.abs(Tox-oriX)+Math.abs(Toy-oriY));
        ySpeed=Speed*(Toy-oriY)/(Math.abs(Tox-oriX)+Math.abs(Toy-oriY));
        x+=xSpeed;
        y+=ySpeed;
    }



}
