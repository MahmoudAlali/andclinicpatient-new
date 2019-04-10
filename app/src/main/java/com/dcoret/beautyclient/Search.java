package com.dcoret.beautyclient;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView searchView =  findViewById(R.id.search_view_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_LONG).show();
                //search adapter

                return false;
            }
        });


    }


    private List<String> items;

    private Menu menu;

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        this.menu = menu;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {

//                    loadHistory(query);

                    Toast.makeText(getApplicationContext(),query.toString(),Toast.LENGTH_LONG).show();

                    return true;

                }

            });

        }
        return true;
    }


        // History

//        private void loadHistory(String query) {
//
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//
//                // Cursor
//                String[] columns = new String[] { "_id", "text" };
//                Object[] temp = new Object[] { 0, "default" };
//
//                MatrixCursor cursor = new MatrixCursor(columns);
//
//                for(int i = 0; i < items.size(); i++) {
//
//                    temp[0] = i;
//                    temp[1] = items.get(i);//replaced s with i as s not used anywhere.
//
//                            cursor.addRow(temp);
//
//                }
//
//                // SearchView
//                SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//                final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
//
////                search.setSuggestionsAdapter(new ExampleAdapter(this, cursor, items));
//
//            }
////            return true;
//        }




    }


