package dk.cphbusiness.template

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.amitshekhar.DebugDB
import kotlinx.android.synthetic.main.activity_java.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
        {
            fragmentManager.beginTransaction().replace(R.id.contentFrame, MapFragment()).commit()
        }

        goToMap.setOnClickListener { replaceWithMap() }
        goToList.setOnClickListener { replaceWithList() }

    }

    fun replaceWithList()
    {
        fragmentManager.beginTransaction().replace(R.id.contentFrame, ListFragment()).commit()
    }

    fun replaceWithMap() {
        fragmentManager.beginTransaction().replace(R.id.contentFrame, MapFragment()).commit()
    }

}