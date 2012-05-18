package com.globalis.quponMovil;

import java.io.IOException;
import java.util.List;

import com.globalis.entities.Promotion;
import com.globalis.util.JsonConnector;

import android.app.ListActivity;
import android.os.Bundle;

public class PromotionActivity extends ListActivity{
	
	private List<Promotion> promotions;
	private static final String URL_PROMOTIONS = "192.168.0.192:3000/promotions";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initViews();		
	}

	private void initViews() {
		buscarPromociones();
	}

	private void buscarPromociones() {
		try {
			promotions = JsonConnector.mapPromotions(URL_PROMOTIONS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		try {
			JsonConnector.mapPromotions(URL_PROMOTIONS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		
	}

}
