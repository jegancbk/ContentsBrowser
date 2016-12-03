package coding.excercise.musicbrowser;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import coding.excercise.musicbrowser.models.Content;
import coding.excercise.musicbrowser.utils.ContentBrowserConstants;
import coding.excercise.musicbrowser.utils.ContentSearchUtils;

public class SearchActivity extends AppCompatActivity implements
        ContentListFragment.ContentListInteractionListener,
        ContentDetailsFragment.ContentDetailsInteractionListener, FragmentManager.OnBackStackChangedListener {

    private SearchView.OnQueryTextListener searchListner;
    private ContentListFragment contentListFragment;
    private ContentDetailsFragment contentDetailsFragment;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private String searchQuery = "";
    private String SEARCH_KEY = "search_query";
    private static final String SPINNER_POS = "spinner_pos";
    private Spinner spinner;
    private int spinnerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        shouldDisplayHomeUp();
        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString(SEARCH_KEY);
            spinnerPosition = savedInstanceState.getInt(SPINNER_POS);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.music_search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_choose_entity);
        spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, R.layout.entity_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setSelection(spinnerPosition, true);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        if (searchQuery != null && !searchQuery.isEmpty()) {
            MenuItemCompat.expandActionView(searchMenuItem);
            searchView.setQuery(searchQuery, true);
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (ContentSearchUtils.isNetworkAvailable(getApplicationContext())) {
                    if (ContentSearchUtils.isValidSearchQuery(query)) {
                        loadContentsListFragment(query, spinner.getSelectedItemPosition());
                        searchView.setQuery("", false);
                        searchView.setIconified(true);
                    } else {
                        showErrorDialog(getResources().getString(R.string.error_validation));
                        searchView.setQuery("", false);
                    }
                } else {
                    showErrorDialog(getResources().getString(R.string.error_network));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    private void showErrorDialog(String errorMsg) {

        new AlertDialog.Builder(SearchActivity.this)
                .setMessage(errorMsg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.onActionViewCollapsed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        searchQuery = searchView.getQuery().toString();
        outState.putString(SEARCH_KEY, searchQuery);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (searchView != null) {
            searchQuery = searchView.getQuery().toString();
            outState.putString(SEARCH_KEY, searchQuery);
        }
        outState.putInt(SPINNER_POS, spinner.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    public void loadContentsListFragment(String query, int selectedItemPos) {

        String entity = getResources().getStringArray(R.array.entity_value_array)[selectedItemPos].toString();

        TextView welcomeText = (TextView) findViewById(R.id.welcome);
        welcomeText.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        contentListFragment = ContentListFragment.newInstance(query, entity);
        fragmentTransaction.replace(R.id.frame_layout, contentListFragment,
                ContentBrowserConstants.CONTENT_LIST_FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(ContentBrowserConstants.CONTENT_LIST_FRAGMENT_TAG);
        fragmentTransaction.commit();

    }

    @Override
    public void loadContentDetailsFragment(Content contentItem) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        contentDetailsFragment = ContentDetailsFragment.newInstance(contentItem);
        fragmentTransaction.replace(R.id.frame_layout, contentDetailsFragment,
                ContentBrowserConstants.CONTENT_DETAILS_FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(ContentBrowserConstants.CONTENT_DETAILS_FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canGoBack = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canGoBack);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
