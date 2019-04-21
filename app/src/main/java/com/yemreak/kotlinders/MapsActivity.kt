package com.yemreak.kotlinders

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MyFuncs {

    private lateinit var mMap: GoogleMap

    // HANDLER - MANAGER yapısı
    var locationManager: LocationManager? = null
    var locationListener: LocationListener? = null

    var isRun = false

    val CODE_REQUEST_LOC = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) { // Haritalar hazır olduğunda çalışan fonksiyon
        mMap = googleMap // Haritanın alınması

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager // cast etmemiz gerekmekte
        locationListener = object : LocationListener { // Bu kısım için konum erişimi almamız gerekmekte

            override fun onLocationChanged(p0: Location?) { // Kullanıcının yeri değiştiğinde
                if (p0 != null) {
                    mMap.clear() // Map'i temizliyoruz.

                    val userLoc = LatLng(p0.latitude, p0.longitude) // Enlem ve boylam değerlerini alma
                    mMap.addMarker(MarkerOptions().position(userLoc).title("Konumun burası :)")) // Konumu ve başlığı belirterek işaretliyoruz.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 15f))


                    // Adres değişikliği alma
                    val geocoder = Geocoder(applicationContext, Locale.getDefault()) // Adresi varsayılan yerine göre alsın.

                    try {
                        val adressList = geocoder.getFromLocation(p0.latitude, p0.longitude, 1) // adresleri alıyoruz (1 tane)
                        if (adressList != null && adressList.isNotEmpty()) {

                        }

                    } catch (e: Exception) {
                        showMessage(applicationContext, e.toString())
                    }
                }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {
            }

        }

        // İzin işlemleri
        if (ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { // İzin yoksa
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), CODE_REQUEST_LOC) // ActivityCompat'dan çağırılıyor
        } else { // İzin varsa
            updateLastLocationAndMark()
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            CODE_REQUEST_LOC -> {
                if (grantResults.isNotEmpty()) {
                    val isAllow = ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED
                    if (isAllow) { // izin verildiyse
                        updateLastLocationAndMark()
                    } else {
                        showMessage(applicationContext, R.string.msg_per_need_location)

                        val handler = Handler()
                        val runnable = object : Runnable {
                            override fun run() {
                                isRun = true

                                ActivityCompat.requestPermissions(this@MapsActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), CODE_REQUEST_LOC)
                                handler.postDelayed(this, 10000)
                            }
                        }

                        if (!isAllow && !isRun)
                            handler.post(runnable)
                        else if (isAllow)
                            handler.removeCallbacks(runnable)
                    }
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun mapDefaults() {
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0) // Koordinatların girilmesi
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")) // Marker koyma (kırmızı buton)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)) // Kamerayı koordinatlarıma götürme
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f)) // Yukarıdakinin aynısı ama daha yakın hali
    }

    @SuppressLint("MissingPermission")
    private fun updateLastLocationAndMark() {
        if (locationManager != null) {
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 2f, locationListener) // 0 - 0f yaparsak çok pil yer.

            val lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER) // Son konumu alma

            if (lastLocation != null) {
                val userLocation = LatLng(lastLocation.latitude, lastLocation.longitude) // Konumu enlem boylam şekline çevirme
                mMap.addMarker(MarkerOptions().position(userLocation).title("Konum burası :)")) // Konuma işaret ekleme
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15f)) // Konumu yakından gösterme

                showMessage(applicationContext, "Konum değişti")
            }
        }
    }
}
