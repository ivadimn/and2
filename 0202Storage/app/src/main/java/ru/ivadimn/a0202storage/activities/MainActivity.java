package ru.ivadimn.a0202storage.activities;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.fragments.PersonListFragment;
import ru.ivadimn.a0202storage.fragments.SharePrefFragment;
import ru.ivadimn.a0202storage.storage.StorageFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigation;
    private ActionBarDrawerToggle toggle;
    private FragmentManager fragmentManager;

    public interface OnBackPressedListener {
        public boolean isSelectedMode();
        public void backToNormalMode();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_id);
        navigation = (NavigationView) findViewById(R.id.navigation_id);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigation.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigation))
            drawerLayout.closeDrawers();
        else {
            List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
            if (fragmentList != null && fragmentList.size() > 0) {
                for(Fragment fragment : fragmentList){
                    if(fragment instanceof OnBackPressedListener){
                        if(((OnBackPressedListener)fragment).isSelectedMode()) {
                            ((OnBackPressedListener) fragment).backToNormalMode();
                            return;
                        }
                    }
                }
                super.onBackPressed();
            }
            else
                super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuitem_shared_pref_id:
                showFragment(SharePrefFragment.createFragment());
                break;
            case R.id.menuitem_file_storage_id:
                showFragment(PersonListFragment.createFragment(StorageFactory.FILE_STORAGE));
                break;
            case R.id.menuitem_database_storage_id:
                showFragment(PersonListFragment.createFragment(StorageFactory.DATABASE_STORAGE));
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void showFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.container_id, fragment)
                .addToBackStack(null)
                .commit();
    }
}
