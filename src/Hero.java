import java.awt.image.BufferedImage;

public class Hero extends Figure{

    private BufferedImage[] images={};//主人公图片
    private int index=0;

    private int life;
    private int money;
    private int firepower;//火力

    public Hero(){
        life=3;
        firepower=1;
        money=100;
        image=Main.hero0;//初始是hero0
        images=new BufferedImage[]{Main.hero0,Main.hero1};
        width=image.getWidth();
        height=image.getHeight();
        x=150;y=300;
    }

    public void addLife(){life++;}
    public int getLife(){return life;}

    @Override
    public boolean outOfBound(){return false;}

    @Override
    public void step(){//是做动画用的
     /*   if(images.length>0){
            image=images[index++/10%images.length];
        }*/
    }

    public void moveTo(int x,int y){

        this.x = x - width/2;
        this.y = y - height/2;
    }

    public Bullet[] shoot(){
        Bullet []bullets=new Bullet[1];
        bullets[0]=new Bullet(x+20,y-height/2);
        return bullets;
    }
    public void skill1(){//发射技能1

    }

    public void skill2(){//发射技能2

    }

    public void skill3(){//发射技能3

    }
    /***碰撞算法***/
    //用在主人公碰到怪物就减一条命
    public boolean hit(Monster other){
        int x1 = other.x - this.width/2;                 //x坐标最小距离
        int x2 = other.x + this.width/2 + other.width;   //x坐标最大距离
        int y1 = other.y - this.height/2;                //y坐标最小距离
        int y2 = other.y + this.height/2 + other.height; //y坐标最大距离

        int herox = this.x + this.width/2;               //主人公x坐标中心点距离
        int heroy = this.y + this.height/2;              //主人公y坐标中心点距离

        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
    }






}
