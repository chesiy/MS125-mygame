public class Boss extends Figure{//上下飞，一边发射攻击，每过10s寻主人公一次
    private int blood;
    private boolean direction;

    public Boss(){
        this.image=Main.boss;
        width=image.getWidth();
        height=image.getHeight();
        y=20;
        blood=100;
        x=Main.WIDTH-width-50;
        direction=false;//初始向上
    }

    void lose_blood(int num){
        blood-=num;
    }

    int getBlood(){
        return blood;
    }

    @Override
    public boolean outOfBound(){return false;}

    @Override
    public void step(){
        if(direction==false){
            y+=1.5;
            if(y==Main.HEIGHT-height-50)direction=true;
        }
        else{
            y-=1.5;
            if(y==20)direction=false;
        }
    }

    public boolean attackBy(Heart heart){
        return heart.x<this.x&&this.x<heart.x+heart.width&&heart.y<this.y&&this.y<heart.y+heart.width;
    }


}
