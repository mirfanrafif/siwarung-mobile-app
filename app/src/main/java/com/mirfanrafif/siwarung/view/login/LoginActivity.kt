package com.mirfanrafif.siwarung.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.snackbar.Snackbar
import com.mirfanrafif.siwarung.SiwarungApp
import com.mirfanrafif.siwarung.databinding.ActivityLoginBinding
import com.mirfanrafif.siwarung.view.productlist.MainActivity
import com.mirfanrafif.siwarung.utils.Status
import com.mirfanrafif.siwarung.utils.ViewModelFactory
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: LoginViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        (application as SiwarungApp).appComponent.inject(this)
        if(viewModel.checkSession()) {
            startActivity(Intent(this, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.btnLogin.setOnClickListener {
            if (binding.edtUsername.text.isBlank()) {
                Snackbar.make(binding.root, "Username harus diisi", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.edtPassword.text.isBlank()) {
                Snackbar.make(binding.root, "Password harus diisi", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(
                binding.edtUsername.text.toString(),
                binding.edtPassword.text.toString()
            ).observe(this) { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.btnLogin.isEnabled = false
                    }
                    Status.SUCCESS -> {
                        startActivity(Intent(this, MainActivity::class.java).also { intent ->
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        })
                    }
                    Status.ERROR -> {
                        binding.btnLogin.isEnabled = true
                        Snackbar.make(binding.root, resource.message ?: "", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}