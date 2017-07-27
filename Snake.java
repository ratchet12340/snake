import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;

public class Snake extends JFrame implements KeyListener, Runnable
{
   private JPanel master, game, menu;

   private SnakePanel[][] panels = new SnakePanel[40][40];
   private volatile Thread thread;
   private Random rand;

   private char dir;
   private int snakex;
   private int snakey;
   private int length;

   public Snake()
   {
        this.setSize(400, 400);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setTitle("SNAKE!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        master = new JPanel(new CardLayout());
        game = new JPanel(new GridLayout(40, 40));
        menu = new JPanel();
        menu.setBackground(Color.cyan);

        // REMINDER: GO OVER THIS TODAY!!!!
        for(int row = 0; row < 40; row++)
        {
            for(int col = 0; col < 40; col++)
            {
                panels[col][row] = new SnakePanel();
                if(row == 0 || row == 39 || col == 0 || col == 39)
                {
                    panels[col][row].setBackground(Color.black);
                }
                else
                {
                    panels[col][row].setBackground(Color.yellow);
                }
                game.add(panels[col][row]);
            }
        }

        // snake stuff
        panels[20][20].setBackground(Color.black);
        panels[20][20].setLength(1);
        panels[21][20].setBackground(Color.black);
        panels[21][20].setLength(2);
        panels[22][20].setBackground(Color.black);
        panels[22][20].setLength(3);
        dir = 'r';
        snakex = 22;
        snakey = 20;
        length = 3;

        // food stuff
        rand = new Random();
        generateFood();

        // panel stuff

        master.add(menu, "2");
        master.add(game, "1");
        CardLayout cardlay = (CardLayout)(master.getLayout());
        cardlay.show(master, "1");
        this.add(master);
        // thread stuff
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();

        this.setVisible(true);
   }

   public void generateFood()
   {
       int foodx, foody;
       do
       {
           foodx = 0 + rand.nextInt(39);
           foody = 0 + rand.nextInt(39);
       }
       while(panels[foodx][foody].getLength() > 0);
       panels[foodx][foody].setFood(true);
   }

   public void moveSnake()
   {
       if(dir == 'u')
       {
           snakey--;
       }
       else if(dir == 'd')
       {
           snakey++;
       }
       else if(dir == 'l')
       {
           snakex--;
       }
       else if(dir == 'r')
       {
           snakex++;
       }

       System.out.println(snakex + ", " + snakey);
       if(snakex == 0 || snakex == 39 || snakey == 0 || snakey == 39 || panels[snakex][snakey].getLength() > 1)
       {
           stop();
       }
       else if(panels[snakex][snakey].isFood())
       {
           length++;
           panels[snakex][snakey].setFood(false);
           generateFood();
       }
       panels[snakex][snakey].setLength(length + 1);
   }

   public void stop()
   {
     thread = null;
     CardLayout cardlay = (CardLayout)(master.getLayout());
     cardlay.show(master, "2");
   }

   public void loopSnake()
   {
       for(int row = 1; row < 39; row++)
       {
           for(int col = 1; col < 39; col++)
           {
               panels[col][row].iterate();
               if(panels[col][row].getLength() > 0)
               {
                   panels[col][row].setBackground(Color.black);
               }
               else if(panels[col][row].isFood())
               {
                   panels[col][row].setBackground(Color.red);
               }
               else
               {
                   panels[col][row].setBackground(Color.yellow);
               }
           }
       }
   }

   public void keyPressed(KeyEvent e)
   {
       int keyCode = e.getKeyCode();

       switch(keyCode)
       {
           case KeyEvent.VK_DOWN:
                if(dir != 'u')
                {
                    dir = 'd';
                }
                break;
           case KeyEvent.VK_UP:
                if(dir != 'd')
                {
                    dir = 'u';
                }
                break;
           case KeyEvent.VK_LEFT:
                if(dir != 'r')
                {
                    dir = 'l';
                }
                break;
           case KeyEvent.VK_RIGHT:
                if(dir != 'l')
                {
                    dir = 'r';
                }
                break;
       }
   }

   public void keyReleased(KeyEvent e)
   {

   }

   public void keyTyped(KeyEvent e1)
   {

   }

   public void run()
   {
       Thread thisThread = Thread.currentThread();
       while(thread == thisThread)
       {
           try
           {
               Thread.sleep(100);
               moveSnake();
               loopSnake();
           }
           catch(InterruptedException e)
           {
               System.out.println("Could not sleep the thread! :(((");
           }
       }
    }
}
