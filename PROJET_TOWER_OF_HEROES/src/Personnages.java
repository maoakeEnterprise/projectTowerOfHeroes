import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Personnages{
	private ArrayList<Personnage> les_perso = new ArrayList<Personnage>();
	private ArrayList<Rectangle> persoR = new ArrayList<Rectangle>();
	Rectangle stockage_rect;
	//LES LIENS 
	private Menu menu;
	private Etages les_etages;
	private Information info;
	private Separateurs separate;
	private Monsters monstre;
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	
	
	private int lvl = 1;
	private int life=3;
	private int attaque=4;
	private Thread t;
	
	Random rd = new Random();
	
	int nx;
	int ny;
	
	Personnages(){
	}
	
	public void personnage_en_plus(int x, int y) {
		 Boolean run_hero=false;
		int position_spawnX=0;
		int position_spawnY=0;
		int etat=0;
		int etat_etage=1;
		if(info.get_nb_hero_actuel()<info.get_nb_hero_max() && !(x>menu.getRmenu().x && x<menu.getRmenu().getMaxX() && y>menu.getRmenu().y && y<menu.getRmenu().getMaxY()) ) {
			for(int i=0;i<les_etages.getRectangle().size();i++){
				if(x>les_etages.getRectangle().get(i).x && x<les_etages.getRectangle().get(i).getMaxX() && y>les_etages.getRectangle().get(i).y && y<les_etages.getRectangle().get(i).getMaxY()) {
					info.set_nb_hero_actuel(1);
					run_hero=true;
					if(i%2==1) {
						nx = rd.nextInt(32)-16;
						
						position_spawnX = (int)les_etages.getRectangle().get(i).getX()+20+nx;
						etat = 2;
					}
					else {
						nx = rd.nextInt(32)-16;
						position_spawnX = (int)les_etages.getRectangle().get(i).getX()+(int)les_etages.getRectangle().get(i).getWidth()-55+nx;
						etat =1;
					}
					ny = rd.nextInt(32)-16;
					position_spawnY = (int)les_etages.getRectangle().get(i).getY()+(int)les_etages.getRectangle().get(i).getHeight()-50+ny;
				}
			}

			if(run_hero) {
				stockage_rect = new Rectangle(position_spawnX,position_spawnY,32,32);
				Personnage perso = new Personnage(stockage_rect, etat,etat_etage);
				perso.setlattaque(attaque);
				perso.setlife(life);
				perso.setlvl(lvl);
				perso.setEtage(les_etages);
				perso.setSeparateur(separate);
				perso.setMonster(monstre);
				perso.setInfo(info);
				Thread perso_thread = new Thread(perso);
				perso_thread.start();
				les_perso.add(perso);	
				persoR.add(stockage_rect);
			}
		}
	}
	
	public void draw(Graphics g) {
		for(int i=0;i<les_perso.size();i++) {
			les_perso.get(i).draw(g);
		}
	}
	
	public void moveMab(int nav, int x) {
		
		for(int i=0;i<les_perso.size();i++) {
			les_perso.get(i).moveMab(nav, x);
		}
	}
	public void next_lvl_hero() {
		life+=(10*lvl);
		attaque+=(5*lvl);
		lvl+=1;
	}
	public void verif_life_hero() {
		for(int i=0;i<les_perso.size();i++) {
			if(!les_perso.get(i).getMort()) {
				persoR.remove(i);
				les_perso.remove(i);
				info.set_nb_hero_actuel(-1);
			}
		}
	}
	public Personnages getPersonnages() {return this;}
	public void setEtage(Etages eta) {les_etages =eta;}
	public void setInformation(Information inf) {info = inf;}
	public ArrayList<Rectangle> getPersoRect(){return persoR;}
	public ArrayList<Personnage> getPerso(){return les_perso;}
	public int getlife() {return life;}
	public int getlvl() {return lvl;}
	public int getattaque() {return attaque;}
	public void setMenu(Menu m) {menu = m;}
	public void setSeparate(Separateurs s) {separate =s ;}
	public void setMonster(Monsters m) {monstre = m;}
	
	
}
