package com.rivaldorendy.nik_converter

import android.content.Context
import com.rivaldorendy.nik_converter.Utils.Companion.toMap
import org.json.JSONObject
import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

class NIKConverter {

    /**
     * Converts a given NIK (Nomor Induk Kependudukan) string into NIKData object.
     *
     * @param nik The NIK string to convert.
     * @param context The context to access Android resources.
     * @param translateToId Flag indicating whether to translate to Indonesian.
     * @return An instance of NIKData if conversion is successful, null otherwise.
     */
    fun convert(nik: String, context: Context, translateToId : Boolean = false): NIKData? {
        if (nik.length != 16) return null
        return getNikData(nik, context, translateToId)
    }

    private fun getNikData(nik: String, context: Context, translateToId : Boolean = false): NIKData? {
        var nikData: NIKData?

        try {
            val provinceCode = nik.substring(0, 2)
            val cityCode = nik.substring(0, 4)
            val districtCode = nik.substring(0, 6)
            val birthDateCode = nik.substring(6, 8).toInt()
            val birthMonthCode = nik.substring(8, 10).toInt()
            val birthYearCode = nik.substring(10, 12).toInt()
            val uniqueComputerizationCode = nik.substring(12, 16)

            val province = getProvinceValue(provinceCode, context)
            val city = getCityValue(cityCode, context)

            val district = getDistrictValue(districtCode, context).split(" -- ")
            val districtName = district.first()
            val districtPostalCode = district.last()

            val gender = getGender(birthDateCode, translateToId)
            val birthDate = if (birthDateCode < 41) birthDateCode else (birthDateCode - 40)
            val birthMonth = getMonth(birthMonthCode, translateToId)
            val birthYear = getYear(birthYearCode)
            val birthDay = getDayOfWeek(birthYear, birthMonthCode, birthDate, translateToId)
            val birthdayCountdown = getBirthdayCountdown(birthMonthCode, birthDate, translateToId)
            val age = getAge(birthYear, birthMonthCode, birthDate, translateToId)
            val zodiacSign = getZodiacSign(birthMonthCode, birthDate)
            val chineseZodiac = getChineseZodiac(birthYear, translateToId)

            if (province.equals(null) || city.equals(null) || district.equals(null) ||
                birthDate > 31 || birthMonthCode > 12
            ) return null

            nikData = NIKData(
                province,
                city,
                districtName,
                districtPostalCode,
                gender,
                birthDate,
                birthMonth,
                birthYear,
                birthDay,
                birthdayCountdown,
                age,
                zodiacSign,
                chineseZodiac,
                uniqueComputerizationCode
            )

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return nikData
    }

    fun getGender(birthDateCode : Int, translateToId: Boolean) : String{
        return if (birthDateCode < 41) {
            if(translateToId) "Laki-laki" else "Male"
        } else if(translateToId) "Perempuan" else "Female"
    }

    fun getDayOfWeek(
        year: Int,
        month: Int,
        dayOfMonth: Int,
        translateToId: Boolean
    ): String {
        return when (LocalDate.of(year, month, dayOfMonth).dayOfWeek.value) {
            1 -> if (translateToId) "Senin" else "Monday"
            2 -> if (translateToId) "Selasa" else "Tuesday"
            3 -> if (translateToId) "Rabu" else "Wednesday"
            4 -> if (translateToId) "Kamis" else "Thursday"
            5 -> if (translateToId) "Jumat" else "Friday"
            6 -> if (translateToId) "Sabtu" else "Saturday"
            7 -> if (translateToId) "Minggu" else "Sunday"
            else -> "-"
        }
    }


    fun getMonth(month: Int, translateToId: Boolean): String {
        return when (month) {
            1 -> if (translateToId) "Januari" else "January"
            2 -> if (translateToId) "Februari" else "February"
            3 -> if (translateToId) "Maret" else "March"
            4 -> if (translateToId) "April" else "April"
            5 -> if (translateToId) "Mei" else "May"
            6 -> if (translateToId) "Juni" else "June"
            7 -> if (translateToId) "Juli" else "July"
            8 -> if (translateToId) "Agustus" else "August"
            9 -> if (translateToId) "September" else "September"
            10 -> if (translateToId) "Oktober" else "October"
            11 -> if (translateToId) "November" else "November"
            12 -> if (translateToId) "Desember" else "December"
            else -> "-"
        }
    }

    fun getYear(year: Int): Int {
        val formattedYear = when {
            year < 10 -> "0$year"
            else -> year.toString()
        }
        return when (year) {
            in 0..21 -> "20$formattedYear".toInt()
            else -> "19$formattedYear".toInt()
        }
    }


    fun getAge(year: Int, month: Int, dayOfMonth: Int, translateToId: Boolean): String {
        val period = Period.between(LocalDate.of(year, month, dayOfMonth), LocalDate.now())
        return if (translateToId) "${period.years} Tahun - ${period.months} Bulan - ${period.days} Hari"
        else "${period.years} Years - ${period.months} Months - ${period.days} Days"
    }

    fun getBirthdayCountdown(month: Int, dayOfMonth: Int, translateToId: Boolean): String {
        val period =
            Period.between(LocalDate.of(LocalDate.now().year, month, dayOfMonth), LocalDate.now())
        var returnText =
            if (translateToId) "${abs(period.days)} Hari lagi" else "${abs(period.days)} Days Left"
        if (period.months != 0) returnText =
            if (translateToId) "${abs(period.months)} Bulan - $returnText" else "${abs(period.months)} Months - $returnText"
        return returnText
    }

    fun getZodiacSign(month: Int, dayOfMonth: Int): String {
        return when {
            (1 == month && dayOfMonth >= 20) || (2 == month && dayOfMonth < 19) -> "Aquarius"
            (2 == month && dayOfMonth >= 19) || (3 == month && dayOfMonth < 21) -> "Pisces"
            (3 == month && dayOfMonth >= 21) || (4 == month && dayOfMonth < 20) -> "Aries"
            (4 == month && dayOfMonth >= 20) || (5 == month && dayOfMonth < 21) -> "Taurus"
            (5 == month && dayOfMonth >= 21) || (6 == month && dayOfMonth < 22) -> "Gemini"
            (6 == month && dayOfMonth >= 21) || (7 == month && dayOfMonth < 23) -> "Cancer"
            (7 == month && dayOfMonth >= 23) || (8 == month && dayOfMonth < 23) -> "Leo"
            (8 == month && dayOfMonth >= 23) || (9 == month && dayOfMonth < 23) -> "Virgo"
            (9 == month && dayOfMonth >= 23) || (10 == month && dayOfMonth < 24) -> "Libra"
            (10 == month && dayOfMonth >= 24) || (11 == month && dayOfMonth < 23) -> "Scorpio"
            (11 == month && dayOfMonth >= 23) || (12 == month && dayOfMonth < 22) -> "Sagittarius"
            (12 == month && dayOfMonth >= 22) || (1 == month && dayOfMonth < 20) -> "Capricorn"
            else -> "-"
        }
    }

    fun getChineseZodiac(year: Int, translateToId: Boolean): String {
        return when (year % 12) {
            0 -> if (translateToId) "Monyet" else "Monkey"
            1 -> if (translateToId) "Ayam" else "Rooster"
            2 -> if (translateToId) "Anjing" else "Dog"
            3 -> if (translateToId) "Babi" else "Pig"
            4 -> if (translateToId) "Tikus" else "Rat"
            5 -> if (translateToId) "Kerbau" else "Ox"
            6 -> if (translateToId) "Harimau" else "Tiger"
            7 -> if (translateToId) "Kelinci" else "Rabbit"
            8 -> if (translateToId) "Naga" else "Dragon"
            9 -> if (translateToId) "Ular" else "Snake"
            10 -> if (translateToId) "Kuda" else "Horse"
            11 -> if (translateToId) "Kambing" else "Goat"
            else -> "-"
        }
    }

    fun getProvinceValue(key: String, context: Context): String {
        return getProvinceMap(context)[key].toString()
    }

    fun getCityValue(key: String, context: Context): String {
        return getCityMap(context)[key].toString()
    }

    fun getDistrictValue(key: String, context: Context): String {
        return getDistrictMap(context)[key].toString()
    }

    private fun getProvinceMap(context: Context): Map<String, *> {
        val text = context.assets.open("province.json").bufferedReader().use { it.readText() }
        return JSONObject(text).toMap()
    }

    private fun getCityMap(context: Context): Map<String, *> {
        val text = context.assets.open("city.json").bufferedReader().use { it.readText() }
        return JSONObject(text).toMap()
    }

    private fun getDistrictMap(context: Context): Map<String, *> {
        val text = context.assets.open("district.json").bufferedReader().use { it.readText() }
        return JSONObject(text).toMap()
    }
}
