import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Monsters{
	//LIAISON
	private Etages etage;
	private Personnages perso;
	private Soldats soldat;
	private Information info;
	
	
	private Random rd = new Random();
	private int n;
	//rd.nextInt(3)+1;
	
	private ArrayList<Monster> monstre = new ArrayList<Monster>();
	private ArrayList<Rectangle> monstreR = new ArrayList<Rectangle>();
	
	Monsters(){
		
	}
	
	public void moveMab(int nav, int x) {
		for(int i=0;i<monstre.size();i++) {
			monstre.get(i).moveMab(nav, x);
		}
	}
	
	public void ajoute_monstre(Rectangle r, int etat, int i) {
		Monster m = new Monster(r, etat,i);
		m.setlife(i*4);
		m.setatq(i*2);
		m.setlifemax(m.getlife());
		m.setInformation(info);
		monstre.add(m);
		monstreR.add(r);
	}
	public void draw(Graphics g) {
		for(int i=0;i<monstre.size();i++)
			monstre.get(i).draw(g);
	}
	
	public Monsters getMonsters() {return this;}
	public ArrayList<Rectangle> getRectMonster() {return monstreR;}
	public ArrayList<Monster> getTabMonster(){return monstre;}
	public void setEtages(Etages e) {etage=e;}
	public void setInformation(Information inf) {info=inf;}
	

}
