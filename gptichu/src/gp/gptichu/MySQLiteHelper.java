package gp.gptichu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "tichu.db";
	private static final int DATABASE_VERSION = 1;
	//Table Old_Game
	public static final String OLDGAME_TABLE = "old_game";
	public static final String OLD_TEAM1 = "team1";
	public static final String OLD_TEAM2 = "team2";
	public static final String OLD_SCORE1 = "score1";
	public static final String OLD_SCORE2 = "score2";
	public static final String OLD_RNDNUM = "round_num";
	public static final String OLD_DLNUM = "deal_num";
	public static final String OLD_DEALER1 = "dealer1";
	public static final String OLD_DEALER2 = "dealer2";
	public static final String OLD_DEALER3 = "dealer3";
	public static final String OLD_DEALER4 = "dealer4";
	private static final String OLDGAME_CREATE =
			"create table old_game (team1 text not null, team2 text not null, "
	+ "score1 integer, score2 integer, round_num integer, deal_num integer,"
	+ "dealer1 text not null, dealer2 text not null, dealer3 text not null, dealer4 text not null," 
	+ " PRIMARY KEY(team1, team2, round_num));";
	//Table Team
	public static final String TEAM_TABLE = "team";
	public static final String TEAM_PLAYER1 = "player1";
	public static final String TEAM_PLAYER2 = "player2";
	public static final String TEAM_NAME = "team_name";
	private static final String TEAM_CREATE =
				"create table team (team_name text not null primary key,"
	+ "player1 text not null, player2 text not null);";
	//Table VS
	public static final String VS_TABLE = "vs";
	public static final String VS_TEAM1 = "team1";
	public static final String VS_TEAM2 = "team2";
	private static final String VS_TEAM_CREATE =
				"create table vs (team1 text not null, team2 text not null,"
	+ " PRIMARY KEY(team1, team2));";
	//Team Statistics
	public static final String STAT_TEAM_TABLE = "team_stats";
	public static final String STAT_TEAM_TEAM1 = "team1";
	public static final String STAT_TEAM_TEAM2 = "team2";
	public static final String STAT_TEAM_GAMES = "games_played";
	public static final String STAT_TEAM_SCORE1 = "score1";
	public static final String STAT_TEAM_SCORE2 = "score2";
	public static final String STAT_TEAM_DATE = "dategame";
	private static final String STAT_TEAM_CREATE =
	 		    "create table team_stats (team1 text not null, team2 text not null, games_played integer,"
	+ "score1 integer, score2 integer, "
	+ "dategame text not null, PRIMARY KEY(team1, team2, games_played));";
	//Player Statistics
	public static final String STAT_PLAYER_TABLE = "player_stats";
	public static final String STAT_PLAYER_USERNAME = "username";
	public static final String STAT_PLAYER_TICHU = "tichu";
	public static final String STAT_PLAYER_GTICHU = "gtichu";
	public static final String STAT_PLAYER_NTICHU = "ntichu";
	public static final String STAT_PLAYER_NGTICHU = "ngtichu";
	private static final String STAT_PLAYER_CREATE =
				"create table player_stats (username text not null primary key,"
	+ "tichu integer, gtichu integer, ntichu integer, ngtichu integer);";
	// Database creation sql statement
	public MySQLiteHelper(Context context) { 
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(OLDGAME_CREATE);
		database.execSQL(TEAM_CREATE);
		database.execSQL(STAT_TEAM_CREATE);
		database.execSQL(STAT_PLAYER_CREATE);
		database.execSQL(VS_TEAM_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		//db.execSQL("DROP TABLE IF EXISTS" + TABLE_COMMENTS);
		onCreate(db);
	}
}