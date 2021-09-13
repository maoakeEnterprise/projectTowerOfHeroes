

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Donnee_texte {
	ArrayList<ArrayList<String>> donnee;
	Donnee_texte(){}
	
    public void add_donne_in_file(String[] donnee, String filetext){
        try {
            File file = init_file_text(filetext);
            ArrayList<ArrayList<String>> donnee_file = recup_info_init(filetext);
            BufferedWriter pww = new BufferedWriter(new FileWriter(file));
            PrintWriter pw = new PrintWriter(pww);
            //log(""+donnee_file.size());
            if(donnee_file.size()>0){
            	for(int i=0;i<donnee_file.size();i++) {
            		for(int j=0;j<donnee_file.get(i).size();j++) {
            			pw.print(donnee_file.get(i).get(j));
                        pw.print("::");
            		}
            		pw.println("");
            	}
            }
            for (int i=0;i<donnee.length;i++){
                pw.print(donnee[i]);
                pw.print("::");
            }
            pw.flush();
            pw.close();
        }catch (IOException e) {
        	log(e.getMessage());
        }

    }
    public void update_donne_in_file(String[] donnee, String filetext, int position) {
    	try {
            File file = init_file_text(filetext);
            ArrayList<ArrayList<String>> donnee_file = recup_info_init(filetext);
            BufferedWriter pww = new BufferedWriter(new FileWriter(file));
            PrintWriter pw = new PrintWriter(pww);
            //log(""+donnee_file.size());
            if(donnee_file.size()>0){
            	for(int i=0;i<donnee_file.size();i++) {
            		if(i!=position) {
	            		for(int j=0;j<donnee_file.get(i).size();j++) {
	            			pw.print(donnee_file.get(i).get(j));
	                        pw.print("::");
	            		}
            		}
            		if(i==position) {
            			for(int j=0;j<donnee_file.get(i).size();j++) {
                			pw.print(donnee[j]);
                            pw.print("::");
                		}
            		}
            		pw.println("");
            	}
            }
            pw.flush();
            pw.close();
        }catch (IOException e) {
        	log(e.getMessage());
        }
    }
    public ArrayList recup_info_init(String file){
        ArrayList<ArrayList<String>> donne=new ArrayList<>();
        ArrayList<String> stock;
        String[] stock_donne;
        try {
        	File f = init_file_text(file);
            FileInputStream pr = new FileInputStream(f);
            InputStreamReader inputStreamReader = new InputStreamReader(pr);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            while ((lines=bufferedReader.readLine())!=null) {
               stock_donne = lines.split("::");
               stock = new ArrayList<>();
               for(int i=0;i<stock_donne.length;i++)
                   stock.add(stock_donne[i]);

               donne.add(stock);
            }
            pr.close();
        }
        catch (IOException e){
        	log(e.getMessage());
        }
        return donne;
    }
    
    public File init_file_text(String name_file){
        File f = new File(name_file);
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
    
    private void log(String text) {
    	System.out.println(text);
    }
    
}
