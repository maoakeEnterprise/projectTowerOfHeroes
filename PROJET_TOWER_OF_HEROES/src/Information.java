import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Information {
	Etages e;
	//STAT INFORMATION
	private int or;
	private int nb_hero_actuel;
	private int nb_soldat;
	private int nb_etage;
	private int nb_etage_seal;
	private int nb_hero_max;
	
	private ArrayList<Image> img = new ArrayList<Image>();
	private Rectangle imgR,imgR1, imgR2;
	private Image dbImage;
	private Animator hero;
	private Animator soldat;
	//LES LIENS 
	private Menu menu;
	private Personnages les_perso;
	private Etages les_etages;
	private Soldats les_soldat;
	Information(){
		or=300;
		nb_hero_actuel=0;
		nb_hero_max = 5;
		nb_soldat=0;
		nb_etage=0;
		nb_etage_seal=0;
		img.add(new ImageIcon("projet/coin.png").getImage());
		img.add(new ImageIcon("projet/castle.png").getImage());
		img.add(new ImageIcon("projet/seel.png").getImage());
		imgR = new Rectangle(20,35,0,0);
		imgR1 = new Rectangle(20,85,0,0);
		imgR2 = new Rectangle(130,85,0,0);
		init();
	}
	public void draw(Graphics graph) {
		ColorModel cm = ColorModel.getRGBdefault();
		Graphics2D g = (Graphics2D)graph;
		hero.update(System.currentTimeMillis());
		soldat.update(System.currentTimeMillis());
		
		//Rectangle de l'or
		g.setColor(new Color(0,0,0,80));
		g.fillRect(10, 30, 100, 40);
		g.drawImage(img.get(0),imgR.x,imgR.y,30,30, null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(""+or, 55, 55);
		
		//Rectangle nombre hero
		g.setColor(new Color(0,0,0,80));
		g.fillRect(120, 30, 100, 40);
		g.drawImage(hero.sprite, 125, 35, 32, 32,  null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(""+nb_hero_actuel+"/"+nb_hero_max, 165, 55);
		
		//Rectangle nombre soldat
		g.setColor(new Color(0,0,0,80));
		g.fillRect(230, 30, 100, 40);
		g.drawImage(soldat.sprite, 235, 35, 32, 32,  null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(""+nb_soldat, 275, 55);
		
		//Rectangle etage max
		g.setColor(new Color(0,0,0,80));
		g.fillRect(10, 80, 100, 40);
		g.drawImage(img.get(1),imgR1.x,imgR1.y,30,30, null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(""+nb_etage, 55, 105);
		
		//Rectangle etage scellé
		g.setColor(new Color(0,0,0,80));
		g.fillRect(120, 80, 100, 40);
		g.drawImage(img.get(2),imgR2.x,imgR2.y,30,30, null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(""+nb_etage_seal, 165, 105);
	}
	
	private void init() {
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
		
		sprites.add(ss.grabSprite(32, 0, 32, 32));
		sprites.add(ss.grabSprite(32, 32, 32, 32));
		sprites.add(ss.grabSprite(32, 96, 32, 32));
		sprites.add(ss.grabSprite(32, 64, 32, 32));
		
		sprites1.add(ss.grabSprite(128, 0, 32, 32));
		sprites1.add(ss.grabSprite(128, 32, 32, 32));
		sprites1.add(ss.grabSprite(128, 96, 32, 32));
		sprites1.add(ss.grabSprite(128, 64, 32, 32));
		
		hero = new Animator(sprites);
		hero.setSpeed(200);
		hero.play();
		
		soldat = new Animator(sprites1);
		soldat.setSpeed(200);
		soldat.play();
	}
	private void affichage_or() {
		
	}
	
	public int get_or() {return or;}
	public int get_nb_hero_actuel() {return nb_hero_actuel;}
	public int get_nb_soldat() {return nb_soldat;}
	public int get_nb_etage() {	return nb_etage;}
	public int get_nb_etage_seal() {return nb_etage_seal;}
	public int get_nb_hero_max() {return nb_hero_max;}
	
	public void set_or(int nb) {or+=nb;}
	public void set_nb_hero_actuel(int nb) {nb_hero_actuel+=nb;}
	public void set_nb_soldat(int nb) {nb_soldat+=nb;}
	public void set_nb_etage() {nb_etage++;}
	public void set_nb_etage_seal(int nb) {nb_etage_seal+=nb;}
	public void set_nb_hero_max(int bonus) {nb_hero_max+=bonus;}
	public void setPerso(Personnages perso) {les_perso = perso;}
	public Information getInformation() {return this;}
	public void setEtage(Etages eta) {les_etages =eta;}
	public void setSoldats(Soldats soldat) {les_soldat = soldat;}
	
	public void set_init_or(int nb) {or=nb;}
	public void set_init_seal(int nb) {nb_etage_seal=nb;}
	public void set_init_nb_etage(int nb) {nb_etage=nb;}
	public void set_init_qtte_hero(int nb) {nb_hero_max=nb;}
}