import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Soldats implements Runnable{
	
	private ArrayList<Soldat> les_soldat = new ArrayList<Soldat>();
	private ArrayList<Rectangle> soldatR = new ArrayList<Rectangle>();
	
	//LES LIENS 
	private Information info;
	private Etages etage;
	private Separateurs separate;
	private Monsters monstre;
	
	//STAT
	private int lvl = 0;
	private int life=3;
	private int attaque=2;
	private int CDR=6000;
	private int count_CDR=1;
	
	Soldats(){}
	
	public void soldat_en_plus() {
		int seal= info.get_nb_etage_seal();
		int position_spawnY=0;
		int position_spawnX=0;
		if(lvl>0) {
			position_spawnX = (int)etage.getRectangle().get(seal).getX()+(int)etage.getRectangle().get(seal).getWidth()-44;
			position_spawnY = (int)etage.getRectangle().get(seal).getY()+(int)etage.getRectangle().get(seal).getHeight()-32;
			Rectangle stockage_rect = new Rectangle(position_spawnX,position_spawnY,32,32);
			Soldat soldat = new Soldat(stockage_rect, 1,0);
			soldat.setlattaque(attaque);
			soldat.setlife(life);
			soldat.setEtage(etage);
			soldat.setSeparateur(separate);
			soldat.setMonster(monstre);
			soldat.setInfo(info);
			Thread perso_thread = new Thread(soldat);
			perso_thread.start();
			les_soldat.add(soldat);	
			soldatR.add(stockage_rect);
			info.set_nb_soldat(1);
			
		}
	}
	public void draw(Graphics g) {
		for(int i=0;i<les_soldat.size();i++) {
			les_soldat.get(i).draw(g);
		}
	}
	public void init() {}
	public void next_lvl() {
		life+=(13*lvl);
		attaque+=(8*lvl);
		lvl+=1;
	}
	public void moveMab(int nav, int x) {
		for(int i=0;i<les_soldat.size();i++) {
			les_soldat.get(i).moveMab(nav, x);
		}
	}
	public void verif_life_soldat() {
		for(int i=0;i<les_soldat.size();i++) {
			if(!les_soldat.get(i).getMort()) {
				soldatR.remove(i);
				les_soldat.remove(i);
				info.set_nb_soldat(-1);
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true){
				soldat_en_plus();
				Thread.sleep(CDR);
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	public Soldats getSoldats() {return this;}
	public void set_Information(Information inf) {info = inf;}
	public void set_Etage(Etages etages) {etage= etages;}
	public void set_Separateur(Separateurs sprt) {separate = sprt;}
	public void set_Monstre(Monsters m) {monstre = m;};
	public ArrayList<Rectangle> getSoldatsR() {return soldatR;}
	public ArrayList<Soldat> getSoldatsArray(){return les_soldat;}
	public void set_lvl(int bonus) {lvl+=bonus;}
	public void set_life(int bonus) {life+=bonus;}
	public void set_atq(int bonus) {attaque+=bonus;}
	public void set_CDR(int bonus) {CDR-=bonus;count_CDR++;}
	public void set_init_CDR(int bonus) {CDR=bonus;}
	public int get_lvl() {return lvl;}
	public int get_life() {return life;}
	public int get_atq() {return attaque;}
	public int get_CDR() {return CDR;}
	public void set_count_CDR(int bonus) {count_CDR=bonus;}
	public int get_count_CDR() {return count_CDR;}
	
	

}
