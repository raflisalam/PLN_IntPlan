package com.raflisalam.magang.ui.fragment.pangkep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.raflisalam.magang.adapter.ListPelangganAdapter
import com.raflisalam.magang.databinding.FragmentPangkepBinding
import com.raflisalam.magang.ui.fragment.pangkep.viewmodel.PangkepViewModel

class PangkepFragment : Fragment() {

    private var _pangkepFragment: FragmentPangkepBinding? = null
    private val binding get() = _pangkepFragment as FragmentPangkepBinding
    private lateinit var adapter: ListPelangganAdapter
    private lateinit var viewModel: PangkepViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _pangkepFragment = FragmentPangkepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = ListPelangganAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(PangkepViewModel::class.java)
        viewModel.setDataPangkep()
        viewModel.getDataPangkep().observe(this, {
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