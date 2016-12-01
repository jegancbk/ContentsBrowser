package coding.excercise.musicbrowser;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity implements ContentListFragment.ContentListInteractionListener {

    private SearchView.OnQueryTextListener searchListner;
    private ContentListFragment contentListFragment;
    private SearchView searchView;
    private MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_choose_entity);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, R.layout.entity_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        searchMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadContentsListFragment(query, spinner.getSelectedItemPosition());
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
    }

    public void loadContentsListFragment(String query, int selectedItemPos) {

        Toast.makeText(this, query, Toast.LENGTH_LONG).show();
        Toast.makeText(this, getResources().getStringArray(R.array.entity_value_array)[selectedItemPos].toString(), Toast.LENGTH_LONG).show();

        String entity = getResources().getStringArray(R.array.entity_value_array)[selectedItemPos].toString();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        contentListFragment = ContentListFragment.newInstance(query, entity);
        fragmentTransaction.replace(R.id.frame_layout, contentListFragment, "contentListFragment");
        fragmentTransaction.addToBackStack("contentListFragment");
        fragmentTransaction.commit();

    }

    @Override
    public void fetchContentList(Uri uri) {

    }
}
