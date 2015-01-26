package com.zac.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class WindowUtil {
	// 设置窗体大小
    public static Dimension getSize() {
        return new Dimension(1050, 700);
    }
    
    // 计算窗体居中显示时左上角坐标
    public static Point getLocation(Dimension dim) {
        Toolkit toolKit = Toolkit.getDefaultToolkit();// 获得Toolkit实例
        Dimension screenSize = toolKit.getScreenSize();// 获得显示器大小
        
        /*
        if ((screenSize.width < getSize().width) || (screenSize.height < getSize().height)) {
            JOptionPane.showMessageDialog(null, "显示器分辨率至少为600×400", "", JOptionPane.WARNING_MESSAGE);
            System.exit(0);// 终止程序
        }
        */
        
        int x = (screenSize.width - dim.width) / 2;// 计算左上角横坐标
        int y = (screenSize.height - dim.height) / 2;// 计算左上角纵坐标
        return new Point(x, y);
    }
}
