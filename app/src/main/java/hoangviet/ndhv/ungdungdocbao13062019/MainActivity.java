package hoangviet.ndhv.ungdungdocbao13062019;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    ListView listViewtieude;
    ArrayList<String> arraytittle,arrayLink;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new readRSS().execute("https://vnexpress.net/rss/khoa-hoc.rss");

    }
    private class readRSS extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            // ánh xạ
            listViewtieude = (ListView)findViewById(R.id.listViewtieude);
            arraytittle = new ArrayList<>();
            arrayLink = new ArrayList<>();
            adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arraytittle);

            listViewtieude.setAdapter(adapter);
            listViewtieude.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent =  new Intent(MainActivity.this,sencondactiviy.class);
                    intent.putExtra("linkdulieu", arrayLink.get(position));
                    startActivity(intent);
                }
            });
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            String tieude = "";
            // để chứa các item
            NodeList nodeList = document.getElementsByTagName("item");
            for (int i = 0;i<nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
                tieude = parser.getValue(element,"title");
                arraytittle.add(tieude);
                arrayLink.add(parser.getValue(element,"link"));
            }
            adapter.notifyDataSetChanged();

        }
    }

}
