package hanifah.sipuk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailMotorActivity extends AppCompatActivity {

    Intent i;
    TextView txtNama,txtKet,txtHarga,txtJenis,txtSilinder,txtTahun,txtMinDp;
    Button btnSimulasi;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_motor);

        txtNama = (TextView) findViewById(R.id.txtNamaMotor);
        txtHarga = (TextView) findViewById(R.id.txtHarga);
        txtJenis = (TextView) findViewById(R.id.txtJenisMotor);
        txtKet = (TextView) findViewById(R.id.txtDeskripsi);
        txtSilinder = (TextView) findViewById(R.id.txtSilinder);
        txtTahun = (TextView) findViewById(R.id.txtTahun);
        txtMinDp = (TextView) findViewById(R.id.txtMinDP);
        btnSimulasi = (Button) findViewById(R.id.btnSimulasi);

        i = getIntent();
        final String nama = i.getStringExtra("nama");
        String ket = i.getStringExtra("ket");
        final String harga = i.getStringExtra("harga");
        String silinder = i.getStringExtra("silinder");
        String tahun = i.getStringExtra("tahun");
        final String jenis = i.getStringExtra("jenis");
        key = i.getStringExtra("key");
        final String dp = i.getStringExtra("dp");

        txtNama.setText(nama);
        txtKet.setText(ket);
        txtHarga.setText("Rp. "+harga);
        txtJenis.setText(jenis);
        txtSilinder.setText(silinder);
        txtTahun.setText(tahun);
        txtMinDp.setText(dp);

        btnSimulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(),InputSimulasi.class);
                i.putExtra("nama",nama);
                i.putExtra("jenis",jenis);
                i.putExtra("harga",harga);
                i.putExtra("dp",dp);
                startActivity(i);
            }
        });

    }
}
