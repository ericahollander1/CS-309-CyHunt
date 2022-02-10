package com.example.cyhunt.Model;

import com.android.volley.VolleyError;
import com.example.cyhunt.triviaData;

import java.util.ArrayList;

public interface CustomVolleyListenerApprovalList {
    /**
     * Handles the successful completion of a JSON request. We basically just want the IDs and questions
     * on the first pass
     * @param l the arrayList returned by the JSON request
     */
    void onSuccess(ArrayList<triviaData> l);

    /**
     * Handles the failed completion of a JSON request
     * @param error
     */
    void onError(VolleyError error);

    /**
     * We need to retreive the authors seperately. Since the requests will come in asynchronously, we'll
     * need to manually retrieve the positions the authors are meant for
     * @param s
     */
    void onAuthorRetrieved(String s, int pos);
}
