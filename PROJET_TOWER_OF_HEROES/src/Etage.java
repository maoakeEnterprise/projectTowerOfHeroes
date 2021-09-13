import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Etage {
	int state;
	private ArrayList<Image> etage = new ArrayList();
	private Rectangle etageR;
	private int nombre;
	private int etage_actuel;
	
	//LIAISON
	private Information info;
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	//RescaleOp rop = new RescaleOp(scales, offsets, null);
	Etage(Rectangle r, int etat, int nb, Information inf){
		etage.add(new ImageIcon("projet/Etage1.png").getImage());
		etage.add(new ImageIcon("projet/Etage2.png").getImage());
		etage.add(new ImageIcon("projet/Etage3.png").getImage());
		info = inf;
		
		etageR = r;
		state = etat;
		nombre = nb;
	}
	public void draw(Graphics graph) {
		Graphics2D g = (Graphics2D)graph;
		g.drawImage(etage.get(state), etageR.x, etageR.y,etageR.width,etageR.height, null);
		g.setFont(new Font("Arial", Font.BOLD, 50));
		g.setColor(new Color(255,255,255,50));
		if(nombre<10)
			g.drawString(""+nombre, etageR.x+90, etageR.y+70);
		else if(nombre>9 && nombre<100)
			g.drawString(""+nombre, etageR.x+75, etageR.y+70);
		else if(nombre>99 && nombre<1000)
			g.drawString(""+nombre, etageR.x+60, etageR.y+70);
		else if(nombre>999 && nombre<10000)
			g.drawString(""+nombre, etageR.x+45, etageR.y+70);
		if(nombre%5 == 0 && nombre != 0) {
			g.setColor(new Color(255,255,255,30));
			g.fillRect(etageR.x+10, etageR.y, 180, 100);
		}
		if(info.get_nb_etage_seal()>=nombre && info.get_nb_etage_seal() != 0) {
			g.setColor(new Color(0,0,0,80));
			g.fillRect(etageR.x+10, etageR.y+10, 180, 90);
		}
		
	}
	
	public void moveMab(int nav, int x) {
		switch(nav) {
		default:
			break;
		case PAN_UP:
			etageR.y-=x;
			break;
		case PAN_DOWN:
			etageR.y+=x;
			break;
		}
	}
}
