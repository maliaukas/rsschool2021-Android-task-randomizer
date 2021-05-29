package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import kotlin.random.Random
import kotlin.random.nextInt

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    private var listener: ActionListener? = null

    interface ActionListener {
        fun onBackButtonPressed(previousNumber: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        val generatedValue = generate(min, max)
        result?.text = generatedValue.toString()

        fun backPressed() {
            listener?.onBackButtonPressed(generatedValue)
        }

        backButton?.setOnClickListener { backPressed() }

        // https://developer.android.com/guide/navigation/navigation-custom-back#implement_custom_back_navigation
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            backPressed()
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return Random.nextInt(min..max)
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()

            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)

            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }
}