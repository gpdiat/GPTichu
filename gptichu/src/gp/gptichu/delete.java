package gp.gptichu;

import gp.gptichu.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class delete extends Activity {
	private TichuDataSource datasource;
	private String[] players = new String[200];
	private String[] teams = new String[200];
	private String player, team;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		//Alert Dialogs
		final AlertDialog alertDialog = new AlertDialog.Builder(delete.this).create();
		final AlertDialog alertDialog1 = new AlertDialog.Builder(delete.this).create();
		final AlertDialog alertDialog2 = new AlertDialog.Builder(delete.this).create();
		final AlertDialog alertDialog3 = new AlertDialog.Builder(delete.this).create();
		final AlertDialog alertDialog4 = new AlertDialog.Builder(delete.this).create();
		final AlertDialog alertDialog5 = new AlertDialog.Builder(delete.this).create();
		alertDialog.setButton("Διαγραφή", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent i = new Intent(delete.this, deleteok.class);
				i.putExtra("team",team);
				i.putExtra("type","team");
				startActivity(i);
			}
		});  
		alertDialog.setButton2("Ακύρωση", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});  
		alertDialog4.setButton("Διαγραφή", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
					Intent i = new Intent(delete.this, deleteok.class);
					i.putExtra("player",player);
					i.putExtra("type","player");
					startActivity(i);
			}
		});  
		alertDialog4.setButton2("Ακύρωση", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});  
	    alertDialog5.setButton2("ΟΚ", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});  
		alertDialog1.setButton("Διαγραφή", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
					Intent i = new Intent(delete.this, deleteok.class);
					i.putExtra("type","database");
					startActivity(i);
			}
		});  
		alertDialog1.setButton2("Ακύρωση", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});  
		alertDialog2.setButton2("OK", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface arg0, int arg1) {
			}
		});  
		alertDialog3.setButton2("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		}); 	
		//Fill drop down menus with choices
		ArrayAdapter <CharSequence> adapter1 =
				new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter <CharSequence> adapter2 =
				new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		datasource = new TichuDataSource(this);
		datasource.open();
		//Get teams
	    teams = datasource.getTeams();
	    if(teams == null) {
    		adapter1.add(" ");  
	    }
	    else {
	    	int k=0;
	    	while (teams[k]!=null){
	    		adapter1.add(teams[k]);   
	    		k++;
	    	}
	    }
	    //Get players
	    players = datasource.getPlayers();
	    if(players == null) {
    		adapter2.add(" ");  
	    }
	    else {
	    	int k=0;
	    	while (players[k]!=null) {
	    		adapter2.add(players[k]);   
	    		k++;
	    	}
	    }
	    final Spinner spinner1 = (Spinner) findViewById(R.id.delsp1);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			   if(teams != null) {
			   team = teams[position];
			   }
		    }
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }
		});
        final Spinner spinner2 = (Spinner) findViewById(R.id.delsp2);
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			   if(players != null) {
			   player = players[position];
			   }
		    }
		    public void onNothingSelected(AdapterView<?> parentView) {	
		    }
		});
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
	    //Define buttons
		Button b1 = (Button) findViewById(R.id.delbtn1);
		Button b2 = (Button) findViewById(R.id.delbtn2);
		Button b3 = (Button) findViewById(R.id.delbtn3);
		//Button 1 is pushed
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(teams == null) {
					alertDialog2.setTitle("Λάθος");
       			 	alertDialog2.setMessage("Δεν υπάρχουν αποθηκευμένες ομάδες");
       			 	alertDialog2.show();	
				}
				else {
					alertDialog.setTitle("Επιβεβαίωση");
       			 	alertDialog.setMessage("Είστε σίγουροι ότι θέλετε να διαγράψετε" +
       			 	" την ομάδα " + team + "; (οι παίκτες που την απαρτίζουν " +
       			 	"δεν θα διαγραφούν)");
       			 	alertDialog.show();					
				}
 
	         }
	      });
		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(players == null) {
					alertDialog3.setTitle("Λάθος");
       			 	alertDialog3.setMessage("Δεν υπάρχουν αποθηκευμένοι παίκτες");
       			 	alertDialog3.show();	
				}
				else {
	                boolean exists = datasource.PlayerExistsInTeam(player);
					if(exists == true) {
						alertDialog5.setTitle("Λάθος");
	       			 	alertDialog5.setMessage("Ο παίκτης " + player + 
	       			 	" ανήκει σε ομάδα! Διαγράψτε πρώτα την ομάδα" +
	       			 	" που ανήκει και μετά τον παίχτη!");
	       			 	alertDialog5.show();
					}
					else {
						alertDialog4.setTitle("Επιβεβαίωση");
						alertDialog4.setMessage("Είστε σίγουροι ότι θέλετε να διαγράψετε" +
						" τον παίχτη " + player + ";");
						alertDialog4.show();
					}
				}
 
	        }
	    });
		//Button 3 is   pushed
		b3.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View arg0) {
	        	 alertDialog1.setTitle("Επιβεβαίωση");
       			 alertDialog1.setMessage("Είστε σίγουροι ότι θέλετε να διαγράψετε" +
       			 " την βάση δεδομένων; Όλοι οι παίκτες, οι ομάδες και τα στατιστικά" +
       			 " θα διαγραφούν εντελώς!");
       			 alertDialog1.show(); 			
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
	    		Intent i = new Intent(delete.this, GptichuActivity.class);
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