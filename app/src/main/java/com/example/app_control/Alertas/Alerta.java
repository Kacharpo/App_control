package com.example.app_control.Alertas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app_control.Blank;
import com.example.app_control.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Alerta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Alerta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Alerta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment inicio.
     */
    // TODO: Rename and change types and number of parameters
    public static Alerta newInstance(String param1, String param2) {
        Alerta fragment = new Alerta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private Button btn_atras,btn_alerta;
    private TextView tv_alerta;
    private CardView card1,card2,card3,card4,card5,card6,card7,card8;
    private View v;

    private FragmentTransaction transaction,transaction1;
    private Fragment fragmentMensaje,fragmentInundacion,fragmentServicio,fragmentTarjeta,
            fragmentAsaltante,fragmentTrafico,fragmentDisponibilidad,fragmentAsistencia;
    private Fragment fragmentAlerta,fragmentBlank,fragmentBlank1;
    private boolean f = false;
    private boolean fa = false;
private FrameLayout fl_f;
private RelativeLayout rl_a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_alerta, container, false);
        btn_atras = (Button)v.findViewById(R.id.btn_c_atras);
        btn_alerta = (Button)v.findViewById(R.id.btn_c_alerta);

        card1 = (CardView)v.findViewById(R.id.cv_c);
        card2 = (CardView)v.findViewById(R.id.cv_c1);
        card3 = (CardView)v.findViewById(R.id.cv_c2);
        card4 = (CardView)v.findViewById(R.id.cv_c3);
        card5 = (CardView)v.findViewById(R.id.cv_c4);
        card6 = (CardView)v.findViewById(R.id.cv_c5);
        card7 = (CardView)v.findViewById(R.id.cv_c6);
        card8 = (CardView)v.findViewById(R.id.cv_c7);
        tv_alerta = (TextView)v.findViewById(R.id.textVAlerta);
        fl_f =(FrameLayout)v.findViewById(R.id.fm_c_fondo);
        rl_a=(RelativeLayout)v.findViewById(R.id.rl_c_alertas);

        fragmentBlank = new Blank();
        fragmentBlank1 = new Blank();
        btn_alerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f==false){
                    rl_a.setVisibility(View.VISIBLE);
                    fl_f.setVisibility(View.VISIBLE);
                    f = true;
                    // init();
                }else if(f==true){
                    rl_a.setVisibility(View.INVISIBLE);
                    fl_f.setVisibility(View.INVISIBLE);
                    f = false;
                    if(fa==true){
                        btn_atras.setVisibility(View.INVISIBLE);
                        transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.fm_c_alerta,fragmentBlank1);
                        transaction1.commit();
                        fa=false;
                    }
                }
            }
        });

        fragmentMensaje = new AlertaMensaje();
        fragmentInundacion = new AlertaInundacion();
        fragmentServicio = new AlertaServicio();
        fragmentTarjeta = new AlertaTarjeta();
        fragmentAsaltante = new AlertaAsaltante();
        fragmentTrafico = new AlertaTrafico();
        fragmentDisponibilidad = new AlertaDisponibilidad();
        fragmentAsistencia = new AlertaAsistencia();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentMensaje);
                transaction1.commit();
                fa=true;
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentInundacion);
                transaction1.commit();
                fa=true;
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentServicio);
                transaction1.commit();
                fa=true;
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentTarjeta);
                transaction1.commit();
                fa=true;
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentAsaltante);
                transaction1.commit();
                fa=true;
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentTrafico);
                transaction1.commit();
                fa=true;
            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentDisponibilidad);
                transaction1.commit();
                fa=true;
            }
        });
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_a.setVisibility(View.INVISIBLE);
                btn_atras.setVisibility(View.VISIBLE);
                transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentAsistencia);
                transaction1.commit();
                fa=true;
            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_a.setVisibility(View.VISIBLE);
                btn_atras.setVisibility(View.INVISIBLE);
                transaction1=getActivity().getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.fm_c_alerta,fragmentBlank1);
                transaction1.commit();
                fa=false;
            }
        });

        return v;
    }

}