package com.duyvu.viethundictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.view.View;

import com.duyvu.viethundictionary.adapter.CustomDictionaryAdapter;
import com.duyvu.viethundictionary.adapter.DictionaryAdapter;
import com.duyvu.viethundictionary.model.Word;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity implements DictionaryAdapter.DictionaryItemClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set language
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String language = sharedPref.getString("language", null);
        if (language == null){
            editor.putString("language", "en");
            language = "en";
            editor.apply();
        }

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());


        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dictionary, R.id.nav_custom_dictionary, R.id.nav_quiz,
                R.id.nav_tools)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.nav_dictionary) {
                    if (searchView != null){
                        searchView.setVisibility(View.VISIBLE);
                        searchView.setOnQueryTextListener(DictionaryAdapter.getInstance());
                        DictionaryAdapter.getInstance().setSearchView(searchView);
                        DictionaryAdapter.getInstance().setListener(MainActivity.this);
                    }
                } else if(destination.getId() == R.id.nav_custom_dictionary) {
                    if (searchView != null){
                        searchView.setVisibility(View.VISIBLE);
                        searchView.setOnQueryTextListener(CustomDictionaryAdapter.getInstance());
                        CustomDictionaryAdapter.getInstance().setSearchView(searchView);
                        CustomDictionaryAdapter.getInstance().setListener(MainActivity.this);
                    }
                }
                else if (searchView != null)
                    searchView.setVisibility(View.GONE);

            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(DictionaryAdapter.getInstance());
        DictionaryAdapter.getInstance().setListener(this);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onItemSelected(Word item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.TAG, item);
        startActivity(intent);
    }
}
