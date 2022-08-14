package edu.neu.team28finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.neu.team28finalproject.controller.Controller;
import edu.neu.team28finalproject.controller.ControllerImpl;
import edu.neu.team28finalproject.datatransferobjects.Symbol;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllStocksActivity extends AppCompatActivity {

    List<StockListObj> stocks;
    SearchView searchBar;
    StockListAdapter sa;
    ProgressBar mProgressBar;
    TextView loading;
    TextRecognizer recognizer;
    ImageButton camera;
    Bitmap imageBitmap;
    private final Controller controller = new ControllerImpl();
    private static final String TAG = "AllStocks";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @SuppressLint({"SourceLockedOrientationActivity", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stocks);
        searchBar = findViewById(R.id.searchView);
        mProgressBar = findViewById(R.id.progress_bar);
        loading = findViewById(R.id.loading);
        camera = findViewById(R.id.cameraButton);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        stocks = new ArrayList<>();
        RecyclerView listRecyclerView = findViewById(R.id.stockListRecyclerView);
        listRecyclerView.setHasFixedSize(true);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sa = new StockListAdapter(stocks,this);
        listRecyclerView.setAdapter(sa);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        1);
            }

        }
        controller.getSymbols().enqueue(new Callback<List<Symbol>>() {
            @Override
            public void onResponse(@NonNull Call<List<Symbol>> call,
                                   @NonNull Response<List<Symbol>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < Objects.requireNonNull(response.body()).size(); i++) {
                        StockListObj stock = new StockListObj(response.body().get(i).getDisplaySymbol(),
                                response.body().get(i).getDescription());
                        stocks.add(stock);
                    }
                    stocks.sort(new Comparator<StockListObj>() {
                        @Override
                        public int compare(final StockListObj object1, final StockListObj object2) {
                            return object1.getTicker().compareTo(object2.getTicker());
                        }
                    });
                    sa.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    Log.i(TAG, "getSymbolsOnResponse: " + response.body());
                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.i(TAG, "getSymbolsOnResponseNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Symbol>> call,
                                  @NonNull Throwable t) {
                Log.i(TAG, "getSymbolsOnFailure: " + t);
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(containsName(stocks, query)){
                    List<StockListObj> temp;
                    temp = stocks.stream().filter(stockListObj ->
                                    stockListObj.getName().toLowerCase().contains(query.toLowerCase()))
                            .collect(Collectors.toList());
                    StockListAdapter sa = new StockListAdapter(temp,AllStocksActivity.this);
                    listRecyclerView.setAdapter(sa);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(containsName(stocks, newText)){
                    List<StockListObj> temp;
                    temp = stocks.stream().filter(stockListObj ->
                                    stockListObj.getName().toLowerCase().contains(newText.toLowerCase()))
                            .collect(Collectors.toList());
                    StockListAdapter sa = new StockListAdapter(temp,AllStocksActivity.this);
                    listRecyclerView.setAdapter(sa);
                } else {
                    StockListAdapter sa = new StockListAdapter(stocks,AllStocksActivity.this);
                    listRecyclerView.setAdapter(sa);
                }
                return false;
            }
        });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public boolean containsName(final List<StockListObj> list, final String name){
        return list.stream().anyMatch(o -> o.getName().toLowerCase().contains(name.toLowerCase()));
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            detectTxt();

        }
    }

    private void detectTxt() {
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);
        TextRecognizer detector = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        detector.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text visionText) {
                        processTextBlock(visionText);
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AllStocksActivity.this,
                                        "Image to text failed",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
    }
    private void processTextBlock(Text result) {
        String resultText = result.getText();
        this.searchBar.setQuery(resultText, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dispatchTakePictureIntent();
                        }
                    });

                } else {
                    Toast.makeText(this, "Permission denied to use the camera", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}