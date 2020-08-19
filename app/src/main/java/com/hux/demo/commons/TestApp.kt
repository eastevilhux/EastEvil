package com.hux.demo.commons

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.good.framework.db.DaoMaster
import com.good.framework.db.DaoMaster.DevOpenHelper
import com.good.framework.db.DaoSession
import com.good.framework.utils.LogUtil
import com.hux.demo.constans.AppModel


class TestApp : Application() {

    companion object{
        private val DB_NAME = "life_house_db"
    }

    private var mHelper: DevOpenHelper? = null
    private var db: SQLiteDatabase? = null
    private var mDaoMaster: DaoMaster? = null
    private var mDaoSession: DaoSession? = null

    override fun onCreate() {
        super.onCreate()
        setDatabase();
        LogUtil.debug = true;
        AppModel.initModel();
    }


    /**
     * 设置greenDao
     */
    private fun setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = DevOpenHelper(this, DB_NAME, null)
        db = mHelper!!.getWritableDatabase()
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = DaoMaster(db)
        mDaoSession = mDaoMaster!!.newSession()
    }

    fun getDaoSession(): DaoSession? {
        return mDaoSession
    }

    fun getDb(): SQLiteDatabase? {
        return db
    }
}