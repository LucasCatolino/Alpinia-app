package com.example.Alpinia;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.Alpinia.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.Alpinia.ui.home.HomeViewModel;

import java.util.List;

public class FragmentHome extends Fragment {

    View vista;
    private Button createRoomButton;
    private Button modifyRoomButton;
    private Button deleteRoomButton;
    private Button getRoomButton;
    private Button getRoomsButton;
    private TextView resultTextView;
    private Room room;
    private int index = 1;



    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        vista = inflater.inflate(R.layout.fragment_home, container, false);

        resultTextView = vista.findViewById(R.id.result);

        createRoomButton= (Button) vista.findViewById(R.id.create_room);
        if (createRoomButton != null) {
            createRoomButton.setOnClickListener(v -> createRoom());
        }

        modifyRoomButton = (Button) vista.findViewById(R.id.modify_room);
        if (modifyRoomButton != null) {
            modifyRoomButton.setOnClickListener(v -> modifyRoom());
        }

        deleteRoomButton = (Button) vista.findViewById(R.id.delete_room);
        if (deleteRoomButton != null) {
            deleteRoomButton.setOnClickListener(v -> deleteRoom());
        }

        getRoomButton = (Button) vista.findViewById(R.id.get_room);
        if (getRoomButton != null) {
            getRoomButton.setOnClickListener(v -> getRoom());
        }

        getRoomsButton = (Button) vista.findViewById(R.id.get_rooms);
        if (getRoomsButton != null) {
            getRoomsButton.setOnClickListener(v -> getRooms());
        }

        final TextView textView = vista.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return vista;
    }

    private void createRoom() {
        Toast.makeText(getContext(), "Creating Room", Toast.LENGTH_LONG).show();
        room = new Room("kitchen" + index++, new RoomMeta("9m2"));
        ApiClient.getInstance().addRoom(room, new Callback<Result<Room>>() {
            @Override
            public void onResponse(@NonNull Call<Result<Room>> call, @NonNull Response<Result<Room>> response) {
                if (response.isSuccessful()) {
                    Result<Room> result = response.body();
                    if (result != null) {
                        room.setId(result.getResult().getId());
                        showResult(room.toString());
                        toggleButtons(true);
                    } else {
                        showResult("null");
                    }
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<Room>> call, @NonNull Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void modifyRoom() {
        room.getMeta().setSize("6m2");
        ApiClient.getInstance().modifyRoom(room, new Callback<Result<Boolean>>() {
            @Override
            public void onResponse(@NonNull Call<Result<Boolean>> call, @NonNull Response<Result<Boolean>> response) {
                if (response.isSuccessful()) {
                    Result<Boolean> result = response.body();
                    showResult(result != null ? result.getResult().toString() : "null");
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<Boolean>> call, @NonNull Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void deleteRoom() {
        ApiClient.getInstance().deleteRoom(room.getId(), new Callback<Result<Boolean>>() {
            @Override
            public void onResponse(@NonNull  Call<Result<Boolean>> call, @NonNull Response<Result<Boolean>> response) {
                if (response.isSuccessful()) {
                    Result<Boolean> result = response.body();
                    showResult(result != null ? result.getResult().toString() : "null");
                    //TODO: si se queda sin habitaciones, que bloquee los botones menos el de create
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<Boolean>> call, @NonNull Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void getRoom() {
        ApiClient.getInstance().getRoom(room.getId(), new Callback<Result<Room>>() {
            @Override
            public void onResponse(@NonNull Call<Result<Room>> call, @NonNull Response<Result<Room>> response) {
                if (response.isSuccessful()) {
                    Result<Room> result = response.body();
                    resultTextView.setText(result != null ? result.getResult().toString() : "null");
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<Room>> call, @NonNull Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void getRooms() {
        ApiClient.getInstance().getRooms(new Callback<Result<List<Room>>>() {
            @Override
            public void onResponse(@NonNull Call<Result<List<Room>>> call, @NonNull Response<Result<List<Room>>> response) {
                if (response.isSuccessful()) {
                    Result<List<Room>> result = response.body();
                    showResult(result != null ? TextUtils.join(",", result.getResult()) : "null");
                } else {
                    handleError(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result<List<Room>>> call, @NonNull Throwable t) {
                handleUnexpectedError(t);
            }
        });
    }

    private void showResult(String result) {
        String format = getResources().getString(R.string.result);
        resultTextView.setText(String.format(format, result));
    }

    private void toggleButtons(boolean enabled) {
        createRoomButton.setEnabled(enabled);
        modifyRoomButton.setEnabled(enabled);
        deleteRoomButton.setEnabled(enabled);
        getRoomButton.setEnabled(enabled);
        getRoomsButton.setEnabled(enabled);
    }

    private <T> void handleError(Response<T> response) {
        Error error = ApiClient.getInstance().getError(response.errorBody());
        String text = getResources().getString(R.string.error_message, error.getDescription().get(0), error.getCode());
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    private void handleUnexpectedError(Throwable t) {
        String LOG_TAG = "App";
        Log.e(LOG_TAG, t.toString());
    }
}