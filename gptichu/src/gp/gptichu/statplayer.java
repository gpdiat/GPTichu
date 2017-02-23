package gp.gptichu;

import gp.gptichu.R;

import java.text.DecimalFormat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class statplayer extends Activity {
	private TichuDataSource datasource;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statplayer);
		datasource = new TichuDataSource(this);
		datasource.open();
		Bundle extras = getIntent().getExtras();
		if(extras!=null) {
			double tsuc=0.0, gtsuc=0.0;
			Integer[] tichu_stat = new Integer[4];
			String player = extras.getString("player");
			tichu_stat = datasource.getPlayerStat(player);			
			TextView plname = (TextView)findViewById(R.id.plname);
			TextView tichu = (TextView)findViewById(R.id.tichu_view);
			TextView ntichu = (TextView)findViewById(R.id.ntichu_view);
			TextView gtichu = (TextView)findViewById(R.id.gtichu_view);
			TextView ngtichu = (TextView)findViewById(R.id.ngtichu_view);
			TextView tichu_suc = (TextView)findViewById(R.id.tichu_suc);
			TextView gtichu_suc = (TextView)findViewById(R.id.gtichu_suc);
			Button b = (Button)findViewById(R.id.original1);
			b.setOnClickListener(new View.OnClickListener() {
		         public void onClick(View arg0) {
		        	 Intent i = new Intent(statplayer.this, GptichuActivity.class);
		        	 startActivity(i);
		         }
		    });
			tsuc = (100.0*(double)tichu_stat[0])/((double)tichu_stat[0]+(double)tichu_stat[1]);
			gtsuc = (100.0*(double)tichu_stat[2])/((double)tichu_stat[2]+(double)tichu_stat[3]);
			plname.setText("("+player+")");
			tichu.setText(Integer.toString(tichu_stat[0]));
			ntichu.setText(Integer.toString(tichu_stat[1]));
			gtichu.setText(Integer.toString(tichu_stat[2]));
			ngtichu.setText(Integer.toString(tichu_stat[3]));
			if((tichu_stat[0] + tichu_stat[1]) == 0) {
				tsuc = 0.0;
			}
			if((tichu_stat[2] + tichu_stat[3]) == 0) {
				gtsuc = 0.0;
			}
	        DecimalFormat df = new DecimalFormat("#.##");
			tichu_suc.setText(df.format(tsuc)+"%");
			gtichu_suc.setText(df.format(gtsuc)+"%");
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
	    		Intent i = new Intent(statplayer.this, GptichuActivity.class);
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