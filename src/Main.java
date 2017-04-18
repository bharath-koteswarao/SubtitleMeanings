import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by koteswarao on 17-04-2017.
 */
class UI extends JFrame implements ActionListener {
    final String app_id = "5736a606";
    final String app_key = "08847442e1f2f68722ed448bd9ce24be";
    Container container;
    JTextField tf1;
    JLabel jl1;
    JButton jb1;
    Font f1;

    public UI() {
        container = this.getContentPane();
        container.setLayout(null);
        tf1 = new JTextField();
        jl1 = new JLabel();
        jb1 = new JButton();
        f1 = new Font("TimesNewRoman", Font.PLAIN, 30);
        tf1.setBounds(75, 0, 150, 50);
        jl1.setBounds(50, 51, 200, 50);
        jb1.setBounds(110, 102, 80, 50);
        jb1.setText("Find");
        jb1.addActionListener(this);
        tf1.setFont(f1);
        container.add(tf1);
        container.add(jb1);
        container.add(jl1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            String word = tf1.getText().toLowerCase();
            String url_formed = "https://od-api.oxforddictionaries.com:443/api/v1/entries/en/" + word;
            try {
                URL url = new URL(url_formed);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("app_id", app_id);
                urlConnection.setRequestProperty("app_key", app_key);
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                JSONObject object = new JSONObject(stringBuilder.toString());
                JSONArray results=object.getJSONArray("results");
                JSONObject obj2=results.getJSONObject(0);
                JSONArray lexicalEntries=obj2.getJSONArray("lexicalEntries");
                JSONObject entriesobj=lexicalEntries.getJSONObject(0);
                JSONArray entries=entriesobj.getJSONArray("entries");
                JSONObject sensesObj=entries.getJSONObject(0);
                JSONArray senses=sensesObj.getJSONArray("senses");
                JSONObject defObj=senses.getJSONObject(0);
                JSONArray definitions=defObj.getJSONArray("definitions");
                jl1.setText(definitions.getString(0));
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String... args) {
        UI ui = new UI();
        ui.setVisible(true);
        ui.setSize(300, 200);
        ui.setTitle("Dictionary");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


}
}
