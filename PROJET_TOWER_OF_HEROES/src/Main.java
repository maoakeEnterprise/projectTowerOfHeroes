import javax.swing.JFrame;

public class Main extends JFrame implements Runnable{
	World game = new World();
	start st;
	Thread t;
	Main(){
		st = new start();
		setSize(400,600);
		this.setTitle("TOWER OF MAOAKE");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		add(st);
		//add(game);
		t = new Thread(this);
		t.start();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main m = new Main();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				if(st.getstar()) {
					st.setstar(false);
					st.setVisible(false);
					game.setposition(st.getposition());
					add(game);
					
				}
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
