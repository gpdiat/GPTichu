package gp.gptichu;

import gp.gptichu.R;

import android.app.Activity;
import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
    
public class GptichuActivity extends Activity {
	private TichuDataSource datasource;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	    AppRater.app_launched(this);
	//	AdView Ad = (AdView)findViewById(R.id.ad);
	   //Alert Dialog
		final AlertDialog alertDialog = new AlertDialog.Builder(GptichuActivity.this).create();
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {}
		});
		

		//Create datasource and open database
		datasource = new TichuDataSource(this);
		datasource.open();
			//For version 1.3.1
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if(!prefs.getBoolean("firstTime", false)) {
			if(datasource.oldExistsAny() == true){datasource.newOldGame();}  
			if(datasource.StatTeamExistsAny() == true){datasource.newTeamStats();}
			if(datasource.VSExistsAny() == true){datasource.newVS();}
			// run your one time code 
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean("firstTime", true);
			editor.commit();
	    }
			//Define buttons
		Button b1 = (Button) findViewById(R.id.resume);
		Button b2 = (Button) findViewById(R.id.newgame);
		Button b3 = (Button) findViewById(R.id.stats);
		Button b4 = (Button) findViewById(R.id.delete);
		Button b5 = (Button) findViewById(R.id.rules);
		//Button 1 is pushed
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(datasource.oldExistsAny()) { 
					Intent i = new Intent(GptichuActivity.this, pending.class);
					startActivity(i);
				}
				else {
					//If there is no pending game
					alertDialog.setTitle("Λάθος");
					alertDialog.setMessage("Δεν εκκρεμεί κανένα παιχνίδι!");
				    alertDialog.show();
				}
			}
		});
		//Button 2 is pushed
		b2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(GptichuActivity.this, page2.class);
				startActivity(i);
			}
		});
		//Button 3 is pushed
		b3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(datasource.getPlayers() != null) {
					Intent i = new Intent(GptichuActivity.this, statistics.class);
					startActivity(i);
				}
				else {
					alertDialog.setTitle("Λάθος");
				    alertDialog.setMessage("Δεν υπάρχουν στατιστικά!");
					alertDialog.show();
				}
			}
	    });
		//Button 4 is pushed
		b4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(GptichuActivity.this, edit.class);
				startActivity(i);
			}
	    });
		//Button 5 is pushed
		b5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(GptichuActivity.this, rules.class);
				startActivity(i);
			}
		});
		datasource.close(); 
	}
	
	public void onDestroy() {
        super.onDestroy();
        
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.game_menu, menu);
	    return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.exit:
	    		Intent intent = new Intent(Intent.ACTION_MAIN);
	    		intent.addCategory(Intent.CATEGORY_HOME);
	    		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    		startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
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