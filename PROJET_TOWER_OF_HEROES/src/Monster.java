import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Monster implements Runnable{
	static final int PAN_UP = 1, PAN_DOWN = -1;
	BufferedImage sprite;
	Animator monstre_gauche;
	Animator monstre_droite;
	private Rectangle monsterR;
	private Rectangle stokage;
	private Rectangle mortR;
	
	private int life=10;
	private int life_max=10;
	private int atq=4;
	
	Random rd = new Random();
	int nx;
	int ny;
	int etat;
	Thread t;
	boolean death_monster=false;
	int compteur_death=0;
	int compteur_apparition=0;
	int compteur_desapparition=0;
	boolean app_b=true;
	int etage_actuel=0;
	Image img = new ImageIcon("projet/coin.png").getImage();
	Rectangle orR;
	Rectangle orR2;
	
	boolean anime_or=false;
	
	int widthRlifemax = 30;
	
	Information info;
	
	int stockor1;
	int stockor2;
	
	int compteur_regen=0;
	
	Monster(Rectangle r, int e,int etage){
		etage_actuel = etage;
		nx = rd.nextInt(4)+0;
		ny = rd.nextInt(1);
		init(r);
		etat = e;
		t = new Thread(this);
		t.start();
	}
	
	private void init(Rectangle r) {
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = loader.loadImage("monsters_set.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		
		ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> sprites1 = new ArrayList<BufferedImage>();
		
		sprites.add(ss.grabSprite(nx*96+0, ny*96+32, 32, 32));
		sprites.add(ss.grabSprite(nx*96+32, ny*96+32, 32, 32));
		sprites.add(ss.grabSprite(nx*96+64, ny*96+32, 32, 32));
		
		sprites1.add(ss.grabSprite(nx*96+0, ny*96+64, 32, 32));
		sprites1.add(ss.grabSprite(nx*96+32, ny*96+64, 32, 32));
		sprites1.add(ss.grabSprite(nx*96+64, ny*96+64, 32, 32));
		
		monstre_gauche = new Animator(sprites);
		monstre_gauche.setSpeed(100);
		monstre_gauche.play();
		
		monstre_droite = new Animator(sprites1);
		monstre_droite.setSpeed(100);
		monstre_droite.play();
		
		monsterR = new Rectangle(r.x,r.y+40,r.width,r.height);
		stokage = new Rectangle(monsterR.x,monsterR.y-40,monsterR.width,40);
		mortR = new Rectangle(monsterR.x,monsterR.y-40,monsterR.width,40);
		orR  = new Rectangle(monsterR.x,monsterR.y-40,15,15);
		orR2 = new Rectangle(monsterR.x+30,monsterR.y-40,15,15);
		
		
	}
	public void draw(Graphics graph) {
		Graphics2D g = (Graphics2D)graph;
		if(etage_actuel>=info.get_nb_etage_seal()) {
			if(etat == 1) {
				monstre_gauche.update(System.currentTimeMillis());
				if(death_monster) {
					g.drawImage(monstre_gauche.sprite, mortR.x, mortR.y,mortR.width,mortR.height, null);
				}
				g.drawImage(monstre_gauche.sprite, monsterR.x, monsterR.y,monsterR.width,monsterR.height, null);
				
				}
			else if(etat == 2) {
				monstre_droite.update(System.currentTimeMillis());
				if(death_monster) {
					g.drawImage(monstre_droite.sprite, mortR.x, mortR.y,mortR.width,mortR.height, null);
				}
				g.drawImage(monstre_droite.sprite, monsterR.x, monsterR.y,monsterR.width,monsterR.height, null);
				
				}
			if(!death_monster) {
				g.setColor(new Color(190,41,46));
				g.fillRect(monsterR.x, monsterR.y-8, (int)((widthRlifemax*life)/life_max), 3);
				g.setColor(new Color(0,0,0,80));
				g.fillRect(monsterR.x+(int)((widthRlifemax*life)/life_max), monsterR.y-8, (int)((widthRlifemax*(life_max-life))/life_max), 3);
			}
			if(death_monster && compteur_desapparition<501) {
				g.drawImage(img, orR.x, orR.y,15,15, null);
				g.drawImage(img, orR2.x, orR2.y,15,15, null);
			}
		}
		
	}
	
	public void moveMab(int nav, int x) {
		switch(nav) {
		default:
			break;
		case PAN_UP:
			monsterR.y-=x;
			stokage.y-=x;
			mortR.y-=x;
			break;
		case PAN_DOWN:
			monsterR.y+=x;
			stokage.y+=x;
			mortR.y+=x;
			break;
		}
	}
	
	public void la_mort() {
		if(life <=0) {
			monsterR = new Rectangle();
			death_monster = true;
			compteur_death = 0;
			compteur_desapparition=0;
		}
	}
	public void respawn() {
		compteur_death++;
		if(compteur_death==4000) {
			death_monster = false;
			life = life_max;
			monsterR = new Rectangle(stokage.x,stokage.y-1,stokage.width,stokage.height+1);
			mortR = new Rectangle(mortR.x,stokage.y,mortR.width,40);
			anime_or = false;
		}
	}
	public void apparition() {
		compteur_apparition++;
		if(compteur_apparition%12==0) {
			monsterR = new Rectangle(monsterR.x,monsterR.y-1,monsterR.width,monsterR.height+1);
		}
		if(compteur_apparition==500) {
			app_b=false;
		}
	}
	public void animation_mort() {
		compteur_desapparition++;
		if(compteur_desapparition%12==0 && compteur_desapparition<501) {
			mortR = new Rectangle(mortR.x,mortR.y+1,mortR.width,mortR.height-1);
			orR = new Rectangle(orR.x-(int)((60+orR.x)/(500/12)),orR.y+stockor1,15,15);
			orR2 = new Rectangle(orR2.x-(int)((60+orR2.x)/(500/12)),orR2.y+stockor1,15,15);
		}
	}
	public void animation_or() {
		if(!anime_or) {
			anime_or = true;
			orR = new Rectangle(mortR.x,stokage.y,15,15);
			orR2 = new Rectangle(mortR.x+30,stokage.y,15,15);
			stockor1=((-60-stokage.y)/(500/12));
		}
	}
	public void regen() {
		compteur_regen++;
		
		if(compteur_regen>999 && life<life_max) {
			compteur_regen=0;
			int ajout_life = ( life_max * 10 ) / 100;
			if(ajout_life+life > life_max)
				life = life_max;
			else
				life +=ajout_life;
		}
	}
	
	
	public void setlife(int l) {life += l;}
	public void setlifemax(int l) {life_max=l;}
	public int getlifemax() {return life_max;}
	public void setatq(int att) {atq += att;}
	public void collisionlife(int l) {life += l;}
	public int getlife() {return life;}
	public int getatq() {return atq;}
	public Rectangle getRect() {return monsterR;};
	public void setInformation(Information i) {info = i;}
	
	public boolean getexist_or_not() {
		if(etage_actuel>=info.get_nb_etage_seal())
			return true;
		else
			return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true){
				regen();
				if(app_b)
					apparition();
				if(death_monster) {
					animation_or();
					respawn();
					animation_mort();
				}
				else
					la_mort();
				
				Thread.sleep(1);
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		
	}

}
