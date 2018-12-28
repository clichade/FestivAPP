package com.example.trjano.festivapp.utilities


import com.example.trjano.festivapp.R
import java.util.*
import kotlin.collections.RandomAccess

/**Converts date into String*/
fun transform_date(year: Int, month: Int, day: Int): String = "$day/$month/$year"

/**
 * Generates a random number between from(Inclusive) to to(inclusive)
 */
fun rand(from: Int, to: Int) : Int {
    return Random().nextInt(to +1 - from) + from
}

fun get_random_concert_image(): Int {
    val concert_imgs = listOf(R.drawable.image_concert_1,
            R.drawable.image_concert_2,R.drawable.image_concert_3,
            R.drawable.image_concert_4,R.drawable.image_concert_5)

    return concert_imgs[rand(0,4)]
}

fun get_random_festival_image(): Int {
    val festival_imgs = listOf(R.drawable.image_festival_1,R.drawable.image_festival_2,
            R.drawable.image_festival_3,R.drawable.image_festival_4)

    return festival_imgs[rand(0,3)]
}

