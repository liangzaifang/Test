import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author dare
 * @create 2022-05-17 22:48
 */
public class MyTcp extends JFrame {
    private JLabel label;
    private JTextField jTextField;
    private JButton jb1,jb2;
    private JScrollPane jScrollPane;
    private static Thread t;
    private BufferedReader reader;
    private ServerSocket server;
    private Socket Tsocket;
    DefaultTableModel model;
    public MyTcp()
    {
        JFrame frame = new JFrame("欢儿远控控制器");
        frame.setSize(1000,600);
        frame.setLocation(200,50);
        frame.setLayout(null);
        //设界面和虚拟机一起关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置界面可见
        frame.setVisible(true);

        //设置绑定端口界面
        JPanel Jp1 = new JPanel();
        Jp1.setLayout(null);
        frame.add(Jp1);
        Jp1.setBounds(100,30,410,50);
        label = new JLabel("绑定端口");
        jTextField = new JTextField("8282");
        jb1 = new JButton("开始");
        jb2 = new JButton("停止");
        Jp1.add(label);
        Jp1.add(jTextField);
        Jp1.add(jb1);
        Jp1.add(jb2);
        label.setBounds(0,0,80,30);
        jTextField.setBounds(80,0,170,30);
        jb1.setBounds(260,0,70,30);
        jb2.setBounds(340,0,70,30);

        //设置查看表格
        JTable table = new JTable();
        model = (DefaultTableModel)table.getModel();
        model.setColumnIdentifiers(new String[]{"","主机IP","SessionID"});
        model.addRow(new String[]{"*",null,null});
        table.setModel(model);
        jScrollPane = new JScrollPane(table);
        frame.add(jScrollPane);
        jScrollPane.setBounds(100,200,600,300);

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getserver();
            }
        });

    }

    void getserver()
    {
        try {
            server = new ServerSocket(Integer.parseInt(jTextField.getText().trim()));
            System.out.println("服务器已经启动");
            while(true)
            {
                System.out.println("等待客户机的连接");
                Tsocket = server.accept();
                t= new Thread(new Runnable() {
                @Override
                public void run() {
                    model.addRow(new String[]{"",MyCLien.Ip,""});
                }
            });
                t.start();
                reader = new BufferedReader(new InputStreamReader(Tsocket.getInputStream()));
                getClientMessage();
               }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void getClientMessage()
    {
        try {
            while (true)
            {
                System.out.println("客户机："+reader.readLine());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            if(reader!=null)
            {
                reader.close();
            }
            if(Tsocket!=null)
            {
                Tsocket.close();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[]args)
    {
        MyTcp myTcp = new MyTcp();
    }
}
