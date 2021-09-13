import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Separateur {
	private Image separateur;
	private Rectangle separateurR;
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	Separateur(Rectangle r){
		separateur = new ImageIcon("projet/Barre.png").getImage();
		separateurR = r;
	}
	public void draw(Graphics graph) {
		Graphics2D g = (Graphics2D)graph;
		g.drawImage(separateur, separateurR.x, separateurR.y,200,10, null);
	}
	public void moveMab(int nav,int x) {
		switch(nav) {
		default:
			break;
		case PAN_UP:
			separateurR.y-=x;
			break;
		case PAN_DOWN:
			separateurR.y+=x;
			break;
		}
	}
	
	public Separateur getSeparateur() {return this;}
	public Rectangle getRect() {return separateurR;}
}
