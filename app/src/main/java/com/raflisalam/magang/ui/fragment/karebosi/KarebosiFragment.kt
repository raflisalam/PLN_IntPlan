package com.raflisalam.magang.ui.fragment.karebosi

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
import com.raflisalam.magang.databinding.FragmentKarebosiBinding
import com.raflisalam.magang.ui.fragment.karebosi.viewmodel.KarebosiViewModel

class KarebosiFragment : Fragment() {

    private var _karebosiFragment: FragmentKarebosiBinding? = null
    private val binding get() = _karebosiFragment as FragmentKarebosiBinding
    private lateinit var viewModel: KarebosiViewModel
    private lateinit var adapter: ListPelangganAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _karebosiFragment = FragmentKarebosiBinding.inflate(inflater, container, false)
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
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(KarebosiViewModel::class.java)
        viewModel.setDataKarebosi()
        viewModel.getDataKarebosi().observe(this, {
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