import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author dare
 * @create 2022-05-17 23:00
 */
public class MyCLien extends JFrame {
    public static Socket Csocket;
    public static String Ip;
    private PrintWriter writer;
    private JTextField tf = new JTextField();
    private JTextArea ta = new JTextArea();
    Container cc;
    public MyCLien(String title)
    {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cc = this.getContentPane();
        final JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBorder(new BevelBorder(BevelBorder.RAISED));
        getContentPane().add(jScrollPane,BorderLayout.CENTER);
        jScrollPane.setViewportView(ta);
        cc.add(tf,"South");
        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writer.println(tf.getText());
                ta.append(tf.getText()+"\n");
                ta.setSelectionEnd(ta.getText().length());
                tf.setText("");
            }
        });
    }
    private void connect()
    {
       ta.append("尝试连接");
        try {
            Csocket = new Socket("169.254.79.29",8282);
            Ip=Csocket.getInetAddress().getHostAddress();
            writer = new PrintWriter(Csocket.getOutputStream(),true);
            ta.append("连接成功");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[]args)
    {
        MyCLien myCLien = new MyCLien("连接服务器");
        myCLien.setSize(400,400);
        myCLien.setVisible(true);
        myCLien.connect();
    }
}
