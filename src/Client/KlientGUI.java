package Client;

import Client.Game;
import Client.KHandler;
import Client.Klient;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Stworzone przez Krzyśka dnia 2017-09-08.
 */

public class KlientGUI  extends JPanel {
    public int width=800,height=600;
    private JTextField loginField;
    private JButton loginButton;
    private JLabel loginLabel;
    private JLabel passLabel;
    private JPasswordField passField;
    public JList<String> chatField;
    public JTextField chatSend;
    public JScrollPane scrollPane;
    private Klient klient;
    private KHandler handler;
    public JFrame frame;
    public Canvas canvas;



    public KlientGUI(Klient klient,KHandler handler) {
        this.handler=handler;
        this.klient=klient;
        //construct components
        loginField = new JTextField ();
        loginButton = new JButton ("Zaloguj");
        loginLabel = new JLabel ("Login:");
        passLabel = new JLabel ("Hasło:");
        passField = new JPasswordField ();
        passField.setEchoChar('*');

        chatField= new JList<String>(new DefaultListModel<String>());
        chatSend= new JTextField ();
        scrollPane = new JScrollPane(chatField);
        loginField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    try {
                        klient.send("ACCESS|"+loginField.getText()+"|"+passField.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    long start=System.currentTimeMillis();
                    while(System.currentTimeMillis()-start<4000){
                        if(klient.getPakiet().equals("ACCEPT"))
                        {
                            klient.removePakiet();
                            System.out.println("GAME START~!!!!KLIENTGUI");

                            createGame();
                            remove(loginButton);
                            remove(loginLabel);
                            remove(passLabel);
                            remove(passField);
                            remove(loginField);
                            repaint();
                            break;
                        }
                    }
                }
            }
        });
        passField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    try {
                        klient.send("ACCESS|"+loginField.getText()+"|"+passField.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    long start=System.currentTimeMillis();
                    while(System.currentTimeMillis()-start<4000){
                        if(klient.getPakiet().equals("ACCEPT"))
                        {
                            klient.removePakiet();
                            System.out.println("GAME START~!!!!KLIENTGUI");

                            createGame();
                            remove(loginButton);
                            remove(loginLabel);
                            remove(passLabel);
                            remove(passField);
                            remove(loginField);
                            repaint();
                            break;
                        }
                    }
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    klient.send("ACCESS|"+loginField.getText()+"|"+passField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                long start=System.currentTimeMillis();
                while(System.currentTimeMillis()-start<4000){
                    if(klient!=null)
                    if(klient.getPakiet()!=null){
                    if(klient.getPakiet().equals("WRONG_LOGIN"))
                        break;
                    if(klient.getPakiet().equals("WRONG_PASSWORD"))
                        break;
                    if(klient.getPakiet().equals("ACCEPT"))
                    {   klient.removePakiet();
                        System.out.println("GAME START~!!!!KLIENTGUI");

                        createGame();
                        remove(loginButton);
                        remove(loginLabel);
                        remove(passLabel);
                        remove(passField);
                        remove(loginField);
                        repaint();
                        break;
                    }
                }}
            }
        } );

        chatSend.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {

                    try {
                        handler.klient.send("CHAT|"+chatSend.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    chatSend.setText("");
                }

            }
        });

        //adjust size and set layout
        setPreferredSize (new Dimension(width, height));
        setLayout (null);

        //add components
        add (loginField);
        add (loginButton);
        add (loginLabel);
        add (passLabel);
        add (passField);

        scrollPane.setBounds (3, height-138, width-12, 70);
        //chatField.setBorder(BorderFactory.createLineBorder(Color.black));
        chatSend.setBounds (3, height-66, width-12, 25);


        //set component bounds (only needed by Absolute Positioning)
        loginField.setBounds (width/2-50, height/2-80, 100, 25);
        loginLabel.setBounds (width/2-95, height/2-80, 65, 25);

        passField.setBounds (width/2-50, height/2-50, 100, 25);
        passLabel.setBounds (width/2-95, height/2-50, 100, 25);

        loginButton.setBounds (width/2-50, height/2-20, 100, 25);

    }

    public void createGame(){
      frame.getContentPane().removeAll();

        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(chatSend);
        frame.setLocationRelativeTo(null);
        canvas.setBounds(0,0,width,300);
       frame.getContentPane().add(canvas);

        ((DefaultListModel) handler.kgui.chatField.getModel()).addElement("WITAJ NA SERWERZE!");
        handler.kgui.chatField.ensureIndexIsVisible(handler.kgui.chatField.getModel().getSize()-1);

        canvas.setFocusable(true);
        chatField.setFocusable(false);
        canvas.requestFocus();
        frame.revalidate();
        chatField.setVisible(true);
        frame.repaint();


        Game game=new Game(handler);
        handler.setGame(game);
        handler.game.isLogged=true;
        Thread t=new Thread(game);
        t.start();

    }

    public void main () {

        frame = new JFrame ("Klient");
        frame.setSize(width,height);

        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add (this);
        frame.setLocationRelativeTo(null);
        frame.setVisible (true);
        frame.setPreferredSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));


        canvas=new Canvas();

        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
     //   createGame();
    }
    public JFrame getFrame(){
        return frame;
    }

}
