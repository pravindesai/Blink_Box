package com.realy.blink_box

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.realy.blink_box.databinding.FragmentSecondBinding
import com.realy.blink_box.gridAdapter.BlinkAdapter
import com.realy.blink_box.gridAdapter.BoxItem
import com.realy.blink_box.gridAdapter.IOnItemClicked
import kotlinx.coroutines.*

class SecondFragment : Fragment(), IOnItemClicked {

    private var binding: FragmentSecondBinding? = null
    var number = 0
    var boxesList:MutableList<BoxItem> = mutableListOf()
    var blinkAdapter:BlinkAdapter? = null

    var blinkJob: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSecondBinding.inflate(inflater, container, false)
        number = arguments?.getInt("number")?:0

        var xAxis = 0
        var yAxis = 0

        (0 .. (number*number)-1 ).forEach {index->
            if (yAxis==number){
                xAxis = xAxis+1
                yAxis = 0
            }
            val box = BoxItem(index,false, x = xAxis, y=yAxis)
            boxesList.add(box)
            yAxis = yAxis+1
        }

        Log.e("***", boxesList.toString())

        blinkAdapter = BlinkAdapter(boxesList, this)

        binding?.gridRv?.layoutManager = GridLayoutManager(getActivity(), number)
        binding?.gridRv?.adapter = blinkAdapter

        return binding?.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onItemClicked(selectedBox: BoxItem) {
        boxesList.forEach { itrBox ->
            itrBox.isSelected = (itrBox.x == selectedBox.x) || (itrBox.y == selectedBox.y)
        }

        val x = selectedBox.x
        val y = selectedBox.y
        val relativePositions = arrayOf(Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1))

        for ((dx, dy) in relativePositions) {
            var newX = x + dx
            var newY = y + dy
            while (newX in 0 until number && newY in 0 until number) {
                val index = newX * number + newY
                val diagonalBox = boxesList.find { it.itemNumber == index }
                diagonalBox?.let {
                    diagonalBox.isSelected = true
                }
                newX += dx
                newY += dy
            }
        }
        blinkAdapter?.notifyDataSetChanged()

        blinkJob?.cancel()
        blinkJob = lifecycleScope.launch {
            delay(3000)
            boxesList.forEach { it.isSelected = false }
            withContext(Dispatchers.Main){
                blinkAdapter?.notifyDataSetChanged()
            }
        }

    }
}