import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Stworzone przez Krzyśka dnia 2017-09-08.
 */
public class SerwerGUI extends JPanel {
    public int width=667,height=367;
    public JList<String> chat;
    public JList<String> users;
    public JTextField send;
    public JScrollPane scrollPane;

    SHandler handler;

    public SerwerGUI(SHandler handler) {
        //construct preComponents
        this.handler=handler;
        String[] chatItems = {};
        String[] usersItems = {};

        //construct components

        chat = new JList<String>(new DefaultListModel<String>());
        users =new JList<String>(new DefaultListModel<String>());
        send = new JTextField (1);


        send.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER) {
                    try {
                        handler.serwer.send(send.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    send.setText("");
                }
            }
        });
        //adjust size and set layout

        setPreferredSize (new Dimension(width, height));
        setLayout (null);
        scrollPane = new JScrollPane(chat);
        scrollPane.setBounds(5,5,500,305);
        add(scrollPane);

        //add components
       // add (chat);
        add (users);
        add (send);

        //set component bounds (only needed by Absolute Positioning)
      //  chat.setBounds (5, 5, 500, 305);
        users.setBounds (510, 5, 150, 305);
        send.setBounds (5, 315, 655, 45);
    }


    public void main () {
        JFrame frame = new JFrame ("Server");
        frame.setBounds(50,50,width,height);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (this);

        frame.pack();
        frame.setVisible (true);
    }
}
