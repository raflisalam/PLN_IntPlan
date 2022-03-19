package com.raflisalam.magang.ui.fragment.maros

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raflisalam.magang.R
import com.raflisalam.magang.adapter.ListPelangganAdapter
import com.raflisalam.magang.databinding.FragmentMarosBinding
import com.raflisalam.magang.model.DataPelanggan
import com.raflisalam.magang.ui.fragment.maros.viewmodel.MarosViewModel


class MarosFragment : Fragment() {

    private var _marosFragment: FragmentMarosBinding? = null
    private val binding get() = _marosFragment as FragmentMarosBinding
    private lateinit var adapter: ListPelangganAdapter
    private lateinit var viewModel: MarosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,): View {
        _marosFragment = FragmentMarosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearching()
        setupAdapter()
    }

    private fun setSearching() {
        binding.apply {
            inputSearching.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val query = s.toString()
                    adapter.filter.filter(query)
                }
            })
        }
    }


    private fun setupAdapter() {
        adapter = ListPelangganAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MarosViewModel::class.java)
        viewModel.setDataMaros()
        viewModel.getDataMaros().observe(this, {
            if (it != null) {
                adapter.setListPelanggan(it)
                adapter.notifyDataSetChanged()
                setupRecylerView()
            }
        })
    }

    private fun setupRecylerView() {
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(context)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }
}