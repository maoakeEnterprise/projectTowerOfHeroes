import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Menu implements Runnable{
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	
	private ArrayList<Image> img = new ArrayList<Image>();
	//LIAISON
	private Personnages les_perso;
	private Information info;
	private Etages les_etages;
	private Soldats les_soldat;
	
	
	private Rectangle menu;
	private Rectangle hero;
	private Rectangle soldat;
	
	
	//INTERFACE HERO
	private Rectangle hero_lvlup;
	private int bonus_lvl_hero=1;
	private int bonus_life_hero=10;
	private int  bonus_attack_hero=5;
	private Rectangle qtte_hero;
	private int bonus_qtte_hero=3;
	private Rectangle etage_seal;
	private int bonus_etage_steal=10;
	
	
	//INTERFACE SOLDAT
	private Rectangle soldat_lvlup;
	private int bonus_lvl_soldat=1;
	private int bonus_life_soldat=2;
	private int  bonus_attack_soldat=4;
	private Rectangle CDR;
	private int bonus_CDR=  100;
	
	//retour 
	private Rectangle back;
	
	
	private BufferedImage img_hero;
	private BufferedImage img_soldat;
	
	private Rectangle recthero;
	private Rectangle rectsoldat;
	
	private Rectangle hover_lvlup;
	private Rectangle hover_lvlup_soldat;
	private Rectangle hover_qtte;
	private Rectangle hover_seal;
	
	private Rectangle hover_CDR;
	
	//les prix
	private int prix_lvl_hero=100;
	private int prix_qtte=300;
	private int prix_seal=5000;
	private int prix_lvl_soldat=100;
	private int prix_CDR=8000;
	
	Thread t;
	
	boolean herob = false;
	boolean soldatb = false;
	boolean close_m = false;
	
	int compteur=0;
	

	
	
	Menu(){
		img.add(new ImageIcon("projet/seel.png").getImage());
		img.add(new ImageIcon("projet/retour.png").getImage());
		img.add(new ImageIcon("projet/timer.png").getImage());
		
		menu = new Rectangle(0,510,399,500);
		hero = new Rectangle(50,520,100,30);
		soldat = new Rectangle(250,520,100,30);
		back = new Rectangle(-1000,-1000,0,0);

		//INTERFACE HERO
		hero_lvlup = new Rectangle(-1000,-1000,0,0);
		qtte_hero = new Rectangle(-1000,-1000,0,0);
		etage_seal = new Rectangle(-1000,-1000,0,0);
		//INTERFACE SOLDAT
		soldat_lvlup = new Rectangle(-1000,-1000,0,0);
		CDR = new Rectangle(-1000,-1000,0,0);
		
		recthero = new Rectangle(60,522,25,25);
		rectsoldat = new Rectangle(260,522,25,25);
		hover_lvlup = new Rectangle(-1000,-1000,0,0);
		hover_lvlup_soldat = new Rectangle(-1000,-1000,0,0);
		hover_qtte = new Rectangle(-1000,-1000,0,0);
		hover_seal = new Rectangle(-1000,-1000,0,0);
		hover_CDR = new Rectangle(-1000,-1000,0,0);
		init();
		
		t = new Thread(this);
		t.start();
		
		
	}
	
	public void draw(Graphics g){
		
		//===================================================================
		//DESSINS DU GRAND RECTANGLE MENU
		//LE MENU
		g.setColor(new Color(0,0,0,120));
		g.fillRoundRect(menu.x, menu.y, menu.width, menu.height,15,15);
		g.setColor(new Color(56,56,56));
		g.fillRoundRect(menu.x+20, menu.y+20, menu.width, menu.height,15,15);
		//===================================================================
		//DESSINS DES PTITS RECTANGLE DU MENU
		g.setColor(new Color(180,180,180,120));
		//CASE HERO
		g.fillRoundRect(hero.x, hero.y, hero.width, hero.height,15,15);
		//CASE SOLDAT
		g.fillRoundRect(soldat.x, soldat.y, soldat.width, soldat.height,15,15);
		//CASE RETOUR
		g.fillRoundRect(back.x, back.y, back.width, back.height,15,15);
		//CASE HERO LVLUP
		if(hero_lvlup.y>menu.y && menu.y==300)
			g.fillRoundRect(hero_lvlup.x, hero_lvlup.y, hero_lvlup.width, hero_lvlup.height,15,15);
		//CASE QTTE HERO
		g.fillRoundRect(qtte_hero.x, qtte_hero.y, qtte_hero.width, qtte_hero.height,15,15);
		//CASE ETAGE SEAL 
		g.fillRoundRect(etage_seal.x, etage_seal.y, etage_seal.width, etage_seal.height,15,15);
		//CASE SOLDAT LVL UP
		g.fillRoundRect(soldat_lvlup.x, soldat_lvlup.y, soldat_lvlup.width, soldat_lvlup.height,15,15);
		//CASE CDR
		g.fillRoundRect(CDR.x, CDR.y, CDR.width, CDR.height,15,15);
		
		
		//===================================================================
		//DESSINS DE CE QUI EST DANS CHAQUE RECTANGLE DU MENU
		
		
		//CASE HERO - CASE HERO LVLUP
		if(!(menu.y==300)) {
			g.drawImage(img_hero, recthero.x, recthero.y, recthero.width, recthero.height,  null);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("HERO",recthero.x + 30, recthero.y+20);
		}
		else {
			if(hero_lvlup.y>menu.y && menu.y==300) {
			g.drawImage(img_hero, recthero.x, recthero.y, recthero.width, recthero.height,  null);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString("HERO",recthero.x + 30, recthero.y+20);
			
			g.setFont(new Font("Arial", Font.BOLD, 11));
			g.drawString("lvl    :",hero_lvlup.x + 80, hero_lvlup.y+10);
			g.drawString(""+les_perso.getlvl(),hero_lvlup.x + 110, hero_lvlup.y+10);
			g.setColor(Color.green);
			g.drawString("--> "+(les_perso.getlvl()+bonus_lvl_hero),hover_lvlup.x + 120, hover_lvlup.y+10);
			
			
			g.setColor(Color.white);
			g.drawString("life  :",hero_lvlup.x + 80, hero_lvlup.y+28);
			g.drawString(""+les_perso.getlife(),hero_lvlup.x + 110, hero_lvlup.y+28);
			g.setColor(Color.green);
			g.drawString("--> "+(les_perso.getlife()+10*les_perso.getlvl()),hover_lvlup.x + 120, hover_lvlup.y+28);
			
			g.setColor(Color.white);
			g.drawString("Atq :",hero_lvlup.x + 170, hero_lvlup.y+28);
			g.drawString(""+les_perso.getattaque(),hero_lvlup.x + 200, hero_lvlup.y+28);
			g.setColor(Color.green);
			g.drawString("--> "+(les_perso.getattaque()+7*les_perso.getlvl()),hover_lvlup.x + 210, hover_lvlup.y+28);
			
			if(info.get_or()<prix_lvl_hero)
				g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.drawString(prix_lvl_hero+" $", hero_lvlup.x+200, hero_lvlup.y+60);
			}
		}
		//CASE SOLDAT - CASE SOLDAT LVL UP
		g.drawImage(img_soldat, rectsoldat.x, rectsoldat.y, rectsoldat.width, rectsoldat.height,  null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("SOLDAT",rectsoldat.x+ 30, rectsoldat.y+20);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString("lvl :"+les_soldat.get_lvl(),soldat_lvlup.x + 95, soldat_lvlup.y+10);
		g.drawString("",soldat_lvlup.x + 125, soldat_lvlup.y+10);
		g.setColor(Color.green);
		g.drawString("--> "+(les_soldat.get_lvl()+bonus_lvl_soldat),hover_lvlup_soldat.x + 130, hover_lvlup_soldat.y+10);
		
		g.setColor(Color.white);
		g.drawString("life :"+les_soldat.get_life(),soldat_lvlup.x + 95, soldat_lvlup.y+28);
		g.drawString("",soldat_lvlup.x + 125, soldat_lvlup.y+28);
		g.setColor(Color.green);
		g.drawString("--> "+(les_soldat.get_life()+8*les_soldat.get_lvl()),hover_lvlup_soldat.x + 130, hover_lvlup_soldat.y+28);
		
		g.setColor(Color.white);
		g.drawString("Atq :"+les_soldat.get_atq(),soldat_lvlup.x + 185, soldat_lvlup.y+28);
		g.drawString("",soldat_lvlup.x + 215, soldat_lvlup.y+28);
		g.setColor(Color.green);
		g.drawString("--> "+(les_soldat.get_atq()+13*les_soldat.get_lvl()),hover_lvlup_soldat.x + 220, hover_lvlup_soldat.y+28);
		
		if(info.get_or()<prix_lvl_soldat)
			g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(prix_lvl_soldat+" $", soldat_lvlup.x+200, soldat_lvlup.y+60);
		
		
		//CASE RETOUR
		g.drawImage(img.get(1),back.x,back.y-5, back.width,back.height+10, null);
		
		//CASE QTTE HERO
		g.setColor(Color.white);
		g.drawImage(img_hero, qtte_hero.x, qtte_hero.y, recthero.width, recthero.height,  null);
		g.drawImage(img_hero, qtte_hero.x+10, qtte_hero.y+4, recthero.width, recthero.height,  null);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("QTTE",qtte_hero.x+ 40, qtte_hero.y+20);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString(""+info.get_nb_hero_max(),qtte_hero.x+ 90, qtte_hero.y+20);
		g.setColor(Color.green);
		g.drawString("--> "+(info.get_nb_hero_max()+bonus_qtte_hero),hover_qtte.x+ 110, hover_qtte.y+20);
		if(info.get_or()<prix_qtte)
			g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(prix_qtte+" $", qtte_hero.x+200, qtte_hero.y+60);

		//CASE ETAGE SEAL 
		g.setColor(Color.white);
		g.drawImage(img.get(0), etage_seal.x+10, etage_seal.y+4, 20, 20,  null);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("SEAL",etage_seal.x+ 40, etage_seal.y+20);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString(""+info.get_nb_etage_seal(),etage_seal.x+ 90, etage_seal.y+20);
		g.setColor(Color.green);
		g.drawString("--> "+(info.get_nb_etage_seal()+bonus_etage_steal),hover_seal.x+ 110, hover_seal.y+20);
		if(info.get_or()<prix_seal)
			g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(prix_seal+" $", etage_seal.x+200, etage_seal.y+60);
		
		
		//CASE CDR
		g.setColor(Color.white);
		g.drawImage(img.get(2), CDR.x+10, CDR.y+1, 25, 25,  null);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString("CDR",CDR.x+ 40, CDR.y+20);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(" "+(float)les_soldat.get_CDR()/(float)1000+"s",CDR.x+ 90, CDR.y+20);
		g.setColor(Color.green);
		g.drawString("--> "+(float)(les_soldat.get_CDR()-bonus_CDR)/(float)1000 +"s",hover_CDR.x+ 125, hover_CDR.y+20);
		if(info.get_or()<prix_CDR)
			g.setColor(Color.RED);
		g.drawString(prix_CDR+" $", CDR.x+200, CDR.y+60);
	}
	
	
	public void ajout_stat(int x, int y) {
		if(x>hero_lvlup.x && x<hero_lvlup.getMaxX() && y>hero_lvlup.y && y<hero_lvlup.getMaxY() && info.get_or()>= prix_lvl_hero) {
			les_perso.next_lvl_hero();
			info.set_or(-prix_lvl_hero);
			bonus_prix_hero();
		}
		else if(x>qtte_hero.x && x<qtte_hero.getMaxX() && y>qtte_hero.y && y<qtte_hero.getMaxY() && info.get_or()>= prix_qtte) {
			info.set_nb_hero_max(bonus_qtte_hero);
			info.set_or(-prix_qtte);
			bonus_prix_qtte_hero();
		}
		else if(x>etage_seal.x && x<etage_seal.getMaxX() && y>etage_seal.y && y<etage_seal.getMaxY() && info.get_or()>=prix_seal && info.get_nb_etage_seal()+20<info.get_nb_etage()) {
			info.set_nb_etage_seal(bonus_etage_steal);	
			info.set_or(-prix_seal);
			bonus_prix_seal();
		}
		else if(x>soldat_lvlup.x && x<soldat_lvlup.getMaxX() && y>soldat_lvlup.y && y<soldat_lvlup.getMaxY() && info.get_or()>=prix_lvl_soldat) {
			les_soldat.next_lvl();
			info.set_or(-prix_lvl_soldat);
			bonus_prix_soldat();
		}
		else if(x>CDR.x && x<CDR.getMaxX() && y>CDR.y && y<CDR.getMaxY() && info.get_or()>=prix_CDR) {
			
			info.set_or(-prix_CDR);
			bonus_prix_CDR();
			les_soldat.set_CDR(bonus_CDR);
		}
			
	}
	public void next_step(Rectangle r) {
		if(r.intersects(hero_lvlup)) 
			hover_lvlup = new Rectangle(hero_lvlup.x,hero_lvlup.y,0,0);
		else
			hover_lvlup = new Rectangle(-10000,-10000,0,0);
		if(r.intersects(qtte_hero))
			hover_qtte = new Rectangle(qtte_hero.x,qtte_hero.y,0,0);
		else
			hover_qtte = new Rectangle(-10000,-10000,0,0);
		if(r.intersects(etage_seal))
			hover_seal = new Rectangle(etage_seal.x,etage_seal.y,0,0);
		else
			hover_seal = new Rectangle(-10000,-10000,0,0);
		if(r.intersects(CDR))
			hover_CDR = new Rectangle(CDR.x,CDR.y,0,0);
		else
			hover_CDR = new Rectangle(-10000,-10000,0,0);
		if(r.intersects(soldat_lvlup))
			hover_lvlup_soldat = new Rectangle(soldat_lvlup.x,soldat_lvlup.y,0,0);
		else
			hover_lvlup_soldat = new Rectangle(-10000,-10000,0,0);
		
	}
	public void moveMab(int nav, int x) {
		switch(nav) {
		default:
			break;
		case PAN_UP:
			if(qtte_hero.y>menu.y+40) {
			recthero.y-=x;
			hero_lvlup.y-=x;
			qtte_hero.y-=x;
			etage_seal.y-=x;
			}
			break;
		case PAN_DOWN:
			if(hero_lvlup.y<350) {
			recthero.y+=x;
			hero_lvlup.y+=x;
			qtte_hero.y+=x;
			etage_seal.y+=x;
			}
			break;
		}
		
	}
	
	public void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		BufferedImage spriteSheet = null;

		try {
			spriteSheet = loader.loadImage("Characters_set.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		img_hero = ss.grabSprite(32, 0, 32, 32);
		img_soldat = ss.grabSprite(128, 0, 32, 32);
	}
	
	
	public void souris_case(int x, int y){
		if(x>hero.x && x<hero.getMaxX() && y>hero.y && y<hero.getMaxY()) {
			menu = new Rectangle(0,510,399,500);
			hero = new Rectangle(-1000,-1000,0,0);
			soldat = new Rectangle(-1000,-1000,0,0);
			back = new Rectangle(280,520,100,30);
			recthero = new Rectangle(30,560,25,25);
			rectsoldat = new Rectangle(-1000,-1000,25,25);
			
			hero_lvlup = new Rectangle(30,560,300,80);
			qtte_hero = new Rectangle(30,660,300,80);
			etage_seal = new Rectangle(30,760,300,80);
			
			herob = true;
			close_m = false;
			compteur=0;
			}
		else if(x>soldat.x && x<soldat.getMaxX() && y>soldat.y && y<soldat.getMaxY()) {
			menu = new Rectangle(0,510,399,500);
			hero = new Rectangle(-1000,-1000,0,0);
			soldat = new Rectangle(-1000,-1000,0,0);
			back = new Rectangle(280,520,100,30);
			recthero = new Rectangle(-1000,-1000,25,25);
			rectsoldat = new Rectangle(30,560,25,25);
			
			soldat_lvlup = new Rectangle(30,560,300,80);
			CDR = new Rectangle(30,660,300,80);
			soldatb = true;
			close_m = false;
			compteur=0;
		}
		else if(x>back.x && x<back.getMaxX() && y>back.y && y<back.getMaxY() || y<menu.y && (soldatb || herob)) {
			menu = new Rectangle(0,300,399,500);
			hero = new Rectangle(50,520,100,30);
			soldat = new Rectangle(250,520,100,30);
			back = new Rectangle(0,0,0,0);
			recthero = new Rectangle(60,522,25,25);
			rectsoldat = new Rectangle(260,522,25,25);
			
			hero_lvlup = new Rectangle(-1000,-1000,0,0);
			qtte_hero = new Rectangle(-1000,-1000,0,0);
			etage_seal = new Rectangle(-1000,-1000,0,0);
			
			soldat_lvlup = new Rectangle(-1000,-1000,0,0);
			CDR = new Rectangle(-1000,-1000,0,0);
			close_m = true;
			soldatb = false;
			herob = false;
			compteur=0;
		}
	}
	public void bouge_hero() {
		if(menu.y>300)
			menu = new Rectangle(menu.x,menu.y-1,menu.width,menu.height);
		if(recthero.y>350)
			recthero = new Rectangle(recthero.x,recthero.y-1,recthero.width,recthero.height);
		if(hero_lvlup.y>350)
			hero_lvlup = new Rectangle(hero_lvlup.x,hero_lvlup.y-1,hero_lvlup.width,hero_lvlup.height);
		if(qtte_hero.y>450)
			qtte_hero = new Rectangle(qtte_hero.x,qtte_hero.y-1,qtte_hero.width,qtte_hero.height);
		if(etage_seal.y>550)
			etage_seal = new Rectangle(etage_seal.x,etage_seal.y-1,etage_seal.width,etage_seal.height);
		if(back.y>310)
			back = new Rectangle(back.x,back.y-1,back.width,back.height);
	}
	public void bouge_soldat() {
		if(menu.y>300)
			menu = new Rectangle(menu.x,menu.y-1,menu.width,menu.height);
		if(rectsoldat.y>350)
			rectsoldat = new Rectangle(rectsoldat.x,rectsoldat.y-1,rectsoldat.width,rectsoldat.height);
		if(soldat_lvlup.y>350)
			soldat_lvlup = new Rectangle(soldat_lvlup.x,soldat_lvlup.y-1,soldat_lvlup.width,soldat_lvlup.height);
		if(CDR.y>450)
			CDR = new Rectangle(CDR.x,CDR.y-1,CDR.width,CDR.height);
		if(back.y>310)
			back = new Rectangle(back.x,back.y-1,back.width,back.height);
	}
	public void menu_return() {
		if(menu.y<510)
			menu = new Rectangle(menu.x,menu.y+1,menu.width,menu.height);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			while(true) {
				if(herob) {
					bouge_hero();
					}
				if(soldatb) {
					bouge_soldat();
					}
				if(close_m) {
					menu_return();
					}
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setEtage(Etages eta) {les_etages =eta;}
	public void setInformation(Information inf) {info = inf;}
	public void setPersonnage(Personnages perso) {les_perso =perso;}
	public void setSoldat(Soldats soldat) {les_soldat=soldat;}
	public Rectangle getRecthero() {return hero;}
	public Rectangle getRectsoldat() {return soldat;}
	public Rectangle getRmenu() {return menu;}
	
	public void bonus_prix_hero() {prix_lvl_hero = 300*les_perso.getlvl();}
	public void bonus_prix_qtte_hero() {prix_qtte = 800*info.get_nb_hero_max();}
	public void bonus_prix_seal() {if(info.get_nb_etage_seal()>0)prix_seal = 10000*info.get_nb_etage_seal()/10;}
	public void bonus_prix_soldat() {prix_lvl_soldat = 500*(les_soldat.get_lvl()+1);}
	public void bonus_prix_CDR() {prix_CDR = 8000*les_soldat.get_count_CDR();}

}
