import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
public class Project extends JPanel
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("Treat Color");
    Project content = new Project();
    frame.setContentPane(content);
    frame.setLayout(null);
    frame.setSize(400,800);
    frame.setResizable(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  private Timer timer;
  private int width,height;
  private int highScore=0;
  private int checkYBar=0;
  private Player player;
  private ArrayList<Point> points = new ArrayList<>();
  private BarrierColor[] barColors = new BarrierColor[12];
  private BarrierBlack barBlack;
  private BlackGround ground,ground2;
  private Score score;
  private Icon gameOver,bgOver,gameStart,fwR,fwB,fwY,fwG,trophy1,trophy2,zzzz;
  private Icon bgHighScore,btColorStart,btBlackStart,pBlackStart,pColorStart;
  private Icon[] playerI=new Icon[4];
  private Color[] colors=new Color[4];
  private Random rand = new Random();
  private JButton btNewGame = new JButton();
  private boolean checkGame=false;
  private boolean checkResults=true;
  private boolean checkButton=false;
  public Project(){
    playerI[0]=new ImageIcon("C:/Users/DELL/Desktop/Pro/playerR.gif");
    playerI[1]=new ImageIcon("C:/Users/DELL/Desktop/Pro/playerB.gif");
    playerI[2]=new ImageIcon("C:/Users/DELL/Desktop/Pro/playerY.gif");
    playerI[3]=new ImageIcon("C:/Users/DELL/Desktop/Pro/playerG.gif");
    gameOver=new ImageIcon("C:/Users/DELL/Desktop/Pro/gameover.gif");
    gameStart=new ImageIcon("C:/Users/DELL/Desktop/Pro/TreatColor.gif");
    fwR=new ImageIcon("C:/Users/DELL/Desktop/Pro/fireworksR.gif");
    fwB=new ImageIcon("C:/Users/DELL/Desktop/Pro/fireworksB.gif");
    fwY=new ImageIcon("C:/Users/DELL/Desktop/Pro/fireworksY.gif");
    fwG=new ImageIcon("C:/Users/DELL/Desktop/Pro/fireworksG.gif");
    trophy1=new ImageIcon("C:/Users/DELL/Desktop/Pro/Trophy.gif");
    trophy2=new ImageIcon("C:/Users/DELL/Desktop/Pro/Trophy.gif");
    bgHighScore=new ImageIcon("C:/Users/DELL/Desktop/Pro/bgHighScore.gif");
    bgOver=new ImageIcon("C:/Users/DELL/Desktop/Pro/BGOver.png");
    btBlackStart=new ImageIcon("C:/Users/DELL/Desktop/Pro/btBlackStart.gif");
    btColorStart=new ImageIcon("C:/Users/DELL/Desktop/Pro/btColorStart.gif");
    pBlackStart=new ImageIcon("C:/Users/DELL/Desktop/Pro/pBlackStart.gif");
    pColorStart=new ImageIcon("C:/Users/DELL/Desktop/Pro/pColorStart.gif");
    zzzz=new ImageIcon("C:/Users/DELL/Desktop/Pro/ZZZZ.gif");
    colors[0]=new Color(228,72,57);
    colors[1]=new Color(79,122,190);
    colors[2]=new Color(255,190,58);
    colors[3]=new Color(92,165,73);
    ground = new BlackGround(0);
    ground2 = new BlackGround(-height);
    btNewGame.setIcon(btBlackStart);
    btNewGame.setBounds(80,570,230,130);
    btNewGame.addMouseListener(new MouseAdapter(){
      public void mousePressed (MouseEvent event){
        checkYBar=0;
        for(int j=0;j<barColors.length;j++){
        barColors[j]=new BarrierColor();
        }
        player= new Player();
        ground = new BlackGround(0);
        ground2 = new BlackGround(-height);
        barBlack = new BarrierBlack();
        score = new Score();
        checkGame=true;
        checkButton=false;
        btNewGame.setIcon(btBlackStart);
        points = new ArrayList<>();
        points.add(new Point(80+event.getX(),570+event.getY()));
        remove(btNewGame);
        timer.start();
      }
      public void mouseEntered(MouseEvent event){
        checkButton=true;
        btNewGame.setIcon(btColorStart);
        repaint();
      }
      public void mouseExited(MouseEvent event){
        checkButton=false;
        btNewGame.setIcon(btBlackStart);
        repaint();
      }
    });
    add(btNewGame);
    addMouseMotionListener(new MouseMotionAdapter(){
      public void mouseMoved(MouseEvent event){
        if(checkGame){
          points.add(event.getPoint());
          repaint();
        }
      }
    });
    addMouseListener( new MouseAdapter() {
      public void mousePressed(MouseEvent evt) {
        if(checkGame){
          if(player.orderColor==3)
            player.orderColor=0;
          else
            player.orderColor++;
          score.color=colors[player.orderColor];
        }
      }
    });
    ActionListener action = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        if (checkGame) {
          for(int j=0;j<barColors.length;j++){
            barColors[j].updateForNewFrame();
          }
          barBlack.updateForNewFrame();
          ground.updateForNewFrame();
          ground2.updateForNewFrame();
          score.updateForNewFrame();
        }
        repaint();
      }
    };
    timer = new Timer( 30, action );
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    width=getWidth();
    height=getHeight();
    ground.draw(g);
    ground2.draw(g);
    if(checkGame){
      for(int i=0;i<points.size();i++){
        if(i==points.size()-1){
          player.centerX=points.get(i).x;
          player.centerY=points.get(i).y;
          player.draw(g);
          for(int j=0;j<barColors.length;j++){
            barColors[j].draw(g);
          }
          barBlack.draw(g);
          score.draw(g);
          g.drawString("HighScore "+Integer.toString(highScore),5,20);

        }
      }
    }else if(checkResults){
        gameStart.paintIcon(this,g,-3,50);
        if(checkButton){
          pColorStart.paintIcon(this,g,120,530);
        }else{
          zzzz.paintIcon(this,g,120,515);
          pBlackStart.paintIcon(this,g,120,510);
        }
    }else{
        player.draw(g);
        for(int j=0;j<barColors.length;j++){
          barColors[j].draw(g);
        }
        barBlack.draw(g);
        bgOver.paintIcon(this,g,0,0);
        gameOver.paintIcon(this,g,15,150);
        score.font=new Font("Haettenschweiler",Font.BOLD,60);
        score.centerX=score.centerXOver;
        score.centerY=400;
        score.color=Color.BLACK;
        if(score.score>highScore){
          fwR.paintIcon(this,g,-30,300);
          fwB.paintIcon(this,g,70,300);
          fwY.paintIcon(this,g,170,300);
          fwG.paintIcon(this,g,270,300);
          trophy1.paintIcon(this,g,39,300);
          trophy2.paintIcon(this,g,288,300);
          bgHighScore.paintIcon(this,g,-13,330);
          highScore=(int)score.score;
        }
        score.draw(g);
        if(checkButton){
          pColorStart.paintIcon(this,g,120,530);
        }else{
          zzzz.paintIcon(this,g,120,515);
          pBlackStart.paintIcon(this,g,120,510);
        }
    }
  }
  private class Player
  {
    int orderColor,centerX, centerY;
    Icon character;
    Player(){
      orderColor=0;
      character=playerI[orderColor];
    }
    void draw(Graphics g) {
      character=playerI[orderColor];
      character.paintIcon(null,g,centerX-15,centerY-17);
    }
  }
  private class BarrierColor {
    int centerX, centerY;
    boolean isMovingLeft;
    int checkColor;
    Color color;
    BarrierColor() {
      centerX = (int)(width*Math.random());
      centerY = checkYBar-30-(int)(100*Math.random());
      checkYBar=centerY;
      isMovingLeft = (Math.random() < 0.5);
      checkColor=rand.nextInt(4);
      color=colors[checkColor];
    }
    void updateForNewFrame() {
        if (isMovingLeft) {
          centerX -= 10;
          if (centerX <= 0) {
            centerX = 0;
            isMovingLeft = false;
          }
        }
        else {
          centerX += 10;
          if (centerX > width) {
            centerX = width;
            isMovingLeft = true;
          }
        }
        centerY+=10;
        if(centerY-16>height){
          checkYBar=0;
          for(int j=0;j<barColors.length;j++){
            if(barColors[j].centerY<checkYBar)
              checkYBar=barColors[j].centerY;
          }
          if(barBlack.centerY<checkYBar)
            checkYBar=barBlack.centerY;
          checkColor=rand.nextInt(4);
          color=colors[checkColor];
          centerY=checkYBar-30-(int)(100*Math.random());
        }
        if(Math.abs(player.centerX - this.centerX)<=43 &&
              Math.abs(player.centerY - this.centerY)<=28 ) {
                if(checkColor==player.orderColor){
                  checkYBar=0;
                  for(int j=0;j<barColors.length;j++){
                    if(barColors[j].centerY<checkYBar)
                      checkYBar=barColors[j].centerY;
                  }
                  if(barBlack.centerY<checkYBar)
                    checkYBar=barBlack.centerY;
                  checkColor=rand.nextInt(4);
                  color=colors[checkColor];
                  centerY=checkYBar-30-(int)(100*Math.random());
                  score.score+=10;
                }
                else{
                  timer.stop();
                  checkResults=false;
                  checkGame=false;
                  add(btNewGame);
                  repaint();
                }
        }

    }
    void draw(Graphics g) {
      g.setColor(color);
      g.fillRoundRect(centerX - 30, centerY - 15, 60, 30, 20, 20);
    }
  }
  private class BarrierBlack{
    int centerX, centerY;
    BarrierBlack() {
      centerX =75+(int)((width-150)*Math.random());
      centerY=checkYBar-30-(int)(100*Math.random());
    }
    void updateForNewFrame() {
        centerY+=10;
        if(centerY-20>height){
          checkYBar=0;
          for(int j=0;j<barColors.length;j++){
            if(barColors[j].centerY<checkYBar)
              checkYBar=barColors[j].centerY;
          }
          centerX =75+(int)((width-150)*Math.random());
          centerY=checkYBar-30-(int)(100*Math.random());
       }
       if(Math.abs(player.centerX - this.centerX) <= 88 &&
             Math.abs(player.centerY - this.centerY) <= 28) {
             timer.stop();
             checkResults=false;
             checkGame=false;
             add(btNewGame);
             repaint();
           }
    }
    void draw(Graphics g) {
      g.setColor(Color.BLACK);
      g.fillRoundRect(centerX-75, centerY-15, 150, 30, 10, 10);
    }
  }
  private class BlackGround
  {
    int centerX, centerY;
    Icon ibg;
    BlackGround(int y){
      centerX=0;
      centerY=y;
      ibg=new ImageIcon("C:/Users/DELL/Desktop/Pro/blackground.gif");
    }
    void updateForNewFrame() {
      centerY+=10;
      if(centerY>=height){
        centerY=-height;
      }
    }
    void draw(Graphics g) {
      ibg.paintIcon(null,g,centerX,centerY);
    }
  }
  private class Score{
    int centerX, centerY,checkXScore,centerXOver;
    double score;
    Font font=new Font("Haettenschweiler",Font.BOLD,25);
    Color color;
    Score(){
      score=0;
      centerX=350;
      centerY=20;
      checkXScore=10;
      color = colors[0];
      centerXOver=180;
    }
    void updateForNewFrame() {
        score+=0.1;
        if(score>checkXScore){
          centerX-=8;
          centerXOver-=10;
          checkXScore*=10;
        }
    }
    void draw(Graphics g) {
      g.setFont(font);
      g.setColor(color);
      g.drawString(Integer.toString((int)score),centerX,centerY);
    }
  }
}
