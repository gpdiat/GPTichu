package gp.gptichu;


import gp.gptichu.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
 
public class statteam extends Activity {
	private TichuDataSource datasource;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statteam);
		datasource = new TichuDataSource(this);
		datasource.open();
		Bundle extras = getIntent().getExtras();
		if(extras!=null) {
			TableLayout tl = (TableLayout)findViewById(R.id.tablestat_team);

			String team = extras.getString("team");
			String[] vsteams = new String[50];
			TextView teamname = (TextView)findViewById(R.id.teamname);
			teamname.setText("("+team+")");
			vsteams = datasource.getVS(team);
			int i=0;
			//While there are new opponents
			while(vsteams[i]!=null) {
				TableRow tr1 = new TableRow(this);
				TextView tv1 = new TextView(this);
				TextView tv2 = new TextView(this);
				TextView tv0 = new TextView(this);
				TextView tv01 = new TextView(this);
		        tr1.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		        		LayoutParams.WRAP_CONTENT));
		        tr1.setBackgroundColor(Color.parseColor("#ffaa55"));
		        tv1.setText(team);
		        tv1.setWidth(100);
		        tv2.setText(vsteams[i]);
		        tv2.setWidth(100);
		        tv1.setTextColor(Color.parseColor("#000000"));
		        tv1.setTextSize(12);
		        tv2.setTextColor(Color.parseColor("#000000"));
		        tv2.setTextSize(12);
		        tr1.addView(tv0);
		        tr1.addView(tv1);
		        tr1.addView(tv2);
		        tr1.addView(tv01);
		        tl.addView(tr1,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		        		  LayoutParams.WRAP_CONTENT));
		        int games=1;
		        String[] teams_stat = new String[3];
		        teams_stat = datasource.getTeamStat(team, vsteams[i], games);
				int won=0;
				//All the games played with one team
		        while(teams_stat!=null) {
		        	TableRow tr2 = new TableRow(this);
		        	TextView tv3 = new TextView(this);
					TextView tv4 = new TextView(this);
					TextView tv5 = new TextView(this);
					TextView tv6 = new TextView(this);
		        	tr2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		        	tv3.setText(Integer.toString(games)+".");
		        	tv3.setTextSize(11);
		        	tv4.setText(teams_stat[0]);
		        	tv5.setText(teams_stat[1]);
		        	tv6.setText(teams_stat[2]);
		        	tv4.setTextSize(11);
		        	tv5.setTextSize(11);
		        	tv6.setTextSize(11);
		        	if(Integer.parseInt(teams_stat[0])>Integer.parseInt(teams_stat[1])) {
		        		won++;
		        	}
		        	tr2.addView(tv3);
		        	tr2.addView(tv4);
		        	tr2.addView(tv5);
		        	tr2.addView(tv6);
		        	tl.addView(tr2,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
			        		  LayoutParams.WRAP_CONTENT));
		        	games++;
		        	teams_stat = datasource.getTeamStat(team, vsteams[i], games);
		        }
		        TableRow tr3 = new TableRow(this);
		        TextView tv7 = new TextView(this);
		        TextView tv8 = new TextView(this);
		        TextView tv9 = new TextView(this);
		        TextView tv10 = new TextView(this);
		        TableRow tr4 = new TableRow(this);
		        TextView tv11 = new TextView(this);
		        TextView tv12 = new TextView(this);
		        TextView tv13 = new TextView(this);
		        TextView tv14 = new TextView(this);
		        tr3.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		        		LayoutParams.WRAP_CONTENT));
		        tr4.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		        		LayoutParams.WRAP_CONTENT));
		        tv7.setText("Νίκες ");
		        tv8.setText(Integer.toString(won));
		        tv11.setText("Ηττες ");
		        tv12.setText(Integer.toString(games-1-won));
		        tv7.setTextSize(12);
		        tv11.setTextSize(12);
		        tv8.setTextSize(11);
		        tv12.setTextSize(11);
		        tr3.addView(tv7);
		        tr3.addView(tv8);
		        tr3.addView(tv9);
		        tr3.addView(tv10);
		        tr4.addView(tv11);
		        tr4.addView(tv12);
		        tr4.addView(tv13);
		        tr4.addView(tv14);
		        tl.addView(tr3,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		        		  LayoutParams.WRAP_CONTENT));
		        tl.addView(tr4,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
		        		  LayoutParams.WRAP_CONTENT));
		        i++;
			}
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
	    		Intent i = new Intent(statteam.this, GptichuActivity.class);
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