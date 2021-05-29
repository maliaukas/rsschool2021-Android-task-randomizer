package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var min: EditText? = null
    private var max: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    private var listener: ActionListener? = null

    interface ActionListener {
        fun onGenerateButtonPressed(min: Int, max: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        min = view.findViewById(R.id.min_value)
        max = view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = String.format(getString(R.string.previous_result), result)

        generateButton?.setOnClickListener {
            val minStr = min?.text.toString()
            val maxStr = max?.text.toString()

            if (minStr.isBlank() || maxStr.isBlank()) {
                showSnackBar(view, "Please fill all the fields!")
                return@setOnClickListener
            }

            val minValue: Long? = minStr.toLongOrNull()
            val maxValue: Long? = maxStr.toLongOrNull()

            if (minValue == null) {
                showSnackBar(view, "Cannot parse min value!")
                return@setOnClickListener
            }

            if (maxValue == null) {
                showSnackBar(view, "Cannot parse max value!")
                return@setOnClickListener
            }

            if (minValue > Int.MAX_VALUE) {
                showSnackBar(view, "Entered min value is too big!")
                return@setOnClickListener
            }

            if (maxValue > Int.MAX_VALUE) {
                showSnackBar(view, "Entered max value is too big!")
                return@setOnClickListener
            }

            if (minValue > maxValue) {
                showSnackBar(view, "Max shouldn't be less than min!")
                return@setOnClickListener
            }

            listener?.onGenerateButtonPressed(minValue.toInt(), maxValue.toInt())
        }
    }

    private fun showSnackBar(v: View, m: String) {
        Snackbar.make(
            v,
            m,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}