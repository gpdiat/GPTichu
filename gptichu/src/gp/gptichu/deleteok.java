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

public class deleteok extends Activity {
	private TichuDataSource datasource;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deleteok);
		datasource = new TichuDataSource(this);
		datasource.open();
		Bundle extras = getIntent().getExtras();
		if(extras!=null) {
			String type = extras.getString("type");
			if(type.equals("team")) {
				String team = extras.getString("team");
				datasource.deleteTeam(team);
				TextView tv = (TextView)findViewById(R.id.deleteup);
				tv.setText("�������� ������");
				TextView tv1 = (TextView)findViewById(R.id.deletedown);
				tv1.setText("� ����� " + team + " ���������� �� ��������!");
			}
			else if(type.equals("player")) {
				String player = extras.getString("player");
				datasource.deletePlayer(player);
				TextView tv = (TextView)findViewById(R.id.deleteup);
				tv.setText("�������� ������");
				TextView tv1 = (TextView)findViewById(R.id.deletedown);
				tv1.setText("� ������� " + player + " ���������� �� ��������!");
			}
			else if(type.equals("database")) {
				datasource.deleteAll();
				TextView tv = (TextView)findViewById(R.id.deleteup);
				tv.setText("�������� ����� ���������");
				TextView tv1 = (TextView)findViewById(R.id.deletedown);
				tv1.setText("� ���� ��������� ���������� �� ��������!\n" +
				"���� �� �������, �� ������ ��� �� ���������� ����� ��������� �������!");
			}
			Button b = (Button) findViewById(R.id.original);
			//Button is pushed
			b.setOnClickListener(new View.OnClickListener() {
				public void onClick(View arg0) {
					Intent i = new Intent(deleteok.this, GptichuActivity.class);
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
	    		Intent i = new Intent(deleteok.this, GptichuActivity.class);
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