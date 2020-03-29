package com.android.bottomsheetexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // XML Widgets
    private RecyclerView RecyclerView;
    private FloatingActionButton FAB;
    private LinearLayout BottomSheet;
    private BottomSheetBehavior SheetBehavior;
    private EditText EditText;
    private Button AddButton;

    //
    private RecyclerAdapter RecyclerAdapter;
    private List<String> RecyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setContents();
        this.AddSampleText();
    }

    /************************************************************************
     * Purpose:         Menu Inflater Override
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    /************************************************************************
     * Purpose:         XML & Var Contents
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    private void setContents()
    {
        // Recycler View
        this.RecyclerList = new ArrayList<>();
        this.RecyclerAdapter = new RecyclerAdapter(this.RecyclerList);
        this.RecyclerView = findViewById(R.id.main_recyclerview);
        this.RecyclerView.setHasFixedSize(true);
        this.RecyclerView.setAdapter(this.RecyclerAdapter);
        this.RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Bottom Sheet
        this.FAB = findViewById(R.id.main_floatingactionbutton);
        this.FAB.setOnClickListener(this);
        this.EditText = findViewById(R.id.bottomsheet_edittext);
        this.AddButton = findViewById(R.id.bottomsheet_addbutton);
        this.AddButton.setOnClickListener(this);
        this.BottomSheet = findViewById(R.id.bottomsheet);
        this.SheetBehavior = BottomSheetBehavior.from(this.BottomSheet);
        this.SheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback()
        {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState)
            {
                switch (newState)
                {
                    case BottomSheetBehavior.STATE_EXPANDED:
                    {
                        MainActivity.this.FAB.setVisibility(View.GONE);
                        MainActivity.this.EditText.requestFocus();
                        MainActivity.this.showKeyboard();
                        break;
                    }

                    case BottomSheetBehavior.STATE_HIDDEN:
                    {
                        MainActivity.this.FAB.setVisibility(View.VISIBLE);
                        MainActivity.this.hideKeyboard();
                        break;
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset)
            {

            }
        });
    }

    private void AddSampleText()
    {
        this.RecyclerList.add(getString(R.string.SampleText));
        this.RecyclerAdapter.notifyDataSetChanged();
    }

    /************************************************************************
     * Purpose:         Button Click Handlers
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.main_floatingactionbutton:
            {
                this.FAB.setVisibility(View.GONE);
                this.SheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            }
            case R.id.bottomsheet_addbutton:
            {
                this.RecyclerList.add(this.EditText.getText().toString());
                this.RecyclerAdapter.notifyDataSetChanged();
                this.EditText.setText("");
                this.SheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        if (this.SheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN)
        {
            this.SheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        else
        {
            super.onBackPressed();
        }
    }

    /************************************************************************
     * Purpose:         Utility
     * Precondition:    .
     * Postcondition:   .
     ************************************************************************/
    public void showKeyboard()
    {
        View view = getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideKeyboard()
    {
        View view = getCurrentFocus();
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
