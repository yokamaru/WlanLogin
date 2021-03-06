package net.shirayu.android.WlanLogin;

import net.shirayu.android.WlanLogin.R;
import net.shirayu.android.WlanLogin.R.id;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class AuthManageActivity extends Activity {
	private AuthInfoSQLitepenHelper db_mng;
	private ArrayAdapter<String> adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth_manage);
		
		db_mng = new AuthInfoSQLitepenHelper(this);
		
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        db_mng.setAll_SSID(adapter);
        adapter.add( getResources().getString(R.string.add_new) );
        ListView listView = (ListView) findViewById(id.listview);
        listView.setAdapter(adapter);
        
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // Get the clicked item
                String item = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(AuthManageActivity.this, AuthEditActivity.class);
                intent.putExtra(Const.ssid, item);
                intent.putExtra(Const.position, position);
                if(item.equals( getResources().getString(R.string.add_new) ) ){
                    intent.putExtra(Const.newid, true);
                }
                else{
                    intent.putExtra(Const.newid, false);
                };
                startActivityForResult(intent, 0);
            }
        });
        
        /*
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                String item = (String) listView.getSelectedItem();
                Toast.makeText(AuthManageActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        */
	}
	
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (resultCode == RESULT_OK) {
        	 final int pos = data.getIntExtra(Const.position, 0);
        	 final String ssid = data.getStringExtra(Const.ssid);
        	 final String original_ssid = data.getStringExtra(Const.original_ssid);

             // Modify the adapter
             if(original_ssid.equals( getResources().getString(R.string.add_new) ) ){
             }
             else{
            	 adapter.remove(original_ssid);
             };
             if(! ssid.equals("")){
            	 adapter.insert(ssid, pos);
             }
             adapter.notifyDataSetChanged();
         }
         else if (resultCode == RESULT_CANCELED) {
         }
         else if (data != null) {
         }
    }
    
}
