package com.brand.store

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.brand.store.activity.LoginActivity
import com.brand.store.data.Wallet

import com.brand.store.databinding.ActivityMainBinding
import com.brand.store.fragment.AddToCartFragment
//import com.brand.store.fragments.About_AppFragment
//import com.brand.store.fragments.AddToCartFragment
import com.brand.store.fragment.DashboardFragment
import com.brand.store.fragment.FavoiretFragment
import com.brand.store.fragment.ProfileFragment
//import com.brand.store.fragments.FavoiretFragment
//import com.brand.store.fragments.ProfileFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout : FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var btnlogout  : Button
    lateinit var appbarlayout : AppBarLayout


    fun replaceFragment(fragment: Fragment, title: String) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, fragment)
            transaction.commit()
            supportActionBar?.title = title
            drawerLayout.closeDrawers()
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening $title: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    var previousMenuItem: MenuItem? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerlayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigation_view)
        appbarlayout = findViewById(R.id.appbarlayout)


        setUpToolbar()

        openDashboard()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()



        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null){
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it


            when(it.itemId){
                R.id.dashboard ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, DashboardFragment())
                        .commit()

                    supportActionBar?.title = "Dashboard"
                    drawerLayout.closeDrawers()
                }
                R.id.add_to_cart -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, AddToCartFragment())
                        .commit()
                    supportActionBar?.title = "Add To Cart"
                    drawerLayout.closeDrawers()
                }
                R.id.favorites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, FavoiretFragment())
                        .commit()

                    supportActionBar?.title = "Favourite"
                    drawerLayout.closeDrawers()
                }
                R.id.account ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame, ProfileFragment())
                        .commit()

                    supportActionBar?.title = "Account"
                    drawerLayout.closeDrawers()
                }
//                R.id.about_app ->{
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame, About_AppFragment())
//                        .commit()
//
//                    supportActionBar?.title = "About App"
//                    drawerLayout.closeDrawers()
//                }
                R.id.logout -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    sharedPreferences.edit().clear().apply()
                    finish()
                    Toast.makeText(this@MainActivity,"Thank You For Visiting My Online Store", Toast.LENGTH_SHORT).show()


                }

            }

            return@setNavigationItemSelectedListener true

        }

    }
    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)
    }
    fun openDashboard(){
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Dashboard"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)



    }
}


