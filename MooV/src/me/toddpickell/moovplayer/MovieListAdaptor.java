package me.toddpickell.moovplayer;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class MovieListAdaptor extends BaseAdapter {
	
	/** Context Instance */
	private final Context context;
	
	/** Movie List */
	private final ArrayList<Movie> movieList;
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return movieList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return movieList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.movie_item, null);
		}
		
		Movie movie = (Movie) getItem(position);
		
		ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
		
		if(movie.getThumbnailPath() != null) {
			thumbnail.setImageURI(Uri.parse(movie.getThumbnailPath()));
		} else {
			thumbnail.setImageResource(R.drawable.ic_launcher);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		title.setText(movie.getTitle());
		
		TextView duration = (TextView) convertView.findViewById(R.id.duration);
		duration.setText(getDurationAsString(movie.getDuration()));
		
		return convertView;
	}

	private static String getDurationAsString(long duration) {
		// TODO Auto-generated method stub
		long milliseconds = duration % 1000;
		long seconds = duration / 1000;
		long minutes = seconds / 60;
		seconds %= 60;
		long hours = minutes / 60;
		minutes %= 60;
		
		String durationString = String.format("%1$02d:%2$01d:%3$02d.%4$03d", hours, minutes, seconds, milliseconds);
		
		return durationString;
	}

	/**
	 * Constructor
	 * @param context context instance
	 * @param movieList movie list
	 */
	public MovieListAdaptor(Context context, ArrayList<Movie> movieList) {
		// TODO Auto-generated method stub
		this.context = context;
		this.movieList = movieList;

	}

}
