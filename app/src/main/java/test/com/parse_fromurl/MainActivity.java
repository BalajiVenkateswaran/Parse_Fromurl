package test.com.parse_fromurl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String strUrl="http://roadtrippers.herokuapp.com/api/v1/cities/";
        lv=(ListView)findViewById(R.id.list);
        context=MainActivity.this;
        DownloadTask dt=new DownloadTask();
        dt.execute(strUrl);
    }
    private String downloadUrl(String strUrl) throws IOException{
        String data="";
        InputStream istream=null;
        try{
            URL url=new URL(strUrl);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.connect();
            istream=connection.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(istream));
            StringBuffer sb=new StringBuffer();
            String line="";
            while((line=br.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
            br.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.d("while downloading url",e.toString());
        }finally{
            istream.close();
        }
        return data;
    }
    private class DownloadTask extends AsyncTask<String, Integer, String>{
        String data=null;
        @Override
        protected String doInBackground(String... url) {
            try{
                data=downloadUrl(url[0]);

            }catch(Exception e){

            }
            return data;
        }
        protected  void onPostExecute(String result){
            ListViewLoaderTask listtask=new ListViewLoaderTask();
            listtask.execute(result);

        }

    }
    private class ListViewLoaderTask extends  AsyncTask<String, Void, SimpleAdapter>{
        JSONObject jobject;
        @Override
        protected SimpleAdapter doInBackground(String... params) {
            try{
                jobject=new JSONObject(params[0]);
                JSONParser jp=new JSONParser();
                jp.parse(jobject);

            }catch(Exception e) {
            }
                JSONParser jp=new JSONParser();
                List<HashMap<String,String>> cities=null;
                try{
                    cities=jp.parse(jobject);
                }catch(Exception e){

                }
                String[] from={"cityname","cover","img"};
                int[] to={R.id.textView,R.id.textView2};
                SimpleAdapter adapter=new SimpleAdapter(getBaseContext(),cities,R.layout.custom,from,to);
            /* SimpleAdapter adapter =
                    new MyAdapter(MainActivity.this,cities,
                            R.layout.custom,
                            new String[]{"cityname","cover"},
                            new int[]{R.id.textView,R.id.textView2}); */

            return adapter;
        }
        protected void onPostExecute(SimpleAdapter adapter){
            lv.setAdapter(adapter);

        }
    }//Hello

}
