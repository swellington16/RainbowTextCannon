import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class RainbowTextCannon extends JPanel
{
    JTextField cannon_loader;
    JButton cannon_launcher;
    JTextPane impact_zone;
    JTextArea clipboard;
    StyledDocument doc;
    final float RAINBOW_INIT = 0.0f; //minimum hue
    final float RAINBOW_END = 1.0f; //maximum hue
    final float SATURATION = 1.0f; //saturation
    final float VALUE = 1.0f; //value
    final double RAINBOW_INC = 0.005f; 

    //Color[] colors = new Color[RAINBOW];

    public RainbowTextCannon()
    {
        

        cannon_loader = new JTextField(50);
        cannon_launcher = new JButton("Fire Skittles Text");
        doc = (StyledDocument) new DefaultStyledDocument();
        impact_zone = new JTextPane(doc);
        clipboard = new JTextArea(300,300);
        this.add(cannon_loader);
        this.add(cannon_launcher);
        this.add(impact_zone);
        this.add(clipboard);

        cannon_launcher.addActionListener(new CannonListener());
    }
    
    
    
    public String toHex(Color color)
    {
        String red = Integer.toHexString(color.getRed());
        String green = Integer.toHexString(color.getGreen());
        String blue = Integer.toHexString(color.getBlue());
        
        if(red.length() < 2)
        {
            red = "0"+red;
        }
        if(blue.length() < 2)
        {
            blue = "0"+blue;
        }
        if(green.length() < 2)
        {
            green = "0"+green;
        }
        
        System.out.println("("+red+","+green+","+blue+")"); //debugging
        String hexcode = "#"+red+green+blue;
        System.out.println(hexcode+"\n"); //debugging
        return hexcode;
    }
    
    public String getColored(String str, Color color)
    {
        String result;
        String hexcode = toHex(color);
        result = "[color=\""+hexcode+"\"]"+str+"[/color]"; 
        return result;
    }

    private class CannonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            clipboard.setText("");
            impact_zone.setText(cannon_loader.getText());
            String str = impact_zone.getText();
            float index = RAINBOW_INIT;
            for(int i = 0; i < str.length(); i++)
            {
                String x = Character.toString(str.charAt(i));
                if(!(x.equals(" ")))
                {
                    float ind = index % RAINBOW_END;
                    Color hue = Color.getHSBColor(ind,SATURATION,VALUE);
                    String coloredChar = getColored(x,hue);
                    clipboard.setText(clipboard.getText() + coloredChar);
                    Style style = impact_zone.addStyle("Hue",null);
                    StyleConstants.setForeground(style,hue);
                    doc.setCharacterAttributes(i,str.length(),impact_zone.getStyle("Hue"),true);
                    index += RAINBOW_INC;
                }
                else
                {
                    clipboard.setText(clipboard.getText() + " ");
                }
            }
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        RainbowTextCannon cannon = new RainbowTextCannon();
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cannon);
        frame.setVisible(true);

    }
}
