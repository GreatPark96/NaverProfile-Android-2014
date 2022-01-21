package com.syam.naverprofile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Finish extends Activity {
	
	LinearLayout linearLayout = null;
	Button saveBtn = null;
	Bitmap bm = null;
	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;

	private Uri mImageCaptureUri;
	private ImageView mPhotoImageView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_finish);

		call();
		
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

		TextView text1 = (TextView) findViewById(R.id.name);
		text1.setText(getIntent().getExtras().getString("edit1"));

		TextView text2 = (TextView) findViewById(R.id.job);
		text2.setText(getIntent().getExtras().getString("edit2"));
		
		TextView text3 = (TextView) findViewById(R.id.birth);
		text3.setText(getIntent().getExtras().getString("edit3"));
		
		TextView text4 = (TextView) findViewById(R.id.company);
		text4.setText(getIntent().getExtras().getString("edit4"));

		TextView text5 = (TextView) findViewById(R.id.bodysize);
		text5.setText(getIntent().getExtras().getString("edit5"));

		TextView text6 = (TextView) findViewById(R.id.school);
		text6.setText(getIntent().getExtras().getString("edit6"));
		
		TextView text7 = (TextView) findViewById(R.id.gold);
		text7.setText(getIntent().getExtras().getString("edit7"));
		
		/* TextView text8 = (TextView) findViewById(R.id.sns);
		text8.setText(getIntent().getExtras().getString("edit8"));*/

		mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
		saveBtn = (Button) findViewById(R.id.save);
		saveBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				linearLayout.buildDrawingCache();
				bm = linearLayout.getDrawingCache();

				long time = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH.mm.ss");
				Date dd = new Date(time);
				String strTime = sdf.format(dd);

				String sdcard = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
				File cfile = new File(sdcard + "/ScreenShotTest");
				cfile.mkdirs(); // ������ ���� ��� ScreenShotTest ��������

				String path = sdcard + "/ScreenShotTest/" + strTime + ".jpg"; // ScreenShotTest
																				// ������
																				// �ð�������
																				// ����
				try {
					FileOutputStream fos = new FileOutputStream(path);
					bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					fos.flush();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				Intent intent = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.parse("file://" + path);
				intent.setData(uri);
				sendBroadcast(intent);

				Toast.makeText(getApplicationContext(), "Image Saved!", 0)
						.show(); // �佺Ʈ �˸�

			}
		});
	}
	

	private void doTakePhotoAction()
	{
		/*
		 * ���� �غ���
		 * http://2009.hfoss.org/Tutorial:Camera_and_Gallery_Demo
		 * http://stackoverflow.com/questions/1050297/how-to-get-the-url-of-the-captured-image
		 * http://www.damonkohler.com/2009/02/android-recipes.html
		 * http://www.firstclown.us/tag/android/
		 */

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		// �ӽ÷� ����� ������ ��θ� ����
		String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
		
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PICK_FROM_CAMERA);
	}
	
	/**
	 * �ٹ����� �̹��� ��������
	 */
	private void doTakeAlbumAction()
	{
		// �ٹ� ȣ��
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(intent, PICK_FROM_ALBUM);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode != RESULT_OK)
		{
			return;
		}

		switch(requestCode)
		{
			case CROP_FROM_CAMERA:
			{
				// ũ���� �� ������ �̹����� �Ѱ� �޽��ϴ�. �̹����信 �̹����� �����شٰų� �ΰ����� �۾� ���Ŀ�
				// �ӽ� ������ �����մϴ�.
				final Bundle extras = data.getExtras();
	
				if(extras != null)
				{
					Bitmap photo = extras.getParcelable("data");
					mPhotoImageView.setImageBitmap(photo);
				}
	
				// �ӽ� ���� ����
				File f = new File(mImageCaptureUri.getPath());
				if(f.exists())
				{
					f.delete();
				}
	
				break;
			}
	
			case PICK_FROM_ALBUM:
			{
				// ������ ó���� ī�޶�� �����Ƿ� �ϴ�  break���� �����մϴ�.
				// ���� �ڵ忡���� ���� �ո����� ����� �����Ͻñ� �ٶ��ϴ�.
				
				mImageCaptureUri = data.getData();
			}
			
			case PICK_FROM_CAMERA:
			{
				// �̹����� ������ ������ ���������� �̹��� ũ�⸦ �����մϴ�.
				// ���Ŀ� �̹��� ũ�� ���ø����̼��� ȣ���ϰ� �˴ϴ�.
	
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(mImageCaptureUri, "image/*");
	
				intent.putExtra("outputX", 93);
				intent.putExtra("outputY", 116);
				intent.putExtra("aspectX", 93);
				intent.putExtra("aspectY", 116);
				intent.putExtra("scale", true);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, CROP_FROM_CAMERA);
	
				break;
			}
		}
	}

	public void call()
	{
		DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				doTakePhotoAction();
			}
		};
		
		DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				doTakeAlbumAction();
			}
		};
		
		DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		};
		
		new AlertDialog.Builder(this)
			.setTitle("���ε��� �̹��� ����")
			.setPositiveButton("�����Կ�", cameraListener)
			.setNeutralButton("�ٹ�����", albumListener)
			.setNegativeButton("���", cancelListener)
			.show();
	}
}
