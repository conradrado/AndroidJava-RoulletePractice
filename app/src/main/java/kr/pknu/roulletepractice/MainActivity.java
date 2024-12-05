package kr.pknu.roulletepractice;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private LuckyWheel luckyWheel;
    private List<WheelItem> wheelItems = new ArrayList<>();
    private String point;

    public boolean Init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        luckyWheel = findViewById(R.id.Roulette);

        generateInitialWheelItems();

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                WheelItem wheelItem = wheelItems.get(Integer.parseInt(point) - 1);
                String money = wheelItem.text;
                Toast.makeText(MainActivity.this, money, Toast.LENGTH_SHORT).show();
                searchOnNaverMaps(money);
            }
        });

        Button addItem = findViewById(R.id.btnAdd);
        EditText input = findViewById(R.id.edtItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Init){
                    wheelItems = new ArrayList<>();
                    Init = true;
                }
                else{
                    String item = input.getText().toString();
                    addNewWheelItem(item);
                }

            }
        });

        Button start = findViewById(R.id.btnRoll);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wheelItems.isEmpty()) {
                    Random random = new Random();
                    point = String.valueOf(random.nextInt(wheelItems.size()) + 1);
                    luckyWheel.rotateWheelTo(Integer.parseInt(point));
                } else {
                    Toast.makeText(MainActivity.this, "Add items to the wheel first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button remove = findViewById(R.id.btnRemove);
        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!wheelItems.isEmpty()){
                    wheelItems.remove(wheelItems.size()-1);
                    List<WheelItem> updatedWheelItems = new ArrayList<>(wheelItems);
                    luckyWheel.addWheelItems(updatedWheelItems);
                }
                else {
                    Toast.makeText(MainActivity.this, "No items in the wheel!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void generateInitialWheelItems() {
        String[] defaultItems = {""};
        for (String item : defaultItems) {
            addNewWheelItem(item);
        }
    }

    private void addNewWheelItem(String input) {
        Drawable d = getResources().getDrawable(R.drawable.ic_money, null);
        Bitmap bitmap = drawableToBitmap(d);
        WheelItem newItem = new WheelItem(generateRandomColor(), bitmap, input);
        wheelItems.add(newItem);

        // 전체 리스트를 다시 설정
        List<WheelItem> updatedWheelItems = new ArrayList<>(wheelItems);
        luckyWheel.addWheelItems(updatedWheelItems);
    }

    private int generateRandomColor(){
        Random random = new Random();
        return Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void searchOnNaverMaps(String query) {
        String url = "nmap://search?query=" + Uri.encode(query);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setPackage("com.nhn.android.nmap");

        // 네이버 지도 앱이 설치되어 있는지 확인
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // 네이버 지도 앱이 설치되어 있지 않을 경우 Play Store로 이동
            Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap"));
            startActivity(playStoreIntent);
        }
    }


}
