package com.xaerostudio.footy.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xaerostudio.footy.models.FoodRecipe
import com.xaerostudio.footy.utils.Constants.Companion.RECIPES_TABLE


@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}