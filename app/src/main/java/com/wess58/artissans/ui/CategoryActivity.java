package com.wess58.artissans.ui;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wess58.artissans.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {
    @BindView(R.id.categoriesTextView) TextView mCategoriesTextView;
    @BindView(R.id.categoriesList) ListView mCategoriesList;
    private String[] categories = new String[]{
            "Paintings","Drawings","Prints","Abstracts","Photography","Craft","Mixed Media","Sculpture","Digital Art","Murals","Pixel Art"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        Typeface oldEnglishFonts = Typeface.createFromAsset(getAssets(), "fonts/Medula_One/MedulaOne-Regular.ttf");
        mCategoriesTextView.setTypeface(oldEnglishFonts);

        //we'll create an ArrayAdapter and set our ListView adapter to the new adapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories );
        mCategoriesList.setAdapter(adapter);
        


    }
}
