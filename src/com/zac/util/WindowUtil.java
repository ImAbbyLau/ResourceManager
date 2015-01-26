package com.zac.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class WindowUtil {
	// ���ô����С
    public static Dimension getSize() {
        return new Dimension(1050, 700);
    }
    
    // ���㴰�������ʾʱ���Ͻ�����
    public static Point getLocation(Dimension dim) {
        Toolkit toolKit = Toolkit.getDefaultToolkit();// ���Toolkitʵ��
        Dimension screenSize = toolKit.getScreenSize();// �����ʾ����С
        
        /*
        if ((screenSize.width < getSize().width) || (screenSize.height < getSize().height)) {
            JOptionPane.showMessageDialog(null, "��ʾ���ֱ�������Ϊ600��400", "", JOptionPane.WARNING_MESSAGE);
            System.exit(0);// ��ֹ����
        }
        */
        
        int x = (screenSize.width - dim.width) / 2;// �������ϽǺ�����
        int y = (screenSize.height - dim.height) / 2;// �������Ͻ�������
        return new Point(x, y);
    }
}
