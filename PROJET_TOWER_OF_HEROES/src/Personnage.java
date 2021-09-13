import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Personnage implements Runnable{
	BufferedImage sprite;
	
	//LIAISON
	Etages etage;
	Separateurs separateur;
	Monsters monstre;
	Information info;
	
	Animator hero_gauche;
	Animator hero_droite;
	Animator hero_monter;
	
	Animator mort_gauche;
	Animator mort_droite;
	
	
	BufferedImage img_mort;
	BufferedImage img_mort2;
	
	private Rectangle Rect;
	private Rectangle flash;
	private Image img;
	
	
	int etat;
	int etat_before;
	int etat_etage;
	
	private boolean etage_next=false;
	
	boolean move_bool=false;
	boolean monter_bool=false;
	boolean move_collision=false;
	boolean move_collision2=false;
	
	//Animation dgt
	boolean verif = false;
	int effet_dgt=0;
	Rectangle dgt1 = new Rectangle(-1000,-1000,0,0);
	Rectangle dgt2 = new Rectangle(-1000,-1000,0,0);
	int monstre_collision;
	int compteur_dgt = 0;
	
	
	//STATISTIQUE DU PERSONNAGE
	private int lvl = 1;
	private int HP = 2;
	private int Strenght = 4;
	private int agilite = 10;
	private int speed=7;
	int move_x=0;
	int move_y=0;
	
	
	
	
	
	boolean life_or_death=true;
	boolean start_animate_mort=false;
	
	int compteur = 0;
	
	
	int etage_actuel=0;

	int compteur_desapparition=0;
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	
	int xDirection=0, yDirection=0;
	
	
	
	
	
	Personnage(Rectangle r, int etat_perso, int etat_etage_ext){
		init(r);
		etat = etat_perso;
		flash = new Rectangle(r.x-90,r.y-80,200,200);
		img=new ImageIcon("projet/flash.png").getImage();
	}
	
	public void draw(Graphics graph) {
		Graphics2D g = (Graphics2D)graph;
		g.drawImage(img, flash.x, flash.y,flash.width,flash.height, null);
		g.setColor(Color.RED);
		if(start_animate_mort && etat==2) {
			mort_droite.update(System.currentTimeMillis());
			g.drawImage(mort_droite.sprite, Rect.x, Rect.y+5,Rect.width,Rect.height, null);
			}
		else if(start_animate_mort && etat==1) {
			mort_gauche.update(System.currentTimeMillis());
			g.drawImage(mort_gauche.sprite, Rect.x, Rect.y+5,Rect.width,Rect.height, null);
			}
		else {
			if(etat == 1) {
				hero_gauche.update(System.currentTimeMillis());
				g.drawImage(hero_gauche.sprite, Rect.x, Rect.y,Rect.width,Rect.height, null);
				}
			else if(etat == 2) {
				hero_droite.update(System.currentTimeMillis());
				g.drawImage(hero_droite.sprite, Rect.x, Rect.y,Rect.width,Rect.height, null);
				}
			else if(etat == 3) {
				hero_monter.update(System.currentTimeMillis());
				g.drawImage(hero_monter.sprite, Rect.x, Rect.y,Rect.width,Rect.height, null);
				}
		}
		if(verif && etat == 2) {
			g.setColor(new Color(255,255,255,200-effet_dgt));
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("-"+Strenght,  dgt1.x+40, dgt1.y-20);
			g.setColor(new Color(190,41,46,200-effet_dgt));
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("-"+monstre.getTabMonster().get(monstre_collision).getatq(),  dgt2.x, dgt2.y-20);
			
		}
		else if(verif && etat == 1) {
			g.setColor(new Color(255,255,255,200-effet_dgt));
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("-"+Strenght,  dgt1.x+-40, dgt1.y-20);
			g.setColor(new Color(190,41,46,200-effet_dgt));
			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("-"+monstre.getTabMonster().get(monstre_collision).getatq(),  dgt2.x, dgt2.y-20);
		}
		
		
	}
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
	    int w = image.getWidth(), h = image.getHeight();
	    int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
	    Graphics2D g = result.createGraphics();
	    g.translate((neww - w) / 2, (newh - h) / 2);
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}

	private static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}
	public static BufferedImage getImageAssombrieEclaircie(BufferedImage image, float assombrissement) {
		BufferedImage dstImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
	    RescaleOp op = new RescaleOp(assombrissement, 0, null);// éclaircir de 10%
	    dstImage = op.filter(image, image);
	    return dstImage;
	}
	
	private void init(Rectangle r) {
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = loader.loadImage("Characters_set.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		
		ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> sprites1 = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> sprites2 = new ArrayList<BufferedImage>();
		
		ArrayList<BufferedImage> sprites3 = new ArrayList<BufferedImage>();
		ArrayList<BufferedImage> sprites4 = new ArrayList<BufferedImage>();
		
		sprites.add(ss.grabSprite(0, 32, 32, 32));
		sprites.add(ss.grabSprite(32, 32, 32, 32));
		sprites.add(ss.grabSprite(64, 32, 32, 32));
		
		sprites1.add(ss.grabSprite(0, 64, 32, 32));
		sprites1.add(ss.grabSprite(32, 64, 32, 32));
		sprites1.add(ss.grabSprite(64, 64, 32, 32));
		
		sprites2.add(ss.grabSprite(0, 96, 32, 32));
		sprites2.add(ss.grabSprite(32, 96, 32, 32));
		sprites2.add(ss.grabSprite(64, 96, 32, 32));
		
		hero_gauche = new Animator(sprites);
		hero_gauche.setSpeed(100);
		hero_gauche.play();
		
		hero_droite = new Animator(sprites1);
		hero_droite.setSpeed(100);
		hero_droite.play();
		
		hero_monter = new Animator(sprites2);
		hero_monter.setSpeed(100);
		hero_monter.play();
		
		img_mort = ss.grabSprite(32, 96, 32, 32);
		img_mort2 = ss.grabSprite(32, 96, 32, 32);
		
		
		img_mort = rotate(img_mort,-1.6);
		img_mort2 = rotate(img_mort2,1.6);
		
		sprites3.add(img_mort);
		sprites3.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),-1.6),0.7f));
		sprites3.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),-1.6),0.5f));
		sprites3.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),-1.6),0.2f));
		sprites3.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),-1.6),0.0f));
		
		sprites4.add(img_mort2);
		sprites4.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),1.6),0.7f));
		sprites4.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),1.6),0.5f));
		sprites4.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),1.6),0.2f));
		sprites4.add(getImageAssombrieEclaircie(rotate(ss.grabSprite(32, 96, 32, 32),1.6),0.0f));
		
		mort_gauche = new Animator(sprites4);
		mort_gauche.setSpeed(100);
		mort_gauche.play();
		
		mort_droite = new Animator(sprites3);
		mort_droite.setSpeed(100);
		mort_droite.play();
		
		Rect = r;
		
	}
	
	public void moveMab(int nav, int x) {
		switch(nav) {
		default:
			break;
		case PAN_UP:
			Rect.y-=x;
			flash.y-=x;
			dgt1.y-=x;
			dgt2.y-=x;
			break;
		case PAN_DOWN:
			Rect.y+=x;
			flash.y+=x;
			dgt1.y+=x;
			dgt2.y+=x;
			break;
		}
	}
	
	public void move() {
		Rect.x += xDirection;
		Rect.y += yDirection;
	}
	
	public void setDirectionY(int d) {yDirection = d;}
	public void setDirectionX(int d) {xDirection = d;}
	
	public void gravity() {
		if(etat != 3 ) {
			for(int i=0;i<separateur.getRectSeparateur().size();i++) {
				if(Rect.intersects(etage.getRectangle().get(i))) {
					if(Rect.intersects(etage.getRectangle().get(i)) && Rect.getMaxY() != separateur.getRectSeparateur().get(i).getY()) {
						setDirectionY(1);
						move_bool = false;
					}
					else {
						setDirectionY(0);
						move_bool = true;
					}
				}
				
			}
		}
	}
	public void monter() {
		if(Rect.getMaxY() > separateur.getRectSeparateur().get(etage_actuel+1).getY()) {
			etat=3;
			setDirectionY(-1);
		}
		else {
			setDirectionY(0);
			monter_bool=false;
			if((etage_actuel+1)%2 == 0)
				etat = 1;
			else
				etat = 2;
			}
	}
	public void gauche_droite() {
		for(int i=0;i<etage.getRectangle().size();i++) {
			if(Rect.intersects(etage.getRectangle().get(i)) && etage_next == false) {
				if(i%2==0 && Rect.x > etage.getRectangle().get(i).x+12) {
					etat=1;
					setDirectionX(-1);
				}else if(i%2!=0 && Rect.getMaxX() < etage.getRectangle().get(i).getMaxX()-12){
					etat=2;
					setDirectionX(1);
				}
				else {
					monter_bool=true;
					move_bool=false;
					setDirectionX(0);
					etage_actuel = i;
				}
			}
		}
	}
	
	public void collision() {
		for(int i=0;i<monstre.getTabMonster().size();i++) {
			if(Rect.intersects(monstre.getTabMonster().get(i).getRect()) && monstre.getTabMonster().get(i).getexist_or_not()) {
				dgt1 = new Rectangle(Rect.x,Rect.y,0,0);
				dgt2 = new Rectangle(Rect.x,Rect.y,0,0);
				if(Strenght<monstre.getTabMonster().get(i).getlife()) {
					if(etat==2)
						setDirectionX(-2);
					if(etat==1)
						setDirectionX(2);
					move_collision = true;
					compteur = 0;
					speed=2;
					
				}
				else {
					setDirectionY(-1);
					setDirectionX(0);
					compteur = 0;
					speed=2;
					info.set_or(300*(i+1));
					move_collision2 = true;
				}
				monstre_collision = i;
				monstre.getTabMonster().get(i).collisionlife(-Strenght);
				setlife_collision(-monstre.getTabMonster().get(i).getatq());
			}
		}
	}
	public void mv_collision() {
		compteur++;
		if(compteur%4==0)
			speed++;
		if(speed==7) {
			move_collision = false;
		}
	}
	public void mv_collision2() {
		compteur++;
		if(compteur%2==0)
			speed++;
		if(speed==7) {
			move_collision2 = false;
		}
	}
	public void mort() {
		if(HP<=0) {
			start_animate_mort = true;
		}
	}
	public void animation_mort() {
		compteur_desapparition++;
		if(compteur_desapparition>35 && compteur_desapparition<71) {
			Rect = new Rectangle(Rect.x,Rect.y+1,Rect.width,Rect.height-1);
		}
		if(compteur_desapparition==70) {
			life_or_death = false;
		}
		
	}
	public void animation_dgts() {
		
		if(move_collision2 || move_collision) {
			verif = true;
			effet_dgt=0;
			
		}
		if(verif) {
			compteur_dgt++;
			dgt1 = new Rectangle(dgt1.x,dgt1.y-1,0,0);
			dgt2 = new Rectangle(dgt1.x,dgt1.y-1,0,0);
			if(effet_dgt<197)
				effet_dgt+=3;
			if(compteur_dgt==70)
				verif=false;
		}
	}
	public void rattraper_seal() {
		for(int i=0;i<etage.getRectangle().size();i++) {
			if(Rect.intersects(etage.getRectangle().get(i)))
				if(i<info.get_nb_etage_seal())
					speed=2;
				else
					speed=7;
		}
	}
	
	@Override
	public void run() {
		try{
			while(true){
				rattraper_seal();
				collision();
				animation_dgts();
				if(!move_collision2)
					gravity();
				if(!start_animate_mort)
					move();
				mort();
				if(start_animate_mort)
					animation_mort();
				if(!move_collision && !move_collision2) {
					if(move_bool)
						gauche_droite();
					if(monter_bool)
						monter();				
					etage_next = false;
				}
				if(move_collision) {
					mv_collision();
				}
				if(move_collision2) {
					mv_collision2();
				}
				if(flash.width>0 && flash.height>0) {
					flash = new Rectangle(flash.x+1,flash.y+1,flash.width-2,flash.height-2);
				}
				
				if(etat==3)
					Thread.sleep(speed+10);
				else
					Thread.sleep(speed);
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		
		
	}	
	public int getlife() {return HP;}
	public int getatq() {return Strenght;}
	public void setlife(int bonus) {HP= bonus;}
	public void setlife_collision(int dgt) {HP+=dgt;}
	public void setlvl(int bonus) {lvl= bonus;}
	public void setlattaque(int bonus) {Strenght= bonus;}
	public void setEtage(Etages etg) {etage =etg;}
	public void setSeparateur(Separateurs sprt) {separateur = sprt;}
	public void setMonster(Monsters mstr) {monstre = mstr;}
	public void setInfo(Information i) {info = i;}
	public boolean getMort() {return life_or_death;}
	public Rectangle getRect() {return Rect;}
	
	
}
