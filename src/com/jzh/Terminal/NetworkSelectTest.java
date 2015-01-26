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

	private static final int UMTSnum = 3;  //����UMTS��������
	private static final int WLANnum = 10;  //����WLAN��������
	private static final int Terminalnum = 1;  //�����ն�����
	private static  String VisibleNet  ;
	
	//public static void main(String[] args) {
	public static String GetVisibleNet(){
		//�����������
		Random rand = new Random(new Date().getTime());
		
		//�������Գ�����ͼ
		NetworkSceneMap sceneMap = new NetworkSceneMap(MapSize, MapSize);

		//����WiMax���粢��ӽ����Գ�����
		sceneMap.addNetwork(new Network("1-1", NetworkType.WiMax, new Point(WiMaxRadius, WiMaxRadius), WiMaxRadius));
		while(true){
			//����2��UMTS���粢��ӽ����Գ�����
			for(int i = 1; i <= UMTSnum; i++) {
				int x, y;
				do {
					x = rand.nextInt(WiMaxRadius * 2);
					y = rand.nextInt(WiMaxRadius * 2);
				} while(Math.pow(Math.abs(x - WiMaxRadius), 2) + Math.pow(Math.abs(y - WiMaxRadius), 2) >= Math.pow(WiMaxRadius - UMTSRadius, 2));
				sceneMap.addNetwork(new Network("2-" + i, NetworkType.UMTS, new Point(x, y), UMTSRadius));
			}
			
			//����WLAN���粢��ӽ����Գ�����
			for(int i = 1; i <= WLANnum; i++) {
				int x, y;
				do {
					x = rand.nextInt(WiMaxRadius * 2);
					y = rand.nextInt(WiMaxRadius * 2);
				} while(Math.pow(Math.abs(x - WiMaxRadius), 2) + Math.pow(Math.abs(y - WiMaxRadius), 2) >= Math.pow(WiMaxRadius - WLANRadius, 2));
				sceneMap.addNetwork(new Network("3-" + i, NetworkType.WLAN, new Point(x, y), WLANRadius));
			}
			
			
			List<Network> networkSet = sceneMap.networkSet;
			
			//�����ն�����Щ����ĸ��Ƿ�Χ
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
			
			//��ӡ�����������������Ϣ
			
			System.out.println("������Ϣ��");
			for(int i = 0; i < networkSet.size(); i++) {
				Network net = networkSet.get(i);
				System.out.println(net);
			}
			System.out.println();
			
			System.out.print("�����ն����꣺" + p);
			System.out.print("�����ն�����������ĸ��Ƿ�Χ��");
			System.out.println(printLine);
			if(num++ >= Terminalnum) {
				return VisibleNet;
			}
			
			
		}
	}
}

/*
 * ���糡����ͼ����λ���ף�
 * PS����ʹ��ʮ������ϵ�ĵ�һ����
 */
class NetworkSceneMap {
	int xLong;  //������ͼX�᷶Χ
	int yLong;  //������ͼY�᷶Χ
	
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
 * ������ͼ�ϵĵ㣨��λ���ף�
 */
class Point {
	int x;  //���X������
	int y;  //���Y������
	
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
 * ��������
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
		return "�������ƣ�" + Name + " | �������ͣ�" + networkType + " | ��վ���꣺" + bsLocation + " | ���ǰ뾶��" + coverageRadius;
	}
}

enum NetworkType {
	WiMax, UMTS, WLAN
}