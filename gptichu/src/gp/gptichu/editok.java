package gp.gptichu;

import gp.gptichu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class editok extends Activity {
	private TichuDataSource datasource;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editok);
		datasource = new TichuDataSource(this);
		datasource.open();
		Bundle extras = getIntent().getExtras();
		if(extras!=null) {
			String type = extras.getString("type");
			if(type.equals("team")) {
				String OldTeamName = extras.getString("OldTeamName");
				String NewTeamName = extras.getString("NewTeamName");
				TextView tv = (TextView)findViewById(R.id.editup);
				tv.setText("Αλλαγή Ονόματος Ομάδας");
				TextView tv1 = (TextView)findViewById(R.id.editdown);
				datasource.EditTeam(OldTeamName, NewTeamName);
				tv1.setText("Η ομάδα " + OldTeamName + " μετονομάστηκε σε " + NewTeamName + " με επιτυχία!");
			}
			else if(type.equals("player")) {
				String OldPlayerName = extras.getString("OldPlayerName");
				String NewPlayerName = extras.getString("NewPlayerName");
				TextView tv = (TextView)findViewById(R.id.editup);
				tv.setText("Αλλαγή Ονόματος Παίχτη");
				TextView tv1 = (TextView)findViewById(R.id.editdown);
				datasource.EditPlayer(OldPlayerName, NewPlayerName);
			    tv1.setText("Ο παίχτης " + OldPlayerName + " μετονομάστηκε σε " + NewPlayerName + " με επιτυχία!");
			}
			Button b = (Button) findViewById(R.id.original);
			//Button is pushed
			b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent i = new Intent(editok.this, GptichuActivity.class);
					startActivity(i);
		        }
		    });
		}
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
	    		Intent i = new Intent(editok.this, GptichuActivity.class);
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