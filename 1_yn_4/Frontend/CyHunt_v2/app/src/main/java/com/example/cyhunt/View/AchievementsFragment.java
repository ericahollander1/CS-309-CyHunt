package com.example.cyhunt.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cyhunt.Presenter.AchievementsPresenter;
import com.example.cyhunt.R;

/**
 * Handles any Android related aspect of the Achievements page
 * A simple {@link Fragment} subclass.
 * Use the {@link AchievementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Lexi
 */
public class AchievementsFragment extends Fragment implements IAchievementsView {

    private SharedPreferences sharedPreferences;
    private AchievementsPresenter presenter;
    private ImageView achievement_1;
    private ImageView achievement_2;
    private ImageView achievement_3;
    private ImageView achievement_4;
    private ImageView achievement_5;
    private ImageView achievement_6;
    private ImageView achievement_7;
    private ImageView achievement_8;
    private ImageView achievement_9;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor
     */
    public AchievementsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AchievementsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AchievementsFragment newInstance(String param1, String param2) {
        AchievementsFragment fragment = new AchievementsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a fragment
     * @param savedInstanceState a savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Creates the view to be inflated
     * @param inflater a layout inflator
     * @param container a container
     * @param savedInstanceState a savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);

        sharedPreferences = getActivity().getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);
        presenter = new AchievementsPresenter(this, getActivity().getApplicationContext());

        initializeAchievements(view);
        unlockAchievements();

        return view;
    }

    private void initializeAchievements(View view) {
        achievement_1 = (ImageView) view.findViewById(R.id.achievement_1);
        achievement_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_ACCOUNT", -1) == 1) {
                    createPopup("Achievement completed!","Created an account.");
                } else {
                    createPopup("To complete this achievement:","Create an account");
                }
            }
        });

        achievement_2 = (ImageView) view.findViewById(R.id.achievement_2);
        achievement_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_FIRST_FOUND_CY", -1) == 1) {
                    createPopup("Achievement completed!","Found Cy for the first time.");
                } else {
                    createPopup("To complete this achievement:","Find Cy for the first time");
                }
            }
        });

        achievement_3 = (ImageView) view.findViewById(R.id.achievement_3);
        achievement_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_FIRST_TRIVIA_CORRECT", -1) == 1) {
                    createPopup("Achievement completed!","Correctly answered a trivia question for the first time.");
                } else {
                    createPopup("To complete this achievement:","Correctly answer a trivia question for the first time");
                }
            }
        });

        achievement_4 = (ImageView) view.findViewById(R.id.achievement_4);
        achievement_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_25_POINTS", -1) == 1) {
                    createPopup("Achievement completed!","Earned 25 points.");
                } else {
                    createPopup("To complete this achievement:","Earn 25 points.");
                }
            }
        });

        achievement_5 = (ImageView) view.findViewById(R.id.achievement_5);
        achievement_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_50_POINTS", -1) == 1) {
                    createPopup("Achievement completed!","Earned 50 points.");
                } else {
                    createPopup("To complete this achievement:","Earn 50 points.");
                }
            }
        });

        achievement_6 = (ImageView) view.findViewById(R.id.achievement_6);
        achievement_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_75_POINTS", -1) == 1) {
                    createPopup("Achievement completed!","Earned 75 points.");
                } else {
                    createPopup("To complete this achievement:","Earn 75 points.");
                }
            }
        });

        achievement_7 = (ImageView) view.findViewById(R.id.achievement_7);
        achievement_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_TRIVIA_1", -1) == 1) {
                    createPopup("Achievement completed!","Created one approved trivia question.");
                } else {
                    createPopup("To complete this achievement:","Create one approved trivia question.");
                }
            }
        });

        achievement_8 = (ImageView) view.findViewById(R.id.achievement_8);
        achievement_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_TRIVIA_2", -1) == 1) {
                    createPopup("Achievement completed!","Created two approved trivia questions.");
                } else {
                    createPopup("To complete this achievement:","Created two approved trivia questions.");
                }
            }
        });

        achievement_9 = (ImageView) view.findViewById(R.id.achievement_9);
        achievement_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_TRIVIA_3", -1) == 1) {
                    createPopup("Achievement completed!","Created three approved trivia questions.");
                } else {
                    createPopup("To complete this achievement:","Create three approved trivia questions.");
                }
            }
        });
    }

    private void unlockAchievements() {
        int darkRed = Color.rgb(124, 37, 41);
        if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_ACCOUNT", -1) == 1) {
            achievement_1.setColorFilter(darkRed);
        }
        if (sharedPreferences.getInt("ACHIEVEMENT_FIRST_FOUND_CY", -1) == 1) {
            achievement_2.setColorFilter(darkRed);
        }
        if (sharedPreferences.getInt("ACHIEVEMENT_FIRST_TRIVIA_CORRECT", -1) == 1) {
            achievement_3.setColorFilter(darkRed);
        }
        if (sharedPreferences.getInt("ACHIEVEMENT_25_POINTS", -1) == 1) {
            achievement_4.setColorFilter(darkRed);
        }
        if (sharedPreferences.getInt("ACHIEVEMENT_50_POINTS", -1) == 1) {
            achievement_5.setColorFilter(darkRed);
        }
        if (sharedPreferences.getInt("ACHIEVEMENT_75_POINTS", -1) == 1) {
            achievement_6.setColorFilter(darkRed);
        }
    }

    private void createPopup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //nothing happens
                //popup closes
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}