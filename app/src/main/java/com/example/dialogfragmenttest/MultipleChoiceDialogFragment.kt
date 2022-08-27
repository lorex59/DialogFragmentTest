package com.example.dialogfragmenttest

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import java.util.ArrayList


class MultipleChoiceDialogFragment : DialogFragment() {

    private var result: MutableList<String> = mutableListOf()
        get() = requireArguments().getStringArrayList(ARG) as MutableList<String>

    private val listEmployee = arrayOf<String>("Первый клиент", "Второй", "Третий")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogItem = listEmployee

        val checkBox = listEmployee
            .map { it in result }
            .toBooleanArray()

        return AlertDialog.Builder(requireContext())
            .setTitle("Выбор сотрудников")
            .setMultiChoiceItems(dialogItem, checkBox, null)
            .setPositiveButton("Сохранить") { dialog, _ ->
                val checkerPosition = (dialog as AlertDialog).listView.checkedItemPositions
                for (temp in listEmployee.indices) {
                    checked(checkerPosition[temp], temp)
                }
            }
            .create()
    }

    private fun checked(value: Boolean, position: Int) {
        if (value && listEmployee[position] !in result) result.add(listEmployee[position])
    }

    companion object {
        @JvmStatic
        private val TAG = MultipleChoiceDialogFragment::class.java.simpleName

        @JvmStatic
        private val KET_RESPONSE = "KET_RESPONSE"

        @JvmStatic
        private val ARG = "ARG"

        @JvmStatic
        private val REQUEST_KEY = "$TAG:defaultRequestKey"

        fun show(manager: FragmentManager, list: ArrayList<String>) {
            val dialogFragment = MultipleChoiceDialogFragment()
            dialogFragment.arguments = bundleOf(ARG to list)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener (manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (ArrayList<String>) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner, FragmentResultListener { _, result ->
                result.getStringArrayList(KET_RESPONSE)?.let { listener.invoke(it) }
            })
        }
    }
}