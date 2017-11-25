package com.hudapc.iaksunshine;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hudapc.iaksunshine.adapter.RVForeCast;
import com.hudapc.iaksunshine.databinding.ActivityMainBinding;
import com.hudapc.iaksunshine.model.DummyForeCast;
import com.hudapc.iaksunshine.model.ForeCast;
import com.hudapc.iaksunshine.model.RequestDailyForeCast;
import com.hudapc.iaksunshine.model.Weather;

import java.util.ArrayList;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;

// TODO 3 Layout Item
// menggunakan layout untuk satu row data
//
// +-------+--------------------------------+-----+-----+
// |       |  teks hari                     |     |     |
// |       |  teks cuaca                    |     |     |
// +-------+--------------------------------+-----+-----+
//  icon                                      max   min
//                                           temp  temp
//
// layout ini nantinya seperti dalam gambar yang ada di dalam projek.
// buat di dalam resoucre layout dengan nama __rowdata1__.
// sampai di sini aplikasi bisa di-run (belum ada perubahan berarti).
// jangan lupa beri id untuk icon, teks hari, teks cuaca, max temp, dan min temp.

// TODO 4 adapter
// - buat package adapter untuk menyimpan segala jenis adapter dalam project
// - buat class adapter dengan meng-extends dari class RecyclerView.Adapter
//     kurang lebih nama class akan seperti ini :
//
//       public class __namaclassRV__ extends RecyclerView.Adapter
//
//     akan ada beberapa pemberitahuan error, abaikan.
// - buat innerclass dalam badan class __namaclassRV__ dengan meng-extends dari
//   class RecyclerView.ViewHolder
//     kurang lebih nama class akan seperti ini :
//
//       public class __namaclassVH__ extends RecyclerView.ViewHolder
//
//     akan ada beberapa pemberitahuan error, abaikan.
// - dalam badan class __namaclassVH__ buat method constructor dengan parameter View
//   sebagai __input__
//     contoh :
//
//       public __namaclassVH__(View __input__)
//
// - dalam method constructor panggil method constructor dari class parent RecyclerView.ViewHolder
//   (super(View)) dengan memasukkan variable __input__ yang saat memanggil method tersebut.
//   sampai di sini seharusnya error dalam class __namaclassVH__ sudah hilang.
// - class __namaclassRV__ masih ada error. di akhir RecyclerView.Adapter
//   tambahkan <__namaclassRV__.__namaclassVH__>. sehingga class __namaclassRV__ akan menjadi
//
//     public class __namaclassRV__ extends RecyclerView.Adapter<__namaclassRV__.__namaclassVH__>
//
//   masih ada beberapa error (sabar :3).
// - masih di class __namaclassRV__ overide method
//     * public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//         ubah ViewHolder sesuai dengan innerclass yang sudah dibuat (__namaclassVH__).
//     * public void onBindViewHolder(ViewHolder holder, int position)
//         ubah ViewHolder sesuai dengan innerclass yang sudah dibuat (__namaclassVH__).
//     * public int getItemCount()
//   perlu cara cepat...??
//     @ arahkan pointer ke nama class __namaclassRV__
//     @ tekan ALT + enter
//     @ pilih implement method
//     @ pilih semua method yang ada di dalam daftar itu.
//     @ ok
//   sampai saat ini seharusnya tidak ada lagi error, tapi belum ada perubahan juga XD.
// - masih di class __namaclassRV__, edit badan fungsi onCreateViewHolder
//     @ pertama buat objek LayoutInflater dengan nama __layoutinfalter__.
//
//         LayoutInflater __layoutinfalter__ = LayoutInflater.from(parent.getContext());
//
//     @ selanjutnya buat objek View dengan nama __viewrow__ dari layout __rowdata1__
//       (ingat TODO2 :3) menggunakan fungsi inflate(int __idlayout__, GroupView __parent__) dari
//       objek LayoutInflater (__layoutinfalter__)
//
//         View __viewrow__ = __layoutinfalter__.inflate(R.layout.__rowdata1__, parent, false);
//
//     @ selanjutnya buat objek dai innerclass  __namaclassVH__, dengan nama __holderrow__.
//       contructor dari class __namaclassVH__ memerlukan parameter input berupa objek View.
//       masukkan objek View yang sudah kita buat di langkah sebelumnya (__viewrow__) ke dalam
//       contructor __namaclassVH__.
//
//         __namaclassVH__ __holderrow__ = new __namaclassVH__(__viewrow__);
//
//     @ langkah selanjutnya jadikan objek __namaclassVH__ yang sudah dibuat di langkah sebelumnya
//       (__holderrow__) sebagai nilai balik.
//
//         return __holderrow__;

/** TODO 7 Mendapatkan API KEY
 * - dapatkan API KEY dari https://openweathermap.org
 * - jika belum punya akun daftar terlebih dahulu.
 * - keterangan lebih lanjut untuk mendapatkan API KEY lihat https://openweathermap.org/appid#get
 * - setelah dapat API KEY lakukan tes dengan url ini
 *     http://api.openweathermap.org/data/2.5/forecast/daily?id=524901&cnt=16&appid=__APIKEY__&units=metric
 *   jika berhasil, akan terlihat text dengan format json
 */

/** TODO 8 Membuat Model class
 * data yang kita ambil dari openweathermap.org berupa text dengan format json.
 * teks dengan format json mudah untuk kelola (ubah dan tulis nilai).
 * - buat package model untuk menyimpan seluruh model class yang diperlukan aplikasi.
 * - buat model class sesuai dengan berkas instruksi yang ada di dalam projek ini.
 */

/** TODO 9 List Untuk Adapter
 * - buka class adapter __namaclassRV__
 * - tambahkan member objek List untuk menyimpan objek ForeCast dengan
 *   nama __listForeCastAdapter__
 *
 *     List<ForeCast> __listForeCastAdapter__;
 *
 */

/** TODO 10 Konstruktor dan ArrayList
 * - buat constructor untuk class __namaclassRV__ dengan parameter objek ArrayList
 *   untuk menyimpan objek ForeCast
 *
 *     public __namaclassRV__(ArrayList<ForeCast> list)
 *
 * - dalam badan constructor inisialisasi member objek List __listForeCastAdapter__
 *   dengan data dari parameter constructor.
 *
 *     __listForeCastAdapter__ = list;
 *
 * akan ada error pada MainActivity, biarkan dulu.
 */

/** TODO 11 Inisialisasi Widget Dalam ViewHolder
 * - buat member untuk menyimpan widget dalam class ViewHolder  __namaclassVH__
 *   (ingat _TODO 3)
 * - inisialisasi setiap member widget sesuai dengan id yang ditulis dalam layout xml.
 *   setelah
 *
 *     super(__view__);
 *
 *    mulai inisialisasi semua widget.
 *
 *      member_widget = (__classview__) __view__.findViewById(R.id.__idwidget__);
 *
 */

/** TODO 12 Method Bind
 * - di dalam class __namaclassVH__ buat method public void bind dengn parameter objek ForeCast
 *
 *     public void bind(ForeCast item)
 *
 * - coba set salah satu member widget yang ada dari parameter yang dimasukkan. contoh set widget maximum
 *   dan minimum temperatur. dalam badan method bind tulis
 *
 *      __widgetMaxTemperature__.setText(String.valueOf(item.temp.max));
 *      __widgetMinTemperature__.setText(String.valueOf(item.temp.min));
 *
 * - dalam class __namaclassRV__ edit method onBindViewHolder. dalam bada method itu lakukan
 *     @ ambil objek ForeCast yang ada dalam member __listForeCastAdapter__ sesuai dengan posisi dari
 *       parameter position
 *
 *         ForeCast item = __listForeCastAdapter__.get(position);
 *
 *     @ panggil method bind dari ViewHolder (__namaclassVH__) dari parameter  holder. masukkan objek
 *       ForeCast (item) yang sudah diambil tadi ke dalam fungsi bind.
 *
 *         holder.bind(item);
 *
 * - dalam class __namaclassRV__ edit method getItemCount. ubah nilai return sesuai dengan jumlah
 *   data yang ada dalam member __listForeCastAdapter__.
 *
 *     return __listForeCastAdapter__.size();
 */

public class MainActivity extends AppCompatActivity
    implements RVForeCast.OnClickForeCast
{
// TODO 5 Awal Uji Coba RecyclerView dengan Adapter
// - buat member objek untuk widget RecyclerView dengan nama __wrv__.
// - buat member objek untuk adapter dari class __namaclassRV__ dengan nama __adapterrv__.
    ActivityMainBinding binding;
    //RecyclerView mRvForeCast;
    RVForeCast mAdapter;

    ArrayList<DummyForeCast> mData;

/** TODO 13 ArrayList Untuk Adapter
 * - inisialsisaikan objek ArrayList untuk menyimpan objek ForeCast
 *
 *     ArrayList<ForeCast> __listForeCastAct__;
 */
    ArrayList<ForeCast> mDataForeCast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

// TODO 6 Uji Coba RecyclerView dengan Adapter
// - inisialsisasi objek __wrv__ dengan RecyclerView yang ada di layout activity ini.
// - inisialsisasi objek __adapterrv__ dengan objek baru dari class __namaclassRV__
// - set LayoutManager untuk objek __wrv__ dengan fungsi
//   RecyclerView.setLayoutManager(LinearLayoutManager layoutmanager).
//
//     __wrv__.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
//
// - set Adapter untuk objek __wrv__ dengan fungsi RecyclerView.setAdapter(RecyclerView.Adapter adapter)
//
//     __wrv__.setAdapter(__adapterrv__);
//
// - buka class __namaclassRV__ edit method getItemCount isi nilai return dengan nilai sesuka hati
//   (jangan negatif, usahakan di atas 1). contoh :
//
//     return 15;
//
// sampai saat ini aplikasi sudah bisa di-run. terbaik...
        mDataForeCast = new ArrayList<>();

        //mRvForeCast = (RecyclerView) findViewById(R.id.rv_forecast);
        mAdapter = new RVForeCast(mDataForeCast);
        //mRvForeCast.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        binding.rvForecast.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        binding.rvForecast.setAdapter(mAdapter);
        mAdapter.setListener(this);

        //populateDummyData();
        getdataFromAPI();

/** TODO 14 inisialisasi ArrayList dan memperbaiki yang error
 * - sebelum inisialisasi objek adapater (__adapterrv__), inisialisasi objek ArrayList yang sudah
 *   dibuat tadi
 *
 *     __listForeCastAct__ = new ArrayList<>();
 *
 * - masukkan objek __listForeCastAct__ ke dalam inisialisasi objek __adapterrv__.
 *
 *     __adapterrv__ = __namaclassRV__(__listForeCastAct__);
 */
    }

    @Override
    public void onClick(ForeCast item)
    {
        Weather itemWeather = item.getWeather().get(0);
        Toast.makeText(
                    this,
                    itemWeather.getDescription(),
                    Toast.LENGTH_LONG)
                .show();
        Intent intent = new Intent(this, DetailActivity.class);
        //intent.putExtra("min", item.getTemp().getMin());
        String jsonForeCast = new Gson().toJson(item);
        intent.putExtra("json_forecast", jsonForeCast);
        startActivity(intent);
    }

    private void populateDummyData()
    {
        for(int i = 0; i < 20; i++)
        {
            DummyForeCast tmp = new DummyForeCast();
            tmp.setDay("Senin");
            tmp.setWeather("gerimis");
            tmp.setMax(i);
            tmp.setMin(i);

            mData.add(tmp);
        }
        mAdapter.notifyDataSetChanged();
    }

/** TODO 16 fungsi mengambil data dari API
 *  - tulis fungsi ini
 *  - edit sesuaikan kepunyaan anda
 *  - panggil fungsi ini dari method onCreate, paling bawah
 */

    private void getdataFromAPI()
    {
        final String sampleUrl = "https://samples.openweathermap.org/data/2.5/forecast/daily?id=524901&lang=zh_cn&appid=b1b15e88fa797225412429c1c50c122a1";
        final String url = "http://api.openweathermap.org/data/2.5/forecast/daily?" +
                "id=524901&" +
                "cnt=16&" +
                "appid=4a00a3f448b218cea14962fc960351a1&"+
                "units=metric";
        RequestQueue request = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //Log.d("MainActivity", "onResponse: "+ response);
                        try
                        {
                            RequestDailyForeCast dailyForecast = new Gson().fromJson(response,  RequestDailyForeCast.class);
                            //Log.d(TAG, dailyForecast.toString());
                            mDataForeCast.addAll(dailyForecast.getList());
                            mAdapter.notifyDataSetChanged();
                        }
                        catch (JsonSyntaxException e)
                        {
                            Log.d("MainActivity", e.getMessage());
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if(error != null)
                        {
                            Log.e("MainActivity",error.networkResponse.data.toString());
                        }
                        else
                        {
                            Log.e("MainActivity","something error happnened");
                        }
                    }
                }
        );

        request.add(stringRequest);
    }
}
