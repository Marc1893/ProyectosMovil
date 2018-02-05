package jorgemedina.guardanotas_v5.Media;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;



import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jorgemedina.guardanotas_v5.Datos.ActivityDatos;
import jorgemedina.guardanotas_v5.Datos.DaoMedia;
import jorgemedina.guardanotas_v5.Datos.POJO_Media_Serial;
import jorgemedina.guardanotas_v5.R;

/**
 * Created by jlmgm on 05/12/2017.
 */

public class ActivityMedia extends AppCompatActivity {

    int tomaID = 0;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        Bundle datos = this.getIntent().getExtras();
        int recupera_idRegistro = datos.getInt("idregistro_integer");
        tomaID = recupera_idRegistro;

        //Toast.makeText(ActivityMedia.this, ""+tomaID, Toast.LENGTH_SHORT).show();

        recycler = (RecyclerView) findViewById(R.id.lista_Fotos);



        cargar();

    }

    public void cargar(){

        List<POJO_Media_Serial> items = new ArrayList<>();

        DaoMedia dao = new DaoMedia(ActivityMedia.this);
        items = dao.buscarTodosDeTarea(tomaID);

        //items.add(new POJO_Media_Serial(R.drawable.nota, 10, "IMAGEN", "LLLL/LLLLLL"));

        // Obtener el Recycler
        //recycler = (RecyclerView) findViewById(R.id.lista_Fotos);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MediaAdapter(items);
        recycler.setAdapter(adapter);


    }





    private String mDirAbsoluto = null;

    private static final int REQUEST_CODE_CAMARA = 1;
    private static final int SCALE_FACTOR_IMAGE_VIEW = 4;
    private static final String ALBUM = "GuardaNotas";
    private static final String EXTENSION_JPEG = ".jpg";

    public void btnMedia_click(View v){

        Toast.makeText(ActivityMedia.this,"Tomar foto",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = null;

        try {

            // Crea el Nombre de la Fotografía
            String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            String nombre = ALBUM + "_" + fechaHora;
            // Crea el Archivo de la Fotografía
            file = nombrarArchivo(ActivityMedia.this, ALBUM, nombre, EXTENSION_JPEG);

            // Obtiene el Nombre y el Directorio Absoluto y los Muestra
            //textView1.setText("Nombre: " + file.getName());
            //textView2.setText("Dir. Absoluto: " + file.getAbsolutePath());

            // Guarda el Directorio Absoluto en una Variable Global
            mDirAbsoluto = file.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        } catch (IOException e) {

            e.printStackTrace();
            file = null;
            mDirAbsoluto = null;

        }

        startActivityForResult(intent, REQUEST_CODE_CAMARA);

    }

    private File nombrarArchivo(Context context, String album, String nombre, String extension) throws IOException {

        return new File(obtenerDirectorioPublico(context, album), nombre + extension);

    }

    private File obtenerDirectorioPublico(Context context, String album) {

        File file = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), album);

            if (file != null) {

                if (!file.mkdirs()) {

                    if (!file.exists()) {

                        Toast.makeText(context, "Error al crear el directorio.", Toast.LENGTH_SHORT).show();
                        return null;

                    }

                }

            }

        } else {

            Toast.makeText(context, "Tarjeta SD no disponible.", Toast.LENGTH_SHORT).show();
            file = new File(context.getFilesDir(), album);

        }

        return file;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CODE_CAMARA:

                if (resultCode == RESULT_OK) {

                    //Bitmap bitmap = escalarBitmap(mDirAbsoluto, SCALE_FACTOR_IMAGE_VIEW);
                    //imageView.setImageBitmap(bitmap);

                    try {

                        POJO_Media_Serial objNota = new POJO_Media_Serial(0, tomaID, String.valueOf(mDirAbsoluto), "FOTO");
                        DaoMedia dao = new DaoMedia(ActivityMedia.this);

                        if(dao.insert(new POJO_Media_Serial(0,objNota.getId_TareaNota(),objNota.getDir_uri(),objNota.getDescripMedia()))>0) {

                            Toast.makeText(getBaseContext(), "Foto guardada", Toast.LENGTH_SHORT).show();
                            cargar();

                        }else{

                            Toast.makeText(getBaseContext(), "La foto no pudo ser guardada", Toast.LENGTH_SHORT).show();

                        }

                    }catch (Exception err){

                        Toast.makeText(getBaseContext(),err.getMessage(),Toast.LENGTH_LONG).show();

                    }

                }

                break;

            default:

                break;

        }

    }





























    }
