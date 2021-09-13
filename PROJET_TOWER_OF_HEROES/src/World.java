import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class World extends JPanel implements Runnable,MouseWheelListener, MouseMotionListener, MouseListener{
	
	
	
	
	//===============================================================================================================
	// INITIALISATION DES VARIABLE DU JEU
	//===============================================================================================================
	//POUR FAIRE CHARGER LE GRAPHICS POUR LE DRAW
	private Image dbImage;
	private Graphics dbg;
	
	
	//DIMENSION DEFINIE POUR LA FENETRE DE JEU
	static final int largeur = 600, hauteur = 400;
	static final Dimension gameDim = new Dimension(hauteur, largeur);
	
	
	//THREAD DU JEU
	private Thread game;
	//BOOLEAN POUR FAIRE TOURNER LE JEU
	private Boolean running = false;
	
	static final int PAN_UP = 1, PAN_DOWN = -1;
	
	
	//VARIABLE POUR LE SCROLLING DE LA MAP 
	private int stade_scroll = 0;
	
	
	
	//===============================================================================================================
	//CREATION DES VARIABLES DE TOUT LES OBJETS APPARTENANT AU JEU
	//===============================================================================================================
	
	//==========================================================
	//CREATION DU SOL, DU PREMIER ETAGE 
	//==========================================================
	// Y: 210 COORDONNE DE COMMENCEMENT POUR LES ETAGES SUIVANTS 
	private sol Sol = new sol(0,300);
	private Etages etages ;
	private Separateurs separates = new Separateurs(200);
	//CREATION DES PERSONNAGES
	private Personnages les_persos= new Personnages();
	//POUR LAFFICHAGE DES INFORMATION
	private Information inf = new Information();
	//CREATION DU MENU
	private Menu menu = new Menu();
	//CREATION DES SOLDATS
	private Soldats les_soldat = new Soldats();
	//CREATION DES MONSTRE
	private Monsters monstre = new Monsters();
	//CREATION CIEL
	private Ciel ciel = new Ciel();
	
	
	//VARIABLE POUR LE DRAG AND DROP 
	int drag_etat=0;
	int variable_stockage_dragl=0;
	boolean test_collision=true;
	boolean test=false;
	//VARIABLE COORDONNE SOURIE
	Rectangle r1;
	Rectangle r2 = new Rectangle(100, 0, 200, 1000);
	Rectangle r_hero;
	Rectangle r_move=new Rectangle(0,0,10,10);
	
	//coord de la sourie
	int mouse_X;
	int mouse_Y;
	
	int mouse_Xclick;
	int mouse_Yclick;
	
	int rectX;
	int rectY;
	
	//rayon de soleil
	Image img_sun = new ImageIcon("projet/sunbeam.png").getImage();
	
	Donnee_texte c = new Donnee_texte();
	
	int position;
	boolean bool_init=false;
	
	Rectangle deployer_save = new Rectangle(370,175,20,40);
	Rectangle saveR = new Rectangle(deployer_save.x+20,deployer_save.y-55,400,150);
	Rectangle deployer_nord = new Rectangle(100,0,200,25);
	Rectangle deployer_sud = new Rectangle(100,571,200,25);
	
	Rectangle bouton_save = new Rectangle(saveR.x+60,saveR.y+40,200,80);
	
	boolean save_rect_b=false;
	
	
	
	
	World(){
		setPreferredSize(gameDim);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		etages = new Etages(200,inf);
		
		//CONNEXION INFORMATION
		inf.setPerso(les_persos);
		inf.setEtage(etages);
		inf.setSoldats(les_soldat);
		//CONNEXION ETAGES
		etages.setInformation(inf);
		etages.setPersonnage(les_persos);
		etages.setSeparateur(separates);
		etages.setSoldat(les_soldat);
		etages.setMonstre(monstre);
		//CONEXION PERSONNAGE
		les_persos.setEtage(etages);
		les_persos.setInformation(inf);
		les_persos.setMenu(menu);
		les_persos.setSeparate(separates);
		les_persos.setMonster(monstre);
		//CONNEXION SOLDAT
		les_soldat.set_Etage(etages);
		les_soldat.set_Information(inf);
		les_soldat.set_Separateur(separates);
		les_soldat.set_Monstre(monstre);
		Thread t = new Thread(les_soldat);
		t.start();
		//CONNEXION MENU
		menu.setEtage(etages);
		menu.setInformation(inf);
		menu.setPersonnage(les_persos);
		menu.setSoldat(les_soldat);
		//CONNEXION MONSTRE
		monstre.setInformation(inf);
	}
	
	public void init() {
		System.out.println(position);
		ArrayList<ArrayList<String>> donnee;
		donnee = c.recup_info_init("information.txt");
		inf.set_init_or((Integer.parseInt(donnee.get(position).get(0))));
		for(int i=0;i<Integer.parseInt(donnee.get(position).get(2));i++) {
			etages.init_etage_en_plus();;
		}
		inf.set_init_seal((Integer.parseInt(donnee.get(position).get(1))));
		inf.set_init_qtte_hero((Integer.parseInt(donnee.get(position).get(3))));
		
		donnee = c.recup_info_init("hero.txt");
		int boucle = Integer.parseInt(donnee.get(position).get(0));
		for(int i=1;i<boucle;i++) {
			les_persos.next_lvl_hero();
		}
		donnee = c.recup_info_init("soldat.txt");
		boucle = Integer.parseInt(donnee.get(position).get(0));
		les_soldat.set_init_CDR(Integer.parseInt(donnee.get(position).get(1)));
		les_soldat.set_count_CDR(Integer.parseInt(donnee.get(position).get(2)));
		for(int i=0;i<boucle;i++) {
			les_soldat.next_lvl();
		}
		menu.bonus_prix_hero();
		menu.bonus_prix_qtte_hero();
		menu.bonus_prix_seal();
		menu.bonus_prix_soldat();
		menu.bonus_prix_CDR();
		
	}
	
	public void addNotify(){
		super.addNotify();
		startGame();
	}
	
	public void startGame(){
		if(game == null || !running){
			game = new Thread(this);
			game.start();
			running = true;
		}
	}
	
	public void stopGame() {
		if (running){
			running = false;
		}
	}
	
	private void gameUpdate(){
		if (running && game != null){
			//update game state
		}
	}
	
	private void gameRender() {
		if (dbImage == null) {// create the buffer
			dbImage = createImage(hauteur, largeur);
			if (dbImage == null) {
				System.err.println("dbImage is still null!");
				return;
			}
			else{
				dbg = dbImage.getGraphics();
			}
		}
		//Clear the screen
		dbg.setColor(Color.WHITE);
		dbg.fillRect(0, 0, hauteur, largeur);
		//Draw Game elements
		draw(dbg);
	}
	private void paintScreen(){
		Graphics g;
		try{
			g = this.getGraphics();
			if (dbImage != null && g != null){
				g.drawImage(dbImage, 0, 0, null);
			}
			Toolkit.getDefaultToolkit().sync(); // for some os such as linux
			g.dispose();
		}catch(Exception e){
			System.err.println(e);
		}
		
	}
	
	private void draw(Graphics g) {
		
		r1 = new Rectangle(mouse_X, mouse_Y, 10, 10);
		r_hero = new Rectangle(mouse_Xclick,mouse_Yclick,10,10);
		ciel.draw(g);
		Sol.draw(g);
		etages.draw(g);

		les_persos.draw(g);
		les_soldat.draw(g);
		monstre.draw(g);
		separates.draw(g);

		inf.draw(g);
		menu.draw(g);
		
		test_collision =r1.intersects(r2);
		etages.etage_en_plus();
		Graphics2D graph = (Graphics2D)g;
		float alpha1 = (float) 0.5;
		AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha1);
		graph.setComposite(acomp);
		graph.drawImage(img_sun,0,0,400,200, null);
		alpha1 = (float) 1;
		acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha1);
		graph.setComposite(acomp);
		
		
		if(save_rect_b) {
			graph.setColor(new Color(54,54,54,100));
			g.fillRect(0, 0, 400, 571);
		}
		graph.setColor(new Color(54,54,54));
		g.fillRect(deployer_nord.x, deployer_nord.y, deployer_nord.width, deployer_nord.height);
		g.fillRect(deployer_sud.x, deployer_sud.y, deployer_sud.width, deployer_sud.height);
		if(deployer_nord.y==0) {
			graph.setColor(new Color(254,254,225,120));
			if(rectX>deployer_nord.x && rectX<deployer_nord.getMaxX() && rectY>deployer_nord.y && rectY<deployer_nord.getMaxY())
				graph.setColor(new Color(179,0,0));
			graph.setFont(new Font("Arial", Font.BOLD, 15));
			graph.drawString("SAUVEGARDER", deployer_nord.x+40, deployer_nord.y+20);
		}
		if(deployer_nord.y==280) {
			graph.setColor(new Color(254,254,225,120));
			if(rectX>deployer_nord.x && rectX<deployer_nord.getMaxX() && rectY>deployer_nord.y && rectY<deployer_sud.getMaxY())
				graph.setColor(new Color(179,0,0));
			graph.setFont(new Font("Arial", Font.BOLD, 20));
			graph.drawString("SAUVEGARDER", deployer_nord.x+25, deployer_nord.y+30);
		}
		
		menu.next_step(r_move);
		mouse_Xclick = 0;
		mouse_Yclick = 0;
		les_persos.verif_life_hero();
		les_soldat.verif_life_soldat();
		
	}


	public void run() {
		
				try {
					while(running) {
						gameUpdate();
						gameRender();
						paintScreen();
						if(bool_init) {
							System.out.println("test");
							init();
							bool_init=false;
						}
						if(save_rect_b)
							deployer_rect_save();
						else
							reployer_rect_save();
						//log(""+save_rect_b);
						Thread.sleep(1);
						}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
			
		}
		
		
	}
	public void log(String S) {
		System.out.println(S);
	}
	public void moveMab(int nav, int x) {
		switch(nav) {
		default:
			break;
		case PAN_UP:
			
			break;
		case PAN_DOWN:
			
			break;
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) { 
        int notches = e.getWheelRotation(); 
        
        if(!r_move.intersects(menu.getRmenu())) {
	        if (notches < 0) { 
	            if(Sol.getRect().getMaxY()>570) {
	        		stade_scroll--;
	        		Sol.moveMab(Sol.PAN_UP,10);
	        		etages.moveMab(etages.PAN_UP,10);
	        		separates.moveMab(etages.PAN_UP,10);
	        		les_persos.moveMab(les_persos.PAN_UP, 10);
	        		les_soldat.moveMab(PAN_UP, 10);
	        		monstre.moveMab(PAN_UP, 10);
	        		moveMab(PAN_UP,10);
	        	}
	        } else { 
	        	if(etages.getRectangle().get(etages.getRectangle().size()-1).getY()-200<0) {
		            Sol.moveMab(Sol.PAN_DOWN,10);
		            etages.moveMab(etages.PAN_DOWN,10);
		            separates.moveMab(etages.PAN_DOWN,10);
		            les_persos.moveMab(les_persos.PAN_DOWN, 10);
		            les_soldat.moveMab(PAN_DOWN, 10);
		            monstre.moveMab(PAN_DOWN, 10);
		            moveMab(PAN_DOWN,10);
	        	}
	        	
	
	        } 
        }
        else {
        	if (notches < 0) { 
	        	menu.moveMab(PAN_UP, 10);   
	        } else {
	        	menu.moveMab(PAN_DOWN, 10);
	        	}

        }
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		int calcul;
		Rectangle R = new Rectangle(e.getX(),e.getY(),10,10);
		mouse_X = e.getX()-1;
		mouse_Y = e.getY()-1;
		
		if(!r_move.intersects(menu.getRmenu())) {
			if(drag_etat == 0) {
				variable_stockage_dragl = e.getY();
				drag_etat = 1;
			}
			else {
				calcul = variable_stockage_dragl - e.getY();
				if(!test_collision) {
					if(calcul > 0 ){
						if(Sol.getRect().getMaxY()>570) {
							Sol.moveMab(Sol.PAN_UP,2);
			        		etages.moveMab(etages.PAN_UP,2);
			        		separates.moveMab(etages.PAN_UP,2);
			        		les_persos.moveMab(les_persos.PAN_UP, 2);
			        		les_soldat.moveMab(PAN_UP, 2);
			        		monstre.moveMab(PAN_UP, 2);
			        		moveMab(PAN_UP,2);
						}
					}
					else if(calcul < 0){
						if(etages.getRectangle().get(etages.getRectangle().size()-1).getY()-200<0) {
							Sol.moveMab(Sol.PAN_DOWN,2);
				            etages.moveMab(etages.PAN_DOWN,2);
				            separates.moveMab(etages.PAN_DOWN,2);
				            les_persos.moveMab(les_persos.PAN_DOWN, 2);
				            les_soldat.moveMab(PAN_DOWN, 2);
				            monstre.moveMab(PAN_DOWN, 2);
				            moveMab(PAN_DOWN,2);
						}
					}
				}
				variable_stockage_dragl = e.getY();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		drag_etat = 0;
		mouse_Xclick = e.getX();
		mouse_Yclick = e.getY();
	} 
	@Override
	public void mouseMoved(MouseEvent e) {
		rectX = e.getX();
		rectY = e.getY();
		r_move = new Rectangle(rectX,rectY,2,2);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		mouse_Xclick = e.getX();
		mouse_Yclick = e.getY();
		//log(""+mouse_Xclick);
		//log(""+mouse_Yclick);
		if(!save_rect_b && !(rectX>deployer_nord.x && rectX<deployer_nord.getMaxX() && rectY>deployer_nord.y && rectY<deployer_nord.getMaxY())) {
			menu.souris_case(mouse_Xclick, mouse_Yclick);
			menu.ajout_stat(mouse_Xclick, mouse_Yclick);
			les_persos.personnage_en_plus(mouse_Xclick,mouse_Yclick);
		}
		if(mouse_Xclick>deployer_nord.x && mouse_Xclick<deployer_nord.getMaxX() && mouse_Yclick>deployer_nord.y && mouse_Yclick<deployer_nord.getMaxY() && !save_rect_b) {
			save_rect_b = true;
		}
		else if(mouse_Xclick>deployer_nord.x && mouse_Xclick<deployer_sud.getMaxX() && mouse_Yclick>deployer_nord.y && mouse_Yclick<deployer_sud.getMaxY() && save_rect_b) {
			save_rect_b = false;
			save();
		}
		
		// TODO Auto-generated method stub
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
	}
	
	public void deployer_rect_save() {
		if(deployer_nord.y<280) {
			deployer_nord = new Rectangle(deployer_nord.x,deployer_nord.y+1,deployer_nord.width,deployer_nord.height);
		}
		if(deployer_sud.y>305) {
			deployer_sud = new Rectangle(deployer_sud.x,deployer_sud.y-1,deployer_sud.width,deployer_sud.height);
		}
	}
	public void reployer_rect_save() {
		if(deployer_nord.y>0) {
			deployer_nord = new Rectangle(deployer_nord.x,deployer_nord.y-1,deployer_nord.width,deployer_nord.height);
		}
		if(deployer_sud.y<571) {
			deployer_sud = new Rectangle(deployer_sud.x,deployer_sud.y+1,deployer_sud.width,deployer_sud.height);
		}
	}
	
	public void save() {
		String[] information = {""+inf.get_or(),""+inf.get_nb_etage_seal(),""+inf.get_nb_etage(),""+inf.get_nb_hero_max()};
		String[] info_soldat = {""+les_soldat.get_lvl(),""+les_soldat.get_CDR(),""+les_soldat.get_count_CDR()};
		String[] info_hero = {""+les_persos.getlvl()};
		c.update_donne_in_file(information, "information.txt", position);
		c.update_donne_in_file(info_soldat, "soldat.txt", position);
		c.update_donne_in_file(info_hero, "hero.txt", position);
	}
	
	public void setposition(int posi) {position = posi;bool_init=true;}
	
	

	
	
	
	

}
