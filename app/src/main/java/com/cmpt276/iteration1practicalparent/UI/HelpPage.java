package com.cmpt276.iteration1practicalparent.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.cmpt276.iteration1practicalparent.R;

import org.w3c.dom.Text;

import java.util.Objects;

public class HelpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        setTitle("Help");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // linking homepage background
        TextView home = (TextView) findViewById(R.id.homePageBackground);
        home.setMovementMethod(LinkMovementMethod.getInstance());

        // linking timer background
        TextView time = (TextView) findViewById(R.id.timerBack);
        home.setMovementMethod(LinkMovementMethod.getInstance());

    }

}