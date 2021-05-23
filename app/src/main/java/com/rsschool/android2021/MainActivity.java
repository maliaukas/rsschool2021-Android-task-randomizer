package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity
        extends AppCompatActivity
        implements FirstFragment.ActionListener, SecondFragment.ActionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, firstFragment)
                .commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, secondFragment)
                .commit();
    }

    @Override
    public void onGenerateButtonPressed(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onBackButtonPressed(int previousNumber) {
        openFirstFragment(previousNumber);
    }
}
