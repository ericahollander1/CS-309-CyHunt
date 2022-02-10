package com.example.cyhunt.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.VolleyError;
import com.example.cyhunt.ApprovalAdapter;
import com.example.cyhunt.Model.CustomVolleyListenerApprovalList;
import com.example.cyhunt.R;
import com.example.cyhunt.triviaData;

import com.example.cyhunt.Presenter.TriviaListFunctionality;

import java.util.ArrayList;

/**This class will eventually list all of the recently submitted trivia for judgement by admin users
 * A simple {@link Fragment} subclass.
 * Use the {@link ProposedTriviaListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProposedTriviaListFragment extends Fragment implements CustomVolleyListenerApprovalList {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView evaluationList;
    private ArrayAdapter adapter;
    private ArrayList<triviaData> l = new ArrayList();
    private TriviaListFunctionality t;

    public ProposedTriviaListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProposedTriviaListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProposedTriviaListFragment newInstance(String param1, String param2) {
        ProposedTriviaListFragment fragment = new ProposedTriviaListFragment();
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
        t = new TriviaListFunctionality(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_proposed_trivia_list, container, false);
        evaluationList = (RecyclerView) view.findViewById(R.id.evaluationList);
        ApprovalAdapter a = new ApprovalAdapter(l);
        evaluationList.setAdapter(a);
        //leaderboard.setHasFixedSize(true);
        evaluationList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //leaderboard.setAdapter(a);
        l = t.getTriviaQuestions(this);
        return view;
    }

    @Override
    public void onSuccess(ArrayList<triviaData> array) {
        l = array;
        for(int i = 0; i < array.size(); i++)
        {
           t.getAuthor(this, i, l.get(i).ID);
        }
        ApprovalAdapter a = new ApprovalAdapter(l);
        evaluationList.setAdapter(a);
    }

    @Override
    public void onError(VolleyError error) {
        Log.d("ERROR", error.toString());
    }

    @Override
    public void onAuthorRetrieved(String s, int pos) {
        triviaData trivia = new triviaData();
        trivia.question = l.get(pos).question;
        trivia.ID = l.get(pos).ID;
        trivia.author = s;
        l.set(pos, trivia);
        ApprovalAdapter a = new ApprovalAdapter(l);
        evaluationList.setAdapter(a);
    }
}