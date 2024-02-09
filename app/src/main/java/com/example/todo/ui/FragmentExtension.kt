package com.example.todo.ui

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
fun Fragment.showDialog(
        message: String,
        posActionName: String?=null,
        posActionCallBack: (()->Unit)?=null,
        negActionName: String?=null,
        negActionCallBack: (()->Unit)?=null,
        isCancelable: Boolean = true
):AlertDialog {
    val alertDialogBuilder = AlertDialog.Builder(requireContext())
    alertDialogBuilder.setMessage(message)

    alertDialogBuilder.setPositiveButton(
        posActionName
    ){ dialog, p1 ->
        dialog?.dismiss()
        posActionCallBack?.invoke()
    }

    alertDialogBuilder.setNegativeButton(
        negActionName
    ){ dialog, p1 ->
        dialog?.dismiss()
        negActionCallBack?.invoke()
    }

    alertDialogBuilder.setCancelable(isCancelable)
    return alertDialogBuilder.show()
}

