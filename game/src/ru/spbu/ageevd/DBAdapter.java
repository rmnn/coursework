package ru.spbu.ageevd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

	// ���� ���� ������
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

	/**
	 * ������� ����� ������� ������ ���. ���� ������ ������� - ������������
	 * ����� ������ rowId ����� -1
	 */
	public long createUser(String userName, String userPassword) {
		ContentValues initialValues = createContentValues(userName,
				userPassword);

		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	/**
	 * ���������� ������ �� ����� ���������� ������ ���
	 * 
	 * @return ������ � ������������ ���� �������
	 */
	public Cursor fetchAllUsers() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_USER, KEY_PASSWORD }, null, null, null, null, null);
	}

	/**
	 * ���������� ������, ������������������ �� ��������� ������
	 */
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

	public boolean isUserExist(String userName) {
		Cursor cursor = database
			.rawQuery("SELECT password FROM users WHERE login="
					+ userName, null);
		return cursor.getColumnCount() != 0? true : false ;
		
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