package com.example.Alpinia.ui.devices;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Alpinia.R;
import com.example.Alpinia.ApiClient;
import com.example.Alpinia.Error;
import com.example.Alpinia.Result;
import com.example.Alpinia.faucet.FaucetState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class FaucetDialog extends Fragment {

        private String deviceId; //id del faucet
        private int positionInRecyclerView; //posición que va a ocupar

        // botones que tengo en el layout
        private Switch switchOpenClose;
        private Button buttonDispense, butttonStop, buttonStart, buttonCancel;
        private EditText textAmount;
        private Spinner spinnerQuantity;

        // variable para hacer la conexión con la API
        private ApiClient api;

        private boolean isOpen;

        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.faucet_card, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            readBundle(getArguments());

            init(getView());
        }

        // conecto las variables creadas con los botones que tengo en el layout
        private void init(View view) {
            switchOpenClose = view.findViewById(R.id.faucet_switch);
            spinnerQuantity = view.findViewById(R.id.unit_spinner);
            textAmount = view.findViewById(R.id.amount);
           // buttonDispense = view.findViewById(R.id.amount_dispenser);
           // butttonStop = view.findViewById(R.id.stop_button);
            buttonStart = view.findViewById(R.id.start_button);
            buttonCancel = view.findViewById(R.id.cancel_button);

            // creo una instancia de la API y la guado en la variable api
            api = ApiClient.getInstance();

            api.getFaucetState(deviceId, new Callback<Result<FaucetState>>() {
                @Override
                public void onResponse(Call<Result<FaucetState>> call, Response<Result<FaucetState>> response) {
                    if(response.isSuccessful()) {
                        Result<FaucetState> result = response.body();
                        if(result != null) {
                            FaucetState faucetState = result.getResult();
                            isOpen = faucetState.isOpen();
                            switchOpenClose.setChecked(isOpen);
                        }
                    } else {
                        handleError(response);
                    }
                }

                @Override
                public void onFailure(Call<Result<FaucetState>> call, Throwable t) {
                    handleUnexpectedError(t);
                }
            });




            buttonStart.setVisibility(View.INVISIBLE);
            buttonCancel.setVisibility(View.INVISIBLE);
            textAmount.setVisibility(View.INVISIBLE);
            spinnerQuantity.setVisibility(View.INVISIBLE);




            if(switchOpenClose != null) {
                switchOpenClose.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        openFaucet();
                    } else {
                        closeFaucet();
                    }
                });
            }
        }

        private void readBundle(Bundle bundle) {
            if (bundle != null) {
                deviceId = bundle.getString("deviceId");
                positionInRecyclerView = bundle.getInt("positionInRecyclerView");
            }
        }


        @NonNull
        public static FaucetDialog newInstance(String deviceId, int positionInRecyclerView) {
            Bundle bundle = new Bundle();
            bundle.putString("deviceId", deviceId);
            bundle.putInt("positionInRecyclerView", positionInRecyclerView);

            FaucetDialog fragment = new FaucetDialog();
            fragment.setArguments(bundle);

            return fragment;
        }

        // ABRO EL GRILLO
        private void openFaucet() {
            if(isOpen) {
                Toast.makeText(getContext(), "El grillo ya se encuentra abierto", Toast.LENGTH_LONG).show();
                return;
            }

            // si no está abierto, lo abro usando la api
            api.openFaucet(deviceId, new Callback<Result<Boolean>>() {
                @Override
                public void onResponse(Call<Result<Boolean>> call, Response<Result<Boolean>> response) {
                    if(response.isSuccessful()) {
                        Result<Boolean> result = response.body();
                        if(result != null) {
                            Toast.makeText(getContext(), "Abriendo al grillo....", Toast.LENGTH_LONG).show();
                            isOpen = true;
                           // escondo los botones mientras se abre
                            buttonStart.setVisibility(View.INVISIBLE);
                            buttonCancel.setVisibility(View.INVISIBLE);
                            textAmount.setVisibility(View.INVISIBLE);
                            spinnerQuantity.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        handleError(response);
                    }
                }

                @Override
                public void onFailure(Call<Result<Boolean>> call, Throwable t) {
                    // manejar el error en acaso de falla
                }
            });
        }

        // CIERRO EL GRILLO    (mismo procedimiento que para abrirlo)
        private void closeFaucet() {
            if(!isOpen) {
                Toast.makeText(getContext(), "El grillo ya se encunetra cerrado ", Toast.LENGTH_LONG).show();
                return;
            }

            api.closeFaucet(deviceId, new Callback<Result<Boolean>>() {
                @Override
                public void onResponse(Call<Result<Boolean>> call, Response<Result<Boolean>> response) {
                    if(response.isSuccessful()) {
                        Result<Boolean> result = response.body();
                        if(result != null) {
                            Toast.makeText(getContext(), "CLOSING FAUCET", Toast.LENGTH_LONG).show();
                            isOpen = false;
                            buttonStart.setVisibility(View.INVISIBLE);
                            buttonCancel.setVisibility(View.INVISIBLE);
                            textAmount.setVisibility(View.INVISIBLE);
                            spinnerQuantity.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        handleError(response);
                    }
                }

                @Override
                public void onFailure(Call<Result<Boolean>> call, Throwable t) {
                    // manejar el error en acaso de falla
                }
            });
        }





    }