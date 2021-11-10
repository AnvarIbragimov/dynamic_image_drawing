package mobile.uz.dynamic_image_drawing

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class Utils {
    companion object {
        fun formatCardBalance(value: Double): String {
            val symbols = DecimalFormatSymbols()
            symbols.groupingSeparator = ' '
            val df = DecimalFormat("#,###.#", symbols)
            return df.format(value).replace(",", ".")
        }
    }
}