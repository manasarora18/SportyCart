package com.project.sportycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.SearchView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,HomeAdapter.ProductCommunication {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button cartToolbarButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter homeAdapter;
    private SearchView searchView;
    // this is manas branch
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);


        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("SportyCart");
        toolbar.setSubtitle("Making you sporty!");

//        toolbar.setLogo(android.R.drawable.sym_def_app_icon);

        cartToolbarButton = (Button)findViewById(R.id.carttoolbarbtn);
        cartToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartIntent=new Intent(getApplicationContext(), ViewCartActivity.class);
                startActivity(cartIntent);

                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.drawer_layout),
                        "CART EMPTY", Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        });
        searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent= new Intent(MainActivity.this,SearchResults.class);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                Toast.makeText(getApplicationContext(),query.toString(),Toast.LENGTH_LONG).show();
//                Intent searchIntent=new Intent(MainActivity.this,SearchResults.class);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                Intent intent= new Intent(MainActivity.this,SearchResults.class);
//                return false;
//            }
//        });
        GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        Call<List<Product>> call= getProductsService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    private void generateDataList(List<Product> list){
        recyclerView=findViewById(R.id.my_recycler_view);
        homeAdapter=new HomeAdapter(list,MainActivity.this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeAdapter);
    }
    @Override
    public void onClick(Product product) {
        Intent productintent=new Intent( MainActivity.this, ProductDetails.class);
        productintent.putExtra("Image:", product.getImageUrl());
        productintent.putExtra("ProductName",product.getName());
        productintent.putExtra(("ProductDescription"),(String)product.getDescription());
        productintent.putExtra(("ColorAttribute"),(String)product.getProductAttributes().getColor());
        productintent.putExtra(("SizeAttribute"),(String)product.getProductAttributes().getSize());
        productintent.putExtra(("MaterialAttribute"),(String)product.getProductAttributes().getMaterial());
        startActivity(productintent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "this menu item clicked", Toast.LENGTH_SHORT).show();
        switch(item.getItemId()) {
            case R.id.cricket_nav_menu:
                Intent catintent1 = new Intent(this, CategoryProducts.class);
                catintent1.putExtra("categoryId","cricket");
                this.startActivity(catintent1);
                break;
            case R.id.football_nav_menu:
                Intent catintent2= new Intent(this,CategoryProducts.class);
                catintent2.putExtra("categoryId","football");
                this.startActivity(catintent2);
                break;
            case R.id.badminton_nav_menu:
                Intent catintent3= new Intent(this,CategoryProducts.class);
                catintent3.putExtra("categoryId","badminton");
                this.startActivity(catintent3);
                break;
            case R.id.tennis_nav_menu:
                Intent catintent4= new Intent(this,CategoryProducts.class);
                catintent4.putExtra("categoryId","tennis");
                this.startActivity(catintent4);
                break;
            case R.id.merchandise_nav_menu:
                Intent catintent5= new Intent(this,CategoryProducts.class);
                catintent5.putExtra("categoryId","merchandise");
                this.startActivity(catintent5);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
