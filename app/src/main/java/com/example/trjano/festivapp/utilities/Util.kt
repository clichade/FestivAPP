package com.example.trjano.festivapp.utilities


import com.example.trjano.festivapp.R
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.RandomAccess

object Util {
    /**
     * Fixed values for relations between activities.
     */
    val NONE = "none"
    val TYPE_CONCERT = "CONCERT"
    val TYPE_FESTIVAL= "FESTIVAL"
    val TYPE_BOTH = "CONCERT|FESTIVAL"


    /**Converts date from the Edit Texts into the required format YYYY-MM-DD String*/
    fun format_date(year: Int, month: Int, day: Int): String {
        val formatter = DecimalFormat("00")
        return "$year-${formatter.format(month+1)}-${formatter.format(day)}"
    }

    /**
     * Generates a random number between from(Inclusive) to to(inclusive)
     */
    fun rand(from: Int, to: Int): Int {
        return Random().nextInt(to + 1 - from) + from
    }

    fun get_random_concert_image(): Int {
        val concert_imgs = listOf(R.drawable.image_concert_1,
                R.drawable.image_concert_2, R.drawable.image_concert_3,
                R.drawable.image_concert_4, R.drawable.image_concert_5)

        return concert_imgs[rand(0, 4)]
    }

    fun get_random_festival_image(): Int {
        val festival_imgs = listOf(R.drawable.image_festival_1, R.drawable.image_festival_2,
                R.drawable.image_festival_3, R.drawable.image_festival_4)

        return festival_imgs[rand(0, 3)]
    }

}
