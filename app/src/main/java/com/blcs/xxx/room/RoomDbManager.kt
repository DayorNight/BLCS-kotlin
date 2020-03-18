package com.blcs.xxx.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blcs.xxx.bean.Student
import java.util.concurrent.Executors


/**
 * 创建数据库
 * 通过entities 指定数据库中的表
 * version指定当前数据库版本号
 */
@Database(entities = arrayOf(Student::class), version = 1)
abstract class RoomDbManager : RoomDatabase() {
    abstract val studentDao: StudentDao

    companion object {
        private var instance: RoomDbManager? = null
        fun getInstance(context: Context): RoomDbManager {
            if (instance == null) {
                synchronized(RoomDbManager::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            RoomDbManager::class.java, "BLCS"
                        ).addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                fillDatabase(context)
                            }
                        }).build()
                    }
                }
            }
            return instance!!
        }

        private fun fillDatabase(context: Context) {
            ioThread {
                CHEESE_DATA.map {
                    getInstance(context).studentDao.insertOne(Student(id = 0, name = it))
                }
            }
        }
    }


}

private val EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: () -> Unit) {
    EXECUTOR.execute(f)
}

private val CHEESE_DATA = arrayListOf(
    "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
    "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
    "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
    "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
    "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
    "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
    "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
    "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
    "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
    "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
    "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
    "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
    "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)"
)