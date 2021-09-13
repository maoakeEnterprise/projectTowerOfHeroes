import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Separateurs {
	private int nb_sep;
	private int coordonne;
	private ArrayList<Integer> COORD = new ArrayList();
	private ArrayList<Rectangle> separateR = new ArrayList<Rectangle>();
	private ArrayList<Separateur> separate = new ArrayList<Separateur>();
	private Etages etages;
	private Rectangle sep_rect;
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	Separateurs(int coord){
		nb_sep=0;
		coordonne = coord;
		sep_rect = new Rectangle(100,coordonne+100,10,200);
		separateR.add(sep_rect);
		separate.add(new Separateur(sep_rect));
	}
	public void separate_en_plus() {
		COORD.add(coordonne);
		nb_sep++;
		sep_rect = new Rectangle(100,coordonne,10,200);
		separateR.add(sep_rect);
		separate.add(new Separateur(sep_rect));
		coordonne-=100;
	}
	public void draw(Graphics g) {
		for(int i=1;i<separate.size();i++) {
			separate.get(i).draw(g);
		}
	}
	public void moveMab(int nav, int x) {
		
		for(int i=0;i<separate.size();i++) {
			separate.get(i).moveMab(nav,x);
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
	public Separateurs getSeparateur() {return this;}
	public ArrayList<Rectangle> getRectSeparateur(){return separateR;}
	public ArrayList<Separateur> getUnSeparateur(){return separate;}
}
