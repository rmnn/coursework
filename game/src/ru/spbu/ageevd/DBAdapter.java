package ru.spbu.ageevd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_USER = "login";
	public static final String KEY_PASSWORD = "password";
	private static final String DATABASE_TABLE = "users";
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
				userPassword);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	public Cursor fetchAllUsers() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_USER, KEY_PASSWORD }, null, null, null, null, null);
	}

	public Cursor fetchUser(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_USER, KEY_PASSWORD }, KEY_ROWID + "=" + rowId,
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String userName,
			String userPassword) {
		ContentValues values = new ContentValues();
		values.put(KEY_USER, userName);
		values.put(KEY_PASSWORD, userPassword);
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

	public int getAllUsers() {
		Cursor cursor = database.rawQuery("SELECT COUNT(login) FROM users",
				null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return cursor.getInt(0);

	}
}