package com.example.todo.ui.home.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment: Fragment() {
    lateinit var viewBinding: FragmentSettingsBinding


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

        val languageItem = arrayListOf("English", "Arabic")
        val modeItem = listOf("Light", "Dark")

        val autoCompleteLanguage: AutoCompleteTextView = viewBinding.languageTv
        val autoCompleteMode: AutoCompleteTextView = viewBinding.modeTv

        val adapterLanguage = ArrayAdapter(requireContext(), R.layout.list_item, languageItem)
        val adapterMode = ArrayAdapter(requireContext(), R.layout.list_item, modeItem)

        autoCompleteLanguage.setAdapter(adapterLanguage)
        autoCompleteMode.setAdapter(adapterMode)

        autoCompleteLanguage.setOnItemClickListener { adapterView, view, position, id ->
            val selectedText = adapterView.getItemAtPosition(position)
            if (selectedText == "Arabic") {
                setLocale("ar")
            } else {
                setLocale("en")
            }
        }

        autoCompleteMode.setOnItemClickListener { adapterView, view, position, id ->
            val selectedText = adapterView.getItemAtPosition(position)
            if (selectedText == "Dark") {
                viewBinding.modeTil.startIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_dark_mode
                )
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Set the default drawable for other modes
                viewBinding.modeTil.startIconDrawable = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_light_mode
                )
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }

    fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        requireContext().resources.updateConfiguration(
            configuration,
            requireContext().resources.displayMetrics
        )

        requireActivity().recreate()
    }
}