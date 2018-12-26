import javax.swing.*;
import java.awt.*;

public class JErrorFrame extends JFrame {
    public JErrorFrame(String text){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        JLabel jl = new JLabel(text);
        jl.setFont(new Font("Verdana",Font.PLAIN,15));
        add(jl);
        pack();
        setAutoRequestFocus(true);
        setVisible(true);
        setAlwaysOnTop(true);

    }
}
