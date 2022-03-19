package com.raflisalam.magang.ui.fragment.daya

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
import com.raflisalam.magang.adapter.ListPelangganAdapter
import com.raflisalam.magang.databinding.FragmentDayaBinding
import com.raflisalam.magang.model.DataPelanggan
import com.raflisalam.magang.ui.fragment.daya.viewmodel.DayaViewModel
import com.raflisalam.magang.ui.fragment.karebosi.viewmodel.KarebosiViewModel


class DayaFragment : Fragment() {

    private var _dayaFragment: FragmentDayaBinding? = null
    private val binding get() = _dayaFragment as FragmentDayaBinding
    private lateinit var viewModel: DayaViewModel
    private lateinit var adapter: ListPelangganAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _dayaFragment = FragmentDayaBinding.inflate(inflater, container, false)
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
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DayaViewModel::class.java)
        viewModel.setDataDaya()
        viewModel.getDataDaya().observe(this, {
            if (it != null) {
                adapter.setListPelanggan(it)
                adapter.notifyDataSetChanged()
                setupRecyclerView()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(context)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }
}