import javax.swing.JPanel;

public class SnakePanel extends JPanel
{
    private int length;
    private boolean food;
    
    public SnakePanel()
    {
        length = 0;
        food = false;
    }
    
    public void iterate()
    {
        length--;
    }
    
    public int getLength()
    {
        return length;
    }
    
    public void setLength(int len)
    {
        length = len;
    }
    
    public void setFood(boolean f)
    {
        food = f;
    }
    
    public boolean isFood()
    {
        return food;
    }
}
