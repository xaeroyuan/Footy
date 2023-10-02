package com.xaerostudio.footy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.xaerostudio.footy.utils.Constants.Companion.API_KEY
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_APIKEY
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_DIET
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_NUMBER
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_APIKEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}