package com.example.chap5top2

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnLoad.setOnClickListener {
            Glide.with(this)
                .load("https://img.icons8.com/plasticine/2x/flower.png")
                .into(img1)
        }


        btnCek.setOnClickListener {
            CheckConnectionType()
        }

        btnLok.setOnClickListener {
            val permissionCheck = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Location DIIZINKAN", Toast.LENGTH_LONG).show()
                getLongLat()
            } else {
                Toast.makeText(this, "Permission Location DITOLAK", Toast.LENGTH_LONG).show()
                requestLocationPermission()
            }
        }


    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestLocationPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 201)
    }

    @SuppressLint("MissingPermission")
    fun getLongLat() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

//        Toast.makeText(this,"Lat: ${location.latitude} Long : ${location.longitude}",Toast.LENGTH_LONG).show()

        val latLongText = "Lat: ${location?.latitude} Long : ${location?.longitude}"
        Log.d(MainActivity::class.simpleName, latLongText)
        Toast.makeText(
            this,
            latLongText,
            Toast.LENGTH_LONG
        ).show()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    Toast.makeText(this, "Allow", Toast.LENGTH_LONG)
                        .show()
                    getLongLat()
                } else {
                    Toast.makeText(this, "Denied", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else -> {
                Toast.makeText(this, "Bukan Request yang dikirim", Toast.LENGTH_LONG).show()
            }
        }
    }




    fun CheckConnectionType()
    {
        val connectionManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi_Connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile_data_connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi_Connection!!.isConnectedOrConnecting)
        {
            toast("terhubung ke wifi")
        }
        else
        {
            if (mobile_data_connection!!.isConnectedOrConnecting)
            {
                toast("terhubung ka koneksi data")
            }
            else
            {
                toast("tidak ada internet")
            }
        }
    }

    fun toast(msg: String)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
