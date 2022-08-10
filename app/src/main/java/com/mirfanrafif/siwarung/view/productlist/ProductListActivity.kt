package com.mirfanrafif.siwarung.view.productlist

import android.Manifest
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.google.android.material.snackbar.Snackbar
import com.mirfanrafif.siwarung.R
import com.mirfanrafif.siwarung.SiwarungApp
import com.mirfanrafif.siwarung.core.domain.menu.Category
import com.mirfanrafif.siwarung.core.domain.menu.Product
import com.mirfanrafif.siwarung.databinding.ActivityMainBinding
import com.mirfanrafif.siwarung.utils.Status
import com.mirfanrafif.siwarung.utils.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as SiwarungApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportFragmentManager.beginTransaction()
            .add(binding.wrapper.id, ProductListFragment(), null).commit()
    }


}