package com.jzh.Terminal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NetworkSelectTest {
	public static final int MapSize = 5000;
	public static final int WiMaxRadius = 1000;
	public static final int UMTSRadius = 100;
	public static final int WLANRadius = 100;

	private static final int UMTSnum = 3;  //产生UMTS网络数量
	private static final int WLANnum = 10;  //产生WLAN网络数量
	private static final int Terminalnum = 1;  //测试终端数量
	private static  String VisibleNet  ;
	
	//public static void main(String[] args) {
	public static String GetVisibleNet(){
		//随机数产生器
		Random rand = new Random(new Date().getTime());
		
		//创建测试场景地图
		NetworkSceneMap sceneMap = new NetworkSceneMap(MapSize, MapSize);

		//创建WiMax网络并添加进测试场景中
		sceneMap.addNetwork(new Network("1-1", NetworkType.WiMax, new Point(WiMaxRadius, WiMaxRadius), WiMaxRadius));
		while(true){
			//创建2个UMTS网络并添加进测试场景中
			for(int i = 1; i <= UMTSnum; i++) {
				int x, y;
				do {
					x = rand.nextInt(WiMaxRadius * 2);
					y = rand.nextInt(WiMaxRadius * 2);
				} while(Math.pow(Math.abs(x - WiMaxRadius), 2) + Math.pow(Math.abs(y - WiMaxRadius), 2) >= Math.pow(WiMaxRadius - UMTSRadius, 2));
				sceneMap.addNetwork(new Network("2-" + i, NetworkType.UMTS, new Point(x, y), UMTSRadius));
			}
			
			//创建WLAN网络并添加进测试场景中
			for(int i = 1; i <= WLANnum; i++) {
				int x, y;
				do {
					x = rand.nextInt(WiMaxRadius * 2);
					y = rand.nextInt(WiMaxRadius * 2);
				} while(Math.pow(Math.abs(x - WiMaxRadius), 2) + Math.pow(Math.abs(y - WiMaxRadius), 2) >= Math.pow(WiMaxRadius - WLANRadius, 2));
				sceneMap.addNetwork(new Network("3-" + i, NetworkType.WLAN, new Point(x, y), WLANRadius));
			}
			
			
			List<Network> networkSet = sceneMap.networkSet;
			
			//测试终端在哪些网络的覆盖范围
			int num = 1;
			int x, y;
			do {
				x = rand.nextInt(WiMaxRadius * 2);
				y = rand.nextInt(WiMaxRadius * 2);
			} while(!sceneMap.networkSet.get(0).isPointCoverage(new Point(x, y)));
			Point p = new Point(x, y);			
			StringBuffer printLine = new StringBuffer();
			int count = 0;
			for(int j = 0; j < networkSet.size(); j++) {
				Network net = networkSet.get(j);
				if(net.isPointCoverage(p)) {
					count++;
					printLine.append(net.Name+ ";");
				}
			}
			String VisibleNet = printLine.toString();
			if(count < 3) {
				sceneMap.networkSet.clear();
				sceneMap.addNetwork(new Network("1-1", NetworkType.WiMax, new Point(WiMaxRadius, WiMaxRadius), WiMaxRadius));
				continue;
			}
			
			//打印场景中所有网络的信息
			
			System.out.println("场景信息：");
			for(int i = 0; i < networkSet.size(); i++) {
				Network net = networkSet.get(i);
				System.out.println(net);
			}
			System.out.println();
			
			System.out.print("测试终端坐标：" + p);
			System.out.print("，该终端在以下网络的覆盖范围：");
			System.out.println(printLine);
			if(num++ >= Terminalnum) {
				return VisibleNet;
			}
			
			
		}
	}
}

/*
 * 网络场景地图（单位：米）
 * PS：仅使用十字坐标系的第一象限
 */
class NetworkSceneMap {
	int xLong;  //场景地图X轴范围
	int yLong;  //场景地图Y轴范围
	
	List<Network> networkSet = new ArrayList<Network>();
	
	NetworkSceneMap() {
		super();
	}
	
	NetworkSceneMap(int x, int y) {
		this.xLong = x;
		this.yLong = y;
	}
	
	void addNetwork(Network network) {
		networkSet.add(network);
	}
}

/*
 * 场景地图上的点（单位：米）
 */
class Point {
	int x;  //点的X轴坐标
	int y;  //点的Y轴坐标
	
	Point() {
		super();
	}
	
	Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

/*
 * 网络类型
 */
class Network {
	String Name;
	NetworkType networkType;
	Point bsLocation;
	int coverageRadius;
	
	Network() {
		super();
	}
	
	Network(String name, NetworkType type, Point p, int radius) {
		super();
		this.Name = name;
		this.networkType = type;
		this.bsLocation = p;
		this.coverageRadius = radius;
	}
	
	boolean isPointCoverage(Point p) {
		return Math.pow(Math.abs(p.x - bsLocation.x), 2) + Math.pow(Math.abs(p.y - bsLocation.y), 2) < Math.pow(coverageRadius, 2);
	}
	
	public String toString() {
		return "网络名称：" + Name + " | 网络类型：" + networkType + " | 基站坐标：" + bsLocation + " | 覆盖半径：" + coverageRadius;
	}
}

enum NetworkType {
	WiMax, UMTS, WLAN
}