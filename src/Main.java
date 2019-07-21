import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {
    public static final int WIDTH = 800; // 面板宽
    public static final int HEIGHT = 480; // 面板高
    /** 游戏的当前状态: START RUNNING PAUSE GAME_OVER */
    private int state;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;
    private static final int WIN=4;

    private int score = 0; // 得分
    private Timer timer; // 定时器
    private int intervel = 1000 / 100; // 时间间隔(毫秒)

    public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage monster;
    public static BufferedImage fruit;//奖励
    public static BufferedImage bullet;//子弹
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    public static BufferedImage fire;//怪物喷的火球
    public static BufferedImage shield;
    public static BufferedImage missile;
    public static BufferedImage boss;
    public static BufferedImage heart;//主人公4技能
    public static BufferedImage blood;//boss的血条
    private static BufferedImage win;

    private Bullet[] bullets ={};//子弹数组
    private Hero myhero =new Hero();//主人公
    private Monster[] monsters={};//怪物数组
    private Fruit[] fruits={};//水果数组
    private Fire[] fires={};//火球数组
    private Boss myboss=new Boss();
    private Missile[] missiles={};//1技能的导弹
    private Defenceobj myshield=new Defenceobj(myhero);
    private Heart myheart=new Heart(myhero);
    private Blood bossblood=new Blood(myboss);

    static {
        try {
            background=ImageIO.read(Main.class.getResource("background.JPG"));
            start=ImageIO.read(Main.class.getResource("start.png"));
            monster=ImageIO.read(Main.class.getResource("monster.png"));
            fruit=ImageIO.read(Main.class.getResource("fruit.png"));
            bullet=ImageIO.read(Main.class.getResource("bullet.png"));
            hero0=ImageIO.read(Main.class.getResource("hero.png"));
         //   hero1=ImageIO.read(Main.class.getResource("hero1.JPG"));
            pause=ImageIO.read(Main.class.getResource("pause.png"));
            gameover=ImageIO.read(Main.class.getResource("gameover.png"));
            fire=ImageIO.read(Main.class.getResource("fire.png"));
            boss=ImageIO.read(Main.class.getResource("boss.png"));
            shield=ImageIO.read(Main.class.getResource("shield.png"));
            missile=ImageIO.read(Main.class.getResource("missile.png"));
            heart=ImageIO.read(Main.class.getResource("heart.png"));
            blood=ImageIO.read(Main.class.getResource("bossblood.png"));
            win=ImageIO.read(Main.class.getResource("win.png"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /***画***/
    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null); // 画背景图
        paintHero(g); // 画主人公
        paintBullets(g); // 画子弹
        paintMonsters(g);//画怪物
        paintFruit(g);//画水果
        paintFire(g);//画火
        paintBoss(g);
        paintBossblood(g);
        if(under_defence==true)paintShield(g);//盾存在的时间仅3s
        if(skill4_ready==true)paintHeart(g);
        paintMissile(g);
        paintScore(g); // 画分数
        paintState(g); // 画游戏状态
    }

    public void paintHero(Graphics g) {
        g.drawImage(myhero.getImage(), myhero.getX(), myhero.getY(), null);
    }

    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), (int)b.getX(), (int)b.getY(), null);
        }
    }
    public void paintMonsters(Graphics g){
        for(int i=0;i<monsters.length;i++){
            Monster mon=monsters[i];
            g.drawImage(mon.getImage(),mon.getX(),mon.getY(),null);
        }
    }

    public void paintFruit(Graphics g){
        for(int i=0;i<fruits.length;i++){
            Fruit fru=fruits[i];
            g.drawImage(fru.getImage(),(int)fru.getX(),(int)fru.getY(),null);
        }
    }
    public void paintFire(Graphics g){
        for(int i=0;i<fires.length;i++){
            Fire fir=fires[i];
            g.drawImage(fir.image,(int)fir.getX(),(int)fir.getY(),null);
        }
    }
    public void paintBoss(Graphics g){
        g.drawImage(myboss.getImage(),myboss.getX(),myboss.getY(),null);
    }

    public void paintBossblood(Graphics g){
        g.drawImage(bossblood.image,bossblood.getX(),bossblood.getY(),null);
    }

    public void paintShield(Graphics g){
        g.drawImage(myshield.getImage(),myshield.getX(),myshield.getY(), null);
    }

    public void paintHeart(Graphics g){
        g.drawImage(myheart.getImage(),myheart.getX(),myheart.getY(), null);
    }
    public void paintMissile(Graphics g){
        for(int i=0;i<missiles.length;i++){
            Missile miss=missiles[i];
            g.drawImage(miss.image,(int)miss.getX(),(int)miss.getY(),null);
        }
    }

    public void paintScore(Graphics g) {
        int x = 10; // x坐标
        int y = 25; // y坐标
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 字体
        g.setColor(new Color(0xFF3D36));
        g.setFont(font); // 设置字体
        g.drawString("SCORE:" + score, x, y); // 画分数
        y=y+20; // y坐标增20
        g.drawString("LIFE:" + myhero.getLife(), x, y); // 画命
        y+=25;
        g.drawString("FIRE POWER:"+myhero.firepower,x,y);
        x=WIDTH-250;
        y=25;
        g.drawString("BOSS BLOOD:"+myboss.getBlood(),x,y);

    }

    /** 画游戏状态 */
    public void paintState(Graphics g) {
        switch (state) {
            case START: // 启动状态
                g.drawImage(start, 0, 0, null);
                break;
            case PAUSE: // 暂停状态
                g.drawImage(pause, 0, 0, null);
                break;
            case WIN: //打死boss，胜利了
                g.drawImage(win,0,0,null);
                break;
            case GAME_OVER: // 游戏终止状态
                g.drawImage(gameover, 0, 0, null);
                break;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("My game");
        Main game = new Main(); // 面板对象
        frame.add(game); // 将面板添加到JFrame中
        frame.setSize(WIDTH, HEIGHT); // 设置大小
        frame.setAlwaysOnTop(true); // 设置其总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 默认关闭操作
        frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // 设置窗体的图标
        frame.setLocationRelativeTo(null); // 设置窗体初始位置
        frame.setVisible(true); // 尽快调用paint

        game.action(); // 启动执行
    }

    public void action(){
        MouseAdapter l = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) { // 鼠标移动
                if (state == RUNNING) {

                }
            }
            @Override
            public void mouseEntered(MouseEvent e) { // 鼠标进入
                if (state == PAUSE) { // 暂停状态下运行
                    state = RUNNING;
                }
            }
            @Override
            public void mouseExited(MouseEvent e) { // 鼠标退出
                if (state == RUNNING) { // 游戏未结束，则设置其为暂停
                    state = PAUSE;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) { // 鼠标点击
                switch (state) {
                    case START:
                        state = RUNNING; // 启动状态下运行
                        break;
                    case RUNNING://运行状态下鼠标点哪里，主人公移到哪里
                        if(e.getButton()==e.BUTTON1) {//左键移动主人公
                            myhero.toX = e.getX();
                            myhero.toY = e.getY();
                            myhero.oriX=myhero.getX();
                            myhero.oriY=myhero.getY();
                        //    System.err.println(myhero.toX+" "+myhero.toY+' '+myhero.oriX+' '+myhero.oriY);
                        }
                        if(e.getButton()==e.BUTTON3){//右键发子弹
                            ifshoot=true;
                            myhero.sX = e.getX();
                            myhero.sY= e.getY();
                        }
                        break;
                    case WIN:
                    case GAME_OVER: // 游戏结束，清理现场
                        monsters= new Monster[0]; // 清空飞行物
                        bullets = new Bullet[0]; // 清空子弹
                        myhero = new Hero(); // 重新创建主人公
                        myboss = new Boss();
                        fires = new Fire[0];
                        fruits =new Fruit[0];//清空水果
                        missiles=new Missile[0];
                        fruitnum=0;
                        monsterindex=0;
                        fruitindex=0;
                        skill3_ready=false;
                        freezing=false;
                        skill3_index=0;

                        skill2_ready=false;
                        under_defence=false;
                        skill2_index=0;

                        skill4_ready=false;
                        under_cover=false;
                        skill4_index=0;
                        shieldindex=0;
                        score = 0; // 清空成绩
                        state = START; // 状态设置为启动
                        break;
                }
            }

        };
        KeyAdapter k=new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                char ch=e.getKeyChar();
                System.err.println(ch+' ');
                if(ch=='Q'||ch=='q'){
                    //英雄1技能，追踪boss
                    if(myhero.firepower>0){//英雄的火力值！！！！
                        skill1_ready=true;
                        myhero.firepower--;
                    }
                }
                if(ch=='W'||ch=='w'){
                     //英雄2技能，盾牌
                    skill2_ready=true;
                }
                if(ch=='E'||ch=='e'){
                    //英雄3技能，冷冻火焰
                     skill3_ready=true;
                }
                if(ch=='R'||ch=='r'){
                    //英雄4技能，金刚罩，对一圈的怪物造成伤害
                    if(myhero.firepower>0){
                        skill4_ready=true;
                        myhero.firepower--;
                    }
                }
            }
        };
        this.addMouseListener(l); // 处理鼠标点击操作
        this.addMouseMotionListener(l); // 处理鼠标滑动操作
        this.requestFocus();
        this.addKeyListener(k);

        timer=new Timer();//主流程控制
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(state==RUNNING){
                    enterAction(); // 怪兽入场,水果入场
                    stepAction(); // 走一步
                    shootAction();//打
                    bangAction(); // 子弹打飞行物
                    defenceAction();//盾
                    outOfBoundsAction(); // 删除越界飞行物及子弹
                    checkGameOverAction(); // 检查游戏结束
                }
                repaint();
            }
        },intervel,intervel);
    }

    int monsterindex=0;//怪物入场计数(计时）
    int fruitnum=0;//水果入场计数
    int fruitindex=0;
    //怪物过2s，入场一次

    public void enterAction(){
        monsterindex++;
        if(monsterindex%200==0){//2s 生成一个怪物
             Monster obj=new Monster();//随机生成一个怪物
             monsters=Arrays.copyOf(monsters,monsters.length+1);
             monsters[monsters.length-1]=obj;
        }

        if(fruitnum<3){
            fruitnum++;
            Fruit obj0=new Fruit();
            Fruit obj1=new Fruit();
            Fruit obj2=new Fruit();
            fruits=Arrays.copyOf(fruits,3);
            fruits[0]=obj0;
            fruits[1]=obj1;
            fruits[2]=obj2;
        }
    }
    public void stepAction(){
        for(int i=0;i<monsters.length;i++){//怪物动
            Monster mon=monsters[i];
            mon.step();
        }
        fruitindex++;
        if(fruitindex%10==0){
            for(int i=0;i<fruits.length;i++){
                Fruit fru=fruits[i];
                fru.step();
            }
        }
        myhero.step();
        myboss.step();
        myshield.step(myhero);
        myheart.step(myhero);
        bossblood.step(myboss);
    }

    boolean skill3_ready=false;
    boolean freezing=false;
    int skill3_index=0;

    boolean skill2_ready=false;
    boolean under_defence=false;
    int skill2_index=0;

    boolean skill4_ready=false;
    boolean under_cover=false;
    int skill4_index=0;

    int shieldindex=0;//每5s才能发一次盾牌技能
    public void defenceAction(){
        shieldindex++;
   //     System.err.printf("[O]shieldindex:%d\n", shieldindex);
        if(skill2_ready==true&&shieldindex>=500){
            skill2_index++;
 //           System.err.printf("shieldindex:%d, skill2_idx:%d\n", shieldindex, skill2_index);
            if(skill2_index%300==0){
                under_defence=false;
                skill2_ready=false;
                shieldindex=0;
            }
            else under_defence=true;
        }

        if(skill3_ready==true){
            skill3_index++;
            if(skill3_index%300==0){
                freezing=false;
                skill3_ready=false;
            }
            else {
                freezing=true;
            }
        }
        if(skill4_ready==true){
            skill4_index++;
            if(skill4_index%200==0){
                skill4_ready=false;
                under_cover=false;
            }
            else under_cover=true;
        }
    }

    boolean ifshoot=false;
    boolean skill1_ready=false;
    public void shootAction(){
        if(ifshoot){//主人公点一下右键发一颗子弹
            Bullet[] bs=myhero.shoot();
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
                    bs.length); // 追加数组
            ifshoot=false;
        }
        for(int i=0;i<bullets.length;i++){
            bullets[i].step();
        }

        //怪物一直放火球
        if(monsterindex%150==0&&monsterindex>=200&&monsters.length > 0) {
            Fire[] fs = monsters[monsters.length - 1].shoot(myhero);
            fires = Arrays.copyOf(fires, fires.length + fs.length);
            System.arraycopy(fs, 0, fires, fires.length - fs.length, fs.length);
        }
        if(!freezing){
            for (int i = 0; i < fires.length; i++) {
                fires[i].step();
            }
        }

        //主人公发1技能导弹
        if(skill1_ready){
            Missile[] ms=new Missile[1];
            ms[0]=new Missile(myhero,myboss);
            missiles=Arrays.copyOf(missiles,missiles.length+ms.length);
            System.arraycopy(ms,0,missiles,missiles.length-ms.length,ms.length);
            skill1_ready=false;
        }
        for(int i=0;i<missiles.length;i++){
            missiles[i].step();
        }
    }

    //碰撞检测
    public void bangAction(){
        for(int i=0;i<bullets.length;i++){//检查子弹是否碰到怪和水果
            Bullet b=bullets[i];
            if(bangmonster(b)||bangfruit(b)||bangboss(b)){
                Bullet tmp=bullets[i];
                bullets[i]=bullets[bullets.length-1];
                bullets[bullets.length-1]=tmp;
                bullets=Arrays.copyOf(bullets,bullets.length-1);
                break;//把打到怪和水果的子弹删了
            }
        }
        //检测主人公是否碰到怪发出的技能
        for(int i=0;i<fires.length;i++){
            Fire fi=fires[i];
            if(banghero(fi)){//击中了英雄
                Fire tmp=fires[fires.length-1];
                fires[fires.length-1]=fi;
                fires[i]=tmp;
                fires=Arrays.copyOf(fires,fires.length-1);
            }
        }

        //检测boss是否碰到主人公4技能
        for(int i=0;i<missiles.length;i++){
            Missile mi=missiles[i];
            if(bangboss(mi)){
                Missile tmp=missiles[i];
                missiles[i]=missiles[missiles.length-1];
                missiles[missiles.length-1]=tmp;
                missiles=Arrays.copyOf(missiles,missiles.length-1);
                break;
            }
        }

        //检测boss和monster是否碰到主人公4技能
        if(skill4_ready==true){
            if(bangboss(myheart)){
                score+=30;
            }
            for(int i=0;i<monsters.length;i++){
                if(bangmonster(myheart)){
                    score+=20;
                }
            }
        }

    }
    public void outOfBoundsAction(){
        int index=0;
        Monster[] monsterlives=new Monster[monsters.length];
        for(int i=0;i<monsters.length;i++){
            Monster mon=monsters[i];
            if(!mon.outOfBound()){
                monsterlives[index++]=mon;
            }
        }
        monsters=Arrays.copyOf(monsterlives,index);
        index=0;
        Bullet[] bulletexits=new Bullet[bullets.length];
        for(int i=0;i<bullets.length;i++){
            Bullet bul=bullets[i];
            if(!bul.outOfBounds()){
                bulletexits[index++]=bul;
            }
        }
        bullets=Arrays.copyOf(bulletexits,index);
        index=0;
        Missile[] missileexits=new Missile[missiles.length];
        for(int i=0;i<missiles.length;i++){
            Missile mis=missiles[i];
            if(!mis.outOfBounds()){
                missileexits[index++]=mis;
            }
        }
        missiles=Arrays.copyOf(missileexits,index);
        index=0;
        Fire[] fireexits=new Fire[fires.length];
        for(int i=0;i<fires.length;i++){
            Fire fi=fires[i];
            if(!fi.outOfBounds()){
                fireexits[index++]=fi;
            }
        }
        fires=Arrays.copyOf(fireexits,index);
    }

    public void checkGameOverAction(){
        if(myhero.getLife()<=0){
            state=GAME_OVER;
        }
        if(myboss.getBlood()<=0){
            state=WIN;
        }
    }

    public boolean bangmonster(Bullet bullet){//现在只看怪有没有被打到，不看水果
        int index=-1;
        for (int i=0;i<monsters.length;i++){//之后要写通过边界检查删掉出界monster！！！
            Monster obj=monsters[i];
            if(obj.shootBy(bullet)){//判断是否击中
                index=i;
                break;
            }
        }
        if(index!=-1){//有击中怪
            monsters=Arrays.copyOf(monsters,monsters.length-1);//删掉这个怪
            score+=20;//打中怪加20分
            return true;
        }
        return false;
    }

    public boolean bangmonster(Heart heart){
        int index=-1;
        for (int i=0;i<monsters.length;i++){//之后要写通过边界检查删掉出界monster！！！
            Monster obj=monsters[i];
            if(obj.attackBy(heart)){//判断是否击中
                index=i;
                break;
            }
        }
        if(index!=-1){//有击中怪
            monsters=Arrays.copyOf(monsters,monsters.length-1);//删掉这个怪
            score+=20;//打中怪加20分
            return true;
        }
        return false;
    }

    public boolean bangboss(Missile missile){
        if(myboss.shootBy(missile)){
            myboss.lose_blood(20);
            return true;
        }
        return false;
    }
    public boolean bangboss(Heart heart){
        if(myboss.attackBy(heart)){
            myboss.lose_blood(15);
            return true;
        }
        return false;
    }

    public boolean bangboss(Bullet bullet){
        if(myboss.shootBy(bullet)){
            myboss.lose_blood(3);
            return true;
        }
        return false;
    }

    public boolean bangfruit(Bullet bullet){
        int index=-1;
        for(int i=0;i<fruits.length;i++){
            Fruit fruit=fruits[i];
            if(fruit.shootBy(bullet)){
                index=i;
                break;
            }
        }
        if(index!=-1){
            Fruit tmp=fruits[index];
            fruits[index]=fruits[fruits.length-1];
            fruits[fruits.length-1]=tmp;

            fruits=Arrays.copyOf(fruits,fruits.length-1);
            myhero.addLife();
            return true;
        }
        return false;
    }

    public boolean  banghero(Fire fire){//检查火球有没有撞上主人公,扣血
        if(myhero.shootBy(fire)&&!under_defence){
            myhero.deleLife();
            return true;
        }
        else if(myhero.shootBy(fire))return true;
        return false;
    }



}
