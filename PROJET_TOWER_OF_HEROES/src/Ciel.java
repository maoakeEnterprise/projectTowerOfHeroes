import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ciel implements Runnable{

	private ArrayList<Image> img_fond = new ArrayList<Image>();
	private ArrayList<Image> img_cloud = new ArrayList<Image>();
	Rectangle rect_cloud = new Rectangle(0,50,40,80);
	Rectangle rect_cloud1 = new Rectangle(100,80,40,80);
	
	int xDirection=1, yDirection=0;
	
	Ciel(){
		img_fond.add(new ImageIcon("projet/fond.jpg").getImage());
		img_fond.add(new ImageIcon("projet/fond2.png").getImage());
		
		img_cloud.add(new ImageIcon("projet/cloud.png").getImage());
		img_cloud.add(new ImageIcon("projet/cloud2.png").getImage());
		Thread t_ciel = new Thread(this);
		t_ciel.start();
	}
	
	public void draw(Graphics g) {
		g.drawImage(img_fond.get(0),0,0,400,600, null);
		g.drawImage(img_cloud.get(0),rect_cloud.x,rect_cloud.y,200,200, null);
		g.drawImage(img_cloud.get(1),rect_cloud1.x,rect_cloud1.y,200,200, null);
	}
	
	public void move() {
		rect_cloud.x += xDirection;
		rect_cloud.y += yDirection;
		
		rect_cloud1.x += xDirection;
		rect_cloud1.y += yDirection;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true) {
				move();
				if(rect_cloud1.getX()>400)
					rect_cloud1 = new Rectangle(-100,80,40,80);
				if(rect_cloud.getX()>400)
					rect_cloud = new Rectangle(-200,80,40,80);
				Thread.sleep(50);
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		
	}
}
