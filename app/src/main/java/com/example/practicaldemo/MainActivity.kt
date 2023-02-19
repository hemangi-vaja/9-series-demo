package com.example.practicaldemo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.practicaldemo.databinding.ActivityMainBinding
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var btAdapter: BluetoothAdapter? = null

    private var bluetoothSocket: BluetoothSocket? = null

    private lateinit var bluetoothAdapter: BTAdapter
    private var btList = ArrayList<BTItems>()

    private val viewModel by viewModels<BTViewModel>()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                100
            )
        }
        viewModel.getbt(this)
         viewModel.btItemList.observe(this, androidx.lifecycle.Observer {
             Log.d("<>", "onCreate: "+it.size)
             if (it != null){
                 btList.addAll(it)
                 initRecyclerView(btList)
             }
             else{
                initBtAdapter()
            }
        })
        initBtAdapter()


    }

    private fun initBtAdapter() {
        btAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!btAdapter!!.isEnabled) {
            val enable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(
                enable,
                101
            )
        }
        setupBTDevice()
    }

    private fun setupBTDevice() {
        val pairedDevice = btAdapter!!.bondedDevices
        if (pairedDevice.size > 0) {
            val btItemsList: MutableList<BTItems> = ArrayList<BTItems>()
            for (device in pairedDevice) {
                btItemsList.add(BTItems(device.name, device.address))
            }
            Log.d("<>", "setupBTDevice: " + btItemsList.size)

            // initRecyclerView(btItemsList)
            viewModel.insert(btItemsList, this)
        }
    }

    private fun initRecyclerView(btItemsList: MutableList<BTItems>) {
        bluetoothAdapter = BTAdapter(this@MainActivity, btItemsList) { data ->
            Toast.makeText(this, data.name, Toast.LENGTH_SHORT).show()
        }
        binding.rvBluetooth.adapter = bluetoothAdapter
        bluetoothAdapter.notifyDataSetChanged()
    }

/*
    private fun connectToDevice(device: BluetoothDevice) {
        runOnUiThread {
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // SPP profile
            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                bluetoothSocket?.connect()
                Log.d("<>", "Bluetooth device connected: ${device.name}")
                // handle connected device
            } catch (e: IOException) {
                Log.e("<>", "Failed to connect to Bluetooth device", e)
            }

            // ConnectThread(device).start()
        }
    }
*/
}
