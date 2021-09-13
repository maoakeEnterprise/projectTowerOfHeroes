import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class start extends JPanel implements ActionListener{
	JButton valider = new JButton("Valider");
	JTextField pseudo = new JTextField(15);
	JTextField mdp = new JTextField(15);
	
	private String pseudonyme;
	private String mdpp;
	int position;
	
	JPanel p;
	
	Donnee_texte c;
	
	static final int largeur = 600, hauteur = 400;
	static final Dimension gameDim = new Dimension(hauteur, largeur);
	
	private boolean star=false;
	start(){
		setPreferredSize(gameDim);
		this.setLayout(new GridBagLayout());
		
		c = new Donnee_texte();
		
		GridBagConstraints gbc =  new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		
		p = new JPanel(new BorderLayout());
		p.add(new JLabel("TOWER DU BIG BOSS"),BorderLayout.CENTER);
		
		add(p,gbc);
		
		p = new JPanel(new BorderLayout());
		p.add(new JLabel("PSEUDO :"),BorderLayout.CENTER);
		
		add(p,gbc);
		
		p = new JPanel(new BorderLayout());
		p.add(pseudo);
		add(p,gbc);
		
		p = new JPanel(new BorderLayout());
		p.add(new JLabel("MOT DE PASSE : "),BorderLayout.CENTER);
		add(p,gbc);
		
		p = new JPanel(new BorderLayout());
		p.add(mdp);
		add(p,gbc);
		
		p = new JPanel(new BorderLayout());
		p.add(valider);
		valider.addActionListener(this);
		add(p,gbc);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		boolean verif=true;
		boolean verif1=false;
		if(e.getSource() == valider && !pseudo.getText().isEmpty() && !mdp.getText().isEmpty()) {
			ArrayList<ArrayList<String>> pseudotab = c.recup_info_init("pseudo.txt");
			
			if(pseudotab.size()!=0) {
				for(int i=0;i<pseudotab.size();i++) {
					
					if(pseudotab.get(i).get(0).equals(pseudo.getText())  && !pseudotab.get(i).get(1).equals(mdp.getText()) ) {
						verif = false;
						}
					else if(pseudotab.get(i).get(0).equals(pseudo.getText()) && pseudotab.get(i).get(1).equals(mdp.getText())) {
						verif1 = true;
						verif=false;
						position = i;
						}
				}
				if(verif) {
					String[] info = {pseudo.getText(),mdp.getText()};
					c.add_donne_in_file(info, "pseudo.txt");
					String[] information = {"0","0","0","5"};
					c.add_donne_in_file(information, "information.txt");
					String[] soldat = {"0","6000","1"};
					c.add_donne_in_file(soldat, "soldat.txt");
					String[] hero = {"1"};
					c.add_donne_in_file(hero, "hero.txt");
					position=pseudotab.size();
					star = true;
				}
				if(verif1) {
					star = true;
				}
			}
			else {
				star = true;
				String[] info = {pseudo.getText(),mdp.getText()};
				c.add_donne_in_file(info, "pseudo.txt");
				String[] information = {"0","0","0","5"};
				c.add_donne_in_file(information, "information.txt");
				String[] soldat = {"0","6000","1"};
				c.add_donne_in_file(soldat, "soldat.txt");
				String[] hero = {"1"};
				c.add_donne_in_file(hero, "hero.txt");
				position = 0;
			}
		}
		
	}
	
	public boolean getstar() {return star;}
	public void setstar(boolean t) {star = t;}
	public void setpseudo(String s) {pseudonyme = s;}
	public void setmdp(String s) {mdpp = s;}
	public String getpseudo() {return pseudonyme;}
	public String getmdp() {return mdpp;}
	public int getposition() {return position;}

}
