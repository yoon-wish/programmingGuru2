package com.example.mygallery

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)

        // 권한이 부여되었는지확인(read_external_stroage)
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) { // 이전에 거부한 적이 있는지 물음

                // 이전에 거부한 적이 있으면 설명(경고)
                var dlg = AlertDialog.Builder(this)
                dlg.setTitle("권한이 필요한 이유")
                dlg.setMessage("사진 정보를 얻기 위해서는 외부 저장소 권한이 필수로 필요합니다.")
                dlg.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_EXTERNAL_STORAGE
                    )
                }
                dlg.setNegativeButton("취소", null)
                dlg.show()
            } else {
                //처음 권한 요청(이전에 요청한 적X)
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )
            }
        }else {
            //권한이 이미 허용됨
            getAllPhotos()
        }

        getAllPhotos()
    }

    private fun getAllPhotos() {
        //모든 사진 정보 가져오기
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, // 가져올 항목 배열
                null, // 조건
                null, // 조건
                MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC") // 촬영 최신날짜순

        val fragments = ArrayList<Fragment>()

        if (cursor != null) { // 이미지가 있다면
            while (cursor.moveToNext()) {
                // 사진 경로 url 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                Log.d("MainActivity", uri)
                fragments.add(PhotoFlagment.newInstance(uri)) // uri 정보를 파라미터로 받아 하나하나 생성
            }
            cursor.close()
        }

        // 어댑터
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.updateFragments(fragments)
        viewPager.adapter = adapter

/*
        // 3초마다 자동 슬라이드
        timer(period = 3000){
            runOnUiThread() {
                if(viewPager.currentItem < adapter.count - 1) {
                   viewPager.currentItem = viewPager.currentItem + 1
                }else{
                    viewPager.currentItem = 0
                }
            }
        }
      
 */
    }

}