package com.bangkit.eadi.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.eadi.R

class DataHistoryFragment : Fragment() {

    companion object {
        fun newInstance() = DataHistoryFragment()
    }

    private lateinit var viewModel: DataHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_data_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DataHistoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}