import jdk.nashorn.internal.scripts.JO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.Properties;

public class Main implements ActionListener {
    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;
    static URL icon = null;
    //main frame
    static JFrame frame = new JFrame("Messager");
    static JLabel text = new JLabel();
    static JEditorPane jEditorPane = new JEditorPane();
    static JButton jButton = new JButton("Отправить");
    static JScrollPane jsp = new JScrollPane(text);
    //sys name and pas
    static String pas = "";
    static String name = "";
    //for connection
    static String password = "";
    static String username = "";
    static String urll = "";
    //regframe
    static JPanel namePanel = new JPanel();
    static JPanel regpanel = new JPanel();
    static JPanel passPanel = new JPanel();
    static JLabel n = new JLabel("Имя:");
    static JLabel p = new JLabel("Пароль:");
    static JTextField jname = new JTextField(20);
    static JPasswordField jpass = new JPasswordField(20);
    static JButton accept = new JButton("Ввести");
    static JFrame regFrame = new JFrame("Messager");
    static KeyListener regKey = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                checkReg();
            }
        }
    };
    //join frame
    static JPanel namePanel1 = new JPanel();
    static JPanel regpanel1 = new JPanel();
    static JPanel passPanel1 = new JPanel();
    static JLabel n1 = new JLabel("Имя:");
    static JLabel p1 = new JLabel("Пароль:");
    static JTextField jname1 = new JTextField(20);
    private static JPasswordField jpass1 = new JPasswordField(20);
    static JButton accept1 = new JButton("Ввести");
    static JFrame regFrame1 = new JFrame("Messager");
    static KeyListener joinKey = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                checkJoin();
            }
        }
    };


    private static void in() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        try {
            icon = new URL("http://logick.eu5.org/icon.png");
        }catch (Exception e){new JErrorFrame(e.getMessage());}
        try {

            URL user = new URL("http://logick.eu5.org/username.html");
            URL pass = new URL("http://logick.eu5.org/password.html");
            URL url = new URL("http://logick.eu5.org/url.html");
            URLConnection userConnect = user.openConnection();
            URLConnection passConnect = pass.openConnection();
            URLConnection urlConnect = url.openConnection();
            userConnect.connect();
            passConnect.connect();
            urlConnect.connect();
            BufferedReader userRead = new BufferedReader(new InputStreamReader(userConnect.getInputStream()));
            BufferedReader passRead = new BufferedReader(new InputStreamReader(passConnect.getInputStream()));
            BufferedReader urlRead = new BufferedReader(new InputStreamReader(urlConnect.getInputStream()));
            userRead.readLine();
            passRead.readLine();
            urlRead.readLine();
            username = userRead.readLine();
            password = passRead.readLine();
            urll = urlRead.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(urll, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `mess`");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            frame.setIconImage(ImageIO.read(icon));
        }catch (Exception e){new JErrorFrame(e.getMessage());}
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        //System.out.println(icon.getFile());
        in();
        join();
        new Thread(new Runnable() {
            Thread t = new Thread(new test());

            @Override
            public void run() {
                t.start();
                while (true) {
                    try {
                        t.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        statement = connection.createStatement();
                        resultSet = statement.executeQuery("SELECT * FROM `mess`");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    String texttext = "<html>";

                    try {
                        while (resultSet.next()) {
                            try {
                                texttext += "  " + resultSet.getString("name") + ": " + resultSet.getString("text") + "<br>";
                            } catch (java.lang.NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                        texttext += "<br><html/>";
                        text.setText(texttext);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    frame.repaint();
                }
            }
        }).start();
    }

    private static void reg() {
        regFrame = new JFrame("Регистрация");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        namePanel = new JPanel();
        passPanel = new JPanel();
        regpanel = new JPanel();
        regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regFrame.setResizable(false);
        regFrame.setLocationRelativeTo(null);
        regpanel.setLayout(new GridLayout(3, 1));
        accept.addActionListener(new Main());
        accept.setActionCommand("reg");
        jname.addKeyListener(regKey);
        jpass.addKeyListener(regKey);
        namePanel.add(n);
        namePanel.add(jname);
        passPanel.add(p);
        passPanel.add(jpass);
        regpanel.add(namePanel);
        regpanel.add(passPanel);
        regpanel.add(accept);
        regFrame.add(regpanel);
        regFrame.repaint();
        regFrame.pack();
        try {
            regFrame.setIconImage(ImageIO.read(icon));
        }catch (Exception e){new JErrorFrame(e.getMessage());}
        regFrame.setVisible(true);
        regFrame.setAlwaysOnTop(true);
        regFrame.setAlwaysOnTop(false);
    }

    private static void join() {
        JButton reg = new JButton("Зарегистрироваться");
        reg.addActionListener(new Main());
        reg.setActionCommand("regstart");
        regFrame1.setVisible(false);
        regFrame1 = new JFrame("Вход");
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        namePanel1 = new JPanel();
        passPanel1 = new JPanel();
        regpanel1 = new JPanel();
        regFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regFrame1.setResizable(false);
        regFrame1.setLocationRelativeTo(null);
        regpanel1.setLayout(new GridLayout(4, 1));
        accept1.addActionListener(new Main());
        accept1.setActionCommand("join");
        jname1.addKeyListener(joinKey);
        jpass1.addKeyListener(joinKey);
        namePanel1.add(n1);
        namePanel1.add(jname1);
        passPanel1.add(p1);
        passPanel1.add(jpass1);
        regpanel1.add(namePanel1);
        regpanel1.add(passPanel1);
        regpanel1.add(accept1);
        regpanel1.add(reg);
        regFrame1.add(regpanel1);
        regFrame1.repaint();
        regFrame1.pack();
        try {
            regFrame1.setIconImage(ImageIO.read(icon));
        }catch (Exception e){new JErrorFrame(e.getMessage());}
        regFrame1.setVisible(true);
        regFrame1.setAlwaysOnTop(true);
        regFrame1.setAlwaysOnTop(false);
    }

    private static void mainLoad() {
        jEditorPane.setPreferredSize(new Dimension(200, 80));
        jEditorPane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        String editor = jEditorPane.getText();
                        editor = editor.replaceAll(System.lineSeparator(), "");
                        if (editor.equals("")) {
                            jEditorPane.setText("");
                            return;
                        }
                        statement = connection.createStatement();
                        statement.execute("insert into mess (name,text) values('" + name + "','" + editor + "');");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    jEditorPane.setText("");
                    jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
                }
            }
        });
        JPanel jp = new JPanel();
        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        JPanel all = new JPanel();
        jButton.addActionListener(new Main());
        jButton.setActionCommand("send");
        jp.add(jScrollPane);
        jp.add(jButton);
        jsp.setPreferredSize(new Dimension(380, 360));
        all.add(jsp);
        all.add(jp);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frame.add(all);
        frame.setSize(400, 500);
        frame.repaint();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("send")) {
            jEditorPane.requestFocus();
            String editor = jEditorPane.getText();
            editor = editor.replaceAll(System.lineSeparator(), "");
            if (editor.equals("")) {
                jEditorPane.setText("");
                return;
            }
            try {
                statement = connection.createStatement();
                statement.execute("insert into mess (name,text) values('" + name + "','" + editor + "');");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            jEditorPane.setText("");
            jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
        }
        if (e.getActionCommand().equals("reg")) {
            checkReg();
        }
        if (e.getActionCommand().equals("join")) {
            checkJoin();
        }
        if (e.getActionCommand().equals("regstart")) {
            regFrame1.setVisible(false);
            reg();
        }

    }

    private static void checkJoin() {
        name = jname1.getText();
        pas = String.copyValueOf(jpass1.getPassword());
        if (name.equals("") || password.equals("")) {
            name = "";
            pas = "";
            join();
            return;
        }
        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from users;");
            System.out.println("1");
            while (res.next()) {
                if (name.equals(res.getString("name"))) {
                    if (pas.equals(res.getString("pass"))) {
                        regFrame1.setVisible(false);
                        mainLoad();
                        return;
                    }
                    name = "";
                    pas = "";
                    jname1 = new JTextField(20);
                    jpass1 = new JPasswordField(20);
                    join();
                    return;
                }
            }
            System.out.println("2");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        name = "";
        pas = "";
        join();
    }

    private static void checkReg() {
        name = jname.getText();
        pas = String.copyValueOf(jpass.getPassword());
        if (name.equals("") || pas.equals("")) {
            regFrame.setVisible(false);
            jname = new JTextField(20);
            jpass = new JPasswordField(20);
            name = "";
            pas = "";
            reg();
            return;
        }
        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select name from users;");
            System.out.println("1");
            while (res.next()) {
                if (name.equals(res.getString("name"))) {
                    regFrame.setVisible(false);
                    jname = new JTextField(20);
                    jpass = new JPasswordField(20);
                    reg();
                    name = "";
                    pas = "";
                    return;
                }
            }
            System.out.println("2");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            statement = connection.createStatement();
            statement.execute("insert into users (name, pass) values('" + name + "','" + pas + "');");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        regFrame.setVisible(false);
        //regFrame = null;
        mainLoad();
    }
}

class test implements Runnable {
    @Override
    public void run() {

    }
}
//getClass().getResource("/resource/icon.png")
// insert into users (name, pass) values('admin','asdaps615q');