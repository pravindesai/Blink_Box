package com.realy.blink_box

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.realy.blink_box.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var binding: FragmentFirstBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btn?.setOnClickListener {
            val number = binding?.numberInput?.text.toString().toIntOrNull() ?:0

            if (number<4 || number>10){
                Toast.makeText(requireContext(), "Please enter valid number between 4 and 10 !", Toast.LENGTH_LONG).show()
            }else{
                val action = bundleOf(
                    "number" to number
                )

                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, action)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}