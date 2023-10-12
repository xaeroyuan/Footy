package com.xaerostudio.footy.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.xaerostudio.footy.data.DataStoreRepository
import com.xaerostudio.footy.utils.Constants.Companion.API_KEY
import com.xaerostudio.footy.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.xaerostudio.footy.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.xaerostudio.footy.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_APIKEY
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_DIET
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_NUMBER
import com.xaerostudio.footy.utils.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    var networkStatus = false
    var backOnline = false

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_APIKEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun showNetworkStatus() {
        if(!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else{
            if(backOnline) {
                Toast.makeText(getApplication(), "Back Online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}