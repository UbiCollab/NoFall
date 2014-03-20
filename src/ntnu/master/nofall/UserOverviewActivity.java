package ntnu.master.nofall;

import ntnu.master.nofall.contentprovider.UserContentProvider;
import ntnu.master.nofall.database.UserTable;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/*
 * UserOverviewActivity displays the existing users
 * in a list
 * 
 * You can create new ones via the ActionBar entry "Insert"
 * You can delete existing ones via a long press on the item
 */

public class UserOverviewActivity extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	// private Cursor cursor;
	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);
		this.getListView().setDividerHeight(2);
		fillData();
		registerForContextMenu(getListView());
	}

	// create the menu based on the XML defintion
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listmenu, menu);
		return true;
	}

	// Reaction to the menu selection
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.insert:
			createTodo();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			Uri uri = Uri
					.parse(UserContentProvider.CONTENT_URI + "/" + info.id);
			getContentResolver().delete(uri, null, null);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	public void OpenMedRegActivity(View view){
		Intent intent = new Intent(UserOverviewActivity.this, MedicationRegActivity.class);
		startActivity(intent);
	}
	
	private void createTodo() {
		Intent i = new Intent(this, UserDetailActivity.class);
		startActivity(i);
	}

	// Opens the second activity if an entry is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, UserDetailActivity.class);
		Uri todoUri = Uri.parse(UserContentProvider.CONTENT_URI + "/" + id);
		i.putExtra(UserContentProvider.CONTENT_ITEM_TYPE, todoUri);

		startActivity(i);
	}

	private void fillData() {

		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] { UserTable.COLUMN_NAME };
		// Fields on the UI to which we map
		int[] to = new int[] { R.id.label };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.user_row, null, from,
				to, 0);

		setListAdapter(adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}

	// creates a new loader after the initLoader () call
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { UserTable.COLUMN_ID, UserTable.COLUMN_NAME };
		CursorLoader cursorLoader = new CursorLoader(this,
				UserContentProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// data is not available anymore, delete reference
		adapter.swapCursor(null);
	}

}
