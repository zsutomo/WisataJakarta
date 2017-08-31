package com.skripsi.zulfallah.wisatajakarta;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bluejamesbond.text.DocumentView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanFragment extends Fragment {


    public BantuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bantuan, container, false);

        DocumentView tv_bantuan = view.findViewById(R.id.textView_bantuan);
        String str = "Aplikasi ini memiliki 3 buah tombol,\n yang terdiri dari Informasi Wisata, Bantuan, dan Tentang. <br/> 1. Tombol Informasi Wisata berfungsi untuk melihat informasi wisata yang ada di Jakarta dengan menekan layar pada gambar. <br/> 2. Tombol Bantuan berfungsi sebagai petunjuk. <br/> 3. Tombol Tentang berfungsi untuk menampilkan Informasi Aplikasi. <br/> 4. Untuk keluar dari aplikasi ini anda bisa menekan tombol kembali pada handphone anda.";
        tv_bantuan.setText(Html.fromHtml(str));

        ImageView imageView = view.findViewById(R.id.iv_back_bantuan);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MenuFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commit();
            }
        });

        return view;
    }

}
