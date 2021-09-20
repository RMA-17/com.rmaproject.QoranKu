package com.rmaproject.myqoran.ui.quran.read.surah

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rmaproject.myqoran.R
import com.rmaproject.myqoran.data.QuranDatabase
import com.rmaproject.myqoran.databinding.FragmentReadQuranBinding
import com.rmaproject.myqoran.ui.home.QuranViewModel

class FragmentQuranRead: Fragment(R.layout.fragment_read_quran) {

    val binding:FragmentReadQuranBinding by viewBinding()
    private val viewModel:QuranViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val surahNumber = arguments?.getInt(KEY_SURAH_NUMBER) ?: 1
        val jozzNumber = arguments?.getInt(KEY_JOZZ_NUMBER) ?: 1
        val pageNumber = arguments?.getInt(KEY_PAGE_NUMBER) ?: 1


        viewModel.getPositionTab().observe(viewLifecycleOwner, { tabPos ->
            viewModel.getTotalAyahList().observe(viewLifecycleOwner, { totalAyah ->
                when (tabPos){
                    TAB_SURAH -> {
                        getAyahFromSurahNumber(surahNumber, totalAyah)
                    }
                    TAB_JUZ -> {
                        getAyahFromJozzNumber(jozzNumber, totalAyah)
                    }
                    TAB_PAGE -> {
                        getAyahFromPageNumber(pageNumber, totalAyah)
                    }
                }

            })

        })

    }

    //Untuk mengakses sesuatu yang ada di folder res, butuh context
    private fun getAyahFromSurahNumber(surahNumber:Int, totalAyah:List<Int>){
        //Surah
            val database = QuranDatabase.getInstance(requireContext())
            val quranDao = database.quranDao()
            quranDao.readQuranBySurah(surahNumber).asLiveData().observe(viewLifecycleOwner, Observer { listQuran ->
                val adapter = FragmentQuranReadAdapter(listQuran, totalAyah)
                binding.RecyclerVIew.adapter = adapter
                binding.RecyclerVIew.layoutManager = LinearLayoutManager(context)
                adapter.copyOnclickListener = { quran, position ->
                    val text:String = "${quran.TextQuran}" +
                            "\n" +
                            "\n" +
                            "${quran.translation}" +
                            "\n" +
                            "\n" +
                            "QS. Surah ${quran.SurahName_en}: ${quran.AyahNumber}"
                    val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Copy ayah to Clipboard", text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(requireContext(), "Ayat berhasil disalin", Toast.LENGTH_SHORT).show()
                }
                adapter.shareOnclickListener = { quran, position ->
                    val text:String = "${quran.TextQuran}" +
                            "\n" +
                            "\n" +
                            "${quran.translation}" +
                            "\n" +
                            "\n" +
                            "QS. Surah ${quran.SurahName_en}: ${quran.AyahNumber}"
                    //ShareCompat digunakan untuk meng-share content
                    //Jika dalam activity, penulisannya begini: val share:Intent = ShareCompat.IntentBuilder(Context)
                    //Jika dalam fragment seperti ini:
                    ShareCompat.IntentBuilder(requireContext())
                        .setText(text)
                        .setType("text/plain")
                        .setChooserTitle("Judul")
                        .startChooser()

                }
            })
    }

    private fun getAyahFromJozzNumber(jozzNumber:Int, totalAyah:List<Int>){
        //Jozz
            val database = QuranDatabase.getInstance(requireContext())
            val quranDao = database.quranDao()
            quranDao.readQuranByJozz(jozzNumber).asLiveData().observe(viewLifecycleOwner, Observer { listQuran ->
                val adapter = FragmentQuranReadAdapter(listQuran, totalAyah)
                binding.RecyclerVIew.adapter = adapter
                binding.RecyclerVIew.layoutManager = LinearLayoutManager(context)

                adapter.copyOnclickListener = { quran, position ->
                    val text:String = "${quran.TextQuran}" +
                            "\n" +
                            "\n" +
                            "${quran.translation}" +
                            "\n" +
                            "\n" +
                            "QS. Surah ${quran.SurahName_en}: ${quran.AyahNumber}"
                    val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Copy ayah to Clipboard", text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(requireContext(), "Ayat berhasil disalin", Toast.LENGTH_SHORT).show()

                }
                adapter.shareOnclickListener = { quran, position ->
                    val text:String = "${quran.TextQuran}" +
                            "\n" +
                            "\n" +
                            "${quran.translation}" +
                            "\n" +
                            "\n" +
                            "QS. Surah ${quran.SurahName_en}: ${quran.AyahNumber}"
                    //ShareCompat digunakan untuk meng-share content
                    //Jika dalam activity, penulisannya begini: val share:Intent = ShareCompat.IntentBuilder(Context)
                    //Jika dalam fragment seperti ini:
                    ShareCompat.IntentBuilder(requireContext())
                        .setText(text)
                        .setType("text/plain")
                        .setChooserTitle("Judul")
                        .startChooser()

                }

            })
    }

    private fun getAyahFromPageNumber(pageNumber:Int, totalAyah: List<Int>){
            val database = QuranDatabase.getInstance(requireContext())
            val quranDao = database.quranDao()
            quranDao.readQUranByPage(pageNumber).asLiveData().observe(viewLifecycleOwner, Observer { listQuran ->
                val adapter = FragmentQuranReadAdapter(listQuran, totalAyah)
                binding.RecyclerVIew.adapter = adapter
                binding.RecyclerVIew.layoutManager = LinearLayoutManager(context)

                adapter.copyOnclickListener = { quran, position ->
                    val text:String = "${quran.TextQuran}" +
                            "\n" +
                            "\n" +
                            "${quran.translation}" +
                            "\n" +
                            "\n" +
                            "QS. Surah ${quran.SurahName_en}: ${quran.AyahNumber}"
                    val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Copy ayah to Clipboard", text)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(requireContext(), "Ayat berhasil disalin", Toast.LENGTH_SHORT).show()

                }
                adapter.shareOnclickListener = { quran, position ->
                    val text:String = "${quran.TextQuran}" +
                            "\n" +
                            "\n" +
                            "${quran.translation}" +
                            "\n" +
                            "\n" +
                            "QS. Surah ${quran.SurahName_en}: ${quran.AyahNumber}"
                    //ShareCompat digunakan untuk meng-share content
                    //Jika dalam activity, penulisannya begini: val share:Intent = ShareCompat.IntentBuilder(Context)
                    //Jika dalam fragment seperti ini:
                    ShareCompat.IntentBuilder(requireContext())
                        .setText(text)
                        .setType("text/plain")
                        .setChooserTitle("Judul")
                        .startChooser()

                }
            })
    }

    //Companion object berguna untuk menyetor sebuah const value, dan bisa digunakan di tempat lain
    companion object {
        const val KEY_SURAH_NUMBER = "SURAHNUMBERDISPLAY"
        const val KEY_JOZZ_NUMBER = "JOZZNUMBER"
        const val KEY_PAGE_NUMBER = "PAGENUMBER"
        const val TAB_SURAH = 0
        const val TAB_JUZ = 1
        const val TAB_PAGE = 2
    }
}