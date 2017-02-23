package gp.gptichu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TichuDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	public TichuDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	public long insertTeam(String team_name, String player1, String player2) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(MySQLiteHelper.TEAM_PLAYER1, player1);
		initialValues.put(MySQLiteHelper.TEAM_PLAYER2, player2);
		initialValues.put(MySQLiteHelper.TEAM_NAME, team_name);
		return database.insert(MySQLiteHelper.TEAM_TABLE, null, initialValues);
	}
	public long insertVSTeam(String team1, String team2) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(MySQLiteHelper.VS_TEAM1, team1);
		initialValues.put(MySQLiteHelper.VS_TEAM2, team2);
		return database.insert(MySQLiteHelper.VS_TABLE, null, initialValues);
	}
	public void newVS() {
		String query = "select * from " + MySQLiteHelper.VS_TABLE;
		Cursor c = database.rawQuery(query, null);
		int Team1Column = c.getColumnIndex(MySQLiteHelper.VS_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.VS_TEAM2);
		if(c!=null) {
			c.moveToFirst();
			do {
				int result = c.getString(Team1Column).compareTo(c.getString(Team2Column));
				if (result > 0) {
					ContentValues args = new ContentValues();
				    args.put(MySQLiteHelper.VS_TEAM1, c.getString(Team2Column));
				    args.put(MySQLiteHelper.VS_TEAM2, c.getString(Team1Column));
				    database.update(MySQLiteHelper.VS_TABLE,
				    args, MySQLiteHelper.VS_TEAM1 + "=?" + " AND " +
				    MySQLiteHelper.VS_TEAM2 + "=?",
				    new String[] { c.getString(Team1Column), c.getString(Team2Column) });  										
				}
			}
			while(c.moveToNext());
		c.close();
		}	
	}
	public void newOldGame() {
		String query = "select * from " + MySQLiteHelper.OLDGAME_TABLE;
		Cursor c = database.rawQuery(query, null);
		int Team1Column = c.getColumnIndex(MySQLiteHelper.OLD_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.OLD_TEAM2);
		int Score1Column = c.getColumnIndex(MySQLiteHelper.OLD_SCORE1);
		int Score2Column = c.getColumnIndex(MySQLiteHelper.OLD_SCORE2);
		int RoundColumn = c.getColumnIndex(MySQLiteHelper.OLD_RNDNUM);
		if(c!=null) {
			c.moveToFirst();
			do {
				int result = c.getString(Team1Column).compareTo(c.getString(Team2Column));
				if (result > 0) {
					ContentValues args = new ContentValues();
				    args.put(MySQLiteHelper.OLD_TEAM1, c.getString(Team2Column));
				    args.put(MySQLiteHelper.OLD_TEAM2, c.getString(Team1Column));
				    args.put(MySQLiteHelper.OLD_SCORE1, c.getString(Score2Column));
				    args.put(MySQLiteHelper.OLD_SCORE2, c.getString(Score1Column));
				    database.update(MySQLiteHelper.OLDGAME_TABLE,
				    args, MySQLiteHelper.OLD_TEAM1 + "=?" + " AND " +
				    MySQLiteHelper.OLD_TEAM2 + "=?" + " AND " + MySQLiteHelper.OLD_RNDNUM +
				    "=?",
				    new String[] { c.getString(Team1Column), c.getString(Team2Column), 
				    		c.getString(RoundColumn) });  										
				}
			}
			while(c.moveToNext());
		c.close();
		}	
	}
	public void newTeamStats() {
		String query = "select * from " + MySQLiteHelper.STAT_TEAM_TABLE;
		Cursor c = database.rawQuery(query, null);
		int Team1Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_TEAM2);
		int Score1Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE1);
		int Score2Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE2);
		int GamesColumn = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_GAMES);
		if(c!=null) {
			c.moveToFirst();
			do {
				int result = c.getString(Team1Column).compareTo(c.getString(Team2Column));
				if (result > 0) {
					ContentValues args = new ContentValues();
				    args.put(MySQLiteHelper.STAT_TEAM_TEAM1, c.getString(Team2Column));
				    args.put(MySQLiteHelper.STAT_TEAM_TEAM2, c.getString(Team1Column));
				    args.put(MySQLiteHelper.STAT_TEAM_SCORE1, c.getString(Score2Column));
				    args.put(MySQLiteHelper.STAT_TEAM_SCORE2, c.getString(Score1Column));
				    database.update(MySQLiteHelper.STAT_TEAM_TABLE,
				    args, MySQLiteHelper.STAT_TEAM_TEAM1 + "=?" + " AND " +
				    MySQLiteHelper.STAT_TEAM_TEAM2 + "=?" + " AND " +
				    MySQLiteHelper.STAT_TEAM_GAMES + "=?",
				    new String[] { c.getString(Team1Column),
				    		c.getString(Team2Column), c.getString(GamesColumn) });  										
				}
			}
			while(c.moveToNext());
		c.close();
		}	
	}
	public void newVS1(String team) {
		String query = "select * from " + MySQLiteHelper.VS_TABLE +
				" where " + MySQLiteHelper.VS_TEAM1 + "=? or " +
				MySQLiteHelper.VS_TEAM2 + "=?";
		Cursor c = database.rawQuery(query, new String[] { team , team }); 
		int Team1Column = c.getColumnIndex(MySQLiteHelper.VS_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.VS_TEAM2);
		if(c!=null) {
			c.moveToFirst();
			do {
				int result = c.getString(Team1Column).compareTo(c.getString(Team2Column));
				if (result > 0) {
					ContentValues args = new ContentValues();
				    args.put(MySQLiteHelper.VS_TEAM1, c.getString(Team2Column));
				    args.put(MySQLiteHelper.VS_TEAM2, c.getString(Team1Column));
				    database.update(MySQLiteHelper.VS_TABLE,
				    args, MySQLiteHelper.VS_TEAM1 + "=?" + " AND " +
				    MySQLiteHelper.VS_TEAM2 + "=?",
				    new String[] { c.getString(Team1Column), c.getString(Team2Column) });  										
				}
			}
			while(c.moveToNext());
		c.close();
		}	
	}
	public void newOldGame1(String team) {
		String query = "select * from " + MySQLiteHelper.OLDGAME_TABLE +
				" where " + MySQLiteHelper.OLD_TEAM1 + "=? or " +
				MySQLiteHelper.OLD_TEAM2 + "=?";
		Cursor c = database.rawQuery(query, new String[] { team , team });
		int Team1Column = c.getColumnIndex(MySQLiteHelper.OLD_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.OLD_TEAM2);
		int Score1Column = c.getColumnIndex(MySQLiteHelper.OLD_SCORE1);
		int Score2Column = c.getColumnIndex(MySQLiteHelper.OLD_SCORE2);
		int RoundColumn = c.getColumnIndex(MySQLiteHelper.OLD_RNDNUM);
		if(c!=null) {
			c.moveToFirst();
			do {
				int result = c.getString(Team1Column).compareTo(c.getString(Team2Column));
				if (result > 0) {
					ContentValues args = new ContentValues();
				    args.put(MySQLiteHelper.OLD_TEAM1, c.getString(Team2Column));
				    args.put(MySQLiteHelper.OLD_TEAM2, c.getString(Team1Column));
				    args.put(MySQLiteHelper.OLD_SCORE1, c.getString(Score2Column));
				    args.put(MySQLiteHelper.OLD_SCORE2, c.getString(Score1Column));
				    database.update(MySQLiteHelper.OLDGAME_TABLE,
				    args, MySQLiteHelper.OLD_TEAM1 + "=?" + " AND " +
				    MySQLiteHelper.OLD_TEAM2 + "=?" + " AND " + MySQLiteHelper.OLD_RNDNUM +
				    "=?",
				    new String[] { c.getString(Team1Column), c.getString(Team2Column), 
				    		c.getString(RoundColumn) });  										
				}
			}
			while(c.moveToNext());
		c.close();
		}	
	}
	public void newTeamStats1(String team) {
		String query = "select * from " + MySQLiteHelper.STAT_TEAM_TABLE +
				" where " + MySQLiteHelper.STAT_TEAM_TEAM1 + "=? or " +
				MySQLiteHelper.STAT_TEAM_TEAM2 + "=?";
		Cursor c = database.rawQuery(query, new String[] { team , team });
		int Team1Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_TEAM2);
		int Score1Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE1);
		int Score2Column = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE2);
		int GamesColumn = c.getColumnIndex(MySQLiteHelper.STAT_TEAM_GAMES);
		if(c!=null) {
			c.moveToFirst();
			do {
				int result = c.getString(Team1Column).compareTo(c.getString(Team2Column));
				if (result > 0) {
					ContentValues args = new ContentValues();
				    args.put(MySQLiteHelper.STAT_TEAM_TEAM1, c.getString(Team2Column));
				    args.put(MySQLiteHelper.STAT_TEAM_TEAM2, c.getString(Team1Column));
				    args.put(MySQLiteHelper.STAT_TEAM_SCORE1, c.getString(Score2Column));
				    args.put(MySQLiteHelper.STAT_TEAM_SCORE2, c.getString(Score1Column));
				    database.update(MySQLiteHelper.STAT_TEAM_TABLE,
				    args, MySQLiteHelper.STAT_TEAM_TEAM1 + "=?" + " AND " +
				    MySQLiteHelper.STAT_TEAM_TEAM2 + "=?" + " AND " +
				    MySQLiteHelper.STAT_TEAM_GAMES + "=?",
				    new String[] { c.getString(Team1Column),
				    		c.getString(Team2Column), c.getString(GamesColumn) });  										
				}
			}
			while(c.moveToNext());
		c.close();
		}	
	}
	public void EditPlayer(String OldPlayerName, String NewPlayerName) {
		//Update table player_stats
		ContentValues args = new ContentValues();
		args.put(MySQLiteHelper.STAT_PLAYER_USERNAME, NewPlayerName);
		database.update(MySQLiteHelper.STAT_PLAYER_TABLE,
		args, MySQLiteHelper.STAT_PLAYER_USERNAME + "=?",
		new String[] { OldPlayerName });  
		
		//Update table team
		ContentValues args1 = new ContentValues();				
		args1.put(MySQLiteHelper.TEAM_PLAYER1, NewPlayerName);
		database.update(MySQLiteHelper.TEAM_TABLE,
		args1, MySQLiteHelper.TEAM_PLAYER1 + "=?",
	    new String[] { OldPlayerName });  
		//
		ContentValues args2 = new ContentValues();				
		args2.put(MySQLiteHelper.TEAM_PLAYER2, NewPlayerName);
		database.update(MySQLiteHelper.TEAM_TABLE,
		args2, MySQLiteHelper.TEAM_PLAYER2 + "=?",
	    new String[] { OldPlayerName });  
		
		if(oldExistsAny() == true) {
			//Update table old_game
			ContentValues args3 = new ContentValues();				
			args3.put(MySQLiteHelper.OLD_DEALER1, NewPlayerName);
			database.update(MySQLiteHelper.OLDGAME_TABLE,
					args3, MySQLiteHelper.OLD_DEALER1 + "=?",
					new String[] { OldPlayerName });  
			//
			ContentValues args4 = new ContentValues();				
			args4.put(MySQLiteHelper.OLD_DEALER2, NewPlayerName);
			database.update(MySQLiteHelper.OLDGAME_TABLE,
					args4, MySQLiteHelper.OLD_DEALER2 + "=?",
					new String[] { OldPlayerName });  	
			//
			ContentValues args5 = new ContentValues();				
			args5.put(MySQLiteHelper.OLD_DEALER3, NewPlayerName);
			database.update(MySQLiteHelper.OLDGAME_TABLE,
					args5, MySQLiteHelper.OLD_DEALER3 + "=?",
					new String[] { OldPlayerName }); 
			//
			ContentValues args6 = new ContentValues();				
			args6.put(MySQLiteHelper.OLD_DEALER4, NewPlayerName);
			database.update(MySQLiteHelper.OLDGAME_TABLE,
					args6, MySQLiteHelper.OLD_DEALER4 + "=?",
					new String[] { OldPlayerName }); 
		}
	}
	public void EditTeam(String OldTeamName, String NewTeamName) {
		//Update table team
		ContentValues args1 = new ContentValues();				
		args1.put(MySQLiteHelper.TEAM_NAME, NewTeamName);
		database.update(MySQLiteHelper.TEAM_TABLE,
		args1, MySQLiteHelper.TEAM_NAME + "=?",
		new String[] { OldTeamName }); 
		
		//Update table old_game
		if(oldExistsAny() == true) {
			ContentValues args3 = new ContentValues();				
			args3.put(MySQLiteHelper.OLD_TEAM1, NewTeamName);
			database.update(MySQLiteHelper.OLDGAME_TABLE,
					args3, MySQLiteHelper.OLD_TEAM1 + "=?",
					new String[] { OldTeamName });  
			//
			ContentValues args4 = new ContentValues();				
			args4.put(MySQLiteHelper.OLD_TEAM2, NewTeamName);
			database.update(MySQLiteHelper.OLDGAME_TABLE,
					args4, MySQLiteHelper.OLD_TEAM2 + "=?",
					new String[] { OldTeamName });
			newOldGame();
		}
		
		//Update table stat_team
		if(StatTeamExistsAny() == true) {
			ContentValues args5 = new ContentValues();				
			args5.put(MySQLiteHelper.STAT_TEAM_TEAM1, NewTeamName);
			database.update(MySQLiteHelper.STAT_TEAM_TABLE,
					args5, MySQLiteHelper.STAT_TEAM_TEAM1 + "=?",
					new String[] { OldTeamName });  
			//
			ContentValues args6 = new ContentValues();				
			args6.put(MySQLiteHelper.STAT_TEAM_TEAM2, NewTeamName);
			database.update(MySQLiteHelper.STAT_TEAM_TABLE,
					args6, MySQLiteHelper.STAT_TEAM_TEAM2 + "=?",
					new String[] { OldTeamName }); 
			newTeamStats();
		}
		
		//Update table vs 
		if(VSExistsAny() == true) {
			ContentValues args7 = new ContentValues();				
			args7.put(MySQLiteHelper.VS_TEAM1, NewTeamName);
			database.update(MySQLiteHelper.VS_TABLE,
					args7, MySQLiteHelper.VS_TEAM1 + "=?",
					new String[] { OldTeamName });  
			//
			ContentValues args8 = new ContentValues();				
			args8.put(MySQLiteHelper.VS_TEAM2, NewTeamName);
			database.update(MySQLiteHelper.VS_TABLE,
					args8, MySQLiteHelper.VS_TEAM2 + "=?",
					new String[] { OldTeamName }); 
			newVS();
		}
	}
	public long insertOldGame(String team1, String team2, 
			Integer score1, Integer score2, Integer round_num, Integer dealer_num,
			String dealer1, String dealer2, String dealer3, String dealer4) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(MySQLiteHelper.OLD_TEAM1, team1);
		initialValues.put(MySQLiteHelper.OLD_TEAM2, team2);
		initialValues.put(MySQLiteHelper.OLD_SCORE1, score1);
		initialValues.put(MySQLiteHelper.OLD_SCORE2, score2);
		initialValues.put(MySQLiteHelper.OLD_RNDNUM, round_num);
		initialValues.put(MySQLiteHelper.OLD_DLNUM, dealer_num);
		initialValues.put(MySQLiteHelper.OLD_DEALER1, dealer1);
		initialValues.put(MySQLiteHelper.OLD_DEALER2, dealer2);
		initialValues.put(MySQLiteHelper.OLD_DEALER3, dealer3);
		initialValues.put(MySQLiteHelper.OLD_DEALER4, dealer4);
		return database.insert(MySQLiteHelper.OLDGAME_TABLE, null, initialValues);
	}  
	public long insertStatTeam(String team1, String team2, Integer games_played,
			Integer score1, Integer score2, String dategame) {
		ContentValues initialValues = new ContentValues();
		int result = team1.compareTo(team2);
		if (result > 0) {
			initialValues.put(MySQLiteHelper.STAT_TEAM_TEAM1, team2);
			initialValues.put(MySQLiteHelper.STAT_TEAM_TEAM2, team1);
			initialValues.put(MySQLiteHelper.STAT_TEAM_GAMES, games_played);
			initialValues.put(MySQLiteHelper.STAT_TEAM_SCORE1, score2);
			initialValues.put(MySQLiteHelper.STAT_TEAM_SCORE2, score1);
			initialValues.put(MySQLiteHelper.STAT_TEAM_DATE, dategame);
		}
		else if (result < 0) {
			initialValues.put(MySQLiteHelper.STAT_TEAM_TEAM1, team1);
			initialValues.put(MySQLiteHelper.STAT_TEAM_TEAM2, team2);
			initialValues.put(MySQLiteHelper.STAT_TEAM_GAMES, games_played);
			initialValues.put(MySQLiteHelper.STAT_TEAM_SCORE1, score1);
			initialValues.put(MySQLiteHelper.STAT_TEAM_SCORE2, score2);
			initialValues.put(MySQLiteHelper.STAT_TEAM_DATE, dategame);
		}
		return database.insert(MySQLiteHelper.STAT_TEAM_TABLE, null, initialValues);
	}
	public long insertStatPlayer(String username, Integer tichu,
			 Integer gtichu, Integer ntichu, Integer ngtichu) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(MySQLiteHelper.STAT_PLAYER_USERNAME, username);
		initialValues.put(MySQLiteHelper.STAT_PLAYER_TICHU, tichu);
		initialValues.put(MySQLiteHelper.STAT_PLAYER_GTICHU, gtichu);
		initialValues.put(MySQLiteHelper.STAT_PLAYER_NTICHU, ntichu);
		initialValues.put(MySQLiteHelper.STAT_PLAYER_NGTICHU, ngtichu);
		return database.insert(MySQLiteHelper.STAT_PLAYER_TABLE, null, initialValues);
	}
	public void updatePlayerStat(String username, String type, Integer what) {
		String getCount = "SELECT * FROM " +
				     MySQLiteHelper.STAT_PLAYER_TABLE +
		    		 " WHERE " +
		    		 MySQLiteHelper.STAT_PLAYER_USERNAME +
		    		 "=?";		
		Cursor tichuCount = database.rawQuery(getCount, new String[] { username } );
		tichuCount.moveToFirst();
		int i = tichuCount.getColumnIndex(type); 
	    int tcount = tichuCount.getInt(i);
	    ContentValues args = new ContentValues();
	    if(what == 0) {
	    	tcount++;
	    }
	    else {
	        tcount--;
	    }
	    args.put(type, tcount);
	    database.update(MySQLiteHelper.STAT_PLAYER_TABLE,
	    		args,  MySQLiteHelper.STAT_PLAYER_USERNAME + "='" +username+"'", null);  
	}
	public Integer[] getPlayerStat(String username) {
		Integer[] player_stat = new Integer[4];
		String getCount = "SELECT * FROM " +
		    		 MySQLiteHelper.STAT_PLAYER_TABLE +
		    		 " WHERE " +
		    		 MySQLiteHelper.STAT_PLAYER_USERNAME +
		    		 "=?";		
		Cursor tichuCount = database.rawQuery(getCount, new String[] { username } );
		tichuCount.moveToFirst();
			
		int i1 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_PLAYER_TICHU); 
		int i2 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_PLAYER_NTICHU); 
		int i3 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_PLAYER_GTICHU); 
		int i4 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_PLAYER_NGTICHU);
		
		player_stat[0]= tichuCount.getInt(i1);
		player_stat[1]= tichuCount.getInt(i2);
		player_stat[2]= tichuCount.getInt(i3);
		player_stat[3]= tichuCount.getInt(i4);
	    return player_stat;
	}
	public String[] getTeamStat(String vteam1, String team2, Integer games) {
		String[] team_stat = new String[4];
		String query = "select * from " + MySQLiteHelper.STAT_TEAM_TABLE +
				" where " 
				+ MySQLiteHelper.STAT_TEAM_TEAM1 + "=?" +
				" and " 
				+ MySQLiteHelper.STAT_TEAM_TEAM2 + "=?" +
				" and "
				+ MySQLiteHelper.STAT_TEAM_GAMES + "=" + games;
		int result = vteam1.compareTo(team2);
		if (result > 0) {
			Cursor tichuCount = database.rawQuery(query, new String[] { team2, vteam1 } );
			if(tichuCount.getCount() > 0) {	
				tichuCount.moveToFirst();
				int i1 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE1); 
				int i2 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE2); 
				int i3 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_TEAM_DATE); 
				team_stat[0]= tichuCount.getString(i2);
				team_stat[1]= tichuCount.getString(i1);
				team_stat[2]= tichuCount.getString(i3);
			}
			else {
				team_stat = null;
			}
		}
		else if (result < 0) {
			Cursor tichuCount = database.rawQuery(query, new String[] { vteam1, team2 } );
			if(tichuCount.getCount() > 0) {	
				tichuCount.moveToFirst();
				int i1 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE1); 
				int i2 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_TEAM_SCORE2); 
				int i3 = tichuCount.getColumnIndex(MySQLiteHelper.STAT_TEAM_DATE); 
				team_stat[0]= tichuCount.getString(i1);
				team_stat[1]= tichuCount.getString(i2);
				team_stat[2]= tichuCount.getString(i3);
			}
			else {
				team_stat = null;
			}
		}
		return team_stat;
	}
	public String [] getPendingGames() {
		String[] games = new String[50];
		String query = "select " + MySQLiteHelper.OLD_TEAM1 +
					   "," + MySQLiteHelper.OLD_TEAM2 +
				       " from " + MySQLiteHelper.OLDGAME_TABLE;
		Cursor c = database.rawQuery(query, null);
		int Team1Column = c.getColumnIndex(MySQLiteHelper.OLD_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.OLD_TEAM2);
		String team1;
		String team2;
		int i=0;
		int k;
		boolean counter = false;
		if(c!=null) {
			c.moveToFirst();
			do {
				team1 = c.getString(Team1Column);
				team2 = c.getString(Team2Column);
				k=0;
				if(i>0) {
					for(k=0;k<i;k=k+2) {
						if((team1.equals(games[k])) && (team2.equals(games[k+1]))) {
							counter = true;
						}
					}
					if(counter == false) {
						games[i] = team1;
						games[++i] = team2;
						i++;
					}
					else {
						counter = false;
					}
				}
				else {
					games[i] = team1;
					games[++i] = team2;
					i++;
				}
			}
			while(c.moveToNext());
		}
		c.close();
		return games;
	}  
	public boolean Exists(String _id) {
		Cursor cursor = database.rawQuery("select * from " +
		MySQLiteHelper.STAT_PLAYER_TABLE + 
		" where " + MySQLiteHelper.STAT_PLAYER_USERNAME + "=?", new String[] { _id });
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public boolean TeamExists(String _id) { 
		Cursor cursor = database.rawQuery("select * from " +
		MySQLiteHelper.TEAM_TABLE + 
		" where " + MySQLiteHelper.TEAM_NAME + "=?", new String[] { _id });
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public boolean StatTeamExists(String team) { 
		Cursor cursor = database.rawQuery("select * from " +
		MySQLiteHelper.STAT_TEAM_TABLE + 
		" where " + MySQLiteHelper.STAT_TEAM_TEAM1 + " = ?" +
		" or " + MySQLiteHelper.STAT_TEAM_TEAM2 + " = ?", new String[] { team, team });
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public void deleteOld(String team1, String team2) {
		database.delete(MySQLiteHelper.OLDGAME_TABLE,
		MySQLiteHelper.OLD_TEAM1 + "=?" + " AND " +
		MySQLiteHelper.OLD_TEAM2 + "=?",
		new String[] { team1, team2 });
	}
	public void deleteOldRow(String team1, String team2, Integer round) {
		database.delete(MySQLiteHelper.OLDGAME_TABLE,
	    MySQLiteHelper.OLD_TEAM1 + "=?" + " AND " +
		MySQLiteHelper.OLD_TEAM2 + "=? AND " +
		MySQLiteHelper.OLD_RNDNUM + "=" + round,
		new String[] { team1, team2 });
	}
	public String[] getScoreOld(String team1, String team2, Integer games) {
		String[] team_stat = new String[2];
		String query = "select * from " + MySQLiteHelper.OLDGAME_TABLE +
				" where " 
				+ MySQLiteHelper.OLD_TEAM1 + "=?" +
				" and " 
				+ MySQLiteHelper.OLD_TEAM2 + "=?" +
				" and "
				+ MySQLiteHelper.OLD_RNDNUM + "=" + games;
		Cursor tichuCount = database.rawQuery(query, new String[] { team1, team2 } );
		if(tichuCount.getCount() > 0) {	
			tichuCount.moveToFirst();
			int i1 = tichuCount.getColumnIndex(MySQLiteHelper.OLD_SCORE1); 
			int i2 = tichuCount.getColumnIndex(MySQLiteHelper.OLD_SCORE2); 
			team_stat[0]= tichuCount.getString(i1);
			team_stat[1]= tichuCount.getString(i2);
		}
		else {
			team_stat = null;
		}
		return team_stat;
	}
	public int countGamesPlayed(String teamS1, String teamS2) {
		int jcount = 0;
		String query = "select count(*) from " + MySQLiteHelper.STAT_TEAM_TABLE +
		" where " 
		+ MySQLiteHelper.STAT_TEAM_TEAM1 + "=?" +
		" and " 
		+ MySQLiteHelper.STAT_TEAM_TEAM2 + "=?";
		int result = teamS1.compareTo(teamS2);
		if (result > 0) {
			Cursor dataCount1 = database.rawQuery(query, new String[] { teamS2, teamS1 } );
	        dataCount1.moveToFirst();
	        jcount = dataCount1.getInt(0);
	        dataCount1.close();
		}
		else if (result < 0) {
			Cursor dataCount1 = database.rawQuery(query, new String[] { teamS1, teamS2 } );
	        dataCount1.moveToFirst();
	        jcount = dataCount1.getInt(0);
	        dataCount1.close();
		}
        return jcount;
    }
	public boolean VSexists(String teamS1, String teamS2) {
		String query = "select * from " + MySQLiteHelper.VS_TABLE +
		" where " 
		+ MySQLiteHelper.VS_TEAM1 + "=?" +
		" and " 
		+ MySQLiteHelper.VS_TEAM2 + "=?";
		Cursor cursor = database.rawQuery(query, new String[] { teamS1, teamS2 } );
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public String[] getVS (String teamS1) {
		String[] vs = new String [50];
		String query = "select * from " + MySQLiteHelper.VS_TABLE +
		" where " 
		+ MySQLiteHelper.VS_TEAM1 + "=?" +
		" or "
		+ MySQLiteHelper.VS_TEAM2 + "=?";
		Cursor c = database.rawQuery(query, new String[] { teamS1,  teamS1 } );
		int Team1Column = c.getColumnIndex(MySQLiteHelper.VS_TEAM1);
		int Team2Column = c.getColumnIndex(MySQLiteHelper.VS_TEAM2);
		if(c!=null) {
			int i=0;
			c.moveToFirst();
			do {
				if(c.getString(Team1Column).equals(teamS1)) {
					vs[i] = c.getString(Team2Column);
				}
				else {
					vs[i] = c.getString(Team1Column);
				}
				i++;
			}
			while(c.moveToNext());
		c.close();
		}
		return vs;
	}
	public boolean oldExistsAny() { 
		Cursor cursor = database.rawQuery("select * from " +
		MySQLiteHelper.OLDGAME_TABLE, null);
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public boolean StatTeamExistsAny() { 
		Cursor cursor = database.rawQuery("select * from " +
		MySQLiteHelper.STAT_TEAM_TABLE, null);
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public boolean VSExistsAny() { 
		Cursor cursor = database.rawQuery("select * from " +
		MySQLiteHelper.VS_TABLE, null);
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public String[] getTeamPlayers(String team) {
		String[] players = new String[50];
		String query = "select " + MySQLiteHelper.TEAM_PLAYER1 +
		"," + MySQLiteHelper.TEAM_PLAYER2 + " from " 
	       + MySQLiteHelper.TEAM_TABLE +
		" where " 
		+ MySQLiteHelper.TEAM_NAME + "=?";
		Cursor cplayers = database.rawQuery(query, new String[] { team } );
		int Pl1Column = cplayers.getColumnIndex(MySQLiteHelper.TEAM_PLAYER1);
		int Pl2Column = cplayers.getColumnIndex(MySQLiteHelper.TEAM_PLAYER2);
		cplayers.moveToFirst();
		players[0] = cplayers.getString(Pl1Column);
		players[1] = cplayers.getString(Pl2Column);
		cplayers.close();
		return players;				        
	}
	public String[] getTeams() {
		String[] teams = new String[200];
	    int i=0; 
		String query = "select " + MySQLiteHelper.TEAM_NAME + " from " +
	    MySQLiteHelper.TEAM_TABLE + " ORDER BY " + MySQLiteHelper.TEAM_NAME;
		Cursor cteams = database.rawQuery(query, null );
		if(cteams.getCount()>0) {
			int TeamNColumn = cteams.getColumnIndex(MySQLiteHelper.TEAM_NAME);
			cteams.moveToFirst();
			if(cteams!=null) {
				cteams.moveToFirst();
				do {  
					teams[i] = cteams.getString(TeamNColumn);
					i++;
				}
				while(cteams.moveToNext());
			}
		}
		else {
			teams=null;
		}
		cteams.close();
		return teams;				        
    }
	public String[] getPlayers() {
		String[] teams = new String[200];
	    int i=0;
		String query = "select " + MySQLiteHelper.STAT_PLAYER_USERNAME + " from " +
		MySQLiteHelper.STAT_PLAYER_TABLE + " ORDER BY " + MySQLiteHelper.STAT_PLAYER_USERNAME;
		Cursor cteams = database.rawQuery(query, null);
		if(cteams.getCount()>0) {
			int TeamNColumn = cteams.getColumnIndex(MySQLiteHelper.STAT_PLAYER_USERNAME);
			cteams.moveToFirst();
			if(cteams!=null) {
				cteams.moveToFirst();
				do {  
					teams[i] = cteams.getString(TeamNColumn);
					i++;
				}
				while(cteams.moveToNext());
			}
		}
		else {
			teams=null;
		}
		cteams.close();
		return teams;				        
    }
	public String[] getOldGame(String team1, String team2, Integer round) {
		String[] oldgame = new String[50];
		String query = "select " + MySQLiteHelper.OLD_SCORE1 + ","
				+ MySQLiteHelper.OLD_SCORE2 + ","
				+ MySQLiteHelper.OLD_DLNUM + 
				" from " + MySQLiteHelper.OLDGAME_TABLE +
				" where " + MySQLiteHelper.OLD_TEAM1 +
				"=?" + " and "+ MySQLiteHelper.OLD_TEAM2 + "=?" +
				" and " + MySQLiteHelper.OLD_RNDNUM + "=?";
		Cursor cplayers = database.rawQuery(query, new String[] { team1, team2, round.toString() } );
		int Pl1Column = cplayers.getColumnIndex(MySQLiteHelper.OLD_SCORE1);
		int Pl2Column = cplayers.getColumnIndex(MySQLiteHelper.OLD_SCORE2);
		int Pl3Column = cplayers.getColumnIndex(MySQLiteHelper.OLD_DLNUM);
		cplayers.moveToFirst();
		oldgame[0] = cplayers.getString(Pl1Column);
		oldgame[1] = cplayers.getString(Pl2Column);
		oldgame[2] = cplayers.getString(Pl3Column);
		cplayers.close();
		return oldgame;				        
    }
	public String[] getOldDealers(String team1, String team2) {
		String[] oldDealers = new String[4];
		String query = "select * from " + MySQLiteHelper.OLDGAME_TABLE +
				" where " + MySQLiteHelper.OLD_TEAM1 +
				"=?" + " and "+ MySQLiteHelper.OLD_TEAM2 + "=?" +
				" and " + MySQLiteHelper.OLD_RNDNUM + "=1";
		Cursor cplayers = database.rawQuery(query, new String[] { team1, team2 } );
		int Pl1Column = cplayers.getColumnIndex(MySQLiteHelper.OLD_DEALER1);
		int Pl2Column = cplayers.getColumnIndex(MySQLiteHelper.OLD_DEALER2);
		int Pl3Column = cplayers.getColumnIndex(MySQLiteHelper.OLD_DEALER3);
		int Pl4Column = cplayers.getColumnIndex(MySQLiteHelper.OLD_DEALER4);
		cplayers.moveToFirst();
		oldDealers[0] = cplayers.getString(Pl1Column);
		oldDealers[1] = cplayers.getString(Pl2Column);
		oldDealers[2] = cplayers.getString(Pl3Column);
		oldDealers[3] = cplayers.getString(Pl4Column);
		cplayers.close();
		return oldDealers;			        
    }
	public boolean oldExists(String team1, String team2, Integer round) {
		String query = "select * from " + MySQLiteHelper.OLDGAME_TABLE +
				" where " + MySQLiteHelper.OLD_TEAM1 +
				"=?" + " and "+ MySQLiteHelper.OLD_TEAM2 + "=?" +
				" and " + MySQLiteHelper.OLD_RNDNUM + "=?";
		Cursor cursor = database.rawQuery(query, new String[] { team1, team2,round.toString() } );
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	public boolean PlayerExistsInTeam(String player) {
		String query = "select * from " + MySQLiteHelper.TEAM_TABLE +
		" where " 
		+ MySQLiteHelper.TEAM_PLAYER1 + " = ?" +
		" or "
		+ MySQLiteHelper.TEAM_PLAYER2 + " = ?";
		Cursor cursor = database.rawQuery(query, new String[] { player, player } );
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
	    return exists;
	}
	public void deleteTeam(String team) {
		database.delete(MySQLiteHelper.TEAM_TABLE, MySQLiteHelper.TEAM_NAME
				+ " = ?", new String[] { team } );
		database.delete(MySQLiteHelper.OLDGAME_TABLE, MySQLiteHelper.OLD_TEAM1
				+ " = ?" + " or " + MySQLiteHelper.OLD_TEAM2 + " = ?", new String[] { team, team } );
	}
	public void deletePlayer(String player) {
		database.delete(MySQLiteHelper.STAT_PLAYER_TABLE, MySQLiteHelper.STAT_PLAYER_USERNAME
				+ " = ?", new String[] { player } );	
	}
	public void deleteAll() {
		database.delete(MySQLiteHelper.OLDGAME_TABLE, null, null );
		database.delete(MySQLiteHelper.STAT_PLAYER_TABLE, null, null );
		database.delete(MySQLiteHelper.TEAM_TABLE, null, null );
		database.delete(MySQLiteHelper.STAT_TEAM_TABLE, null, null );
		database.delete(MySQLiteHelper.VS_TABLE, null, null );
	}
}
