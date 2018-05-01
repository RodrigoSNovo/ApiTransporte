package transporte.tcc.a.apitransporte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

//https://maps.googleapis.com/maps/api/directions/json?origin=-23.5742561,-46.5819458&destination=-23.550475,-46.5969021&mode=transit&key=AIzaSyDLJdfhDZ3WBZafn_9GwwLJp3w6cM9SHgw

public class MainActivity extends AppCompatActivity {

    Button btnConsultar;
    EditText partida , destino;
    TextView txtResultTempo, txtResultPreco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        partida = (EditText) findViewById(R.id.edtAddressPartida);
        destino = (EditText) findViewById(R.id.edtAddressDestino);
        txtResultTempo = (TextView) findViewById(R.id.txtResultTempo);
        txtResultPreco = (TextView) findViewById(R.id.txtResultPreco);

        partida.setText("-23.5742561,-46.5819458");
        destino.setText("-23.550475,-46.5969021");

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String response = consultar(partida.getText().toString(),destino.getText().toString());
                if(response != null){
                    consultarJSON(response);
                }
            }
        });
    }

    protected String consultar(String partida, String destino) {
        String response;
        try{
            HttpDataHandler http = new HttpDataHandler();
            StringBuilder url = new StringBuilder();
            url.append("https://maps.googleapis.com/maps/api/directions/json?");
            url.append("origin=" + partida);
            url.append("&destination=" + destino);
            url.append("&mode=transit&key=AIzaSyDLJdfhDZ3WBZafn_9GwwLJp3w6cM9SHgw");
            response = http.getHttpData(url.toString());
            return  response;
        }catch (Exception ex){
            return null;
        }
    }

    protected void consultarJSON(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);

            String tempo = ((JSONArray)jsonObject.get("routes")).getJSONObject(1).getJSONObject("legs")
                    .getJSONObject("duration").get("text").toString();
            String preco = ((JSONArray)jsonObject.get("routes")).getJSONObject(1).getJSONObject("fare")
                    .get("text").toString();

            txtResultTempo.setText(String.format("Tempo: %s",tempo));
            txtResultPreco.setText(String.format("Preço: %s",preco));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
