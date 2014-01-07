package org.mc.gallerydemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FlowImageAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context mContext;
	private Integer[] mImageIds = null;
	private String[] mUrls = null;
	private ImageView[] mImages;
	private int mImageWidth;
	private int mImageHeight;
	private DisplayImageOptions mDisplayOptions;

	public FlowImageAdapter(Context c, Integer[] ImageIds, int width, int height) {
		mContext = c;
		mImageIds = ImageIds;
		mImages = new ImageView[mImageIds.length];
		mImageWidth = width;
		mImageHeight = height;
	}
	
	private void initDisplayOptions(){

		Bitmap stubBitmap = BitmapFactory.decodeResource(
				mContext.getResources(), R.drawable.ic_stub);
		Bitmap emptyBitmap = BitmapFactory.decodeResource(
				mContext.getResources(), R.drawable.ic_empty);
		Bitmap errorBitmap = BitmapFactory.decodeResource(
				mContext.getResources(), R.drawable.ic_error);
		
//		mDisplayOptions = new DisplayImageOptions.Builder()
//		.showImageOnLoading(R.drawable.ic_stub)
//		.showImageForEmptyUri(R.drawable.ic_empty)
//		.showImageOnFail(R.drawable.ic_error)
//		.cacheInMemory(false)
//		.cacheOnDisc(false)
//		.considerExifParams(true)
//		.displayer(new RoundedBitmapDisplayer(20))
//		.build();
		mDisplayOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(createReflectedImages(stubBitmap))
		.showImageForEmptyUri(createReflectedImages(emptyBitmap))
		.showImageOnFail(createReflectedImages(errorBitmap))
		.cacheInMemory(true)
		.cacheOnDisc(false)
		.considerExifParams(true)
		.displayer(new GalleryFlowBitmapDisplayer())
		.build();
	}
	
	public FlowImageAdapter(Context c, String[] urls, int width, int height) {
		mContext = c;
		mUrls = urls;
		mImages = new ImageView[urls.length];
		mImageWidth = width;
		mImageHeight = height;
		
		initDisplayOptions();
//		Bitmap stubBitmap = BitmapFactory.decodeResource(
//				mContext.getResources(), R.drawable.ic_stub);
		if(urls!=null&&urls.length>0){
			for(int i=0;i<urls.length;++i){
				ImageView imageView = new ImageView(mContext);
//				imageView.setImageDrawable(createReflectedImages(stubBitmap));
				imageView.setLayoutParams(new CoverFlow.LayoutParams(mImageWidth, mImageHeight));
				ImageLoader.getInstance().displayImage(urls[i],imageView,mDisplayOptions);
				mImages[i] = imageView;
			}
		}else {
			Log.i("gzc", "画廊 urls 为空");
		}
		
	}
	
	public boolean createReflectedImages() {
		// The gap we want between the reflection and the original image
		final int reflectionGap = 1;

		int index = 0;

		for (int imageId : mImageIds) {
			Bitmap originalImage = BitmapFactory.decodeResource(
					mContext.getResources(), imageId);
			
			BitmapDrawable bd = createReflectedImages(originalImage);
			ImageView imageView = new ImageView(mContext);
			imageView.setImageDrawable(bd);
			imageView.setLayoutParams(new CoverFlow.LayoutParams(mImageWidth, mImageHeight));
			// imageView.setScaleType(ScaleType.MATRIX);
			mImages[index++] = imageView;
		}
		return true;
	}

	public static BitmapDrawable createReflectedImages(Bitmap originalImage){
		final int reflectionGap = 1;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		// This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		// Create a Bitmap with the flip matrix applied to it.
		// We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 12 * 11, width, height / 12, matrix, false);

		// Create a new bitmap with same width but taller to fit
		// reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 12), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);

		// Draw in the original image
		canvas.drawBitmap(originalImage, 0, 0, null);

		// Draw in the gap
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap,
				deafaultPaint);

		// Draw in the reflection
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap,
				null);

		// Create a shader that is a linear gradient that covers the
		// reflection
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap,
				0x70ffffff, 0x00ffffff, TileMode.CLAMP);

		// Set the paint to use this shader (linear gradient)
		paint.setShader(shader);

		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width,
				bitmapWithReflection.getHeight() + reflectionGap, paint);

		BitmapDrawable bd = new BitmapDrawable(bitmapWithReflection);
		bd.setAntiAlias(true);

		return bd;
	}
	
	private Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCount() {
		return mImages.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// Use this code if you want to load from resources
		/*
		 * ImageView i = new ImageView(mContext);
		 * i.setImageResource(mImageIds[position]); i.setLayoutParams(new
		 * CoverFlow.LayoutParams(200,150));
		 * i.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		 * 
		 * //Make sure we set anti-aliasing otherwise we get jaggies
		 * BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
		 * drawable.setAntiAlias(true); return i;
		 */

		return mImages[position];
	}

	// Returns the size (0.0f to 1.0f) of the views depending on the
	// 'offset' to the center.
	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

}

