package com.psymoon.simplewebviewapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_browser.*

const val DEFAULT_URL = "https://youtube.com/tv"

class BrowserFragment: Fragment(), TabLayout.OnTabSelectedListener {

    private val webViewTabs = HashMap<TabLayout.Tab, WebView>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_browser, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout.addOnTabSelectedListener(this)

        addNewtab()

        plusButton.setOnClickListener {
            addNewtab()
        }
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {}

    override fun onTabUnselected(p0: TabLayout.Tab?) {}

    override fun onTabSelected(currTab: TabLayout.Tab?) {
        browserFrameLayout.removeAllViews()
        browserFrameLayout.addView(webViewTabs.get(currTab))
    }

    private fun addNewtab() {
        val tabNum = webViewTabs.size + 1
        val newTab = tabLayout.newTab().setText("Tab $tabNum")
        webViewTabs.put(newTab, createWebViewInstance())
        tabLayout.addTab(newTab)
        newTab.select()
    }

    private fun createWebViewInstance(): WebView {
        val webView = WebView(context)

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true

        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.useWideViewPort = true
        webView.settings.setSupportZoom(true)
        webView.settings.allowContentAccess = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.settings.allowFileAccess = true
        webView.settings.allowFileAccessFromFileURLs = true

        webView.loadUrl(DEFAULT_URL)

        return webView
    }
}
