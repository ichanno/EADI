package com.bangkit.eadi.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.eadi.R

class HistoryDetailFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryDetailFragment()
    }

    private lateinit var viewModel: HistoryDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}