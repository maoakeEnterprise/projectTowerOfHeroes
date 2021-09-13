import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class sol {
	
	private Image Sol;
	private Rectangle solR;
	private World m;
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	
	sol(int x, int y){
		Sol = new ImageIcon("projet/sol.png").getImage();
		solR = new Rectangle(x,y,400,300);
	}
	
	public void draw(Graphics graph) {
		Graphics2D g = (Graphics2D)graph;
		g.drawImage(Sol, solR.x, solR.y,400,300, null);
	}
	
	private void setWorld(World monde) {m = monde;}

	public void moveMab(int nav,int x) {
		switch(nav) {
		default:
			break;
		case PAN_UP:
			solR.y-=x;
			break;
		case PAN_DOWN:
			solR.y+=x;
			break;
		}
	}
	public Rectangle getRect() {return solR;}
}
