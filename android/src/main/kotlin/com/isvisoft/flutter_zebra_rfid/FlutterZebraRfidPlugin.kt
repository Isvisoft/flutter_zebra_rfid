package com.isvisoft.flutter_zebra_rfid

import android.util.Log
import androidx.annotation.NonNull
import com.zebra.rfid.api3.TagData
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterZebraRfidPlugin */
class FlutterZebraRfidPlugin : FlutterPlugin, MethodCallHandler,
  RFIDHandler.ResponseHandlerInterface {

  private lateinit var channel: MethodChannel
  private lateinit var rfidHandler: RFIDHandler

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {

    rfidHandler = RFIDHandler()
    rfidHandler.onCreate(this, flutterPluginBinding.applicationContext)

    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_zebra_rfid")
    channel.setMethodCallHandler(this)


  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      Log.d("8888","getPlatformVersion")
      println("8888")
      println("getPlatformVersion")
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun handleTagdata(tagData: Array<TagData>) {
    val sb = StringBuilder()
    for (index in tagData.indices) {
      sb.append(tagData[index].tagID.trimIndent())
    }

    Log.d("8888", sb.toString())
  }

  override fun handleTriggerPress(pressed: Boolean) {
    if (pressed) {
      rfidHandler.performInventory()
    } else {
      rfidHandler.stopInventory()
    }
  }
}
