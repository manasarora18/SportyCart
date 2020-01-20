package com.project.sportycart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,HomeAdapter.ProductCommunication {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button toolBarButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter homeAdapter;
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
//        navigationView.inflateMenu(R.menu.nav_menu);
        getSupportActionBar().setTitle("SportyCart");
        toolbar.setSubtitle("Making you sporty!");
//        toolbar.setLogo(android.R.drawable.sym_def_app_icon);

        toolBarButton = (Button)findViewById(R.id.toolbarbtn);
        toolBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.drawer_layout),
                        "CART EMPTY", Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        });

        GetProductsService getProductsService = RetrofitClientInstance.getRetrofitInstance().create(GetProductsService.class);
        Call<List<Product>> call= getProductsService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Something's Wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onClick(Product product) {
        Intent intent=new Intent( MainActivity.this, ProductDetails.class);
        intent.putExtra("Image:", (String)product.getImageUrl());
        intent.putExtra("ProductName:",(String)product.getName());
        intent.putExtra(("ProductDescription:"),(String)product.getDescription());
        intent.putExtra(("ColorAttribute"),(String)product.getProductAttributes().getColor());
        intent.putExtra(("SizeAttribute"),(String)product.getProductAttributes().getSize());
        intent.putExtra(("MaterialAttribute"),(String)product.getProductAttributes().getMaterial());
        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//
//    }

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
    private void generateDataList(List<Product> list){
        recyclerView=findViewById(R.id.my_recycler_view);
        homeAdapter=new HomeAdapter(list,MainActivity.this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeAdapter);
    }
}
