package you.village

import android.app.Application
import you.village.util.Database

/**
 * Created by Ji Sungbin on 2021/05/14.
 */

class Village : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init()
    }
}
