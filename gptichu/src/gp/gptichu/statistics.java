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

public class statistics extends Activity {
	private TichuDataSource datasource;
	private String[] players = new String[200];
	private String[] teams = new String[200];
	private String player, team;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics);
		final AlertDialog alertDialog = new AlertDialog.Builder(statistics.this).create();
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		datasource = new TichuDataSource(this);
		datasource.open();
		ArrayAdapter <CharSequence> adapter1 =
	     	  new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
	    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ArrayAdapter <CharSequence> adapter2 =
	     	new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
	   adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	    players = datasource.getPlayers();
	    int k=0;
	    while (players[k]!=null) {
       	   adapter1.add(players[k]);    
       	   k++;
        }
	    k=0;
	    teams = datasource.getTeams();
	    if(teams != null) {
	    while (teams[k]!=null) {
       	   adapter2.add(teams[k]);   
       	   k++;
        }
	    }
	    final Spinner spinner1 = (Spinner) findViewById(R.id.statsp1);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
		   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			   player = players[position];
		    }
		    public void onNothingSelected(AdapterView<?> parentView) {	
		    }
		});
        if(teams!=null){
        final Spinner spinner2 = (Spinner) findViewById(R.id.statsp2);
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			   team = teams[position];
		    }
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }
		});
        spinner2.setAdapter(adapter2);
        }
        spinner1.setAdapter(adapter1);
		Button b1 = (Button) findViewById(R.id.statbtn1);
		Button b2 = (Button) findViewById(R.id.statbtn2);
		//Button 1 is pushed
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(datasource.Exists(player) == true) {
					Intent i = new Intent(statistics.this, statplayer.class);
					i.putExtra("player",player);
					startActivity(i);
				}
				else {
					alertDialog.setTitle("Λάθος!");
		    	    alertDialog.setMessage("Δεν έχουν δημιουργηθεί ακόμα στατιστικά του" +
		    	    " παίχτη " + player);
		    	    alertDialog.show();
		    	}
			}
	    });
		//Button 2 is   pushed
		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(teams == null){
					alertDialog.setTitle("Λάθος!");
	       		    alertDialog.setMessage("Δεν υπάρχουν αποθηκευμένες ομάδες!");
	       		    alertDialog.show();
				}
				else if(datasource.StatTeamExists(team) == true) {
					Intent i = new Intent(statistics.this, statteam.class);
					i.putExtra("team",team);
					startActivity(i);
				}
				else {
					alertDialog.setTitle("Λάθος!");
	       		    alertDialog.setMessage("Δεν έχουν δημιουργηθεί ακόμα στατιστικά της" +
	       			" ομάδας " + team);
	       		    alertDialog.show();
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
	    		Intent i = new Intent(statistics.this, GptichuActivity.class);
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