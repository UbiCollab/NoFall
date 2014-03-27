package ntnu.master.nofall;

import ntnu.master.nofall.contentprovider.MyContentProvider;
import ntnu.master.nofall.database.UserTable;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/*
 * TodoDetailActivity allows to enter a new todo item 
 * or to change an existing
 */
public class UserDetailActivity extends Activity {
	private Spinner mCategory;
	private EditText mTitleText;
	private EditText mBodyText;

	private Uri userUri;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.user_edit);

		mCategory = (Spinner) findViewById(R.id.category);
		mTitleText = (EditText) findViewById(R.id.user_edit_name);
		mBodyText = (EditText) findViewById(R.id.user_edit_age);
		Button confirmButton = (Button) findViewById(R.id.user_edit_button);

		Bundle extras = getIntent().getExtras();

		// check from the saved Instance
		userUri = (bundle == null) ? null : (Uri) bundle
				.getParcelable(MyContentProvider.CONTENT_ITEM_TYPE_USER);

		// Or passed from the other activity
		if (extras != null) {
			userUri = extras
					.getParcelable(MyContentProvider.CONTENT_ITEM_TYPE_USER);

			fillData(userUri);
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (TextUtils.isEmpty(mTitleText.getText().toString())) {
					makeToast();
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}

		});
	}

	private void fillData(Uri uri) {
		String[] projection = { UserTable.COLUMN_NAME, UserTable.COLUMN_AGE };
		Cursor cursor = getContentResolver().query(uri, projection, null, null,
				null);
		if (cursor != null) {
			cursor.moveToFirst();
			String category = cursor.getString(cursor
					.getColumnIndexOrThrow(UserTable.COLUMN_NAME));

			for (int i = 0; i < mCategory.getCount(); i++) {

				String s = (String) mCategory.getItemAtPosition(i);
				if (s.equalsIgnoreCase(category)) {
					mCategory.setSelection(i);
				}
			}

			mTitleText.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(UserTable.COLUMN_NAME)));
			mBodyText.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(UserTable.COLUMN_AGE)));

			// always close the cursor
			cursor.close();
		}
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putParcelable(MyContentProvider.CONTENT_ITEM_TYPE_USER, userUri);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	private void saveState() {
		String name = mTitleText.getText().toString();
		String age = mBodyText.getText().toString();

		// only save if either summary or description
		// is available

		if (name.length() == 0 && age.length() == 0) {
			return;
		}

		ContentValues values = new ContentValues();
		values.put(UserTable.COLUMN_NAME, name);
		values.put(UserTable.COLUMN_AGE, age);

		if (userUri == null) {
			// New todo
			userUri = getContentResolver().insert(
					MyContentProvider.CONTENT_URI_USER, values);
		} else {
			// Update todo
			getContentResolver().update(userUri, values, null, null);
		}
	}

	private void makeToast() {
		Toast.makeText(UserDetailActivity.this, "Please maintain a summary",
				Toast.LENGTH_LONG).show();
	}
}
