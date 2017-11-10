package com.hudapc.iaksunshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hudapc.iaksunshine.adapter.RVForeCast;

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

public class MainActivity extends AppCompatActivity
{
// TODO 5 Awal Uji Coba RecyclerView dengan Adapter
// - buat member objek untuk widget RecyclerView dengan nama __wrv__.
// - buat member objek untuk adapter dari class __namaclassRV__ dengan nama __adapterrv__.
    RecyclerView mRvForecast;
    RVForeCast mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mRvForecast = findViewById(R.id.rv_forecast);
        mAdapter = new RVForeCast();

        mRvForecast.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        mRvForecast.setAdapter(mAdapter);
    }
}
