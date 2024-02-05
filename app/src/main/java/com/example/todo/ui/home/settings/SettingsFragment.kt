package com.example.todo.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.todo.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment() {
    lateinit var viewBinding: FragmentSettingsBinding
    var languageItem = arrayListOf("English" , "Arabic")
    var modeItem = arrayListOf("Light" , "Dark")
    lateinit var autoCompleteTextView: AutoCompleteTextView
    lateinit var adapterItems: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}