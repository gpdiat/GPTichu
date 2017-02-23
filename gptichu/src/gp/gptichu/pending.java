package gp.gptichu;

import gp.gptichu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class pending extends Activity {
	private TichuDataSource datasource;
	private int temp = 0;
	private String[] teams = new String[50];
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pending);
		//Create datasource and open database
		datasource = new TichuDataSource(this);
		datasource.open();
		//Create a drop down menu containing all the pending games
        ArrayAdapter <CharSequence> adapter =
        		new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teams = datasource.getPendingGames();
		int i=0;
		while (teams[i]!=null) {
			adapter.add(teams[i] + "-" + teams[i+1]);
			i+=2;
		}
		final Spinner spinner = (Spinner) findViewById(R.id.spinner3);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				position--;
				temp = 2*position+2;
			}
			public void onNothingSelected(AdapterView<?> parentView) {
			}
		});
		Button b = (Button) findViewById(R.id.pending_b);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(pending.this, mainGame.class);
	        	 String[] players = new String[50];
	        	 String[] players2 = new String[50];
	        	 players = datasource.getTeamPlayers(teams[temp]);
	        	 players2 = datasource.getTeamPlayers(teams[temp+1]);
	        	 i.putExtra("team1",teams[temp]);
	        	 i.putExtra("team2",teams[temp+1]);
	        	 i.putExtra("player1",players[0]);
				 i.putExtra("player2",players2[0]);
				 i.putExtra("player3",players[1]);
				 i.putExtra("player4",players2[1]);
	        	 i.putExtra("old_game",true);
	        	 startActivity(i);
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
	    		Intent i = new Intent(pending.this, GptichuActivity.class);
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