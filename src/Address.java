import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author dare
 * @create 2022-05-17 22:44
 */
public class Address {
    public static void main(String[]args)
    {
        InetAddress ip;
        try {
            ip=InetAddress.getLocalHost();
            String localname = ip.getHostName();
            String localip = ip.getHostAddress();
            System.out.println("本机名："+localname);
            System.out.println("本机IP地址："+localip);
        }catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
