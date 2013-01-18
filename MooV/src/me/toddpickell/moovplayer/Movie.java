package me.toddpickell.moovplayer;

import android.database.Cursor;
import android.provider.MediaStore;

/**
 * 
 * @author toddpickell
 * Movie file metadata.
 */

public class Movie {
	/** Movie Title */
	private final String title;
	
	/** Movie File */
	private final String moviePath;
	
	/** MIME Type */
	private final String mimeType;
	
	/** Movie Duration in ms */
	private final long duration;
	
	/** Thumbnail File */
	private final String thumbnailPath;
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Movie [title=" + title + ", moviePath=" + moviePath
				+ ", mimeType=" + mimeType + ", duration=" + duration
				+ ", thumbnailPath=" + thumbnailPath + "]";
	}


	/**
	 * Constructor
	 * @param mediaCursor media cursor
	 * @param thumbnailCursor thumbnail cursor.
	 */
	public Movie(Cursor mediaCursor, Cursor thumbnailCursor) {

		title = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
		
		moviePath = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
		
		mimeType = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
		
		duration = mediaCursor.getLong(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
		
		if((thumbnailCursor != null) && thumbnailCursor.moveToFirst()) {
			thumbnailPath = thumbnailCursor.getString(thumbnailCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
		} else {
			thumbnailPath = null;
		}

	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @return the moviePath
	 */
	public String getMoviePath() {
		return moviePath;
	}


	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}


	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}


	/**
	 * @return the thumbnailPath
	 */
	public String getThumbnailPath() {
		return thumbnailPath;
	}

}
