package hanifah.sipuk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class DetailMotorAdmin extends AppCompatActivity {

    Intent i;
    TextView txtNama,txtKet,txtHarga,txtJenis,txtSilinder,txtTahun,txtMinDp;
    Button btnSimulasi,btnHapus;
    private String key;
    Firebase Sref;
    DialogInterface.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_motor_admin);
        Firebase.setAndroidContext(this);

        Sref = new Firebase("https://sipuk-6aea5.firebaseio.com/").child("motor");

        txtNama = (TextView) findViewById(R.id.txtNamaMotor);
        txtHarga = (TextView) findViewById(R.id.txtHarga);
        txtJenis = (TextView) findViewById(R.id.txtJenisMotor);
        txtKet = (TextView) findViewById(R.id.txtDeskripsi);
        txtSilinder = (TextView) findViewById(R.id.txtSilinder);
        txtTahun = (TextView) findViewById(R.id.txtTahun);
        txtMinDp = (TextView) findViewById(R.id.txtMinDP);
        btnSimulasi = (Button) findViewById(R.id.btnSimulasi);
        btnHapus = (Button) findViewById(R.id.btnHapus);

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

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailMotorAdmin.this);
                builder.setMessage("Apakan anda yakin ?");
                builder.setCancelable(false);

                listener = new DialogInterface.OnClickListener()
                {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == DialogInterface.BUTTON_POSITIVE){
                            Sref.child(key).setValue(null);
                            Toast.makeText(getApplicationContext(),"berhasil dihapus",Toast.LENGTH_SHORT).show();
                            i = new Intent(getApplicationContext(),BerandaAdmin.class);
                            startActivity(i);
                        }

                        if(which == DialogInterface.BUTTON_NEGATIVE){
                            dialog.cancel();
                        }
                    }
                };
                builder.setPositiveButton("Ya",listener);
                builder.setNegativeButton("Tidak", listener);
                builder.show();


            }
        });
    }
}
