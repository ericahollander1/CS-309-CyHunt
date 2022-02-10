package com.example.cyhunt.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.CustomVolleyListenerProCreaTrivia;
import com.example.cyhunt.R;
import com.example.cyhunt.Presenter.ProCreaTriviaFunctionality;

/**This page will allow admin users to create new trivia questions
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateTriviaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTriviaFragment extends Fragment implements CustomVolleyListenerProCreaTrivia {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView question;
    private TextView realAnswer;
    private TextView decoy1;
    private TextView decoy2;
    private TextView decoy3;
    private Button submitButton;
    private Button clearButton;
    private ProCreaTriviaFunctionality p;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateTriviaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTriviaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTriviaFragment newInstance(String param1, String param2) {
        CreateTriviaFragment fragment = new CreateTriviaFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_trivia, container, false);
        p = new ProCreaTriviaFunctionality(getActivity().getApplicationContext());
        question = (TextView)view.findViewById(R.id.questionBody);
        realAnswer = (TextView)view.findViewById(R.id.correctAnswer);
        decoy1 = (TextView)view.findViewById(R.id.decoyAnswer1);
        decoy2 = (TextView)view.findViewById(R.id.decoyAnswer2);
        decoy3 = (TextView)view.findViewById(R.id.decoyAnswer3);
        submitButton = (Button)view.findViewById(R.id.createTriviaButton);
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClickSubmit(v, p);
            }
        });
        clearButton = (Button)view.findViewById(R.id.clearEntries);
        clearButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onClearEntries();
            }
        });
        return view;

    }

    @Override
    public boolean onSuccess(String s) {

        //if not failed
        if(!s.equals("failure"))
        {
            int i = Integer.parseInt(s);
            p.attachAuthorToTrivia(i, this);
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Trivia cannot be added.").setTitle("Trivia creation failed");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //nothing happens but the dialog closes
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    @Override
    public void onSuccessComplete(String s)
    {
        if(!s.equals("failure"))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Trivia has been added to trivia pool").setTitle("Trivia created!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    //nothing happens but the dialog closes
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        onClearEntries();
    }

    @Override
    public void onError(VolleyError error) {

    }
    public void onClearEntries()
    {
        question.setText("");
        realAnswer.setText("");
        decoy1.setText("");
        decoy2.setText("");
        decoy3.setText("");
    }

    public void onClickSubmit(View v, ProCreaTriviaFunctionality p)
    {
        String msg = p.simpleTriviaCheck(question.getText().toString(),
                                         realAnswer.getText().toString(),
                                         decoy1.getText().toString(),
                                         decoy2.getText().toString(),
                                         decoy3.getText().toString());
        if(msg.equals("Valid")) //if the user has created a question, attempt to submit it
        {
            p.submitNewTrivia(question.getText().toString(),
                    realAnswer.getText().toString(),
                    decoy1.getText().toString(),
                    decoy2.getText().toString(),
                    decoy3.getText().toString(),
                    true, this);
        }
        else {
            Log.d("FAILURE", "User has not submitted trivia");
            //if the user has not submitted any trivia, ask them to try again
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(msg).setTitle("Entry invalid!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    //nothing happens but the dialog closes
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}