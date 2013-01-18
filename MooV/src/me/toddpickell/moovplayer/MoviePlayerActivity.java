package me.toddpickell.moovplayer;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * MooV Player
 * @author toddpickell
 *
 */

public class MoviePlayerActivity extends Activity implements OnItemClickListener {

	/** Log tag */
	private static final String LOG_TAG = "MoviePLayer";
	
	/**
	 * On create lifesysle method
	 * 
	 * @param savedINstanceState saved state
	 * @see Activity#onCreate(Bundle)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_player);
		
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		
		//media columns to query
		String[] mediaColumns = { 
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.MIME_TYPE 
		};
		
		//thumbnail columns to query
		String[] thumbnailColumns = { MediaStore.Video.Thumbnails.DATA };
		
		//query external movie content for selected media columns
		Cursor mediaCursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
		
		//loop through media results
		if((mediaCursor != null) && mediaCursor.moveToFirst()) {
			do {
				//get video id
				int id = mediaCursor.getInt(mediaCursor.getColumnIndex(MediaStore.Video.Media._ID));
				
				//get thumbnail associated w/ video
				Cursor thumbnailCursor = managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbnailColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id, null, null);
				
				//new movie object from data
				Movie movie = new Movie(mediaCursor, thumbnailCursor);
				Log.d(LOG_TAG, movie.toString());
				
				//add movie to list
				movieList.add(movie);
				
			} while (mediaCursor.moveToNext());
		}
		
		//define movie list adaptor
		MovieListAdaptor movieListAdaptor = new MovieListAdaptor(this, movieList);
		
		//set list view adaptor to move list adaptor
		ListView movieListView = (ListView) findViewById(R.id.moviesListView);
		movieListView.setAdapter(movieListAdaptor);
		
		//set item click listener
		movieListView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_movie_player, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int posistion, long id) {
		// TODO Auto-generated method stub
		//gets the selected movie
		Movie movie = (Movie) parent.getAdapter().getItem(posistion);
		
		//plays the selected movie
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse(movie.getMoviePath()), movie.getMimeType());
		startActivity(intent);
		
	}

}
