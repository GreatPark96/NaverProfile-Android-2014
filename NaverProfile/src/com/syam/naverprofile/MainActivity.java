package com.syam.naverprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	EditText editText5;
	EditText editText6;
	EditText editText7;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText1 = (EditText) this.findViewById(R.id.editText1);
		editText2 = (EditText) this.findViewById(R.id.editText2);
		editText3 = (EditText) this.findViewById(R.id.editText3);
		editText4 = (EditText) this.findViewById(R.id.editText4);
		editText5 = (EditText) this.findViewById(R.id.editText5);
		editText6 = (EditText) this.findViewById(R.id.editText6);
		editText7 = (EditText) this.findViewById(R.id.editText7);

		Button button = (Button) this.findViewById(R.id.page);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(MainActivity.this, Finish.class);
		intent.putExtra("edit1", editText1.getText().toString());
		intent.putExtra("edit2", editText2.getText().toString());
		intent.putExtra("edit3", editText3.getText().toString());
		intent.putExtra("edit4", editText4.getText().toString());
		intent.putExtra("edit5", editText5.getText().toString());
		intent.putExtra("edit6", editText6.getText().toString());
		intent.putExtra("edit7", editText7.getText().toString());
		startActivity(intent);

	}
}
