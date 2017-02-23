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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class page2 extends Activity {
	private boolean checked1 = false;
	private boolean checked2 = false;
	private TichuDataSource datasource;
	private String temp1;
	private String temp2;
	private String[] teams = new String[200];
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.page2);
		final AlertDialog alertDialog = new AlertDialog.Builder(page2.this).create();
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		datasource = new TichuDataSource(this);
	    datasource.open();
		final EditText et = (EditText)findViewById(R.id.editText1);
    	et.setVisibility(View.GONE);
		final CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox1.setOnClickListener(new View.OnClickListener() {	
        	public void onClick(View arg0) {
        		if (checkBox1.isChecked()) { 
    	        	et.setVisibility(View.VISIBLE);
    	        	Spinner sp = (Spinner)findViewById(R.id.spinner1);
    	        	sp.setVisibility(View.GONE);
    	        	checked1 = true;  
        		}
    	        else {
    	        	et.setVisibility(View.GONE);
    	        	Spinner sp = (Spinner)findViewById(R.id.spinner1);
    	        	sp.setVisibility(View.VISIBLE);
    	        	checked1 = false;
    	        }
        	}
        });
		final EditText et1 = (EditText)findViewById(R.id.editText2);
    	et1.setVisibility(View.GONE);
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox2.setOnClickListener(new View.OnClickListener() {	
        	public void onClick(View arg0) {
        		if (checkBox2.isChecked()) { 
    	        	et1.setVisibility(View.VISIBLE);
    	        	Spinner sp1 = (Spinner)findViewById(R.id.spinner2);
    	        	sp1.setVisibility(View.GONE);
    	        	checked2 = true;
        		}
    	        else {
    	        	et1.setVisibility(View.GONE);
    	        	Spinner sp1 = (Spinner)findViewById(R.id.spinner2);
    	        	sp1.setVisibility(View.VISIBLE);
    	        	checked2 = false;
    	        }
        	}
        });
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		temp1 = (String) spinner1.getSelectedItem();
        	}
        	public void onNothingSelected(AdapterView<?> parentView) {	
        	}
		});
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		   public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			   temp2 = (String) spinner2.getSelectedItem();
		    }
		    public void onNothingSelected(AdapterView<?> parentView) {
		    }
		});
        teams = datasource.getTeams();
        if(teams!=null){
        	ArrayAdapter <CharSequence> adapter1 =
        			new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        	adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	ArrayAdapter <CharSequence> adapter2 =
        			new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        	adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	int i=0;
        	while (teams[i]!=null) {
        		adapter1.add(teams[i]);
        		adapter2.add(teams[i]);
        		i++;
        	}	   
        	spinner1.setAdapter(adapter1);
        	spinner2.setAdapter(adapter2);
        }
        else {
        	Spinner s1 = (Spinner)findViewById(R.id.spinner1);
       		Spinner s2 = (Spinner)findViewById(R.id.spinner2);
       		EditText t1 = (EditText)findViewById(R.id.editText1);
       		EditText t2 = (EditText)findViewById(R.id.editText2);
    		CheckBox c1 = (CheckBox) findViewById(R.id.checkBox1);
    		CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2);
       		s1.setVisibility(View.GONE);
       		s2.setVisibility(View.GONE);
       		t1.setVisibility(View.VISIBLE);
       		t2.setVisibility(View.VISIBLE);
       		c1.setChecked(true);
       		c2.setChecked(true);
       		c1.setClickable(false);
       		c2.setClickable(false);
       		checked1 = false;
       		checked2 = false;
       	}
		Button b = (Button) findViewById(R.id.btnClick);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if ((checked1 == true) && (checked2 == true)) {
					EditText txt1 = (EditText)findViewById(R.id.editText1);	 
					EditText txt2 = (EditText)findViewById(R.id.editText2);
		        	String k1 = txt1.getText().toString().replace(" ", "");
		        	String k2 = txt2.getText().toString().replace(" ", "");
		        	if((k1.equals("")) || (k2.equals(""))) {
		        		alertDialog.setTitle("Λάθος");
	        		    alertDialog.setMessage("Δεν έχετε πληκτρολογήσει όνομα και για τις δύο ομάδες!");
	        			alertDialog.show();
		        	}
	        		else if (k1.equals(k2)) {
	        			 alertDialog.setTitle("Λάθος");
	        		     alertDialog.setMessage("Δεν μπορούν δύο αντίπαλες ομάδες να έχουν το ίδιο όνομα");
	        			 alertDialog.show();	 
	        	    }
	        		else {
	        			Intent i = new Intent(page2.this, players.class);
	        			i.putExtra("team1",k1);
		        		i.putExtra("team2",k2);
		        		if(datasource.TeamExists(k1) == true) {
		        			i.putExtra("checked1",false); 
		        		}
		        		else {
		        			i.putExtra("checked1",true); 
		        		}
		        		if(datasource.TeamExists(k2) == true) {
		        			i.putExtra("checked2",false); 
		        		}
		        		else {
		        			i.putExtra("checked2",true);
		        		}
		        		System.out.println("FTANEI!!");
		        		startActivity(i);
		        	}
		        }
				else if((checked1 == true) && (checked2 == false)) {
					EditText txt1 = (EditText)findViewById(R.id.editText1);
					String k1 = txt1.getText().toString().replace(" ", "");
		        	 if(k1.equals("")) {
		        		 alertDialog.setTitle("Λάθος");
	        		     alertDialog.setMessage("Δεν έχετε πληκτρολογήσει όνομα για την ομάδα 1!");
	        			 alertDialog.show();
		        	 }
		        	 else if(k1.equals(temp2)) {
	        			 alertDialog.setTitle("Λάθος");
	        		     alertDialog.setMessage("Δεν μπορούν δύο αντίπαλες ομάδες να έχουν το ίδιο όνομα");
	        			 alertDialog.show();	 
	        		 }
	        		 else {
	        			 Intent i = new Intent(page2.this, players.class);
	        			 if(datasource.TeamExists(k1) == true) {
	        				 i.putExtra("checked1",false);
	        			 }
	        			 else {
	        				 i.putExtra("checked1",true); 
	        			 }
	        			 i.putExtra("team1",k1);
	        			 i.putExtra("team2", temp2);
	        			 i.putExtra("checked2",false);
	        			 startActivity(i);
	        		 }
	        	 }
	        	 else if((checked1 == false) && (checked2 == true)) {
	        		 EditText txt2 = (EditText)findViewById(R.id.editText2);
	        		 String k2 = txt2.getText().toString().replace(" ", "");
	        		 if(k2.equals("")) {
		        		 alertDialog.setTitle("Λάθος");
	        		     alertDialog.setMessage("Δεν έχετε πληκτρολογήσει όνομα για την ομάδα 2!");
	        			 alertDialog.show();
		        	 }
	        		 else if(k2.toString().equals(temp1)) {
	        			 alertDialog.setTitle("Λάθος");
	        		     alertDialog.setMessage("Δεν μπορούν δύο αντίπαλες ομάδες να έχουν το ίδιο όνομα");
	        			 alertDialog.show();
	        		 }
	        		 else {
	        			 Intent i = new Intent(page2.this, players.class);
	        			 i.putExtra("team1", temp1);
	        			 i.putExtra("team2",k2);
	        			 if(datasource.TeamExists(k2) == true) {
	        				 i.putExtra("checked2",false);
	        			 }
	        			 else {
	        				 i.putExtra("checked2",true);
	        			 }
	        		 	 i.putExtra("checked1",false);
	        		 
	        		 	 startActivity(i);
	        		 }
	        	 }
	        	 else {
	        		 boolean nothing = false;
	        		 if(teams==null){
	        			 EditText txt1 = (EditText)findViewById(R.id.editText1);	 
    		        	 EditText txt2 = (EditText)findViewById(R.id.editText2);
    		        	 temp1 = txt1.getText().toString().replace(" ", "");
    		        	 temp2 = txt2.getText().toString().replace(" ", "");
        			     if((temp1.equals("")) || (temp2.equals(""))) {
    		        		 alertDialog.setTitle("Λάθος");
    	        		     alertDialog.setMessage("Δεν έχετε πληκτρολογήσει ονόματα και για τις δύο ομάδες!");
    	        			 alertDialog.show();
    	        			 nothing=true;
    		        	 }
	        		 }
        			 if(temp1.equals(temp2)) {
        			 	 alertDialog.setTitle("Λάθος");
        			  	 alertDialog.setMessage("Δεν μπορούν δύο αντίπαλες ομάδες να έχουν το ίδιο όνομα!");
        			  	 alertDialog.show();	        			 
        			 }
        			 else{
        				 if(nothing == false) {
        					 if(teams==null){
        						 Intent i = new Intent(page2.this, players.class);
                			  	 i.putExtra("team1", temp1);
                			   	 i.putExtra("team2", temp2);
                			   	 i.putExtra("checked1",true);
                			   	 i.putExtra("checked2",true);
                			   	 startActivity(i);
        					 }
        					 else{
        			  	 Intent i = new Intent(page2.this, players.class);
        			  	 i.putExtra("team1", temp1);
        			   	 i.putExtra("team2", temp2);
        			   	 i.putExtra("checked1",false);
        			   	 i.putExtra("checked2",false);
        			   	 startActivity(i);
        					 }
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
	    		Intent i = new Intent(page2.this, GptichuActivity.class);
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