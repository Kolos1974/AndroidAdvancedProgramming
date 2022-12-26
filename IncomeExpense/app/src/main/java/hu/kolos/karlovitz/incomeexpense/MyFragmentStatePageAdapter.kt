package hu.kolos.karlovitz.incomeexpense

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


// class MyFragmentStatePageAdapter(activity: AppCompatActivity, val itemsCount: Int) :
class MyFragmentStatePageAdapter(activity: FragmentActivity, val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            FragmentPageIncome.newInstance()
        } else {
            FragmentPageExpense.newInstance()
        }
    }

}

