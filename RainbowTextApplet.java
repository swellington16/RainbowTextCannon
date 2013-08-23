import java.awt.*;
import javax.swing.*;


public class RainbowTextApplet extends JApplet
{
    
    public void init()
    {
        // this is a workaround for a security conflict with some browsers
        // including some versions of Netscape & Internet Explorer which do 
        // not allow access to the AWT system event queue which JApplets do 
        // on startup to check access. May not be necessary with your browser. 
        JRootPane rootPane = this.getRootPane();    
        rootPane.putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
    
        //embeds main application
        this.add(new RainbowTextCannon());
        
    }

}
