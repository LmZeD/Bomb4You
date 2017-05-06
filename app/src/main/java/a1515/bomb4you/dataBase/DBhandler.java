package a1515.bomb4you.dataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="Players";
    private static final String TABLE_PLAYER_DETAILS="Player";

    private static final String NAME="Name";
    private static final String CASH="Cash";
    private static final String GOLD="Gold";
    private static final String SCORE="Score";
    private static final String CLAN_NAME="ClanName";
    private static final String GAME_MODE="GameMode";

    public DBhandler(Context context){super(context,DATABASE_NAME,null,DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_PLAYER_DETAIL_TABLE="CREATE TABLE " + TABLE_PLAYER_DETAILS +"("
                +NAME+" TEXT PRIMARY KEY,"+
                CASH+" INTEGER, " +
                GOLD+" INTEGER, " +
                SCORE+" INTEGER, " +
                CLAN_NAME+" TEXT, "+
                GAME_MODE + "TEXT "+ ")";
        db.execSQL(CREATE_PLAYER_DETAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_DETAILS);

        onCreate(db);
    }

    public boolean addCash(){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        return false;
    }
}
