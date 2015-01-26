package com.zac.sql;
 
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
 
public class DataSourcePool {
 
    static int connectionCurrLink = 0;
    static Map<String, String> map = null;
    private static LinkedList<Connection> datasourcePool = new LinkedList<Connection>();
    
    // ͨ����̬�����ע�����ݿ���������֤ע��ִֻ��һ��
    static {
        map = new HashMap<String, String>();
        Properties p = new Properties();
        try {
            p.loadFromXML(new FileInputStream("src\\DataSourcePool.xml"));
            Enumeration<Object> dataSourceSet = p.keys();
            while (dataSourceSet.hasMoreElements()) {
                String key = (String) dataSourceSet.nextElement();
                map.put(key, p.getProperty(key));
            }
            Class.forName(map.get("conectionDriver"));  //��������
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public DataSourcePool() throws Exception {
        createConnection(0);
        // ͨ�����캯��������ʱ���Դﵽ��ʱ�ͷſ�������Ŀ��
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    // �õ��������ӣ�datasourcePool�����м�������ͱ�ʾ�м�����������
                    int leisureLink = DataSourcePool.datasourcePool.size();
                    System.out.println(leisureLink);
                    // ��С������
                    int connectionMinLink = Integer.parseInt(DataSourcePool.map
                            .get("connectionMinLink"));
                    // ���������Ӵ���DataSourcePool���õ���С������ʱ��ر�
                    if (leisureLink > connectionMinLink) {
                        for (int i = 0; i < leisureLink - connectionMinLink; i++) {
                            DataSourcePool.closeConnection(DataSourcePool.getConnection());
                            connectionCurrLink--;
                        }
                    } else {
                        System.out.println("������С����,�������������ӳ�");
                    }
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("��������Ч����С������");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, Long.parseLong(map.get("connectionTimer")));
 
    }
 
    // ��������
    private static void createConnection(int type) throws Exception {
        try {
            int link = 0;
            switch (type) {
            case 0:
                link = Integer.parseInt(map.get("connectionMinLink"));
                break;
            case 1:
                //�����ǰ����+�������Ӵ����趨�����������ʱ����ʹ�����������-��ǰ���ӵ��������Ա���ƽ��
                link = Integer.parseInt(map.get("connectionIncreaseLink"));
                int maxLink = Integer.parseInt(map.get("conectionMaxLink"));
                if (link + connectionCurrLink > maxLink) {
                    link = maxLink - connectionCurrLink;
                }
                break;
            }
            for (int i = 0; i < link; i++) {
                datasourcePool.addLast(DriverManager.getConnection(map.get("connectionUrl"),
                        map.get("connectionName"), map.get("connectionPassword")));
                connectionCurrLink++;
            }
        } catch (NumberFormatException n) {
            throw new NumberFormatException("�������Ӳ�������");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("������������� ,�޷�������������");
        }
    }
 
    // �������
    public static Connection getConnection() throws Exception {
        //ȡ���Ӽ�������ֹ����ȡ��ͬ��������
        synchronized (datasourcePool) {
            if (datasourcePool.size() > 0) {
                return datasourcePool.removeFirst();
            } else if (connectionCurrLink < Integer.parseInt(map.get("conectionMaxLink"))) {
                createConnection(1);
                return datasourcePool.removeFirst();
            }
        }
        return null;
    }
 
    /**
     * �ر�����
     * 
     * @param con
     * @throws SQLException
     */
    public static void closeConnection(Connection con) throws SQLException {
        con.close();
    }
 
    // �ͷ�����
    public void freeConnection(Connection con) {
        datasourcePool.addLast(con);
    }
 
}