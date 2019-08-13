package com.denizsubasi.moviesapp.manager

import android.content.Context
import android.telephony.TelephonyManager
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalizationHelpers @Inject constructor(
        var context: Context
) {

    var country: String = ""

    fun detectCountry(): String {
        if (!detectSIMCountry().isNullOrBlank()) {
            return country.toLowerCase()
        } else if (!detectNetworkCountry().isNullOrBlank()) {
            return country.toLowerCase()
        } else {
            return Locale.getDefault().country.toLowerCase()
        }
    }


    private fun detectSIMCountry(): String? {
        try {
            val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (telephonyManager.simCountryIso.isBlank()) {
                return null
            }

            country = telephonyManager.simCountryIso
            return country
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectNetworkCountry(): String? {
        try {
            val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            country = telephonyManager.networkCountryIso
            return country
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}