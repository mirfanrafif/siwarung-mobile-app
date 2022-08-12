package com.mirfanrafif.siwarung.view.productlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mirfanrafif.siwarung.SiwarungApp
import com.mirfanrafif.siwarung.databinding.ActivityMainBinding
import com.mirfanrafif.siwarung.utils.ViewModelFactory
import javax.inject.Inject


class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: ProductListViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as SiwarungApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = viewModel.getWarung().name
        setSupportActionBar(binding.toolbar)

        supportFragmentManager.beginTransaction()
            .add(binding.wrapper.id, ProductListFragment(), null).commit()
    }


}