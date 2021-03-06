package com.example.test

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import com.example.test.PCcafe.pc_3pop_changdongActivity.pc_3pop_changdongActivity
import com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity.Payment_system_3pop_kwangwoon_univ
import com.example.test.PCcafe.pc_3pop_changdongActivity.Payment_system_3pop_changdong
import com.example.test.PCcafe.pc_ntop_kwangwoon_univActivity.Payment_system_ntop_kwangwoon_univ
import com.example.test.PCcafe.pc_3pop_kwangwoon_univActivity.pc_3pop_kwangwoon_univActivity
import com.example.test.PCcafe.pc_ntop_kwangwoon_univActivity.pc_ntop_kwangwoon_univActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.menubar.charge
import kotlinx.android.synthetic.main.menubar.local
import kotlinx.android.synthetic.main.menubar.pc_cafe
import kotlinx.android.synthetic.main.menubar.point
import android.webkit.WebView
import android.webkit.WebViewClient

import kotlinx.android.synthetic.main.app_bar_menubar.*


class MenubarActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val TAG : String = "MenubarActivity"
    var myUid=mAuth.currentUser?.uid.toString()
    var time3: Long = 0
    var pc_check=10000
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef : DatabaseReference = database.getReference("member")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menubar)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setUpUI()
        val actionBar = supportActionBar
        actionBar!!.title = "홈"

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        if(myUid.equals("null")){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if(myUid.equals(mAuth.currentUser?.uid.toString())==false){
            myUid=mAuth.currentUser?.uid.toString()
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        myRef.child(myUid).child("PCcafe").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                pc_cafe.text = "$value"
            }
        })
        myRef.child(myUid).child("local").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                local.text = "$value"
            }
        })
        myRef.child(myUid).child("point").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                val value = p0?.value
                point.text = "$value"
            }
        })

        charge.setOnClickListener {
            val intent = Intent(this, ChargeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }



    }

    private fun setUpUI() {

        // webview client 객체를 넘긴다.
        //wbMain.setWebViewClient(WebClient())
        webview.webViewClient = WebClient()

        // 브라우저 세팅을 가져온다.
        val set = webview.getSettings()
        // 자바스크립트를 실행가능하게
        set.setJavaScriptEnabled(true)
        // 줌인아웃을 불가능하게
        set.setBuiltInZoomControls(false)
        webview.loadUrl("https://sites.google.com/view/icseclab   ")
       // webview.loadUrl("http://www.zdnet.co.kr/    ")
    }

    internal inner class WebClient : WebViewClient() {
        // URL을 요청했다면...
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    override fun onBackPressed() {
        val time1 = System.currentTimeMillis()
        val time2 = time1 - time3
        if (time2 in 0..2000) {
            finishAffinity()
            //  System.runFinalization()
            //  System.exit(0)
        }
        else {
            time3 = time1
            Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show()
        }
    }
/*
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    */

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menubar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {
            R.id.action_creator -> {
                val intent = Intent(this, CreatorActivity::class.java)
                super.startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_myinfo -> {
                val intent = Intent(this, My_infoActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                // Handle the camera action
            }
            R.id.nav_searchPC -> {
                val intent = Intent(this, Search_pc_cafeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_favorite -> {
                val intent = Intent(this, My_pc_listActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_seat -> {
                if(pc_cafe.text.toString().equals("3POP")) {
                    if(local.text.toString().equals("광운대")) {
                        val intent = Intent(this, pc_3pop_kwangwoon_univActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    } else if(local.text.toString().equals("창동")) {
                        val intent = Intent(this, pc_3pop_changdongActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                } else if(pc_cafe.text.toString().equals("NTop")) {
                    if(local.text.toString().equals("광운대")) {
                        val intent = Intent(this, pc_ntop_kwangwoon_univActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

                    }
                } else{
                    Toast.makeText(baseContext, "PC cafe를 먼저 설정해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.nav_offPC -> {
                System.runFinalization()
                if(pc_cafe.text.toString().equals("3POP")) {
                    if(local.text.toString().equals("광운대")) {
                        val intent = Intent(this, Payment_system_3pop_kwangwoon_univ::class.java)
                        stopService(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    } else if(local.text.toString().equals("창동")) {
                        val intent = Intent(this, Payment_system_3pop_changdong::class.java)
                        stopService(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                } else if(pc_cafe.text.toString().equals("NTop")) {
                    if(local.text.toString().equals("광운대")) {
                        val intent = Intent(this, Payment_system_ntop_kwangwoon_univ::class.java)
                        stopService(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

                    }
                } else{
                    Toast.makeText(baseContext, "PC cafe를 먼저 설정해주세요", Toast.LENGTH_SHORT).show()
                }
                val pcRef: DatabaseReference =
                    database.getReference("PCcafe").child(pc_cafe.text.toString()).child(local.text.toString())

                myRef.child(myUid).child("seat_using").addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        //값이 변경된게 있으면 database의 값이 갱신되면 자동 호출된다
                        val value = p0?.value
                        if (pc_check == 10000) {
                            pcRef.child("$value").child("using").setValue("X")
                            pcRef.child("$value").child("uid").setValue("")
                            pcRef.child("$value").child("time_start").setValue("")
                            myRef.child(myUid).child("seat_using").setValue("")
                            Toast.makeText(baseContext, "PC 로그아웃", Toast.LENGTH_SHORT).show()
                            pc_check = 0
                        } else {
                            if ("$value".equals("") == false) {
                                pc_check = value.toString().toInt()
                            }
                        }
                    }
                })
                if (pc_check != 10000 && pc_check != 0) {
                    pcRef.child(pc_check.toString()).child("using").setValue("X")
                    pcRef.child(pc_check.toString()).child("uid").setValue("")
                    pcRef.child(pc_check.toString()).child("time_start").setValue("")
                    myRef.child(myUid).child("seat_using").setValue("")
                    Toast.makeText(baseContext, "pc 로그아웃", Toast.LENGTH_SHORT).show()
                }

            }

            R.id.nav_logout -> {
                mAuth.signOut()
                Toast.makeText(baseContext, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
