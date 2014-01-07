package org.mc.gallerydemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private ArrayList<Integer> imgList = new ArrayList<Integer>();
	private String[] urls = new String[]{
			"https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
			"https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
			"https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
			"https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s1024/Antelope%252520Butte.jpg",
			"https://lh6.googleusercontent.com/-8HO-4vIFnlw/URquZnsFgtI/AAAAAAAAAbs/WT8jViTF7vw/s1024/Antelope%252520Hallway.jpg",
			"https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg",
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		InitImgList();
		
		Integer[] images = (Integer[])imgList.toArray(new Integer[imgList.size()]);//����ArrayList�������
//		FlowImageAdapter adapter = new FlowImageAdapter(this, images, 380, 280);
//		adapter.createReflectedImages();
		FlowImageAdapter adapter = new FlowImageAdapter(this, urls, 380, 280);
		
		CoverFlow galleryFlow = (CoverFlow) findViewById(R.id.coverflow);
		galleryFlow.setAdapter(adapter);
		galleryFlow.setSelection(1);
	}
	
	private void InitImgList() {
		imgList.add(R.drawable.img01);
		imgList.add(R.drawable.img02);
		imgList.add(R.drawable.img03);
		imgList.add(R.drawable.img04);
	}
}
