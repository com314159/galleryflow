package org.mc.gallerydemo;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private ArrayList<Integer> imgList = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		InitImgList();
		Integer[] images = (Integer[])imgList.toArray(new Integer[imgList.size()]);//返回ArrayList包含的数组
		FlowImageAdapter adapter = new FlowImageAdapter(this, images, 380, 280);
		adapter.createReflectedImages();

		CoverFlow galleryFlow = (CoverFlow) findViewById(R.id.coverflow);
		galleryFlow.setAdapter(adapter);
		galleryFlow.setSelection(1);
	}
	
	private void InitImgList() {
		// 加载图片数据（本demo仅获取本地资源，实际应用中，可异步加载网络数据）
		imgList.add(R.drawable.img01);
		imgList.add(R.drawable.img02);
		imgList.add(R.drawable.img03);
		imgList.add(R.drawable.img04);
	}

}
