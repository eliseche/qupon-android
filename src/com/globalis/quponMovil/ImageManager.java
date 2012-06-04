package com.globalis.quponMovil;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageManager {
	private final int avatar = R.drawable.avatar;
	private HashMap<String, Bitmap> imageMap = new HashMap<String, Bitmap>();
	private File cacheDir;
	private ImageQueue imageQueue = new ImageQueue();
	private Thread imageLoaderThread = new Thread(new ImageQueueManager());
	
	public ImageManager(Context context) {
		// Makes background thread low priority, to avoid affecting UI performance
		imageLoaderThread.setPriority(Thread.NORM_PRIORITY-1);
		/*
		 * Find directory by querying the device to see if external storage is mounted
		 * and if not, by getting the default cache location
		 */
		String sdState = android.os.Environment.getExternalStorageState();
		if(sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			File sdDir = android.os.Environment.getExternalStorageDirectory();
			cacheDir = new File(sdDir, "qupon");
		}
		else {
			cacheDir = context.getCacheDir();
		}
		
		if(!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}
	
	public void displayImage(String url, ImageView imageView) {
		if(imageMap.containsKey(url)) {
			imageView.setImageBitmap(imageMap.get(url));
		}
		else {
			queueImage(url, imageView);
			imageView.setImageResource(avatar);
		}
	}
	
	private void queueImage(String url, ImageView imageView) {
		/*
		 * This imageView might have been used for other images, so we
		 * clear the queue of old tasks before starting
		 */
		imageQueue.clean(imageView);
		ImageRef imageRef = new ImageRef(url, imageView);		
		
		synchronized (imageQueue.imageRef) {			
			imageQueue.imageRef.push(imageRef);
			imageQueue.imageRef.notifyAll();
		}
		
		// Start thread if it's not started yet
		if(imageLoaderThread.getState() == Thread.State.NEW) {
			imageLoaderThread.start();
		}		
	}
	
	private Bitmap getBitmap(String url) {
		String filename = String.valueOf(url.hashCode());
		File file = new File(cacheDir, filename);
		
		// Is the bitmap in our cache?
		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
		if(bitmap != null) {
			return bitmap;
		}
		
		// Nope, we have to download it
		try {
			bitmap = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
			// Save bitmap to cache for later usage
			writeFile(bitmap, file);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void writeFile(Bitmap bitmap, File file) {
		FileOutputStream fileOut = null;
		
		try {
			fileOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOut);			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fileOut != null) {
					fileOut.close();
				}				
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}		
	}
	
	private class ImageQueueManager implements Runnable {
		public void run() {
			try {
				while(true) {
					// Thread waits until there are images in queue to be retrieved
					if(imageQueue.imageRef.size() == 0) {
						synchronized (imageQueue.imageRef) {
							imageQueue.imageRef.wait();							
						}
					}
					
					// When we have images to be loaded
					if(imageQueue.imageRef.size() != 0) {
						ImageRef imageToLoad;
						
						synchronized (imageQueue.imageRef) {
							imageToLoad = imageQueue.imageRef.pop();							
						}
						
						Bitmap bitmap = getBitmap(imageToLoad.imageUrl);
						imageMap.put(imageToLoad.imageUrl, bitmap);
						/*Object tag = imageToLoad.imageView.getTag();						
						if(tag != null && ((String)tag).equals(imageToLoad.imageUrl)) {*/
							BitmapDisplayer bitmapDisplayer = new BitmapDisplayer(bitmap, imageToLoad.imageView);
							Activity activity = (Activity)imageToLoad.imageView.getContext();
							activity.runOnUiThread(bitmapDisplayer);
						//}
					}
					
					if(Thread.interrupted()) {
						break;
					}					
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	private class ImageRef {
		public String imageUrl;
		public ImageView imageView;		
		
		public ImageRef(String imageUrl, ImageView imageView) {
			this.imageUrl = imageUrl;
			this.imageView = imageView;			
		}
	}
	
	private class ImageQueue {
		private Stack<ImageRef> imageRef = new Stack<ImageRef>();
		
		public void clean(ImageView imageView) {
			for(int i = 0; i < imageRef.size(); i++) {
				if(imageRef.get(i).imageView == imageView) {
					imageRef.remove(i);
				}
			}			
		}
	}
	
	private class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;		
		
		public BitmapDisplayer(Bitmap bitmap, ImageView imageView) {
			this.bitmap = bitmap;
			this.imageView = imageView;			
		}
		
		public void run() {
			if(bitmap != null) {
				imageView.setImageBitmap(bitmap);
			}
			else {
				imageView.setImageResource(avatar);
			}
		}
	}
}