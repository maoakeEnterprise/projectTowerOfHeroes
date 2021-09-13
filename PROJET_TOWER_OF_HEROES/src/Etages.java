import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Etages {
	int nb_etage;
	int nb_etage_seal;
	int coordonne;
	int etat;
	private ArrayList<Etage> etage = new ArrayList<Etage>();
	private ArrayList<Rectangle> etageR = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> echelleR = new ArrayList<Rectangle>();
	Rectangle stockage_rect;
	Rectangle stockage_echelle;
	//LES LIENS 
	private Personnages les_perso;
	private Information info;
	private Separateurs separates;
	private Soldats les_soldat;
	private Monsters monstre;
	
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	Etages(int coord, Information inf){
		info = inf;
		nb_etage=0;
		nb_etage_seal=0;
		coordonne = coord;
		stockage_rect= new Rectangle(100,coordonne,200,100);
		etageR.add(stockage_rect);
		etage.add(new Etage(stockage_rect,0,0,info));
		stockage_echelle = new Rectangle(100+20,200,20,100);
		echelleR.add(stockage_echelle);
		init_echelle(coordonne);
	}
	public void init_echelle(int coord) {
		int coordonne = coord;
		for(int i=0;i<5000;i++) {
			coordonne-=100;
			if(i%2 == 0)
				stockage_echelle = new Rectangle(100+160,coordonne,20,100);
			else
				stockage_echelle = new Rectangle(100+20,coordonne,20,100);
			echelleR.add(stockage_echelle);
		}
		
	}
	public void etage_en_plus() {
		for(int i=0;i<les_perso.getPersoRect().size();i++) {
			if(les_perso.getPersoRect().get(i).intersects(echelleR.get(info.get_nb_etage()))){
			coordonne-=100;
			stockage_rect = new Rectangle(100,coordonne,200,100);
			
			etageR.add(stockage_rect);
	
			nb_etage++;
			if(etage.size()%2 == 0) {
				etat=2;
				monstre.ajoute_monstre(new Rectangle(140,coordonne+60,40,0), etat,etage.size()-1);
			}else {
				etat = 1;
				monstre.ajoute_monstre(new Rectangle(220,coordonne+60,40,0), etat,etage.size()-1);
			}
			etage.add(new Etage(stockage_rect,etat, info.get_nb_etage()+1,info));
			info.set_nb_etage();
			separates.separate_en_plus();
			
			}
		}
		for(int i=0;i<les_soldat.getSoldatsR().size();i++) {
			if(les_soldat.getSoldatsR().get(i).intersects(echelleR.get(info.get_nb_etage()))){
			coordonne-=100;
			stockage_rect = new Rectangle(100,coordonne,200,100);
			
			etageR.add(stockage_rect);
	
			nb_etage++;
			if(etage.size()%2 == 0) {
				etat=2;
				monstre.ajoute_monstre(new Rectangle(140,coordonne+60,40,0), etat,i);
			}else {
				etat = 1;
				monstre.ajoute_monstre(new Rectangle(220,coordonne+60,40,0), etat,i);
			}
			etage.add(new Etage(stockage_rect,etat, info.get_nb_etage()+1,info));
			info.set_nb_etage();
			separates.separate_en_plus();
			}
		}

	}
	public void init_etage_en_plus() {
		coordonne-=100;
		stockage_rect = new Rectangle(100,coordonne,200,100);
		
		etageR.add(stockage_rect);

		nb_etage++;
		if(etage.size()%2 == 0) {
			etat=2;
			monstre.ajoute_monstre(new Rectangle(140,coordonne+60,40,0), etat,etage.size()-1);
		}else {
			etat = 1;
			monstre.ajoute_monstre(new Rectangle(220,coordonne+60,40,0), etat,etage.size()-1);
		}
		etage.add(new Etage(stockage_rect,etat, info.get_nb_etage()+1,info));
		info.set_nb_etage();
		separates.separate_en_plus();
	}
	public void draw(Graphics g) {
		for(int i=0;i<etage.size();i++) {
			etage.get(i).draw(g);
		}
	}
	public void moveMab(int nav, int x) {
		
		for(int i=0;i<etage.size();i++) {
			etage.get(i).moveMab(nav, x);
		}
		for(int i=0;i<echelleR.size();i++) {
			switch(nav) {
			default:
				break;
			case PAN_UP:
				echelleR.get(i).y-=x;
				break;
			case PAN_DOWN:
				echelleR.get(i).y+=x;
				break;
			}
		}
		switch(nav) {
		default:
			break;
		case PAN_UP:
			coordonne-=x;
			break;
		case PAN_DOWN:
			coordonne+=x;
			break;
		}
		
	}
	public ArrayList<Rectangle> getRectangle() {return etageR;}
	public ArrayList<Rectangle> getEchelle() {return echelleR;}
	public void setPersonnage(Personnages perso) {les_perso =perso;}
	public void setInformation(Information inf) {info =inf;}
	public void setSeparateur(Separateurs sepa) {separates=sepa;}
	public void setMonstre(Monsters m) {monstre = m;}
	public Etages getEtages() {return this;}
	public void setSoldat(Soldats soldat) {les_soldat=soldat;}
	
}
