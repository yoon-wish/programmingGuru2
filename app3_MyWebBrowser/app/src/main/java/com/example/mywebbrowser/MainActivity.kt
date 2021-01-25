package com.example.mywebbrowser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    lateinit var urlEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        urlEditText = findViewById(R.id.urlEditText)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }

        webView.loadUrl("http://www.google.com")

        registerForContextMenu(webView)

        urlEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {  //키보드의 검색 버튼이 눌렸는지 안눌렸는지 확인
                webView.loadUrl(urlEditText.text.toString())
                true
            } else {
                false
            }
        }
    }

    override fun onBackPressed() {  //뒤로가기 키를 눌렀을 때
        if(webView.canGoBack()){ //이전 페이지로 갈 수 있을 때
            webView.goBack() //이전 페이지로 이동
        } else{ //그렇지 않으면
            super.onBackPressed() //원래 동작 수행
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }

            R.id.action_call -> { //전화하기
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:012-3456-7890")
                if(intent.resolveActivity(packageManager)!=null) { //인텐트를 처리할 액티비티가 있는지 없는지 확인
                    startActivity(intent)
                }
                return true
            }
            R.id.action_send_text -> { //문자보내기
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:" +Uri.encode("012-3456-7890"))
                if(intent.resolveActivity(packageManager)!=null) { //인텐트를 처리할 액티비티가 있는지 없는지 확인
                    startActivity(intent)
                }
                return true
            }
            R.id.action_email -> { //이메일하기
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:example@example.com")
                if(intent.resolveActivity(packageManager)!=null) { //인텐트를 처리할 액티비티가 있는지 없는지 확인
                    startActivity(intent)
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu( menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    /*
    Context메뉴는 option메뉴와 매우 유사
    한가지 추가되는 것은 oncreate 메소드 내의 context메뉴가 필요한 위젯을 등록해야함
    그래서 registerFroContextMenw를 사용해줌 (line33)
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, webView.url.toString())
                intent.setType("text/plain")
                val shareIntent = Intent.createChooser(intent, "공유 페이지")
                startActivity(shareIntent)
                return true
            }
            R.id.action_browser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webView.url))
                startActivity(Intent.createChooser(intent, "Browser"))
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}