import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class RainbowTextCannon extends JPanel
{
    JTextField cannon_loader; //The textfield in which you enter the text to colourize
    JButton cannon_launcher; //The submit button
    JTextPane impact_zone; //The text area that displays a "preview" of the coloured text
    JTextArea clipboard; //The text area the displays the vBulletin colour-coded text to copy-paste to Pojo
    JLabel loader_label, zone_label, forum_label; //a bunch of labels
    StyledDocument doc;
    JPanel loader_panel,preview_panel,forum_panel;
    
    //HSV values
    final float RAINBOW_INIT = 0.0f; //minimum hue
    final float RAINBOW_END = 1.0f; //maximum hue
    final float SATURATION = 1.0f; //saturation
    final float VALUE = 1.0f; //value
    
    final double RAINBOW_INC = 0.005f; //Value by which rainbow hue is incremented with each character


    public RainbowTextCannon()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //this.setPreferredSize(new Dimension(300,300));
        
        loader_panel = new JPanel();
        preview_panel = new JPanel();
        forum_panel = new JPanel();
        loader_panel.setSize(100,100);
        //loader_panel.setLayout(new BoxLayout(loader_panel, BoxLayout.X_AXIS));
        //forum_panel.setLayout(new GridLayout(2,1));
        
        cannon_loader = new JTextField(10);
        cannon_launcher = new JButton("Fire Skittles Text");
        loader_label = new JLabel("Load the cannon with the text here:");
        zone_label = new JLabel("Preview: ");
        forum_label = new JLabel("Copy-paste this text to Pojo: ");
        doc = (StyledDocument) new DefaultStyledDocument();
        impact_zone = new JTextPane(doc);
        clipboard = new JTextArea(20,50);
        
        this.add(loader_panel);
        loader_panel.add(loader_label);
        loader_panel.add(cannon_loader);
        loader_panel.add(cannon_launcher);
        
        this.add(preview_panel);
        preview_panel.add(zone_label);
        preview_panel.add(impact_zone);
        
        this.add(forum_panel);
        forum_panel.add(forum_label);
        forum_panel.add(clipboard);
        
        clipboard.setLineWrap(true); 
        clipboard.setWrapStyleWord(true);
        clipboard.setPreferredSize(new Dimension(50,50));
        clipboard.setSize(clipboard.getPreferredSize());
        cannon_launcher.addActionListener(new CannonListener());
    }
    
    
    //Converts the color to its corresponding hexcode
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
        
        //System.out.println("("+red+","+green+","+blue+")"); //debugging
        String hexcode = "#"+red+green+blue;
        //System.out.println(hexcode+"\n"); //debugging
        return hexcode;
    }
    
    //Converts the color to its corresponding vBulletin tags
    public String getColored(String str, Color color)
    {
        String result;
        String hexcode = toHex(color);
        result = "[color=\""+hexcode+"\"]"+str+"[/color]";
        return result;
    }

    private class CannonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent ae) //Event when button is clicked
        {
            clipboard.setText(""); //Refresh vBulletin text area
            impact_zone.setText(cannon_loader.getText()); //
            String str = impact_zone.getText();
            float index = RAINBOW_INIT;
            for(int i = 0; i < str.length(); i++)
            {
                String x = Character.toString(str.charAt(i));
                if(!(x.equals(" "))) //Accounts for spaces
                {
                    float ind = index % RAINBOW_END;
                    Color hue = Color.getHSBColor(ind,SATURATION,VALUE); //Get colour from specific hue
                    String coloredChar = getColored(x,hue); //Wrap character with vBulletin tags for current hue
                    clipboard.setText(clipboard.getText() + coloredChar); //Append wrapped character to vBulletin text
                    
                    //Generate and display coloured version of each character in the JTextPane
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
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cannon);
        frame.setVisible(true);

    }
}
