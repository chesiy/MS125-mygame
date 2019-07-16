import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    private Bullet[] bullets ={};//子弹数组
    private Hero myhero =new Hero();//主人公
    private Monster[] monsters={};//怪物数组
    private Fruit[] fruits={};//水果数组

    static {
        try {
            background=ImageIO.read(Main.class.getResource("background.JPG"));
         //   start=ImageIO.read(Main.class.getResource("start.JPG"));
            monster=ImageIO.read(Main.class.getResource("monster.png"));
            fruit=ImageIO.read(Main.class.getResource("fruit.png"));
            bullet=ImageIO.read(Main.class.getResource("bullet.png"));
            hero0=ImageIO.read(Main.class.getResource("hero.JPG"));
         //   hero1=ImageIO.read(Main.class.getResource("hero1.JPG"));
            pause=ImageIO.read(Main.class.getResource("pause.png"));
            gameover=ImageIO.read(Main.class.getResource("gameover.png"));


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
        paintScore(g); // 画分数
        paintState(g); // 画游戏状态
    }
    /** 画主人公 */
    public void paintHero(Graphics g) {
        g.drawImage(myhero.getImage(), myhero.getX(), myhero.getY(), null);
    }

    /** 画子弹 */
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
                    null);
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
            g.drawImage(fru.getImage(),fru.getX(),fru.getY(),null);
        }
    }

    /** 画分数 */
    public void paintScore(Graphics g) {
        int x = 10; // x坐标
        int y = 25; // y坐标
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // 字体
        g.setColor(new Color(0xFF3D36));
        g.setFont(font); // 设置字体
        g.drawString("SCORE:" + score, x, y); // 画分数
        y=y+20; // y坐标增20
        g.drawString("LIFE:" + myhero.getLife(), x, y); // 画命
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
            public void mouseClicked(MouseEvent e) { // 鼠标点击
                switch (state) {
                    case START:
                        state = RUNNING; // 启动状态下运行
                        break;
                    case RUNNING://运行状态下鼠标点哪里，主人公移到哪里
                        if(e.getButton()==e.BUTTON1) {//左键移动主人公
                            int x = e.getX();
                            int y = e.getY();
                            //update()
                            myhero.moveTo(x, y);
                        }
                        if(e.getButton()==e.BUTTON3){//右键发子弹
                            int x=e.getX();
                            int y=e.getY();
                            update();
                        }
                        break;
                    case GAME_OVER: // 游戏结束，清理现场
                        monsters= new Monster[0]; // 清空飞行物
                        bullets = new Bullet[0]; // 清空子弹
                        myhero = new Hero(); // 重新创建主人公
                        fruits =new Fruit[0];//清空水果
                        score = 0; // 清空成绩
                        state = START; // 状态设置为启动
                        break;
                }
            }
        };
        this.addMouseListener(l); // 处理鼠标点击操作
        this.addMouseMotionListener(l); // 处理鼠标滑动操作

        timer=new Timer();//主流程控制
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(state==RUNNING){
                    enterAction(); // 怪兽入场,水果入场
                    stepAction(); // 走一步
                    bangAction(); // 子弹打飞行物
                    outOfBoundsAction(); // 删除越界飞行物及子弹
                    checkGameOverAction(); // 检查游戏结束
                }
                repaint();
            }
        },intervel,intervel);
    }

    int monsterindex=0;//怪物入场计数(计时）
    int fruitnum=0;//水果入场计数

    //怪物过5s，入场一次
    public void enterAction(){
        monsterindex++;
        if(monsterindex%500==0){//5s 生成两个怪物
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
        for(int i=0;i<fruits.length;i++){
            Fruit fru=fruits[i];
            fru.step();
        }
    }

    int shootIndex =0;
    public void shootTo(int x,int y){//点一下右键发五颗子弹
        if(shootIndex<5*30){
            shootIndex++;
            if(shootIndex%30==0){//300ms发一颗
                Bullet[] bu=myhero.shoot();//主人公打出子弹
                bullets=Arrays.copyOf(bullets,bullets.length+bu.length);//扩容
                System.arraycopy(bu,0,bullets,bullets.length-bu.length,bu.length);//追加数组
            }
        }
    }
    public void bangAction(){

    }
    public void outOfBoundsAction(){

    }

    public void checkGameOverAction(){

    }


}
