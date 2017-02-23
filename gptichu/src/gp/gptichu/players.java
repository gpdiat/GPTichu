package gp.gptichu;

import gp.gptichu.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class players extends Activity {
	private String team1=null;
	private String team2=null;
	private String pl1 = null;
	private String pl2 = null;
	private String pl3 = null;
	private String pl4 = null;
	private boolean checked1 = false;
	private boolean checked2 = false;
	private boolean checkedpl1 = false;
	private boolean checkedpl2 = false;
	private boolean checkedpl3 = false;
	private boolean checkedpl4 = false;
	private String[] teampl1 = new String[50];
	private String[] teampl2 = new String[50];
	private TichuDataSource datasource;
	String[] players = new String[200];
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.players);
		datasource = new TichuDataSource(this);
	    datasource.open();
		final AlertDialog alertDialog = new AlertDialog.Builder(players.this).create();
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		final AlertDialog alertDialogPend = new AlertDialog.Builder(players.this).create();
		alertDialogPend.setButton("Ολοκλήρωση Παιχνιδιού", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent i = new Intent(players.this, mainGame.class);
				i.putExtra("player1",pl1);
				i.putExtra("player2",pl2);
				i.putExtra("player3",pl3);
				i.putExtra("player4",pl4);
				i.putExtra("team1",team1);
				i.putExtra("team2",team2);
				i.putExtra("old_game",true);
				startActivity(i);
								
			}
		});
		alertDialogPend.setButton2("Νεό Παιχνίδι", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent i = new Intent(players.this, mainGame.class);
				i.putExtra("player1",pl1);
				i.putExtra("player2",pl2);
				i.putExtra("player3",pl3);
				i.putExtra("player4",pl4);
				i.putExtra("team1",team1);
				i.putExtra("team2",team2);
				i.putExtra("old_game",false);
				startActivity(i);	
			}
		});
		Bundle extras = getIntent().getExtras();
		if(extras!=null) {
			team1 = extras.getString("team1");
			team2 = extras.getString("team2");
			checked1 = extras.getBoolean("checked1");
			checked2 = extras.getBoolean("checked2");
			TextView tpl1 = (TextView)findViewById(R.id.text_pl1);
			TextView tpl2 = (TextView)findViewById(R.id.text_pl2);
			TextView tpl3 = (TextView)findViewById(R.id.text_pl3);
			TextView tpl4 = (TextView)findViewById(R.id.text_pl4);
			tpl1.setText("Παίκτης 1 ("+team1+")");
			tpl2.setText("Παίκτης 2 ("+team2+")");
			tpl3.setText("Παίκτης 3 ("+team1+")");
			tpl4.setText("Παίκτης 4 ("+team2+")");
		}    	
		players = datasource.getPlayers();
		if(players != null) {
			ArrayAdapter <CharSequence> adapter1 =
					new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
			adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        ArrayAdapter <CharSequence> adapter2 =
	        		new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
	        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    ArrayAdapter <CharSequence> adapter3 =
		    		new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
	    	adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        ArrayAdapter <CharSequence> adapter4 =
	        		new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
	      	adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      	int i=0;
	      	//If i added a new team
	      	if(checked1 == true) {
	      		while (players[i]!=null) {
	      			//Show all players in drop down
	      			adapter1.add(players[i]);  
	      			adapter3.add(players[i]);  
	      			i++;
	      		}
	      	}
	      	else {
	      		//Show only team players in drop down
        	    teampl1 = datasource.getTeamPlayers(team1);
   				adapter1.add(teampl1[0]); 
   				adapter1.add(teampl1[1]); 
   				adapter3.add(teampl1[0]); 
   				adapter3.add(teampl1[1]);    		
           }
	      	//Same as before
           if(checked2 == true) {
        	   i=0;
        	   while (players[i]!=null) {
        		   adapter2.add(players[i]);
        		   adapter4.add(players[i]);
        		   i++;
        	   }
           }
           else {
        	   teampl2 = datasource.getTeamPlayers(team2);
        	   adapter2.add(teampl2[0]); 
       		   adapter2.add(teampl2[1]); 
       		   adapter4.add(teampl2[0]); 
       		   adapter4.add(teampl2[1]); 
           }
           final Spinner spinner1 = (Spinner) findViewById(R.id.plsp1);
           spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
        	   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		   if(checked1 == true) {
        			   pl1 = players[position];
        		   }
        		   else {
        			   pl1 = teampl1[position];
        		   }
        		}
        	   public void onNothingSelected(AdapterView<?> parentView) {
        	   }
           });
           final Spinner spinner2 = (Spinner) findViewById(R.id.plsp2);
           spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
        	   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		   if(checked2 == true) {
        			   pl2 = players[position];
        		   }
        		   else {
        			   pl2 = teampl2[position];
        		   }
        	   }
        	   public void onNothingSelected(AdapterView<?> parentView) {
        	   }
		   });
           final Spinner spinner3 = (Spinner) findViewById(R.id.plsp3);
           spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
        	   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		   if(checked1 == true) {
        			   pl3 = players[position];
        		   }
        		   else {
        			   pl3 = teampl1[position];
        		   }
               }
		       public void onNothingSelected(AdapterView<?> parentView) {
		       }
		  });
          final Spinner spinner4 = (Spinner) findViewById(R.id.plsp4);
          spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {
        	  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		  if(checked2 == true) {
        			  pl4 = players[position];
        		  }
        		  else {
        			  pl4 = teampl2[position];
        		  }
		      }
		      public void onNothingSelected(AdapterView<?> parentView) {
		      }
		  });
          spinner1.setAdapter(adapter1);
          spinner2.setAdapter(adapter2);
          spinner3.setAdapter(adapter3);
          spinner4.setAdapter(adapter4);
          if(checked1 == false) {
        	  spinner3.setSelection(1);
          }
          if(checked2 == false) {
        	  spinner4.setSelection(1);
          }
          final EditText et1 = (EditText)findViewById(R.id.editText1);
          et1.setVisibility(View.GONE);
          final EditText et3 = (EditText)findViewById(R.id.editText3);
          et3.setVisibility(View.GONE);
          final EditText et2 = (EditText)findViewById(R.id.editText2);
          et2.setVisibility(View.GONE);
          final EditText et4 = (EditText)findViewById(R.id.editText4);
          et4.setVisibility(View.GONE);
          final CheckBox plBox1 = (CheckBox) findViewById(R.id.plBox1);
          final CheckBox plBox3 = (CheckBox) findViewById(R.id.plBox3);
          final CheckBox plBox2 = (CheckBox) findViewById(R.id.plBox2);
          final CheckBox plBox4 = (CheckBox) findViewById(R.id.plBox4);
          if(checked1 == true) {
        	  plBox1.setOnClickListener(new View.OnClickListener() {	
             	public void onClick(View arg0) {
             		if (plBox1.isChecked()) { 
             			et1.setVisibility(View.VISIBLE);
         	        	Spinner plsp1 = (Spinner)findViewById(R.id.plsp1);
         	        	plsp1.setVisibility(View.GONE);
         	        	checkedpl1 = true;  
             		}
         	        else{
         	        	et1.setVisibility(View.GONE);
         	        	Spinner plsp1 = (Spinner)findViewById(R.id.plsp1);
         	        	plsp1.setVisibility(View.VISIBLE);
         	        	checkedpl1 = false;
         	        }
             	}
              });
        	  plBox3.setOnClickListener(new View.OnClickListener() {	
             	public void onClick(View arg0) {
             		if (plBox3.isChecked()) { 
             			et3.setVisibility(View.VISIBLE);
         	        	Spinner plsp3 = (Spinner)findViewById(R.id.plsp3);
         	        	plsp3.setVisibility(View.GONE);
         	        	checkedpl3 = true;  
             		}
         	        else{
         	        	et3.setVisibility(View.GONE);
         	        	Spinner plsp3 = (Spinner)findViewById(R.id.plsp3);
         	        	plsp3.setVisibility(View.VISIBLE);
         	        	checkedpl3= false;
         	        }
             	 }
              });
          }
          else {
        	  checkedpl1 = false;
        	  checkedpl3 = false;
        	  plBox1.setClickable(false);
        	  plBox3.setClickable(false);
          }
          if(checked2 == true) {
        	  plBox2.setOnClickListener(new View.OnClickListener() {	
             	public void onClick(View arg0) {
             		if (plBox2.isChecked()) { 
             			et2.setVisibility(View.VISIBLE);
         	        	Spinner plsp2 = (Spinner)findViewById(R.id.plsp2);
         	        	plsp2.setVisibility(View.GONE);
         	        	checkedpl2 = true;  
             		}
         	        else {
         	        	et2.setVisibility(View.GONE);
         	        	Spinner plsp2 = (Spinner)findViewById(R.id.plsp2);
         	        	plsp2.setVisibility(View.VISIBLE);
         	        	checkedpl2 = false;
         	        }
             	}
              });
        	  plBox4.setOnClickListener(new View.OnClickListener() {	
             	public void onClick(View arg0) {
             		if (plBox4.isChecked()) { 
             			et4.setVisibility(View.VISIBLE);
         	        	Spinner plsp4 = (Spinner)findViewById(R.id.plsp4);
         	        	plsp4.setVisibility(View.GONE);
         	        	checkedpl4 = true;  
             		}
         	        else{
         	        	et4.setVisibility(View.GONE);
         	        	Spinner plsp4 = (Spinner)findViewById(R.id.plsp4);
         	        	plsp4.setVisibility(View.VISIBLE);
         	        	checkedpl4 = false;
         	        }
             	}
              });
          }
          else {
        	  checkedpl2 = false;
        	  checkedpl4 = false;
              plBox2.setClickable(false);
              plBox4.setClickable(false);
          }
        }
		else {
			Spinner s1 = (Spinner)findViewById(R.id.plsp1);
       		Spinner s2 = (Spinner)findViewById(R.id.plsp2);
       		Spinner s3 = (Spinner)findViewById(R.id.plsp3);
       		Spinner s4 = (Spinner)findViewById(R.id.plsp4);
       		EditText t1 = (EditText)findViewById(R.id.editText1);
       		EditText t2 = (EditText)findViewById(R.id.editText2);
       		EditText t3 = (EditText)findViewById(R.id.editText3);
       		EditText t4 = (EditText)findViewById(R.id.editText4);
    		CheckBox c1 = (CheckBox) findViewById(R.id.plBox1);
    		CheckBox c2 = (CheckBox) findViewById(R.id.plBox2);
    		CheckBox c3 = (CheckBox) findViewById(R.id.plBox3);
    		CheckBox c4 = (CheckBox) findViewById(R.id.plBox4);
       		s1.setVisibility(View.GONE);
       		s2.setVisibility(View.GONE);
       		s3.setVisibility(View.GONE);
       		s4.setVisibility(View.GONE);
       		t1.setVisibility(View.VISIBLE);
       		t2.setVisibility(View.VISIBLE);
       		t3.setVisibility(View.VISIBLE);
       		t4.setVisibility(View.VISIBLE);
       		c1.setChecked(true);
       		c2.setChecked(true);
       		c3.setChecked(true);
       		c4.setChecked(true);
       		c1.setClickable(false);
       		c2.setClickable(false);
       		c3.setClickable(false);
       		c4.setClickable(false);
       		checkedpl1 = false;
       		checkedpl2 = false;
       		checkedpl3 = false;
       		checkedpl4 = false;
		}
		Button b = (Button) findViewById(R.id.btnClick1);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(checkedpl1 == true) {
					EditText txt1 = (EditText)findViewById(R.id.editText1);
					pl1 = txt1.getText().toString().replace(" ", "");
				}
				if(checkedpl2 == true) {
					EditText txt2 = (EditText)findViewById(R.id.editText2);
					pl2 = txt2.getText().toString().replace(" ", "");
				}
				if(checkedpl3 == true) {
					EditText txt3 = (EditText)findViewById(R.id.editText3);
					pl3 = txt3.getText().toString().replace(" ", "");
				}
				if(checkedpl4 == true) {
					EditText txt4 = (EditText)findViewById(R.id.editText4);
					pl4 = txt4.getText().toString().replace(" ", "");
				}
				if(players == null) {
					EditText t1 = (EditText)findViewById(R.id.editText1);
		       		EditText t2 = (EditText)findViewById(R.id.editText2);
		       		EditText t3 = (EditText)findViewById(R.id.editText3);
		       		EditText t4 = (EditText)findViewById(R.id.editText4);
				    pl1 = t1.getText().toString().replace(" ", "");
				    pl2 = t2.getText().toString().replace(" ", "");
				    pl3 = t3.getText().toString().replace(" ", "");
				    pl4 = t4.getText().toString().replace(" ", "");    
				}
				if((pl1.equals("")) || (pl2.equals("")) || (pl3.equals("")) || (pl4.equals(""))) {
	        		 alertDialog.setTitle("Λάθος");
       		     alertDialog.setMessage("Δεν έχετε πληκτρολογήσει ονόματα για όλους τους παίχτες!");
       			 alertDialog.show();
	        	 }
				else if((pl1.equals(pl2)) || (pl1.equals(pl3)) || (pl1.equals(pl4)) ||
					 (pl2.equals(pl3)) || (pl2.equals(pl4)) || 
					 	(pl3.equals(pl4))) {
					alertDialog.setTitle("Λάθος");
       			 	alertDialog.setMessage("Δεν μπορούν δύο παίχτες να έχουν το ίδιο όνομα!");
       			 	alertDialog.show();	
				}
				else {
					int result = team1.compareTo(team2);
					if (result > 0) {
						if(datasource.oldExists(team2, team1, 1) == true){
							alertDialogPend.setTitle("Εκκρεμεί Παιχνίδι");
		       			 	alertDialogPend.setMessage("Δεν έχετε ολοκληρώσει παλαιότερο παιχνίδι!Τι επιθυμείτε να κάνετε;");
		       			 	alertDialogPend.show();	
						} 
						else {
							Intent i = new Intent(players.this, mainGame.class);
							i.putExtra("player1",pl1);
							i.putExtra("player2",pl2);
							i.putExtra("player3",pl3);
							i.putExtra("player4",pl4);
							i.putExtra("team1",team1);
							i.putExtra("team2",team2);
							i.putExtra("old_game",false);
							startActivity(i);	
						}
						
					}
					else if (result < 0) {
						if(datasource.oldExists(team1, team2, 1) == true){
							alertDialogPend.setTitle("Εκκρεμεί Παιχνίδι");
		       			 	alertDialogPend.setMessage("Δεν έχετε ολοκληρώσει παλαιότερο παιχνίδι!Τι επιθυμείτε να κάνετε;");
		       			 	alertDialogPend.show();	
						} 
						else {
							Intent i = new Intent(players.this, mainGame.class);
							i.putExtra("player1",pl1);
							i.putExtra("player2",pl2);
							i.putExtra("player3",pl3);
							i.putExtra("player4",pl4);
							i.putExtra("team1",team1);
							i.putExtra("team2",team2);
							i.putExtra("old_game",false);
							startActivity(i);	
						}
					}
					
				}
			}
		});
		datasource.close();
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
	    		Intent i = new Intent(players.this, GptichuActivity.class);
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