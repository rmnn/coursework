package ru.spbu.ageevd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_USER = "login";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_USER_RATING = "rating";
	private static final String DATABASE_TABLE = "users";
	private static final String KEY_NAME = "name";
	private Context context;
	private SQLiteDatabase database;
	private DataBaseHelper dbHelper;

	public DBAdapter(Context context) {
		this.context = context;
	}

	public DBAdapter open() throws SQLException {
		dbHelper = new DataBaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public long createUser(String userName, String userPassword) {
		ContentValues initialValues = createContentValues(userName,
				userPassword, 0, "");

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	public Cursor fetchAllUsers() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_USER, KEY_PASSWORD, KEY_USER_RATING, KEY_NAME }, null, null, null,
				null, null);
	}

	public Cursor fetchUser(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_USER, KEY_PASSWORD, KEY_USER_RATING }, KEY_ROWID
				+ "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public int getUserRating(String userName) {
		Cursor cursor = database.rawQuery(
				"SELECT rating FROM users WHERE TRIM(login) = '"
						+ userName.trim() + "'", null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return 0;
	}
	
	public int getRowId(String userName) {
		Cursor cursor = database.rawQuery(
				"SELECT _id FROM users WHERE TRIM(login) = '"
						+ userName.trim() + "'", null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return 0;
	}
	
	public String getUserName(String userLogin) {
		Cursor cursor = database.rawQuery(
				"SELECT name FROM users WHERE TRIM(login) = '"
						+ userLogin.trim() + "'", null);
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		}
		return null;
	}

	public boolean updateUserRating(String userName, int rating) {
		ContentValues updateValues = createContentValue(rating);
		Log.d("ASD", "SQL RABOTAET " + userName + "   " + Integer.toString(rating));
		if (rating > getUserRating(userName)) {
			Log.d("ASD", "ZASHLI SUDA");
			database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
					+ Integer.toString(getRowId(userName)), null);
			Log.d("ASD", "TUT NE DOLJNY BIT");
			return true;
					
		}
		return false;
	}
	
	public void updateUserName(String userName, String name) {
		ContentValues updateValues = createContentValue(name);
		Log.d("ASD", "SQL RABOTAET " + userName + "   " + name);
			Log.d("ASD", "ZASHLI SUDA");
			database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
					+ Integer.toString(getRowId(userName)), null);
			Log.d("ASD", "TUT NE DOLJNY BIT");
	}

	private ContentValues createContentValues(String userName,
			String userPassword, int rating, String name) {
		ContentValues values = new ContentValues();
		values.put(KEY_USER, userName);
		values.put(KEY_PASSWORD, userPassword);
		values.put(KEY_USER_RATING, rating);
		values.put(KEY_NAME, name);
		return values;
	}

	private ContentValues createContentValue(int rating) {
		ContentValues values = new ContentValues();
		values.put(KEY_USER_RATING, rating);
		return values;
	}
	
	private ContentValues createContentValue(String name) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		return values;
	}

	public Cursor isUserExist(String userName) {
		Cursor cursor = database.rawQuery(
				"SELECT * FROM users WHERE TRIM(login) = '" + userName.trim()
						+ "'", null);
		return cursor;
	}

	public boolean isPasswordCorrect(String userName, String userPassword) {
		Cursor cursor = isUserExist(userName);
		if (cursor.moveToFirst()) {
			if (cursor.getString(2).equals(userPassword)) {
				return true;
			}
		}
		return false;
	}

	public Cursor getTopUsers() {
		return database.rawQuery("SELECT * FROM users order by rating",
				null);
		

	}

	public void updateBD() {
		dbHelper.onUpgrade(database, 1, 2);
	}
}