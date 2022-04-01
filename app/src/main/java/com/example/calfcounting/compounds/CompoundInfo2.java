package com.example.calfcounting.compounds;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calfcounting.R;

import java.util.Objects;

public class CompoundInfo2 extends AppCompatActivity implements View.OnClickListener {

    WebView webView;
    Button buttonToPurchase;
    String stringLink;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compound_info2);


        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle("Описание комбикорма");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.webViewCompoundInfo2Avito);
        buttonToPurchase = findViewById(R.id.buttonCompoundInfo2ToPurchase);

        final Compound compound = getIntent().getParcelableExtra(Compound.class.getSimpleName());
        if (compound != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            stringLink = compound.getLink_to_advert();
            webView.loadUrl(
                    stringLink
            );

            System.out.println("URI: "+compound.getLink_to_advert());
        }
        buttonToPurchase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

//    Thread thread2 = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            try {
//                Thread.currentThread().wait(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            webView.loadUrl(
//                    stringLink
//            );
//        }
//    });
}