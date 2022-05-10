package com.example.paraulogic;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //ELEMENTS GRÀFICS
    private final int[]IDBOTONS={R.id.lletra1,R.id.lletra2,R.id.lletra3,
            R.id.lletra4,R.id.lletra5,R.id.lletra6,R.id.lletraC};
    private UnsortedArraySet<Character> set;
    private BSTMapping<String,Integer> bst;
    private Button btns[];
    private int nParaules;
    //DECLARACIÓ CONSTANTS
    private final int NBOTONS=7;
    private final int NLLETRES=7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

    }
    private void initComponents(){
        set=new UnsortedArraySet<Character>(NLLETRES);
        btns=new Button[NBOTONS];
        bst=new BSTMapping<String,Integer>();
        nParaules=0;
        generateRandomArraySet();//create 7 letters set
        initButtons();           //init buttons and setText
    }
    /*
    S'encarrega d'escriure la lletra continguda en el botó
    dins el TextView de la paraula que es va formant
    */
    public void setLletra(View view){
        Button btn = (Button) findViewById ( view.getId());
        TextView word=(TextView) findViewById ( R.id.paraula );

        String txtOriginal=(String) word.getText();
        word.setText(txtOriginal + (String) btn.getText());
    }

    private void generateRandomArraySet(){
        //init letters
        for (int i = 0; i < NLLETRES; i++) {
            generateLetter();
        }

    }

    private void initButtons(){
        int j=0;
        Iterator it=set.iterator();
        //init buttons
        for (int i = 0; i < NBOTONS; i++) {
            btns[i]=(Button) findViewById(IDBOTONS[i]);
            btns[i].setText(it.next().toString());
        }

    }
    //comment
    private void generateLetter(){
        Random rand=new Random(System.currentTimeMillis());
        int letra=65+rand.nextInt(26);
        //si repetida, afegeix una diferent
        while(set.add((char) letra)==false){
            letra=65+rand.nextInt(26);
        }
    }
    //Aplicam l'array alterat als botons blaus
    public void shuffle(View view){
        char letters[]=unsortedAS2CharArray();

        randomize(letters,NLLETRES-1);
        //Actualitzam el text dels botons exteriors amb el nou array mesclat
        for (int i = 0; i < NBOTONS-1; i++) {
            btns[i].setText(Character.toString(letters[i]));
        }

    }

    private char[] unsortedAS2CharArray(){
        char lletres[]=new char[NLLETRES-1];
        Iterator it=set.iterator();
        for(int i=0;(it.hasNext()&&(i<NLLETRES-1));i++){
            lletres[i]=(char)it.next();
        }

        return lletres;
    }
    //Métode que mescla un array
    static void randomize( char arr[], int n) {
        // Creating a object for Random class
        Random r = new Random(System.currentTimeMillis());

        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = n-1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = r.nextInt(i+1);

            // Swap arr[i] with the element at random index
            char temp = (char)arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

    }
    public void suprimeix(View view){
        TextView word=(TextView) findViewById ( R.id.paraula );
        String str=(String)word.getText();
        //CONTROL ERRORS SI NO HI HA LLETRES!!!!
        if(str.length()>0){
            word.setText(str.substring(0,str.length()-1));
        }

    }

    public void introdueix(View view){
        TextView word=(TextView) findViewById ( R.id.paraula );
        TextView paraulesTrobades=(TextView) findViewById ( R.id.paraulesTrobades );
        String str=(String) word.getText(); //obtenim la paraula central
        Integer times=0;
        //compleix les condicions?
        if(esCorrecte(str)){
            times=bst.get(str);  //obtenim aparicions anteriors
            System.out.println("APARICIONS "+"paraula "+str+" "+times);
            if(times==null){//si no hi havia la paraula
                bst.put(str,1);
                nParaules++;
            }else{ //si ja hi havia la paraula
                bst.put(str,times+1);
            }
            //visualització llista
            paraulesTrobades.setText(Llista());

        }
        //FALTA UN ELSE---> LA PARAULA NO ÉS CORRECTE


    }
    private boolean esCorrecte(String str){
        //Obtenim lletra central
        String lletraC=(String)btns[6].getText();

        return (str.length()>=3) && (str.contains(lletraC));
    }
    private String Llista(){
        String s="Has introduït: "+nParaules+" paraules:";
        Iterator it=bst.IteratorBSTMapping();
        BSTMapping.Pair p;
        if(it.hasNext()){
            p=(BSTMapping.Pair)it.next();
            s+=p.getKey()+" ("+p.getValue()+")";
        }
        while(it.hasNext()){
            p=(BSTMapping.Pair)it.next();
            s+=", "+p.getKey()+" ("+p.getValue()+")";
        }
        return s;
    }
}