package com.example.cyhunt.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.cyhunt.Model.CustomVolleyListenerEvaluateTrivia;
import com.example.cyhunt.Presenter.ProCreaTriviaFunctionality;
import com.example.cyhunt.Presenter.TriviaEvaluationFunctionality;
import com.example.cyhunt.R;
import com.example.cyhunt.triviaData;

import org.w3c.dom.Text;

import java.io.BufferedReader;

/** This class will eventually be a reusable template for trivia questions submitted by collaborators
 * that will allow admins (the users with access to this page) to approve or disaprove the submitted
 * trivia. This will be navigated to via theProposedTriviaListFragment
 * A simple {@link Fragment} subclass.
 * Use the {@link ApproveDisapproveTriviaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApproveDisapproveTriviaFragment extends Fragment implements CustomVolleyListenerEvaluateTrivia {

    private TextView question;
    private TextView realAnswer;
    private TextView decoy1;
    private TextView decoy2;
    private TextView decoy3;
    private Button approveButton;
    private Button disapproveButton;
    private Button nevermind;
    private TriviaEvaluationFunctionality p;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private triviaData trivia;

    public ApproveDisapproveTriviaFragment() {
        // Required empty public constructor
    }
    public ApproveDisapproveTriviaFragment(int i, Context c) {
        triviaData trivia = new triviaData();
        trivia.ID = i;
        p = new TriviaEvaluationFunctionality(c);
        p.getTrivia(this, trivia.ID);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApproveDisapproveTriviaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApproveDisapproveTriviaFragment newInstance(String param1, String param2) {
        ApproveDisapproveTriviaFragment fragment = new ApproveDisapproveTriviaFragment();
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
        View view=   inflater.inflate(R.layout.fragment_approve_disapprove_trivia, container, false);
        question = (TextView)view.findViewById(R.id.triviaQuestionField);
        realAnswer = (TextView)view.findViewById(R.id.correctAnswerField);
        decoy1 = (TextView)view.findViewById(R.id.da1field);
        decoy2 = (TextView)view.findViewById(R.id.da2field);
        decoy3 = (TextView)view.findViewById(R.id.da3field);
        approveButton = (Button)view.findViewById(R.id.ApproveTriviaBtn);
        disapproveButton = (Button)view.findViewById(R.id.DisapproveTriviaBtn);
        nevermind = (Button)view.findViewById(R.id.returnBtn);
        CustomVolleyListenerEvaluateTrivia CVL = this;
        nevermind.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().onBackPressed();
            }
        });
        approveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                p.approveTrivia(CVL, trivia.ID);
            }
        });
        disapproveButton = (Button)view.findViewById(R.id.DisapproveTriviaBtn);
        disapproveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                p.rejectTrivia(CVL, trivia.ID);
            }
        });
        return view;
    }

    @Override
    public boolean onTriviaRetrieved(triviaData t) {
        trivia = t;
        question.setText(t.question);
        realAnswer.setText(t.answer);
        decoy1.setText(t.decoy1);
        decoy2.setText(t.decoy2);
        decoy3.setText(t.decoy3);
        return false;
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onEvaluatioComplete(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(s).setTitle("Trivia evaluated!");
        builder.setPositiveButton("Return to list", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}