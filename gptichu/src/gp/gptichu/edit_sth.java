package gp.gptichu;

import gp.gptichu.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class edit_sth extends Activity {
	private TichuDataSource datasource;
	private String[] players = new String[200];
	private String[] teams = new String[200];
	private String OldTeamName, NewTeamName, OldPlayerName, NewPlayerName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_sth);
		//Alert Dialogs
		final AlertDialog alertDialog = new AlertDialog.Builder(edit_sth.this).create();
		final AlertDialog alertDialog4 = new AlertDialog.Builder(edit_sth.this).create();
		final AlertDialog alertDialog5 = new AlertDialog.Builder(edit_sth.this).create();
		alertDialog.setButton("Αλλαγή", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Intent i = new Intent(edit_sth.this, editok.class);
				i.putExtra("OldTeamName",OldTeamName);
				i.putExtra("NewTeamName",NewTeamName);
				i.putExtra("type","team");
				startActivity(i);
			}
		});  
		alertDialog.setButton2("Ακύρωση", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});  
		alertDialog4.setButton("Αλλαγή", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
					Intent i = new Intent(edit_sth.this, editok.class);
					i.putExtra("OldPlayerName",OldPlayerName);
					i.putExtra("NewPlayerName",NewPlayerName);
					i.putExtra("type","player");
					startActivity(i);
			}
		});  
		alertDialog4.setButton2("Ακύρωση", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});  
	    alertDialog5.setButton("ΟΚ", new DialogInterface.OnClickListener() {
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
	    final Spinner spinner1 = (Spinner) findViewById(R.id.changesp1);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			   if(teams != null) {
			   OldTeamName = teams[position];
			   }
		    }
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }
		});
        final Spinner spinner2 = (Spinner) findViewById(R.id.changesp2);
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			   if(players != null) {
			   OldPlayerName = players[position];
			   }
		    }
		    public void onNothingSelected(AdapterView<?> parentView) {	
		    }
		});
        EditText txt1 = (EditText)findViewById(R.id.edit_sp1);
        txt1.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	NewTeamName = s.toString();
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        }
	    }); 
        EditText txt2 = (EditText)findViewById(R.id.edit_sp2);
        txt2.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	        	NewPlayerName = s.toString();
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        }
	    });
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
	    //Define buttons
		Button b1 = (Button) findViewById(R.id.changebtn1);
		Button b2 = (Button) findViewById(R.id.changebtn2);
		//Button 1 is pushed
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(teams == null) {
					alertDialog5.setTitle("Λάθος");
       			 	alertDialog5.setMessage("Δεν υπάρχουν αποθηκευμένες ομάδες");
       			 	alertDialog5.show();	
				}
				else {
				//	EditText txt1 = (EditText)findViewById(R.id.edit_sp1);
				//	String NewTeamName = txt1.getText().toString().replace(" ", "");
					NewTeamName = NewTeamName.replace(" ", "");
		        	if(NewTeamName.equals("")) {
		        		 alertDialog5.setTitle("Λάθος");
	        		     alertDialog5.setMessage("Δεν έχετε πληκτρολογήσει νέο όνομα για την Ομάδα!");
	        			 alertDialog5.show();
		        	}
		        	else {
		        		boolean already_exists = false;
		        		int k=0;
		     	    	while (teams[k]!=null) { 
		     	    		if(NewTeamName.equals(teams[k])) {
		     	    			already_exists = true;
		     	    			break;
		     	    		}
		     	    		k++;
		     	    	}
		        		if(already_exists == true) {
		        			alertDialog5.setTitle("Λάθος");
	       			 	 	alertDialog5.setMessage("Το όνομα αυτό υπάρχει ήδη!");
	       			 	    alertDialog5.show();			        		 
		        		}
		        	    else {
		        			alertDialog.setTitle("Επιβεβαίωση");
       			 	 		alertDialog.setMessage("Είστε σίγουροι ότι θέλετε να αλλάξετε το όνομα" +
       			 	 		" της ομάδας από " + OldTeamName + " σε " + NewTeamName + ";");
       			 	 		alertDialog.show();	
		        		}	
		        	}
				}
	        }
	      });
		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(players == null) {
					alertDialog5.setTitle("Λάθος");
       			 	alertDialog5.setMessage("Δεν υπάρχουν αποθηκευμένοι παίκτες");
       			 	alertDialog5.show();	
				}
				else {
					//EditText txt1 = (EditText)findViewById(R.id.edit_sp2);
					//String NewPlayerName = txt1.getText().toString().replace(" ", "");
					NewPlayerName = NewPlayerName.replace(" ", "");
		        	if(NewPlayerName.equals("")) {
		        		 alertDialog5.setTitle("Λάθος");
	        		     alertDialog5.setMessage("Δεν έχετε πληκτρολογήσει νέο όνομα για τον Παίχτη!");
	        			 alertDialog5.show();
		        	}
		        	else {
		        		boolean already_exists = false;
		        		int k=0;
		     	    	while (players[k]!=null) { 
		     	    		if(NewPlayerName.equals(players[k])) {
		     	    			already_exists = true;
		     	    			break;
		     	    		}
		     	    		k++;
		     	    	}
		        		if(already_exists == true) {
		        			alertDialog5.setTitle("Λάθος");
	       			 	 	alertDialog5.setMessage("Το όνομα αυτό υπάρχει ήδη!");
	       			 	    alertDialog5.show();			        		 
		        		}
		        	    else {
		        			alertDialog4.setTitle("Επιβεβαίωση");
       			 	 		alertDialog4.setMessage("Είστε σίγουροι ότι θέλετε να αλλάξετε το όνομα" +
       			 	 		" του παίχτη από " + OldPlayerName + " σε " + NewPlayerName + ";");
       			 	 		alertDialog4.show();	
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
	    		Intent i = new Intent(edit_sth.this, GptichuActivity.class);
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