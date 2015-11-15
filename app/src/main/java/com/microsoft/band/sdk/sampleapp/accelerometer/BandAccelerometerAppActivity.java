//Copyright (c) Microsoft Corporation All rights reserved.  
// 
//MIT License: 
// 
//Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
//documentation files (the  "Software"), to deal in the Software without restriction, including without limitation
//the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
//to permit persons to whom the Software is furnished to do so, subject to the following conditions: 
// 
//The above copyright notice and this permission notice shall be included in all copies or substantial portions of
//the Software. 
// 
//THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
//TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
//THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
//CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//IN THE SOFTWARE.
package com.microsoft.band.sdk.sampleapp.accelerometer;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandIOException;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.SampleRate;
import com.parse.Parse;
import com.parse.ParseObject;

import java.net.*;
import java.io.*;
import java.util.*;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.app.Activity;
import android.os.AsyncTask;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BandAccelerometerAppActivity extends Activity {

	private BandClient client = null;
	private Button btnStart;
	private TextView txtStatus;
	EditText txtphoneNo;

	private int counttoseven;
	private float [] daking=new float[21];

/*	private BandAccelerometerAppActivity(){

		}
	}*/

	private void Requesting(float[] daking) throws IOException {
		String finali = "{ 'Inputs': {'input1':{'ColumnNames': ['Column 0', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20'], 'Values': [ [ ";

		for (int i = 0; i < 21; i++) {
			finali += "'";
			finali += daking[i];
			finali += "' ";
		}

		finali += "], [ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' ], ] }, }, 'GlobalParameters' : { } }";

		// String input = "{ 'Inputs': {'input1':{'ColumnNames': ['Column 0', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20'], 'Values': [ [ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' ]], [ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' ], ] }, }, 'GlobalParameters' : { } }";


		URL u = null;
		String url = "https://ussouthcentral.services.azureml.net/workspaces/0a253949230943e788e1b365ce54cd2b/services/b489c3ae96a24c90934dfc2f9d74fb61/execute?api-version=2.0&details=true";

		u = new URL(url);

		HttpURLConnection conn = (HttpURLConnection) u.openConnection();

		conn.setRequestProperty("Authorization", "Bearer IWqRSSsMjLA758M+Zi8CRiU6lkSWi7+NvVyYjl7+oRsdGduJPSS2SUa7dngCDoaMK27nno39X9Sb/ALR6oqSPg==");
		//conn.setRequestProperty("Content-Length","")
		conn.setRequestProperty("Content-Type", "application/json");


		conn.setRequestMethod("POST");

		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

		wr.write(finali);
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String decodedString;
		String out = "";

		while ((decodedString = in.readLine()) != null) {

			out += decodedString;

		}

		String[] parts = out.split("Values");
		String value = parts[1].substring(5, 6);

		in.close();


		if (decodedString == "1") {

			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(txtphoneNo.getText().toString(), null, "Alert", null, null);

		}
	}

	private BandAccelerometerEventListener mAccelerometerEventListener = new BandAccelerometerEventListener() {
		@Override
		public void onBandAccelerometerChanged(final BandAccelerometerEvent event) {
			if (event != null) {
				if (counttoseven == 0) {
					for(int i = 0; i < 21; ++i) {
					daking[i] = 0; }
				}
				if (counttoseven == 7) {

					try {
						Requesting(daking);
					} catch (IOException e){
						e.getMessage();
					}

					for(int i = 0; i < 21; ++i) {
						daking[i] = 0;
					}

					counttoseven = 0;
				}

				appendToUI(String.format(" X = %.3f \n Y = %.3f\n Z = %.3f", event.getAccelerationX(),
						event.getAccelerationY(), event.getAccelerationZ()));

				ParseObject acc = new ParseObject("Acceleration");
				acc.put("x", event.getAccelerationX());
				acc.saveInBackground();
				acc.put("y", event.getAccelerationY());
				acc.saveInBackground();
				acc.put("z", event.getAccelerationZ());
				acc.saveInBackground();

				daking[0+counttoseven*3] = event.getAccelerationX();
				daking[1+counttoseven*3] = event.getAccelerationY();
				daking[2+counttoseven*3] = event.getAccelerationZ();

				counttoseven = counttoseven + 1;

			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "3tWfNTN8s5Z9atToqeZaXnluoIg5VvaZhwLKMB96", "Tqenf4LtJUgIHtanLI6u7iXrPG4DYAhcjc5BSmiM");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtStatus = (TextView) findViewById(R.id.txtStatus);
		btnStart = (Button) findViewById(R.id.btnStart);
		txtphoneNo = (EditText) findViewById(R.id.editText);

		/*btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				txtStatus.setText("");
				new AccelerometerSubscriptionTask().execute();
			}
		});*/

		btnStart.setOnClickListener(new View.OnClickListener() {
										public void onClick(View view) {
											new AccelerometerSubscriptionTask().execute();
										}
									}
		);

	}

	@Override
	protected void onResume() {
		super.onResume();
		txtStatus.setText("");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (client != null) {
			try {
				client.getSensorManager().unregisterAccelerometerEventListener(mAccelerometerEventListener);
			} catch (BandIOException e) {
				appendToUI(e.getMessage());
			}
		}
	}

	private class AccelerometerSubscriptionTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				if (getConnectedBandClient()) {
					appendToUI("Band is connected.\n");
					if(SampleRate.MS32==null)
					{
						BandAccelerometerEvent event = null;
						System.out.println("Error. MS32 is NULL");
						System.out.println(event.getAccelerationX());
						System.out.println(event.getAccelerationY());
						System.out.println(event.getAccelerationZ());
					}
					client.getSensorManager().registerAccelerometerEventListener(mAccelerometerEventListener, SampleRate.MS128);
				} else {
					appendToUI("Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
				}
			} catch (BandException e) {
				String exceptionMessage="";
				switch (e.getErrorType()) {
					case UNSUPPORTED_SDK_VERSION_ERROR:
						exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
						break;
					case SERVICE_ERROR:
						exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
						break;
					default:
						exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
						break;
				}
				appendToUI(exceptionMessage);

			} catch (Exception e) {
				appendToUI(e.getMessage());
			}
			return null;
		}
	}

	@Override
	protected void onDestroy() {
		if (client != null) {
			try {
				client.disconnect().await();
			} catch (InterruptedException e) {
				// Do nothing as this is happening during destroy
			} catch (BandException e) {
				// Do nothing as this is happening during destroy
			}
		}
		super.onDestroy();
	}

	private void appendToUI(final String string) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				txtStatus.setText(string);
			}
		});
	}

	private boolean getConnectedBandClient() throws InterruptedException, BandException {
		if (client == null) {
			BandInfo[] devices = BandClientManager.getInstance().getPairedBands();
			if (devices.length == 0) {
				appendToUI("Band isn't paired with your phone.\n");
				return false;
			}
			client = BandClientManager.getInstance().create(getBaseContext(), devices[0]);
		} else if (ConnectionState.CONNECTED == client.getConnectionState()) {
			return true;
		}

		appendToUI("Band is connecting...\n");
		return ConnectionState.CONNECTED == client.connect().await();
	}
}