package gp.gptichu;

import gp.gptichu.R;

import java.util.Date;
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class winners extends Activity {
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
	private String team1;
	private String team2;
	private TichuDataSource datasource;
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.winners);
		Bundle extras = getIntent().getExtras();
		datasource = new TichuDataSource(this);
        datasource.open();
		if(extras!=null) {
			team1 = extras.getString("winner");
			team2 = extras.getString("team2");
			Integer temp1 = extras.getInt("temp1");
			Integer temp2 = extras.getInt("temp2");
			int result = team1.compareTo(team2);
			if (result > 0) {
				if(datasource.VSexists(team2, team1) == false){
					datasource.insertVSTeam(team2, team1);
				}
				int games_played = datasource.countGamesPlayed( team2,  team1);
				games_played++;
				String datetime = getDateTime();
				datasource.insertStatTeam(team2, team1, games_played, temp2, temp1, datetime);
				datasource.deleteOld(team2, team1); 
			}
			else if (result < 0) {
				if(datasource.VSexists(team1, team2) == false){
					datasource.insertVSTeam(team1, team2);
				}
				int games_played = datasource.countGamesPlayed( team1,  team2);
				games_played++;
				String datetime = getDateTime();
				datasource.insertStatTeam(team1, team2, games_played, temp1, temp2, datetime);
				datasource.deleteOld(team1, team2); 
			}	
			TextView winnertv= (TextView)findViewById(R.id.winnertv);
			//Generate random number
			Random rand = new Random();  
			int min = 1;
			int max = 6;
			int randomNum = rand.nextInt(max - min + 1) + min;
			switch(randomNum) {
			case 1:			
				winnertv.setText("Μπράβο σας\n" + team1 + "!\nΚατατροπώσατε τους " + 
			    team2 + "!\nΕίστε οι απόλυτοι νικητές!!");
				break;
			case 2:			
				winnertv.setText("Είστε φοβεροί\n" + team1 + "! Ξεφτιλίσατε τους " + 
			    team2 + "!");
				break;
			case 3:			
				winnertv.setText("Δεν υπάρχουν λόγια \n" + team1 + "!\nΟι " + 
			    team2 + " είναι άσχετοι απ'ότι φαίνεται!");
				break;
			case 4:			
				winnertv.setText("Απίστευτη νίκη για τους\n" + team1 + "!\nΟι " + 
			    team2 + " δεν είχαν καμία ελπίδα!");
				break;
			case 5:			
				winnertv.setText("Masters of TICHU: " + team1 + "!\nLOSERS: " + 
			    team2);
				break;
			case 6:			
				winnertv.setText("’παιχτοι οι " + team1 + "!\n απέναντι στους " + 
			    team2 + "!\nΜα καλά, είχαν αντίπαλο;;");
				break;
			}
		} 
		Button b = (Button) findViewById(R.id.again);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(winners.this, GptichuActivity.class);
				startActivity(i);
			}
		});
		Button b1 = (Button) findViewById(R.id.end);
		b1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(winners.this, players.class);
				i.putExtra("team1", team1);
			   	i.putExtra("team2", team2);
			   	i.putExtra("checked1",false);
			   	i.putExtra("checked2",false);
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
	    		Intent i = new Intent(winners.this, GptichuActivity.class);
	    		startActivity(i);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}  
	@Override
	public void onBackPressed() {
		Intent i = new Intent(winners.this, GptichuActivity.class);
		startActivity(i);
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

