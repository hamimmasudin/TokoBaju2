package com.example.toko.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.toko.Application.ShopApplication
import com.example.toko.R
import com.example.toko.databinding.FragmentSecondBinding
import com.example.toko.model.Shop

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val shopViewModel: ShopViewModel by viewModels {
        ShopViewModelFactory((applicationContext as ShopApplication).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var shop:Shop? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext=requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        shop=args.shop

        if (shop != null){
            binding.delete.visibility=View.VISIBLE
            binding.simpan.text="Ubah"
            binding.namesosistext.setText(shop?.name)
            binding.editprice.setText(shop?.price)
            binding.notetextedit.setText(shop?.note)
        }
        val name = binding.namesosistext.text
        val price = binding.editprice.text
        val note = binding.notetextedit.text
        binding.simpan.setOnClickListener {

            if (name.isEmpty()){
                Toast.makeText(context,"Nama Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
            }else if (price.isEmpty()){
                Toast.makeText(context,"Harga Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
            }else if (note.isEmpty()){
                Toast.makeText(context,"Keterangan Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
            }
            else{
                if (shop==null){
                    val shop =Shop(0,name.toString(),price.toString(),note.toString())
                    shopViewModel.insert(shop)
                }else{
                    val shop =Shop(shop?.id!!,name.toString(),price.toString(),note.toString())
                    shopViewModel.update(shop)
                }

                findNavController().popBackStack()//untuk dissmiss halaman ini

            }


        }

        binding.delete.setOnClickListener {
            shop?.let { shopViewModel.delete(it) }
            findNavController().popBackStack()//untuk dissmiss halaman ini
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}