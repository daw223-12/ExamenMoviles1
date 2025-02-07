package com.example.examenmoviles1

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.examenmoviles1.database.DatabaseHelper
import com.example.examenmoviles1.placeholder.PlaceholderContent
import com.example.examenmoviles1.placeholder.PlaceholderContent.PlaceholderItem
import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private lateinit var dbHandler: DatabaseHelper

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHandler = DatabaseHelper(context!!)
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        val items: MutableList<PlaceholderItem> = ArrayList()
        val baresList = dbHandler.getAllBares()

        for (bar in baresList) {
            items.add(PlaceholderItem(bar.id.toString(), bar.nombre, bar.puntuacion.toString()))
        }

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(items)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}