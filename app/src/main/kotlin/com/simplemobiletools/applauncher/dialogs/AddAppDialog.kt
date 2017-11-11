package com.simplemobiletools.applauncher.dialogs

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.simplemobiletools.applauncher.R
import com.simplemobiletools.applauncher.adapters.RecyclerDialogAdapter
import com.simplemobiletools.applauncher.models.AppLauncher
import kotlinx.android.synthetic.main.dialog_pick_launcher.view.*
import java.util.*

class AddAppDialog : DialogFragment() {
    companion object {
        lateinit var launchers: ArrayList<AppLauncher>
        var callback: AddLaunchersInterface? = null

        fun newInstance(cb: AddLaunchersInterface, appLaunchers: ArrayList<AppLauncher>): AddAppDialog {
            callback = cb
            launchers = appLaunchers
            return AddAppDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val recyclerView = View.inflate(activity, R.layout.dialog_pick_launcher, null)
        recyclerView.launchers_holder.adapter = RecyclerDialogAdapter(activity, launchers)
        builder.setView(recyclerView)

        builder.setPositiveButton(R.string.ok, { dialogInterface, i ->
            val selectedApps = launchers.filter { it.isChecked } as ArrayList<AppLauncher>
            callback?.addLaunchers(selectedApps)
        })

        builder.setNegativeButton(R.string.cancel, { dialogInterface, i ->
            callback?.updateLaunchers()
        })
        return builder.create()
    }

    interface AddLaunchersInterface {
        fun addLaunchers(launchers: ArrayList<AppLauncher>)

        fun updateLaunchers()
    }
}
