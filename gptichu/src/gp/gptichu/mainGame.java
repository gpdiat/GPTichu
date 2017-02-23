package gp.gptichu;

import gp.gptichu.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class mainGame extends Activity {
	private TichuDataSource datasource;
	private int rowId=1000;
	private int from_this_method = 0;
	private int score1Id=1;
	private int score2Id=2;
	private int textview1Id=2000;
	private int textview2Id=2001;
	private int round_num = 1;
	private int edit_score = 0; 
	private int edit_score2 = 0;
	private int in = 0;
	private boolean radio_pressed = false;
	private boolean the_end = false;
	private boolean tichu_said1 = false, gtichu_said1=false;
	private boolean tichu_said2 = false, gtichu_said2=false;
	private boolean tichu_said3 = false, gtichu_said3=false;
	private boolean tichu_said4 = false, gtichu_said4=false;
	private boolean ntichu_said1 = false, ngtichu_said1=false;
	private boolean ntichu_said2 = false, ngtichu_said2=false;
	private boolean ntichu_said3 = false, ngtichu_said3=false;
	private boolean ntichu_said4 = false, ngtichu_said4=false;
	private boolean tichu_undo1 = false;
	private boolean tichu_undo2 = false;
	private boolean tichu_undo3 = false;
	private boolean tichu_undo4 = false;
	private boolean ntichu_undo1 = false;
	private boolean ntichu_undo2 = false;
	private boolean ntichu_undo3 = false;
	private boolean ntichu_undo4 = false;
	private boolean gtichu_undo1 = false;
	private boolean gtichu_undo2 = false;
	private boolean gtichu_undo3 = false;
	private boolean gtichu_undo4 = false;
    private boolean ngtichu_undo1 = false;
    private boolean ngtichu_undo2 = false;
    private boolean ngtichu_undo3 = false;
    private boolean ngtichu_undo4 = false;
    private boolean onetwo1 = false, onetwo2=false;
    private EditText newscore1;
    private EditText newscore2;
    private boolean old_game = false;
    private int sum1=0;
    private int sum2=0;
    private int time=0;
    private int time1=0;
    private int temp1,temp2;
    private int count_fail = 0;
    boolean clickable = true, addtext = false, addtext1 = false;
    private String[] names = new String[50];
    private String[] names_Team1 = new String[2];
    private String[] names_Team2 = new String[2];
    private String[] names_TeamS = new String[4];
    private String team1,team2;
    public void onCreate(Bundle icicle) {
    	super.onCreate(icicle);
    	setContentView(R.layout.scores);
        datasource = new TichuDataSource(this);
        datasource.open();
        final AlertDialog alertDialog = new AlertDialog.Builder(mainGame.this).create();
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
    	Bundle extras = getIntent().getExtras();
        if(extras!=null) {
        	old_game = extras.getBoolean("old_game");
        	TableLayout tl = (TableLayout)findViewById(R.id.tableLayout1);
        	TableRow tr = new TableRow(this);
        	tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        //	TextView empty1 = new TextView(this);
        //	TextView empty2 = new TextView(this);
       // 	TextView tv = new TextView(this);
       // 	TextView tv1 = new TextView(this);
        	TextView double_1 = (TextView)findViewById(R.id.double1);
        	TextView double_2 = (TextView)findViewById(R.id.double2);
      	  	team1 = extras.getString("team1");
      	  	team2 = extras.getString("team2");
    		int result = team1.compareTo(team2);
    		boolean into = false;
    		if (result > 0) {
    			String temp;
    			temp = team1;
    			team1 = team2;
    			team2 = temp;
    			into = true;
    		}
        	/**/
        	TextView tv = (TextView)findViewById(R.id.team1name);
        	TextView tv1 = (TextView)findViewById(R.id.team2name);
        	tv.setText(team1);
      	  	tv1.setText(team2); 
        	/**/
      	  	double_1.setText(team1);
      	  	double_2.setText(team2);
      	  //	tv.setText(team1);
      	  //	tv1.setText(team2); 
      	  	
    /*	  tv.setTextColor(Color.parseColor("#ffaa55"));
          tv.setTextSize(18);
          tv.setPadding(0, 0, 20, 20);
          tv.setWidth(170);
          tv.setGravity(Gravity.CENTER);
          tv1.setGravity(Gravity.CENTER);
          tv1.setTextColor(Color.parseColor("#ffaa55"));
          tv1.setTextSize(18);
          tv1.setPadding(0, 0, 0, 20);
          tv1.setWidth(170);
          tr.addView(empty1);
          tr.addView(empty2);
          tr.addView(tv);
          tr.addView(tv1);
          tr.setId(rowId); 
      	tl.addView(tr,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
        		  LayoutParams.WRAP_CONTENT));
        		  */   
          rowId++;  

          if(old_game == true){
      	  	TextView pl1 = (TextView)findViewById(R.id.tichu_pl1);
      	  	TextView pl2 = (TextView)findViewById(R.id.tichu_pl2);
      	  	TextView pl3 = (TextView)findViewById(R.id.tichu_pl3);
      	  	TextView pl4 = (TextView)findViewById(R.id.tichu_pl4);
      	  	
        	  Button undo = (Button)findViewById(R.id.undo);
              undo.setEnabled(false);     
              String[] oldgame = new String[50];
              String[] olddealers = new String[4];
              int round_old = 1;
              olddealers = datasource.getOldDealers(team1,team2);
              names[0]=olddealers[0];
              names[1]=olddealers[1];
              names[2]=olddealers[2];
              names[3]=olddealers[3];
              names_Team1=datasource.getTeamPlayers(team1);
              names_Team2=datasource.getTeamPlayers(team2);
              if((names_Team1!=null) && (names_Team2!=null)) {
            	  names_TeamS[0] =  names_Team1[0];       
            	  names_TeamS[1] =  names_Team2[0];       
            	  names_TeamS[2] =  names_Team1[1];       
            	  names_TeamS[3] =  names_Team2[1]; 
              }
              if(names_TeamS[0].length() > 10) {
      	  		char a,b,c,d,e;
      	  		a = names_TeamS[0].charAt(0);
      	  		b = names_TeamS[0].charAt(1);
      	  		c = names_TeamS[0].charAt(2);
      	  		d = names_TeamS[0].charAt(3);
      	  		e = names_TeamS[0].charAt(4);
      	  		String astring = "";
      	  		astring = astring + a;
      	  		astring = astring + b;
      	  		astring = astring + c;
      	  		astring = astring + d;
      	  		astring = astring + e;
      	  		astring = astring + "...";
      	  		pl1.setText(astring);
      	   }
      	   else {
      	  		pl1.setText(names_TeamS[0]);
      	   }
      	if(names_TeamS[1].length() > 10){
     		 char a,b,c,d,e;
          	a = names_TeamS[1].charAt(0);
          	b = names_TeamS[1].charAt(1);
          	c = names_TeamS[1].charAt(2);
          	d = names_TeamS[1].charAt(3);
         	e = names_TeamS[1].charAt(4);
          	String astring = "";
          	astring = astring + a;
          	astring = astring + b;
          	astring = astring + c;
          	astring = astring + d;
          	astring = astring + e;
          	astring = astring + "...";
  	  		pl2.setText(astring);
     	   }
     	   else{
      	  		pl2.setText(names_TeamS[1]);
     	   }
      	if(names_TeamS[2].length() > 10){
     		 char a,b,c,d,e;
          	a = names_TeamS[2].charAt(0);
          	b = names_TeamS[2].charAt(1);
          	c = names_TeamS[2].charAt(2);
          	d = names_TeamS[2].charAt(3);
         	e = names_TeamS[2].charAt(4);
          	String astring = "";
          	astring = astring + a;
          	astring = astring + b;
          	astring = astring + c;
          	astring = astring + d;
          	astring = astring + e;
          	astring = astring + "...";
  	  		pl3.setText(astring);
     	   }
     	   else{
      	  		pl3.setText(names_TeamS[2]);
     	   }
      	if(names_TeamS[3].length() > 10){
     		 char a,b,c,d,e;
          	a = names_TeamS[3].charAt(0);
          	b = names_TeamS[3].charAt(1);
          	c = names_TeamS[3].charAt(2);
          	d = names_TeamS[3].charAt(3);
         	e = names_TeamS[3].charAt(4);
          	String astring = "";
          	astring = astring + a;
          	astring = astring + b;
          	astring = astring + c;
          	astring = astring + d;
          	astring = astring + e;
          	astring = astring + "...";
  	  		pl4.setText(astring);
     	   }
     	   else{
      	  		pl4.setText(names_TeamS[3]);
     	   }  
              
              
              TableLayout oldtl = (TableLayout)findViewById(R.id.tableLayout1);
              while(datasource.oldExists(team1, team2, round_old) == true){
            	  oldgame = datasource.getOldGame(team1, team2, round_old);
              	  TextView overall1= new TextView(getApplicationContext());
              	  TextView overall2 = new TextView(getApplicationContext());
              	  TextView round= new TextView(getApplicationContext());
            	  TextView deal = new TextView(getApplicationContext());
            	  round.setId(textview1Id);
            	  deal.setId(textview2Id);
                  textview1Id += 2;
                  textview2Id += 2;
              	    overall1.setText(oldgame[0]);
                    overall2.setText(oldgame[1]);
                    overall1.setGravity(Gravity.CENTER);
                    overall2.setGravity(Gravity.CENTER);  
              	    TableRow row = new TableRow(this);
              	    round.setWidth(35);
              	    round.setTextSize(12);
              	    round.setText(Integer.toString(round_old)+".");
              	   if(names[Integer.parseInt(oldgame[2])].length() >5){
              		 char a,b,c,d,e;
                   	a = names[Integer.parseInt(oldgame[2])].charAt(0);
                   	b = names[Integer.parseInt(oldgame[2])].charAt(1);
                   	c = names[Integer.parseInt(oldgame[2])].charAt(2);
                   	d = names[Integer.parseInt(oldgame[2])].charAt(3);
                  	e = names[Integer.parseInt(oldgame[2])].charAt(4);
                   	String astring = "";
                   	astring = astring + a;
                   	astring = astring + b;
                   	astring = astring + c;
                   	astring = astring + d;
                   	astring = astring + e;
                   	astring = astring + "...";
              		 deal.setText(astring);
              	   }
              	   else{
              	    deal.setText(names[Integer.parseInt(oldgame[2])]);
              	   }
              	    round.setPadding(0, 0, 3, 0);
              	    deal.setWidth(80);
              	    deal.setTextSize(12);
              	  overall1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
              	  overall2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
           //         overall1.setWidth(170);
           //         overall2.setWidth(170);
                    row.setId(rowId);
                    row.addView(round);
              	    row.addView(deal);
                    row.addView(overall1);
                    row.addView(overall2); 
                    round_old++;
                    rowId++;
                    oldtl.addView(row,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                  		  LayoutParams.WRAP_CONTENT));
            	  ////////////////////////////////////////////
              }
              sum1 = Integer.parseInt(oldgame[0]);
              sum2 = Integer.parseInt(oldgame[1]);
              TableRow row1 = new TableRow(this);
              newscore1 = new EditText(this);
              newscore2 = new EditText(this);
              TextView round1= new TextView(getApplicationContext());
        	  TextView deal1 = new TextView(getApplicationContext());
        	  round1.setId(textview1Id);
        	  deal1.setId(textview2Id);
              textview1Id += 2;
              textview2Id += 2;
        	    round1.setWidth(35);
        	    round1.setTextSize(12);
        	    round1.setText(Integer.toString(round_old)+".");
                time = Integer.parseInt(oldgame[2])+1;
                if(time == 4){
                	time = 0;
                }
                
                if(names[time].length() >5){
                	char a,b,c,d,e;
                  	a = names[time].charAt(0);
                  	b = names[time].charAt(1);
                  	c = names[time].charAt(2);
                  	d = names[time].charAt(3);
                 	e = names[time].charAt(4);
                  	String astring = "";
                  	astring = astring + a;
                  	astring = astring + b;
                  	astring = astring + c;
                  	astring = astring + d;
                  	astring = astring + e;
                  	astring = astring + "...";
                	deal1.setText(astring);
                }
                else{
                deal1.setText(names[time]);
                }
                time++;
                if(time == 4){
                	time = 0;
                }
 	
        	     
        	    round1.setPadding(0, 0, 3, 0);
        	    deal1.setWidth(80);
        	    deal1.setTextSize(12);
        	    newscore1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
        	    newscore2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
       // 	    newscore1.setWidth(170);
       // 	    newscore2.setWidth(170);
        	    newscore1.setId(score1Id);
        	    newscore2.setId(score2Id);
        	    newscore1.setInputType(InputType.TYPE_CLASS_NUMBER);
                newscore2.setInputType(InputType.TYPE_CLASS_NUMBER);
                newscore1.setSelectAllOnFocus(true);
              	newscore2.setSelectAllOnFocus(true);
        	    in = 1;
                newscore1.setText("0");
                newscore2.setText("0");
                in = 0;
                row1.setId(rowId);
                row1.addView(round1);
        	    row1.addView(deal1);
                row1.addView(newscore1);
                row1.addView(newscore2); 
              rowId++;
              oldtl.addView(row1,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
            		  LayoutParams.WRAP_CONTENT));
              round_num = round_old;        
          }
          else{  
        	    names[0]=extras.getString("player1");
        	  	names[1]=extras.getString("player2");
        	  	names[2]=extras.getString("player3");
        	  	names[3]=extras.getString("player4");
        	  	names_TeamS[0]=names[0];
        	  	names_TeamS[1]=names[1];
        	  	names_TeamS[2]=names[2];
        	  	names_TeamS[3]=names[3];
                datasource.deleteOld(team1, team2);
            	  if(datasource.Exists(names[0]) == false){datasource.insertStatPlayer(names[0],0,0,0,0);}
            	  if(datasource.Exists(names[1]) == false){datasource.insertStatPlayer(names[1],0,0,0,0);}
            	  if(datasource.Exists(names[2]) == false){datasource.insertStatPlayer(names[2],0,0,0,0);}
            	  if(datasource.Exists(names[3]) == false){datasource.insertStatPlayer(names[3],0,0,0,0);}
            	  if(into == true) {
            		    names_TeamS[0] = names[1];
            		    names_TeamS[1] = names[0];
              		    names_TeamS[2] = names[3];
              		    names_TeamS[3] = names[2];
              	     if(datasource.TeamExists(team1) == false) {
              		      datasource.insertTeam( team1, names[1], names[3] );              		    
              	     }
            	     if(datasource.TeamExists(team2) == false){
            		      datasource.insertTeam( team2, names[0], names[2] );
            	     }
            	  }
            	  else {
            		 if(datasource.TeamExists(team1) == false) {
            		       datasource.insertTeam( team1, names[0], names[2] );
            	     }
          	         if(datasource.TeamExists(team2) == false){
          		         datasource.insertTeam( team2, names[1], names[3] );
          	         }      		  
            	  }
        	  	TextView pl1 = (TextView)findViewById(R.id.tichu_pl1);
        	  	TextView pl2 = (TextView)findViewById(R.id.tichu_pl2);
        	  	TextView pl3 = (TextView)findViewById(R.id.tichu_pl3);
        	  	TextView pl4 = (TextView)findViewById(R.id.tichu_pl4);
        	  	if(names_TeamS[0].length() > 10) {
          	  		char a,b,c,d,e;
          	  		a = names_TeamS[0].charAt(0);
          	  		b = names_TeamS[0].charAt(1);
          	  		c = names_TeamS[0].charAt(2);
          	  		d = names_TeamS[0].charAt(3);
          	  		e = names_TeamS[0].charAt(4);
          	  		String astring = "";
          	  		astring = astring + a;
          	  		astring = astring + b;
          	  		astring = astring + c;
          	  		astring = astring + d;
          	  		astring = astring + e;
          	  		astring = astring + "...";
          	  		pl1.setText(astring);
          	   }
          	   else {
          	  		pl1.setText(names_TeamS[0]);
          	   }
          	if(names_TeamS[1].length() > 10){
         		 char a,b,c,d,e;
              	a = names_TeamS[1].charAt(0);
              	b = names_TeamS[1].charAt(1);
              	c = names_TeamS[1].charAt(2);
              	d = names_TeamS[1].charAt(3);
             	e = names_TeamS[1].charAt(4);
              	String astring = "";
              	astring = astring + a;
              	astring = astring + b;
              	astring = astring + c;
              	astring = astring + d;
              	astring = astring + e;
              	astring = astring + "...";
      	  		pl2.setText(astring);
         	   }
         	   else{
          	  		pl2.setText(names_TeamS[1]);
         	   }
          	if(names_TeamS[2].length() > 10){
         		 char a,b,c,d,e;
              	a = names_TeamS[2].charAt(0);
              	b = names_TeamS[2].charAt(1);
              	c = names_TeamS[2].charAt(2);
              	d = names_TeamS[2].charAt(3);
             	e = names_TeamS[2].charAt(4);
              	String astring = "";
              	astring = astring + a;
              	astring = astring + b;
              	astring = astring + c;
              	astring = astring + d;
              	astring = astring + e;
              	astring = astring + "...";
      	  		pl3.setText(astring);
         	   }
         	   else{
          	  		pl3.setText(names_TeamS[2]);
         	   }
          	if(names_TeamS[3].length() > 10){
         		 char a,b,c,d,e;
              	a = names_TeamS[3].charAt(0);
              	b = names_TeamS[3].charAt(1);
              	c = names_TeamS[3].charAt(2);
              	d = names_TeamS[3].charAt(3);
             	e = names_TeamS[3].charAt(4);
              	String astring = "";
              	astring = astring + a;
              	astring = astring + b;
              	astring = astring + c;
              	astring = astring + d;
              	astring = astring + e;
              	astring = astring + "...";
      	  		pl4.setText(astring);
         	   }
         	   else{
          	  		pl4.setText(names_TeamS[3]);
         	   }  
                if(round_num == 1)
                {
                	Button undo = (Button)findViewById(R.id.undo);
                    undo.setEnabled(false);                   	
                }
                else{
                	Button undo = (Button)findViewById(R.id.undo);
                    undo.setEnabled(true);       
                }
      	  TableRow tr1 = new TableRow(this);
      	  tr1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
      	  TextView round = new TextView(this);
      	  TextView deal = new TextView(this);
      	round.setId(textview1Id);
  	  deal.setId(textview2Id);
        textview1Id += 2;
        textview2Id += 2;
      	  final EditText score1 = new EditText(this);
      	  final EditText score2 = new EditText(this);
      	  round.setText("1.");
      	  round.setTextSize(12);
      	  if(names[0].length()>5){
      		 char a,b,c,d,e;
            	a = names[0].charAt(0);
            	b = names[0].charAt(1);
            	c = names[0].charAt(2);
            	d = names[0].charAt(3);
           	e = names[0].charAt(4);
            	String astring = "";
            	astring = astring + a;
            	astring = astring + b;
            	astring = astring + c;
            	astring = astring + d;
            	astring = astring + e;
            	astring = astring + "...";
      	  deal.setText(astring);
      	  }
      	  else{
      	  deal.setText(names[0]);
      	  }
      	  deal.setTextSize(12);
      	  time++;
      	  score1.setId(score1Id);
      	  score2.setId(score2Id);
     	 // round.setTextSize(10);
      	  round.setPadding(0, 0, 3, 0);
      	  round.setWidth(35);
      	  // deal.setTextSize(10);
      	  //deal.setPadding(0, 0, 0, 0);
      	  deal.setWidth(80);
      	  tr1.addView(round);
      	  tr1.addView(deal);
      	  tr1.addView(score1);
      	  tr1.addView(score2);
      	score1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
	    score2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
    //  	  score1.setWidth(170);
     // 	  score2.setWidth(170);
      	  score1.setInputType(InputType.TYPE_CLASS_NUMBER);
      	  score2.setInputType(InputType.TYPE_CLASS_NUMBER);
      	  tr1.setId(rowId);
      	  rowId++;
      	  tl.addView(tr1,new TableLayout.LayoutParams(
      			   LayoutParams.FILL_PARENT,
                   LayoutParams.WRAP_CONTENT));
          }
        
        RadioGroup r1 = (RadioGroup)findViewById(R.id.radiog1);
        RadioGroup r2 = (RadioGroup)findViewById(R.id.radiog2);
        RadioGroup r3 = (RadioGroup)findViewById(R.id.radiog3);
        RadioGroup r4 = (RadioGroup)findViewById(R.id.radiog4);
        RadioGroup rdouble = (RadioGroup)findViewById(R.id.rdouble);
       
        final RadioButton arb1 = (RadioButton)findViewById(R.id.first1);
        final RadioButton arb2 = (RadioButton)findViewById(R.id.second1);
        final RadioButton arb3 = (RadioButton)findViewById(R.id.third1);
        final RadioButton arb4 = (RadioButton)findViewById(R.id.fourth1);
        final RadioButton arb5 = (RadioButton)findViewById(R.id.fifth1);
        
        final RadioButton brb1 = (RadioButton)findViewById(R.id.first2);
        final RadioButton brb2 = (RadioButton)findViewById(R.id.second2);
        final RadioButton brb3 = (RadioButton)findViewById(R.id.third2);
        final RadioButton brb4 = (RadioButton)findViewById(R.id.fourth2);
        final RadioButton brb5 = (RadioButton)findViewById(R.id.fifth2);
        
        final RadioButton crb1 = (RadioButton)findViewById(R.id.first3);
        final RadioButton crb2 = (RadioButton)findViewById(R.id.second3);
        final RadioButton crb3 = (RadioButton)findViewById(R.id.third3);
        final RadioButton crb4 = (RadioButton)findViewById(R.id.fourth3);
        final RadioButton crb5 = (RadioButton)findViewById(R.id.fifth3);
        
        final RadioButton drb1 = (RadioButton)findViewById(R.id.first4);
        final RadioButton drb2 = (RadioButton)findViewById(R.id.second4);
        final RadioButton drb3 = (RadioButton)findViewById(R.id.third4);
        final RadioButton drb4 = (RadioButton)findViewById(R.id.fourth4);
        final RadioButton drb5 = (RadioButton)findViewById(R.id.fifth4);
        
        final RadioButton double1 = (RadioButton)findViewById(R.id.rdouble1);
        final RadioButton double2 = (RadioButton)findViewById(R.id.rdouble2);
        final RadioButton double3 = (RadioButton)findViewById(R.id.rdouble3);
        
        newscore1=(EditText)findViewById(score1Id);
    	newscore2=(EditText)findViewById(score2Id);
    	newscore1.setText("0");
    	newscore2.setText("0");
    	newscore1.setSelectAllOnFocus(true);
    	newscore2.setSelectAllOnFocus(true);

    	final TableLayout tichu_table= (TableLayout)findViewById(R.id.tichu_table);
    	final TableRow tichu_zero= (TableRow)findViewById(R.id.tichu_zero);
    	final TableRow tichu_double= (TableRow)findViewById(R.id.tichu_double);
    	final TableRow tichu_choice= (TableRow)findViewById(R.id.tichu_choice);
    	final ImageView img1 = (ImageView)findViewById(R.id.img1);
    	final ImageView img2 = (ImageView)findViewById(R.id.img2);
    	tichu_table.setVisibility(View.GONE);
    	tichu_zero.setVisibility(View.GONE);
    	tichu_double.setVisibility(View.GONE);
    	tichu_choice.setVisibility(View.GONE);
    	img1.setVisibility(View.GONE);
    	img2.setVisibility(View.GONE);
    	final Button tichu_b = (Button)findViewById(R.id.tichu_b);
    	tichu_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	
            	if(tichu_table.isShown() == true){
            		tichu_b.setText("Εμφάνιση Επιλογών");
            	//	tichu_b.setImageResource(R.drawable.cross);
            	tichu_table.setVisibility(View.GONE);
            	tichu_zero.setVisibility(View.GONE);
            	tichu_double.setVisibility(View.GONE);
            	tichu_choice.setVisibility(View.GONE);
            	img1.setVisibility(View.GONE);
            	img2.setVisibility(View.GONE);
            	}
            	else
            	{
            	//	tichu_b.setImageResource(R.drawable.crossnot);
            		tichu_b.setText("Απόκρυψη Επιλογών");
            		tichu_table.setVisibility(View.VISIBLE);
            		tichu_zero.setVisibility(View.VISIBLE);
                	tichu_double.setVisibility(View.VISIBLE);
                	tichu_choice.setVisibility(View.VISIBLE);
                	img1.setVisibility(View.VISIBLE);
                	img2.setVisibility(View.VISIBLE);
            	}
            }
    	});    //////////////////
    	newscore1.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	if((addtext == false) && (radio_pressed==false) && (in == 0)){
	        		if(s.length() > 0){
	        	edit_score = Integer.parseInt(s.toString());  
	        	edit_score2 = 100-edit_score;
	        	newscore2.setText(Integer.toString(edit_score2+0));
	        		}
	        		else{
	        			newscore2.setText("0");
	    	        	
	        		}addtext1 = false;
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        	if((addtext == false) && (radio_pressed==false) && (in == 0)){addtext1 = true;}
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        }  
	    }); 
	    
    	newscore2.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	if((addtext1 == false) && (radio_pressed==false) && (in == 0)){
	        		if(s.length() > 0){
	        	edit_score2 = Integer.parseInt(s.toString()); 
	        	edit_score = 100-edit_score2;
	        	newscore1.setText(Integer.toString(edit_score+0));
	        		}
	        		else{
	        			newscore1.setText("0");
	        		
	        		}	addtext = false;
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        	if((addtext1 == false) && (radio_pressed==false) && (in == 0)){addtext = true;}
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        }
	    }); 
		//Player1 
        r1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	public void onCheckedChanged(RadioGroup group, int checkedId) {
        		newscore1.setClickable(false);
                newscore1.setFocusable(false);
                //newscore1.setEnabled(false);
                newscore2.setClickable(false);
                newscore2.setFocusable(false);
        		// checkedId is the RadioButton selected
        		RadioButton rb = (RadioButton)findViewById(checkedId);
        		int k=0;
        		if(onetwo1 == true){k+=200;}
        		radio_pressed=true;
            	if(rb == arb1){
            		if(onetwo2 == false) {
            			edit_score += 100;
            		
            		if(ntichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));  
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		if(tichu_said2 == true){brb5.setChecked(true);}
            		if(tichu_said3 == true){crb5.setChecked(true);}
            		if(tichu_said4 == true){drb5.setChecked(true);}
            		if(gtichu_said2 == true){brb5.setChecked(true);}
            		if(gtichu_said3 == true){crb5.setChecked(true);}
            		if(gtichu_said4 == true){drb5.setChecked(true);}
            		edit_score -= 100;
            		if((ntichu_said1 == true) || (ngtichu_said1 == true)){count_fail--;}
            		tichu_said1=true;
            		ntichu_said1=false;
            		gtichu_said1=false;
            		ngtichu_said1=false;
            		}
            		else{
            			arb5.setChecked(true);
            		}
            	}
            	else if(rb == arb2){
            		if(count_fail == 3) {
            			from_this_method++;
            			arb5.setChecked(true);
            			from_this_method--;
            		}
            		else {
            		
            		edit_score -= 100;
            		if(tichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score+100+k));
            		}
            		else if(gtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score+200+k));  
            		}
            		else if(ntichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		edit_score += 100;
            		if(ngtichu_said1 == false){count_fail++;}
            		tichu_said1=false;
            		ntichu_said1=true;
            		gtichu_said1=false;
            		ngtichu_said1=false;
            		}
            	}
            	else if(rb == arb3){   
            		if(onetwo2 == false) {
            		edit_score += 200;
            		
            		if(ntichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));  
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		if(tichu_said2 == true){brb5.setChecked(true);}
            		if(tichu_said3 == true){crb5.setChecked(true);}
            		if(tichu_said4 == true){drb5.setChecked(true);}
            		if(gtichu_said2 == true){brb5.setChecked(true);}
            		if(gtichu_said3 == true){crb5.setChecked(true);}
            		if(gtichu_said4 == true){drb5.setChecked(true);}
            		if((ntichu_said1 == true) || (ngtichu_said1 == true)){count_fail--;}
            		edit_score -= 200;
            		tichu_said1=false;
            		ntichu_said1=false;
            		gtichu_said1=true;
            		ngtichu_said1=false;}
            		else
            		{
            			arb5.setChecked(true);
            		}
            	}
            	else if(rb == arb4){   
            		if(count_fail == 3) {
            			from_this_method++;
            			arb5.setChecked(true);
            			from_this_method--;
            		}
            		else {
            			edit_score -= 200;
            			
            		if(tichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score+100+k));
            		}
            		else if(gtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score+200+k));  
            		}
            		else if(ntichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		edit_score += 200;
            		if(ntichu_said1 == false){count_fail++;}
            		tichu_said1=false;
            		ntichu_said1=false;
            		gtichu_said1=false;
            		ngtichu_said1=true;
            		}
            	}
            	else if(rb == arb5){
            		if(tichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score+100+k));
            		}
            		else if(gtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score+200+k));  
            		}
            		else if(ntichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said3 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		if(from_this_method == 0){  
            		if((ntichu_said1 == true) || (ngtichu_said1 == true)){count_fail--;}
            		}
            		if((brb5.isChecked() == true) && (crb5.isChecked() == true) && 
            				(drb5.isChecked() == true) && (double3.isChecked() == true)){
            			newscore1.setClickable(true);
            		    newscore1.setFocusable(true);
            		    newscore1.setFocusableInTouchMode(true);
            		    newscore2.setClickable(true);
            		    newscore2.setFocusable(true);
            		    newscore2.setFocusableInTouchMode(true);
            	    }
            		tichu_said1=false;
            		ntichu_said1=false;
            		gtichu_said1=false;
            		ngtichu_said1=false;
            	} 
            	radio_pressed=false;
            }
        });
      //Player2
        r2.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            	newscore1.setClickable(false);
                newscore1.setFocusable(false);
                //newscore1.setEnabled(false);
                newscore2.setClickable(false);
                newscore2.setFocusable(false);
            	RadioButton rb = (RadioButton)findViewById(checkedId);
            	int k=0;
            	if(onetwo2 == true)
        		{k+=200;}
            	radio_pressed=true;
            	if(rb == brb1){
            		if(onetwo1 == false){
            		edit_score2 += 100;
            		if(ntichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));  
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		if(tichu_said1 == true){arb5.setChecked(true);}
            		if(tichu_said3 == true){crb5.setChecked(true);}
            		if(tichu_said4 == true){drb5.setChecked(true);}
            		if(gtichu_said1 == true){arb5.setChecked(true);}
            		if(gtichu_said3 == true){crb5.setChecked(true);}
            		if(gtichu_said4 == true){drb5.setChecked(true);}
            		edit_score2 -= 100;
            		if((ntichu_said2 == true) || (ngtichu_said2 == true)){count_fail--;}
            		tichu_said2=true;
            		ntichu_said2=false;
            		gtichu_said2=false;
            		ngtichu_said2=false;}
            		else{
            			brb5.setChecked(true);
            		}
            	}
            	else if(rb == brb2){  
            		if(count_fail == 3) {
            			from_this_method++;
            			brb5.setChecked(true);
            			from_this_method--;
            		}
            		else {
            			edit_score2 -= 100;
            		if(tichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2+100+k));
            		}
            		else if(gtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2+200+k));  
            		}
            		else if(ntichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		edit_score2 += 100;
            		if(ngtichu_said2 == false){count_fail++;}
            		tichu_said2=false;
            		ntichu_said2=true;
            		gtichu_said2=false;
            		ngtichu_said2=false;
            		}
            	}
            	else if(rb == brb3){ 
            		if(onetwo1 == false){
            		edit_score2 += 200;
            		if(ntichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));  
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		if(tichu_said1 == true){arb5.setChecked(true);}
            		if(tichu_said3 == true){crb5.setChecked(true);}
            		if(tichu_said4 == true){drb5.setChecked(true);}
            		if(gtichu_said1 == true){arb5.setChecked(true);}
            		if(gtichu_said3 == true){crb5.setChecked(true);}
            		if(gtichu_said4 == true){drb5.setChecked(true);}
            		if((ntichu_said2 == true) || (ngtichu_said2 == true)){count_fail--;}
            		edit_score2 -= 200;
            		tichu_said2=false;
            		ntichu_said2=false;
            		gtichu_said2=true;
            		ngtichu_said2=false;
            		}
            		else{
            			brb5.setChecked(true);
            		}
            	}
            	else if(rb == brb4){   
            		if(count_fail == 3) {
            			from_this_method++;
            			brb5.setChecked(true);
            			from_this_method--;
            		}
            		else {
            			edit_score2 -= 200;
            		if(tichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2+100+k));
            		}
            		else if(gtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2+200+k));  
            		}
            		else if(ntichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		edit_score2 += 200;
            		if(ntichu_said2 == false){count_fail++;}
            		tichu_said2=false;
            		ntichu_said2=false;
            		gtichu_said2=false;
            		ngtichu_said2=true;
            		}
            	}
            	else if(rb == brb5){  
            		if(tichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2+100+k));
            		}
            		else if(gtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2+200+k));  
            		}
            		else if(ntichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		if(from_this_method == 0){ 
            		if((ntichu_said2 == true) || (ngtichu_said2 == true)){count_fail--;}
            		}
            		if((drb5.isChecked() == true) && (crb5.isChecked() == true) && 
            				(arb5.isChecked() == true) && (double3.isChecked() == true)){
            			newscore1.setClickable(true);
            		    newscore1.setFocusable(true);
            		    newscore1.setFocusableInTouchMode(true);
            		    newscore2.setClickable(true);
            		    newscore2.setFocusable(true);
            		    newscore2.setFocusableInTouchMode(true);
            	    }
            		tichu_said2=false;
            		ntichu_said2=false;
            		gtichu_said2=false;
            		ngtichu_said2=false;
            	}  	
            	radio_pressed=false;
            }
        });
        //Player3
        r3.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
            	newscore1.setClickable(false);
                newscore1.setFocusable(false);
                //newscore1.setEnabled(false);
                newscore2.setClickable(false);
                newscore2.setFocusable(false);
            	RadioButton rb = (RadioButton)findViewById(checkedId);
            	radio_pressed=true;
            	int k=0;
            	if(onetwo1 == true)
        		{k+=200;}
            	if(rb == crb1){
            		if(onetwo2 == false){
            		edit_score += 100;
            
            		if(ntichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));  
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		if(tichu_said1 == true){arb5.setChecked(true);}
            		if(tichu_said2 == true){brb5.setChecked(true);}
            		if(tichu_said4 == true){drb5.setChecked(true);}
            		if(gtichu_said1 == true){arb5.setChecked(true);}
            		if(gtichu_said2 == true){brb5.setChecked(true);}
            		if(gtichu_said4 == true){drb5.setChecked(true);}
            		if((ntichu_said3 == true) || (ngtichu_said3 == true)){count_fail--;}
            		edit_score -= 100;
            		tichu_said3=true;
            		ntichu_said3=false;
            		gtichu_said3=false;
            		ngtichu_said3=false;}
            		else{
            			crb5.setChecked(true);
            		}
            	}
            	else if(rb == crb2){  
            		if(count_fail == 3) {
            			from_this_method++;
            			crb5.setChecked(true);
            			from_this_method--;
            		}
            		else {
            			edit_score -= 100;
            		if(tichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score+100+k));
            		}
            		else if(gtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score+200+k));  
            		}
            		else if(ntichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		edit_score += 100;
            		if(ngtichu_said3 == false){count_fail++;}
            		tichu_said3=false;
            		ntichu_said3=true;
            		gtichu_said3=false;
            		ngtichu_said3=false;
            		}
            	}
            	else if(rb == crb3){
            		if(onetwo2==false){
            		edit_score += 200;
         
            		if(ntichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));  
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		if(tichu_said1 == true){arb5.setChecked(true);}
            		if(tichu_said2 == true){brb5.setChecked(true);}
            		if(tichu_said4 == true){drb5.setChecked(true);}
            		if(gtichu_said1 == true){arb5.setChecked(true);}
            		if(gtichu_said2 == true){brb5.setChecked(true);}
            		if(gtichu_said4 == true){drb5.setChecked(true);}
            		if((ntichu_said3 == true) || (ngtichu_said3 == true)){count_fail--;}
            		edit_score -= 200;
            		tichu_said3=false;
            		ntichu_said3=false;
            		gtichu_said3=true;
            		ngtichu_said3=false;}
            		else{
            			crb5.setChecked(true);
            		}
            	}
            	else if(rb == crb4){   
            		if(count_fail == 3) {
            			from_this_method++;
            			crb5.setChecked(true);
            			from_this_method--;
            		}
            		else {
            			edit_score -= 200;
            		if(tichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score+100+k));
            		}
            		else if(gtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score+200+k));  
            		}
            		else if(ntichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		edit_score += 200;
            		if(ntichu_said3 == false){count_fail++;}
            		tichu_said3=false;
            		ntichu_said3=false;
            		gtichu_said3=false;
            		ngtichu_said3=true;
            		}
            	}
            	else if(rb == crb5){  
            		if(tichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score+100+k));
            		}
            		else if(gtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score+200+k));  
            		}
            		else if(ntichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-100+k));
            		}
            		else if(ngtichu_said1 == true){
            			newscore1.setText(Integer.toString(edit_score-200+k));
            		}
            		else{
            			newscore1.setText(Integer.toString(edit_score+k));  
            		}
            		if(from_this_method == 0){ 
            		if((ntichu_said3 == true) || (ngtichu_said3 == true)){count_fail--;}
            		}
            		if((brb5.isChecked() == true) && (arb5.isChecked() == true) && 
            				(arb5.isChecked() == true) && (double3.isChecked() == true)){
            			newscore1.setClickable(true);
            		    newscore1.setFocusable(true);
            		    newscore1.setFocusableInTouchMode(true);
            		    newscore2.setClickable(true);
            		    newscore2.setFocusable(true);
            		    newscore2.setFocusableInTouchMode(true);
            	    }
            		tichu_said3=false;
            		ntichu_said3=false;
            		gtichu_said3=false;
            		ngtichu_said3=false;
            	}  	
            	radio_pressed=false;
            }
        });
      //Player4
        r4.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	newscore1.setClickable(false);
                newscore1.setFocusable(false);
                newscore2.setClickable(false);
                newscore2.setFocusable(false);
            	RadioButton rb = (RadioButton)findViewById(checkedId);
            	radio_pressed=true;
            	int k=0;
            	if(onetwo2 == true)
        		{k+=200;}
            	if(rb == drb1){
            		if(onetwo1 == false){
            		edit_score2 += 100;
            		if(ntichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));  
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		if(tichu_said1 == true){arb5.setChecked(true);}
            		if(tichu_said2 == true){brb5.setChecked(true);}
            		if(tichu_said3 == true){crb5.setChecked(true);}
            		if(gtichu_said1 == true){arb5.setChecked(true);}
            		if(gtichu_said2 == true){brb5.setChecked(true);}
            		if(gtichu_said3 == true){crb5.setChecked(true);}
            		edit_score2 -= 100;
            		if((ntichu_said4 == true) || (ngtichu_said4 == true)){count_fail--;}
            		tichu_said4=true;
            		ntichu_said4=false;
            		gtichu_said4=false;
            		ngtichu_said4=false;
            		}
            		else{
            			drb5.setChecked(true);
            		}
            	}
            	else if(rb == drb2){  
            		if(count_fail == 3) {
            			from_this_method++;
            			drb5.setChecked(true);
            			from_this_method--;
            		}
            		else {
            			edit_score2 -= 100;
            		if(tichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2+100+k));
            		}
            		else if(gtichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2+200+k));  
            		}
            		else if(ntichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		edit_score2 += 100;
            		if(ngtichu_said4 == false){count_fail++;}
            		tichu_said4=false;
            		ntichu_said4=true;
            		gtichu_said4=false;
            		ngtichu_said4=false;
            		}
            	}
            	else if(rb == drb3){
            		if(onetwo1==false){
            		edit_score2 += 200;
            		if(ntichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said4 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));  
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		} 
            		if(tichu_said1 == true){arb5.setChecked(true);}
            		if(tichu_said2 == true){brb5.setChecked(true);}
            		if(tichu_said3 == true){crb5.setChecked(true);}
            		if(gtichu_said1 == true){arb5.setChecked(true);}
            		if(gtichu_said2 == true){brb5.setChecked(true);}
            		if(gtichu_said3 == true){crb5.setChecked(true);}
            		if((ntichu_said4 == true) || (ngtichu_said4 == true)){count_fail--;}
            		edit_score2 -= 200;
            		tichu_said4=false;
            		ntichu_said4=false;
            		gtichu_said4=true;
            		ngtichu_said4=false;
            		}
            		else{
            			drb5.setChecked(true);
            		}
            	}
            	else if(rb == drb4){   
            		if(count_fail == 3) {
            			from_this_method++;
            		    drb5.setChecked(true);
            		    from_this_method--;
            		}
            		else {
            			edit_score2 -= 200;
            		if(tichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2+100+k));
            		}
            		else if(gtichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2+200+k));  
            		}
            		else if(ntichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		edit_score2 += 200;
            		if(ntichu_said4 == false){count_fail++;}
            		tichu_said4=false;
            		ntichu_said4=false;
            		gtichu_said4=false;
            		ngtichu_said4=true;
            		}
            	}
            	else if(rb == drb5){  
            		if(tichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2+100+k));
            		}
            		else if(gtichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2+200+k));  
            		}
            		else if(ntichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-100+k));
            		}
            		else if(ngtichu_said2 == true){
            			newscore2.setText(Integer.toString(edit_score2-200+k));
            		}
            		else{
            			newscore2.setText(Integer.toString(edit_score2+k));  
            		}
            		if(from_this_method == 0){ 
            		if((ntichu_said4 == true) || (ngtichu_said4 == true)){count_fail--;}
            		}
            		if((brb5.isChecked() == true) && (crb5.isChecked() == true) && 
            				(arb5.isChecked() == true) && (double3.isChecked() == true)){
            			newscore1.setClickable(true);
            		    newscore1.setFocusable(true);
            		    newscore1.setFocusableInTouchMode(true);
            		    newscore2.setClickable(true);
            		    newscore2.setFocusable(true);
            		    newscore2.setFocusableInTouchMode(true);
            	    }
            		tichu_said4=false;
            		ntichu_said4=false;
            		gtichu_said4=false;
            		ngtichu_said4=false;
            	}  	
            	radio_pressed=false;
            }
        });
        //Double
        rdouble.setOnCheckedChangeListener(new OnCheckedChangeListener() 
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            	int temp_score = 0;
            	int temp_score2 = 0;

            	radio_pressed = true;
            	newscore1.setClickable(false);
                newscore1.setFocusable(false);
                newscore2.setClickable(false);
                newscore2.setFocusable(false);
            	RadioButton rb = (RadioButton)findViewById(checkedId);
            	if(rb == double1){  
            		if(  ((ntichu_said1==true) && (ngtichu_said1==true)) ||
            			((ntichu_said3==true) && (ngtichu_said3==true))	||
            			(tichu_said2==true) || (gtichu_said2==true) ||
            			(tichu_said4==true) || (gtichu_said4==true) ||
            			((   Integer.parseInt(newscore2.getText().toString()) != 0) &&
            			   ( Integer.parseInt(newscore2.getText().toString()) != -100) &&
            		       (  Integer.parseInt(newscore2.getText().toString()) != -200) &&
            			    (onetwo2 == false))) 
            		{
            			
            			double3.setChecked(true);
            			}
            		else{
            			edit_score += 200;            			
            			if((tichu_said1 == true) || (tichu_said3 == true)){
                			temp_score += 100;
                		}
                		if((gtichu_said1 == true) || (gtichu_said3 == true)){
                			temp_score += 200;
                		}
                		if(ntichu_said1 == true){temp_score += -100;} 
                		if(ntichu_said3 == true){temp_score += -100;} 
                		if(ntichu_said2 == true){temp_score2 += -100;} 
                		if(ntichu_said4 == true){temp_score2 += -100;} 
                		if(ngtichu_said1 == true){temp_score += -200;} 
                		if(ngtichu_said3 == true){temp_score += -200;} 
                		if(ngtichu_said2 == true){temp_score2 += -200;} 
                		if(ngtichu_said4 == true){temp_score2 += -200;} 
            			newscore1.setText(Integer.toString(edit_score+temp_score));
            			newscore2.setText(Integer.toString(edit_score2+temp_score2));
            		
            			edit_score -= 200;
            			onetwo1 = true;
            			onetwo2 = false;
            		}
                }
            	else if(rb == double2){
            		if(((ntichu_said2==true) && (ngtichu_said2==true)) ||
                			((ntichu_said4==true) && (ngtichu_said4==true))	||
                			(tichu_said1==true) || (gtichu_said1==true) ||
                			(tichu_said3==true) || (gtichu_said3==true) ||
            			((   Integer.parseInt(newscore1.getText().toString()) != 0) &&
            			   ( Integer.parseInt(newscore1.getText().toString()) != -100) &&
            					   (  Integer.parseInt(newscore1.getText().toString()) != -200) &&
            					   (onetwo1 == false))) 
            		{
            			double3.setChecked(true);}
            		else{
            			edit_score2 += 200;
                		if((tichu_said2 == true) || (tichu_said4 == true)){
                			temp_score2 += 100;
                		}
                		if((gtichu_said2 == true) || (gtichu_said4 == true)){
                		    	temp_score2 += 200;
                		}
                		if(ntichu_said1 == true){temp_score += -100;} 
                		if(ntichu_said3 == true){temp_score += -100;} 
                		if(ntichu_said2 == true){temp_score2 += -100;} 
                		if(ntichu_said4 == true){temp_score2 += -100;} 
                		if(ngtichu_said1 == true){temp_score += -200;} 
                		if(ngtichu_said3 == true){temp_score += -200;} 
                		if(ngtichu_said2 == true){temp_score2 += -200;} 
                		if(ngtichu_said4 == true){temp_score2 += -200;} 
            			newscore2.setText(Integer.toString(edit_score2+temp_score2));
            			newscore1.setText(Integer.toString(edit_score+temp_score));
            			edit_score2 -= 200;
            			onetwo2=true;
            			onetwo1 = false;	
            		}
                }
            	else{
            		if((tichu_said1 == true) || (tichu_said3 == true)){
            			temp_score += 100;
            		}
            		if((tichu_said2 == true) || (tichu_said4 == true)){
            			temp_score2 += 100;
            		}
            		if((gtichu_said1 == true) || (gtichu_said3 == true)){
            			temp_score += 200;
            		}
            		if((gtichu_said2 == true) || (gtichu_said4 == true)){
            		    	temp_score2 += 200;
            		}
            		if(ntichu_said1 == true){temp_score += -100;} 
            		if(ntichu_said3 == true){temp_score += -100;} 
            		if(ntichu_said2 == true){temp_score2 += -100;} 
            		if(ntichu_said4 == true){temp_score2 += -100;} 
            		if(ngtichu_said1 == true){temp_score += -200;} 
            		if(ngtichu_said3 == true){temp_score += -200;} 
            		if(ngtichu_said2 == true){temp_score2 += -200;} 
            		if(ngtichu_said4 == true){temp_score2 += -200;} 
            		newscore1.setText(Integer.toString(edit_score+temp_score));
                    newscore2.setText(Integer.toString(edit_score2+temp_score2));
            		    
            		if((brb5.isChecked() == true) && (crb5.isChecked() == true) && 
            				(arb5.isChecked() == true) && (drb5.isChecked() == true)){
            			newscore1.setClickable(true);
            		    newscore1.setFocusable(true);
            		    newscore1.setFocusableInTouchMode(true);
            		    newscore2.setClickable(true);
            		    newscore2.setFocusable(true);
            		    newscore2.setFocusableInTouchMode(true);
            	    }
            		onetwo1 = false;
            		onetwo2 = false;
            	}
            	radio_pressed = false;
           
            }
        });

        Button zero = (Button)findViewById(R.id.zero);
        zero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	arb5.setChecked(true);
            	brb5.setChecked(true);
            	crb5.setChecked(true);
            	drb5.setChecked(true);
            	double3.setChecked(true);
            	in = 1;
        		newscore1.setText("0");
            	newscore2.setText("0");
            	in = 0;
            	edit_score = 0;
            	edit_score2 = 0;
            	
            }
            });
        final Button undo= (Button)findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            	  public void onClick(View arg0) { 
            		  TableRow rowsum1=(TableRow)findViewById(rowId-2);
            		  TableRow row_edit=(TableRow)findViewById(rowId-1);
            		  TableLayout tl1 = (TableLayout)findViewById(R.id.tableLayout1);
            		  if(the_end == true){
            			  TableRow rowend=(TableRow)findViewById(rowId);
            			  TableRow rowinvisible=(TableRow)findViewById(rowId-1);
            			  rowinvisible.setVisibility(View.VISIBLE);
            			  rowId++;
            			  rowend.setVisibility(View.GONE);
            			  Button tichu_b = (Button)findViewById(R.id.tichu_b);
            	    	  Button delBut = (Button)findViewById(R.id.nextRound);
                        	delBut.setVisibility(View.VISIBLE);
                        	tichu_b.setText("Εμφάνιση Επιλογών");  
                          tichu_b.setVisibility(View.VISIBLE);
                      	
            			  the_end = false;
            		  }
            		  tl1.removeView(rowsum1);
            		  row_edit.setId(rowId-2);
            		  rowId--;
            		  textview1Id -= 2;
            		  textview2Id -= 2;
            		  TextView round=(TextView)findViewById(textview1Id);
              		  TextView deal=(TextView)findViewById(textview2Id);
              		  round.setId(textview1Id-2);
              		  deal.setId(textview2Id-2);
              		  round_num--;
              		  round.setText(Integer.toString(round_num)+".");
              		  time -= 2;
                      if(time == -2){
                  	    time=2;
                      }
                      else if(time == -1){
                    	    time=3;
                      }
                      if(names[time].length()>5){
                   		 char a,b,c,d,e;
                         	a = names[time].charAt(0);
                         	b = names[time].charAt(1);
                         	c = names[time].charAt(2);
                         	d = names[time].charAt(3);
                        	e = names[time].charAt(4);
                         	String astring = "";
                         	astring = astring + a;
                         	astring = astring + b;
                         	astring = astring + c;
                         	astring = astring + d;
                         	astring = astring + e;
                         	astring = astring + "...";
                   	  deal.setText(astring);
                   	  }
                   	  else{
                   		deal.setText(names[time]);  
                   	  }
                      time++;
                      
                      if(time == 4){
                    	  time = 0;
                      }
                      datasource.deleteOldRow(team1, team2, round_num);
                      String[] str = new String[2];
                      str = datasource.getScoreOld(team1, team2, round_num-1);
                      if(str!=null){
                    	  sum1 = Integer.parseInt(str[0]);
                    	  sum2 = Integer.parseInt(str[1]);
                      }
                      else{
                    	  sum1 = 0;
                    	  sum2 = 0;
                      }
            		  if(tichu_undo1 == true){
                      datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_TICHU,1);}
                      if(tichu_undo2 == true){
                      datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_TICHU,1);}
                      if(tichu_undo3 == true){
                      datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_TICHU,1);}
                      if(tichu_undo4 == true){
                      datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_TICHU,1);}
                      
                      if(ntichu_undo1 == true){
                      datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_NTICHU,1);}
                      if(ntichu_undo2 == true){
                      datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_NTICHU,1);}
                      if(ntichu_undo3 == true){
                      datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_NTICHU,1);}
                      if(ntichu_undo4 == true){
                      datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_NTICHU,1);}
                      
                      if(gtichu_undo1 == true){
                      datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_GTICHU,1);}
                      if(gtichu_undo2 == true){
                      datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_GTICHU,1);}
                      if(gtichu_undo3 == true){
                      datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_GTICHU,1);}
                      if(gtichu_undo4 == true){
                      datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_GTICHU,1);}
                      
                      if(ngtichu_undo1 == true){
                      datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_NGTICHU,1);}
                      if(ngtichu_undo2 == true){
                      datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_NGTICHU,1);}
                      if(ngtichu_undo3 == true){
                      datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_NGTICHU,1);}
                      if(ngtichu_undo4 == true){
                      datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_NGTICHU,1);}
            		  tichu_undo1 = false;
                      tichu_undo2 = false;
                      tichu_undo3 = false;
                      tichu_undo4 = false;
                      ntichu_undo1 = false;
                      ntichu_undo2 = false;
                      ntichu_undo3 = false;
                      ntichu_undo4 = false;
                      gtichu_undo1 = false;
                      gtichu_undo2 = false;
                      gtichu_undo3 = false;
                      gtichu_undo4 = false;
                      ngtichu_undo1 = false;
                      ngtichu_undo2 = false;
                      ngtichu_undo3 = false;
                      ngtichu_undo4 = false;
                      
                 	undo.setEnabled(false);
              }
        });
        Button b = (Button)findViewById(R.id.nextRound);
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////      
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
            	tichu_undo1 = false;
                tichu_undo2 = false;
                tichu_undo3 = false;
                tichu_undo4 = false;
                ntichu_undo1 = false;
                ntichu_undo2 = false;
                ntichu_undo3 = false;
                ntichu_undo4 = false;
                gtichu_undo1 = false;
                gtichu_undo2 = false;
                gtichu_undo3 = false;
                gtichu_undo4 = false;
                ngtichu_undo1 = false;
                ngtichu_undo2 = false;
                ngtichu_undo3 = false;
                ngtichu_undo4 = false;

            	  EditText lala1=(EditText)findViewById(score1Id);
            	  EditText lala2=(EditText)findViewById(score2Id);            	 
            	  if((lala1.getText().length()==0) || (lala2.getText().length()==0)){
              		alertDialog.setTitle("Λάθος");
         			 	alertDialog.setMessage("Δεν έχετε πληκτρολογήσει βαθμολογία!");
         			 	alertDialog.show();	
            	  }
            	  else{
            		  int s1,s2;
            		  s1=Integer.parseInt(lala1.getText().toString());
            		  s2=Integer.parseInt(lala2.getText().toString());
            	if((s1%5!=0) || (s2%5!=0) || (s1>400) || (s2>400) || (s1<-400) || (s2<-400)){
            		alertDialog.setTitle("Λάθος");
       			 	alertDialog.setMessage("Έχετε κάνει κάποιο λάθος στην" +
       			 			" εισαγωγή των βαθμολογιών!");
       			 	alertDialog.show();	
            	}
            	else{
                	Button undo1 = (Button)findViewById(R.id.undo);
                	undo1.setEnabled(true);
            		Button tichu_b1 = (Button)findViewById(R.id.tichu_b);
                    tichu_b1.setText("Εμφάνιση Επιλογών");   

              if(tichu_said1 == true){tichu_undo1=true;
              datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_TICHU,0);}
              if(tichu_said2 == true){tichu_undo2=true;
              datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_TICHU,0);}
              if(tichu_said3 == true){tichu_undo3=true;
              datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_TICHU,0);}
              if(tichu_said4 == true){tichu_undo4=true;
              datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_TICHU,0);}
              
              if(ntichu_said1 == true){ntichu_undo1=true;
              datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_NTICHU,0);}
              if(ntichu_said2 == true){ntichu_undo2=true;
              datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_NTICHU,0);}
              if(ntichu_said3 == true){ntichu_undo3=true;
              datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_NTICHU,0);}
              if(ntichu_said4 == true){ntichu_undo4=true;
              datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_NTICHU,0);}
              
              if(gtichu_said1 == true){gtichu_undo1=true;
              datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_GTICHU,0);}
              if(gtichu_said2 == true){gtichu_undo2=true;
              datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_GTICHU,0);}
              if(gtichu_said3 == true){gtichu_undo3=true;
              datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_GTICHU,0);}
              if(gtichu_said4 == true){gtichu_undo4=true;
              datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_GTICHU,0);}
              
              if(ngtichu_said1 == true){ngtichu_undo1=true;
              datasource.updatePlayerStat(names_TeamS[0],MySQLiteHelper.STAT_PLAYER_NGTICHU,0);}
              if(ngtichu_said2 == true){ngtichu_undo2=true;
              datasource.updatePlayerStat(names_TeamS[1],MySQLiteHelper.STAT_PLAYER_NGTICHU,0);}
              if(ngtichu_said3 == true){ngtichu_undo3=true;
              datasource.updatePlayerStat(names_TeamS[2],MySQLiteHelper.STAT_PLAYER_NGTICHU,0);}
              if(ngtichu_said4 == true){ngtichu_undo4=true;
              datasource.updatePlayerStat(names_TeamS[3],MySQLiteHelper.STAT_PLAYER_NGTICHU,0);}
              
              tichu_said1 = false;
              tichu_said2 = false;
              tichu_said3 = false;
              tichu_said4 = false;
              ntichu_said1 = false;
              ntichu_said2 = false;
              ntichu_said3 = false;
              ntichu_said4 = false;
              gtichu_said1 = false;
              gtichu_said2 = false;
              gtichu_said3 = false;
              gtichu_said4 = false;
              ngtichu_said1 = false;
              ngtichu_said2 = false;
              ngtichu_said3 = false;
              ngtichu_said4 = false;
            	
              //Neo textview me ta progiumena score
          	  TextView overall1= new TextView(getApplicationContext());
          	  TextView overall2 = new TextView(getApplicationContext());
          	  newscore1 = lala1;
              newscore2 = lala2;
          	  temp1 = sum1 + Integer.parseInt(lala1.getText().toString());
  	          temp2 = sum2 + Integer.parseInt(lala2.getText().toString());
          	  overall1.setText(Integer.toString(temp1));
              overall2.setText(Integer.toString(temp2));
              overall1.setGravity(Gravity.CENTER);
              overall2.setGravity(Gravity.CENTER);
  	          sum1=temp1;
  	          sum2=temp2;   
  	        overall1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
  	      overall2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
       //       overall1.setWidth(170);
       //       overall2.setWidth(170);
          	  TableRow row=(TableRow)findViewById(rowId-1);
          	  row.removeView(lala1);
          	  row.removeView(lala2);
              row.addView(overall1);
              row.addView(overall2); 
              time1 = time-1;  
              if (time1 == -1) {
                	time1 = 3;
                }
      		   int result = team1.compareTo(team2);
      		   if (result > 0) {
                   datasource.insertOldGame(team2, team1, temp2, temp1, round_num, time1, names[0],
                   		names[1], names[2], names[3]);  
      		   }
      		   
      		   else if (result < 0) {
                   datasource.insertOldGame(team1, team2, temp1, temp2, round_num, time1, names[0],
                   		names[1], names[2], names[3]);  
      		   }
   
                round_num++;
                edit_score = 0;
                edit_score2 = 0;
                arb5.setChecked(true);
            	brb5.setChecked(true);
            	crb5.setChecked(true);
            	drb5.setChecked(true);
            	double3.setChecked(true);
                		tichu_table.setVisibility(View.GONE);
                		tichu_zero.setVisibility(View.GONE);
                    	tichu_double.setVisibility(View.GONE);
                    	tichu_choice.setVisibility(View.GONE);
                    	img1.setVisibility(View.GONE);
                    	img2.setVisibility(View.GONE);

                TableLayout tl = (TableLayout)findViewById(R.id.tableLayout1);
                TableRow newRow = new TableRow(getApplicationContext());
                newRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
                TextView round = new TextView(getApplicationContext());
                TextView deal = new TextView(getApplicationContext());
                round.setId(textview1Id);
          	  deal.setId(textview2Id);
                textview1Id += 2;
                textview2Id += 2;
                in = 1;
                newscore1.setText("0");
                newscore2.setText("0");
                in = 0;
                round.setText(Integer.toString(round_num)+".");
                if(names[time].length() >5){
                	char a,b,c,d,e;
                  	a = names[time].charAt(0);
                  	b = names[time].charAt(1);
                  	c = names[time].charAt(2);
                  	d = names[time].charAt(3);
                 	e = names[time].charAt(4);
                  	String astring = "";
                  	astring = astring + a;
                  	astring = astring + b;
                  	astring = astring + c;
                  	astring = astring + d;
                  	astring = astring + e;
                  	astring = astring + "...";
                	deal.setText(astring);
                }
                else{
                deal.setText(names[time]);
                }
                round.setTextSize(12);
                deal.setTextSize(12);
                time++;
                if(time == 4){
              	  time=0;
                }
              //  newscore1.setId(score1Id);
               // newscore2.setId(score2Id);
                newRow.addView(round);
                newRow.addView(deal);
                round.setWidth(35);
                round.setPadding(0, 0, 3, 0);
                deal.setWidth(80);
                newRow.addView(newscore1);
                newRow.addView(newscore2);
                newscore1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
                newscore2.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
               // newscore1.setWidth(170);
               // newscore2.setWidth(170);
                newRow.setId(rowId);
                rowId++;
                tl.addView(newRow,new TableLayout.LayoutParams(
                           LayoutParams.FILL_PARENT,
                           LayoutParams.WRAP_CONTENT)); 
                if((temp1<1000 && temp2<1000) || ((temp1>=1000 && temp2>=1000) && temp1==temp2)){
                	
                }
                else
                {
                   newRow.setVisibility(View.GONE);
                  final Button tichu_b = (Button)findViewById(R.id.tichu_b);
                  tichu_b.setVisibility(View.GONE);
              	  Button delBut = (Button)findViewById(R.id.nextRound);
              	delBut.setVisibility(View.GONE);
              	the_end = true;
              	 TableLayout tl2 = (TableLayout)findViewById(R.id.tableLayout2);
              	  TableRow tr2 = new TableRow(getApplicationContext());
                    tr2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
                    Button b = new Button(getApplicationContext());
                    b.setText("ΤΕΛΟΣ!");
                    b.setGravity(Gravity.CENTER);
                    tr2.addView(b);
                    tr2.setId(rowId);
                    tr2.setPadding(0, 70, 0, 0);
                    tr2.setGravity(Gravity.CENTER);
                    tl2.addView(tr2,new TableLayout.LayoutParams(
                               LayoutParams.FILL_PARENT,
                               LayoutParams.WRAP_CONTENT));
                    TableRow.LayoutParams params = (TableRow.LayoutParams)b.getLayoutParams();
                    params.span = 4;
                    b.setLayoutParams(params); 
                    b.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View arg0) {
                      	  
                      	  Intent i = new Intent(mainGame.this, winners.class);
                      	  if(temp1>temp2){  
                      			i.putExtra("winner",team1);
                      			i.putExtra("team2",team2);
                      			i.putExtra("temp1",temp1);
                      			i.putExtra("temp2",temp2);
                       	  }
                      	  else
                      	  {
                      			i.putExtra("winner",team2);
                       			i.putExtra("team2",team1);
                       			i.putExtra("temp1",temp2);
                       			i.putExtra("temp2",temp1);
                      	  }
                       	 startActivity(i);
                        }
                     });
                     
                }
            }   
            }
        }
              });
        datasource.close();
        }
       
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu2, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.exit:
	    		Intent i = new Intent(mainGame.this, GptichuActivity.class);
	    		startActivity(i);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
}