package com.raflisalam.magang.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.raflisalam.magang.databinding.ListPelangganBinding
import com.raflisalam.magang.model.spreadsheet.DataPelanggan
import com.raflisalam.magang.ui.detail.DetailPelangganActivity

class ListPelangganAdapter: RecyclerView.Adapter<ListPelangganAdapter.ViewHolder>(), Filterable{

    private lateinit var listPelanggan: MutableList<DataPelanggan>
    private lateinit var listPelangganFilter: List<DataPelanggan>

    fun setListPelanggan(data: MutableList<DataPelanggan>) {
        this.listPelanggan = data
        listPelangganFilter = ArrayList(data)
    }

    class ViewHolder(val binding: ListPelangganBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListPelangganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listPelanggan[position]) {
                binding.apply {
                    tvIdpel.text = idpel
                    tvUnit.text = unit
                    tvAlamat.text = alamat
                    tvNoHp.text = noHp
                    tvDaya.text = daya
                    tvTarif.text = tarif
                    tvIndikasi.text = indikasi
                    tvStandRek.text = stanRek
                    tvNama.text = nama
                    tvKwhBln1.text = kwhBln1
                    tvKwhBln2.text = kwhBln2
                    tvKwhBln3.text = kwhBln3
                    tagLokasi.text = taggingLokasi
                    imgFotoMeter.text = fotoMeter
                    imgFotoRmh.text = fotoRumah
                    holder.itemView.setOnClickListener {
                        val intent = Intent(holder.itemView.context, DetailPelangganActivity::class.java)
                        intent.putExtra(DetailPelangganActivity.DETAIL_DATA, listPelanggan[position])
                        holder.itemView.context.startActivity(intent)
                    }
                }
            }
        }
     }

    override fun getItemCount(): Int {
        return listPelanggan.size
    }

    override fun getFilter(): Filter {
        return setFilter
    }

    private val setFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: MutableList<DataPelanggan> = ArrayList()
            val filterPattern = charSequence.toString().trim { it <= ' ' }
            for (item in listPelangganFilter) {
                if (item.idpel?.contains(filterPattern)!! || item.nama?.contains(filterPattern)!!) {
                    filteredList.add(item)
                }
            }
            val result = FilterResults()
            result.values = filteredList

            return result
         }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            listPelanggan.clear()
            listPelanggan.addAll(results?.values as Collection<DataPelanggan>)
            notifyDataSetChanged()
        }
    }
}
