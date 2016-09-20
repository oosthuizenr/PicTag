package com.beansontoast.pictag;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorAccent)
                        .neededPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                        .title("title 3")
                        .description("Description 3")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(IntroActivity.this, "We provide solutions to make you love your work", Toast.LENGTH_SHORT).show();
                    }
                }, "Work with love"));
    }
}
