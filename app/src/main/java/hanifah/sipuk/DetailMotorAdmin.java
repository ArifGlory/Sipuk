package hanifah.sipuk;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailMotorAdmin extends AppCompatActivity {

    Intent i;
    TextView txtNama,txtKet,txtHarga,txtJenis,txtSilinder,txtTahun,txtMinDp;
    Button btnSimulasi,btnHapus;
    private String key;
    Firebase Sref;
    DialogInterface.OnClickListener listener;

    ImageView gambar;

    private Uri filePath;
    private StorageReference storageReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_motor_admin);
        Firebase.setAndroidContext(this);

        Sref = new Firebase("https://sipuk-6aea5.firebaseio.com/").child("motor");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        gambar = (ImageView) findViewById(R.id.image);
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
        String gambarTerima = i.getStringExtra("gambar");

        txtNama.setText(nama);
        txtKet.setText(ket);
        txtHarga.setText("Rp. "+harga);
        txtJenis.setText(jenis);
        txtSilinder.setText(silinder);
        txtTahun.setText(tahun);
        txtMinDp.setText(dp);
        showbyte(gambarTerima);

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

    private void showbyte(String nama){
        /*FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://farmartcorp.appspot.com/file/");*/
        progressBar.setVisibility(View.VISIBLE);
        btnSimulasi.setEnabled(false);
        btnHapus.setEnabled(false);
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference islandRef = storageReference.child("file/").child(nama);
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                gambar.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);
                btnSimulasi.setEnabled(true);
                btnHapus.setEnabled(true);
            }
        });
        btnSimulasi.setEnabled(true);
        btnHapus.setEnabled(true);

    }
}
