
/**
 * Write a description of class States here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class State
{
    private String stateName;
    private String capitalName;
    private BufferedImage stateImage;
    private boolean used;
    
    public State(String sn, String cn, BufferedImage b)
    {
        stateName = sn;
        capitalName = cn;
        stateImage = b;
        used = false;
    }
    
    public String getStateName()
    {
        return stateName;
    }
    
    public String getCapitalName()
    {
        return capitalName;
    }
    
    public BufferedImage getStateImage()
    {
        return stateImage;
    }
    
    //if question has already been used.
    public void setStatusUsed(boolean s)
    {
        used = s;
    }
    
    public boolean getStatusUsed()
    {
        return used;
    }
}
