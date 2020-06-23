package com.example.Alpinia.ui.devices;

import com.example.Alpinia.R;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.example.Alpinia.Result
import com.example.Alpinia.door.DoorState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Alpinia.ApiClient;
import com.example.Alpinia.door.DoorState;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoorDialog extends Fragment {

    private ApiClient api;

    private String deviceId;
    private int positionInRecyclerView;

    private Button openButton;
    private Button closeButton;
    private Button lockButton;
    private Button unlockButton;

    private boolean isLocked, isOpen;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.door_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        readBundle(getArguments());
        init(getView());
    }

    public void init(View view) {
        //creo una instancia de la API
        api = ApiClient.getInstance();

        openButton = view.findViewById(R.id.open_button);
        closeButton = view.findViewById(R.id.close_button);
        lockButton = view.findViewById(R.id.block_button);
        unlockButton = view.findViewById(R.id.unblock_button);

        api.getDoorState(deviceId, new Callback<Result<DoorState>>() {
            @Override
            public void onResponse(Call<Result<DoorState>> call, Response<Result<DoorState>> response) {
                if(response.isSuccessful()) {
                    Result<DoorState> result = response.body();
                    if(result != null) {
                        DoorState doorState = result.getResult();
                        isOpen = doorState.isOpen();
                        isLocked = doorState.isLocked();
                        System.out.println("isOpen: " + isOpen);
                        System.out.println("isLocked: " + isLocked);
                        lockUnlockSwitch.setChecked(isLocked);
                        openCloseSwitch.setChecked(!isOpen);
                        if(openCloseSwitch != null && lockUnlockSwitch != null) {
                            openCloseSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                if (isChecked) {
                                    closeDoor();
                                } else {
                                    openDoor();
                                }
                            });
                            lockUnlockSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                if (isChecked) {
                                    lockDoor();
                                } else {
                                    unlockDoor();
                                }
                            });
                        }
                    }
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Result<DoorState>> call, Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            deviceId = bundle.getString("deviceId");
            positionInRecyclerView = bundle.getInt("positionInRecyclerView");
        }
    }

    private <T> void handleError(@NonNull Response<T> response) {
        Error error = ApiClient.getInstance().getError(response.errorBody());
        String text = "ERROR" + error.getDescription().get(0) + error.getCode();
        Log.e("com.example.ultrahome", text);
        Toast.makeText(getContext(), "OOPS! AN UNEXPECTED ERROR OCURRED :(", Toast.LENGTH_LONG).show();
    }

    private void handleUnexpectedError(@NonNull Throwable t) {
        String LOG_TAG = "com.example.ultrahome";
        Log.e(LOG_TAG, t.toString());
        Toast.makeText(getContext(), "OOPS! THERE'S A PROBLEM ON OUR SIDE :(", Toast.LENGTH_LONG).show();
    }

    @NonNull
    public static DoorControllerFragment newInstance(String deviceId, int positionInRecyclerView) {
        Bundle bundle = new Bundle();
        bundle.putString("deviceId", deviceId);
        bundle.putInt("positionInRecyclerView", positionInRecyclerView);

        DoorControllerFragment fragment = new DoorControllerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void openDoor() {
        if(isOpen) {
            Toast.makeText(getContext(), "THE DOOR IS ALREADY OPEN", Toast.LENGTH_LONG).show();
            return;
        }
        if(isLocked) {
            Toast.makeText(getContext(), "THE DOOR IS LOCKED!", Toast.LENGTH_LONG).show();
            return;
        }

        api.openDoor(deviceId, new Callback<Result<Boolean>>() {
            @Override
            public void onResponse(Call<Result<Boolean>> call, Response<Result<Boolean>> response) {
                if(response.isSuccessful()) {
                    Result<Boolean> result = response.body();
                    if(result != null) {
                        Toast.makeText(getContext(), "OPENING DOOR", Toast.LENGTH_LONG).show();
                        isOpen = true;
                        lockUnlockSwitch.setEnabled(false);
                    }
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Result<Boolean>> call, Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void closeDoor() {
        if(!isOpen || isLocked) {
            Toast.makeText(getContext(), "THE DOOR IS ALREADY CLOSED", Toast.LENGTH_LONG).show();
            return;
        }
        api.closeDoor(deviceId, new Callback<Result<Boolean>>() {
            @Override
            public void onResponse(Call<Result<Boolean>> call, Response<Result<Boolean>> response) {
                if(response.isSuccessful()) {
                    Result<Boolean> result = response.body();
                    if(result != null) {
                        Toast.makeText(getContext(), "CLOSING DOOR", Toast.LENGTH_LONG).show();
                        isOpen = false;
                        lockUnlockSwitch.setEnabled(true);
                    }
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Result<Boolean>> call, Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void lockDoor() {
        if(isLocked) {
            Toast.makeText(getContext(), "THE DOOR IS ALREADY LOCKED", Toast.LENGTH_LONG).show();
            return;
        }
        if(isOpen) {
            Toast.makeText(getContext(), "THE DOOR IS OPEN!", Toast.LENGTH_LONG).show();
            return;
        }

        api.lockDoor(deviceId, new Callback<Result<Boolean>>() {
            @Override
            public void onResponse(Call<Result<Boolean>> call, Response<Result<Boolean>> response) {
                if(response.isSuccessful()) {
                    Result<Boolean> result = response.body();
                    if(result != null) {
                        Toast.makeText(getContext(), "LOCKING DOOR", Toast.LENGTH_LONG).show();
                        isLocked = true;
                        openCloseSwitch.setEnabled(false);
                    }
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Result<Boolean>> call, Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void unlockDoor() {
        if(isOpen || !isLocked) {
            Toast.makeText(getContext(), "THE DOOR IS ALREADY UNLOCKED", Toast.LENGTH_LONG).show();
            return;
        }

        api.unlockDoor(deviceId, new Callback<Result<Boolean>>() {
            @Override
            public void onResponse(Call<Result<Boolean>> call, Response<Result<Boolean>> response) {
                if(response.isSuccessful()) {
                    Result<Boolean> result = response.body();
                    if(result != null) {
                        Toast.makeText(getContext(), "UNLOCKING DOOR", Toast.LENGTH_LONG).show();
                        isLocked = false;
                        openCloseSwitch.setEnabled(true);
                    }
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(Call<Result<Boolean>> call, Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }
}
