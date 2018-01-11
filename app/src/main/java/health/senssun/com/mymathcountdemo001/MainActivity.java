package health.senssun.com.mymathcountdemo001;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
	Button btn,btn1;
	List<Order> orders = new ArrayList<Order>();
	String path;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		int length = Const.OrderInfo.orderOne.length;
		for(int i = 0;i < length;i++){
			Order order = new Order( Const.OrderInfo.orderOne[i][0],  Const.OrderInfo.orderOne[i][1],  Const.OrderInfo.orderOne[i][2],  Const.OrderInfo.orderOne[i][3]);
			orders.add(order);
		}
		btn = (Button)findViewById(R.id.btn);
		btn1 = (Button)findViewById(R.id.btn1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String fileName = "excel_"+new Date().toString();
				path =  "/MailDemo/" + fileName ;
			
				try {
					
					ExcelUtil.writeExcel(MainActivity.this,
							orders, path);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String pathString = getExternalFilesDir(null).getPath()+path;
				sendMail("changgepd@gmail.com", "订单",
						"邮件由系统自动发送，请不要回复！", pathString+ ".xls");
				Toast.makeText(MainActivity.this, "邮件发送成功", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	public void sendMail(final String toMail, final String title,
			final String body, final String path){
		new Thread(new Runnable() {
			public void run() {
				EmailUtil emailUtil = new EmailUtil();
				try {
					String account = "zhangjing574839@163.com";
					String password = "zxy5748391314";
					// String authorizedPwd = "vxosxkgtwrtxvoqz";
					emailUtil.sendMail(toMail, account, SimpleMailSender(account),
							account, password, title, body, "");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static String SimpleMailSender(String userName) {
		return  "smtp." + getHost(userName);
	}
	public static String getHost(String userName){
		return userName.split("@")[1];
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
