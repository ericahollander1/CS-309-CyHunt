package com.example.cyhunt.Model;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 *Interface for the LeaderboardFunctionality class
 */
public interface LeaderboardFunctionalityInterface {
     /**
      *Send a jsonArrayRequest to get the list of users as sorted by score. Returns nothing, but the
      * thread it initiates eventually calls a function in CVL to return the info to the view
      * @param CVL
      * @return depreciated
      */
     ArrayList<String> getUsersForDisplay(CustomVolleyListenerArrayList CVL);
}
