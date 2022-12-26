package hu.kolos.karlovitz.incomeexpense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hu.kolos.karlovitz.incomeexpense.databinding.FragmentDetailBinding

class FragmentDetail : Fragment() {

    // Ezt az osztályt is generáltattuk!!
    // val args: FragmentDetailArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ////_binding = FragmentDetailBinding.inflate(layoutInflater)
        //setContentView(binding.root)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ////_binding = FragmentDetailBinding.inflate(inflater, container, false)
        ////val view = binding.root

        val rootView = inflater.inflate(R.layout.fragment_detail, container, false)

        // val fragmentStatePageAdapter = MyFragmentStatePageAdapter(this, 2)
        val fragmentStatePageAdapter = MyFragmentStatePageAdapter(requireActivity(), 2)
        val mainViewPager = rootView.findViewById<ViewPager2>(R.id.mainViewPager)
        val tabLayout = rootView.findViewById<TabLayout>(R.id.tabLayout)

        ////binding.mainViewPager.adapter = fragmentStatePageAdapter
        mainViewPager.adapter = fragmentStatePageAdapter


        TabLayoutMediator(tabLayout, mainViewPager) { tab, position ->
            //To get the first name of doppelganger celebrities
            if (position == 0) {
               tab.text = "Income"
            } else {
               tab.text = "Expense"
            }
        }.attach()

//      return view
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // binding.tvData.text = args.person.name
    }

}