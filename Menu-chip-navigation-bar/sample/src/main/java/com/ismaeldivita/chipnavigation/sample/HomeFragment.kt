package com.ismaeldivita.chipnavigation.sample

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment() : Fragment(){

    private var listener: OnFragmentActionsListener? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentActionsListener){
            listener = context
        }
    }

    override fun onDetach(){
        super.onDetach()
        listener = null;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            listener?.MensajeBoton()
        }
        //val shimmer = view.findViewById<View>(R.id.shimmer_layout) as ShimmerLayout
        //shimmer.startShimmerAnimation()
    }

}
