package com.example.weatherapp;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherOutputAdapter extends ArrayAdapter<Result> {
	
	private List<Result> resultList;
	
	
	
	public WeatherOutputAdapter(Context context, int textViewResourceId, List<Result> resultList){
		super(context, textViewResourceId, resultList);
		this.resultList = resultList;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null){
			LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(R.layout.row, null);
		}
		Result result = resultList.get(position);
		if (result != null){
			ImageView icon = (ImageView) view.findViewById(R.id.icon);
			TextView city = (TextView) view.findViewById(R.id.city);
			TextView description = (TextView) view.findViewById(R.id.description);
			TextView temp = (TextView) view.findViewById(R.id.temp);
			Context context = super.getContext();
			
			if (result.isError()== false && result.isWarning() == false){
				int resource = context.getResources().getIdentifier(
						"ic_" + result.getWeatherList().get(0).getIconName(), "drawable", context.getPackageName());
				assert (resource != 0);
				icon.setImageDrawable(context.getResources().getDrawable(resource));
				
				city.setText(result.getName());
				description.setText(result.getWeatherList().get(0).getDescription());
				//displays the temperature in whole number
				temp.setText(Long.toString(Main.roundTemp(result.getMain().getTempInCelcius())) + "\u00B0" + "C");
			} else {
				if (result.isError()){
					city.setTypeface(null, Typeface.BOLD_ITALIC);
				}
				city.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				city.setGravity(Gravity.CENTER);
				city.setText(result.getName());
			}
			
		}
		return view;
	}
	

}
