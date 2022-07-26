package com.example.mcc_deliveryapp.User;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mcc_deliveryapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import android.content.SharedPreferences;

public class user_checkrate extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_checkrate);

		ImageButton btnMotorcycle = findViewById(R.id.btnMotorcycle);
		ImageButton btnSedan = findViewById(R.id.btnSedan);
		ImageButton btnTruck = findViewById(R.id.btnTruck);

		//
		EditText editText = (EditText)findViewById(R.id.editTextPickUp);
		EditText editText2 = (EditText)findViewById(R.id.editTextDestination);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		String pickloc =sharedPref.getString("key 1","");
		String packageloc =sharedPref.getString("key 4","");

		editText.setText(pickloc);
		editText2.setText(packageloc);

		btnMotorcycle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
						user_checkrate.this,R.style.BottomSheetDialogTheme
				);
				View bottomSheetView = LayoutInflater.from(getApplicationContext())
						.inflate(
								R.layout.motorcycle_dialog,
								(LinearLayout)findViewById(R.id.motorcycle_sheet_dialog)
						);
				bottomSheetView.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(user_checkrate.this,user_findingrider.class);
						startActivity(intent);
					}
				});
				bottomSheetDialog.setContentView(bottomSheetView);
				bottomSheetDialog.show();
			}
		});

		btnSedan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
						user_checkrate.this,R.style.BottomSheetDialogTheme
				);
				View bottomSheetView = LayoutInflater.from(getApplicationContext())
						.inflate(
								R.layout.sedan_dialog,
								(LinearLayout)findViewById(R.id.sedan_sheet_dialog)
						);
				bottomSheetView.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(user_checkrate.this,user_findingrider.class);
						startActivity(intent);
					}
				});
				bottomSheetDialog.setContentView(bottomSheetView);
				bottomSheetDialog.show();
			}
		});

		btnTruck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
						user_checkrate.this,R.style.BottomSheetDialogTheme
				);
				View bottomSheetView = LayoutInflater.from(getApplicationContext())
						.inflate(
								R.layout.truck_dialog,
								(LinearLayout)findViewById(R.id.truck_sheet_dialog)
						);
				bottomSheetView.findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(user_checkrate.this,user_findingrider.class);
						startActivity(intent);
					}
				});
				bottomSheetDialog.setContentView(bottomSheetView);
				bottomSheetDialog.show();
			}
		});
	}


}