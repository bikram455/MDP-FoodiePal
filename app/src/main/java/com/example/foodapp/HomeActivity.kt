package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.forEach
import androidx.core.view.setPadding
import androidx.viewpager2.widget.ViewPager2
import com.example.foodapp.adapter.ViewPagerAdapter
import com.example.foodapp.data.Recipe
import com.example.foodapp.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var tablayout: TabLayout
    private lateinit var  viewPager: ViewPager2
    private lateinit var fab: FloatingActionButton
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabs()
        onFabAction()
    }

    private fun onFabAction() {
        binding.fab.setOnClickListener{
            val currentFragment = supportFragmentManager.findFragmentByTag("f${binding.viewPager.currentItem}")
            if (currentFragment is RecipesFragment) {
                showAddRecipe(currentFragment)
            } else if(currentFragment is BlogFragment) {
                showAddBlog(currentFragment)
            } else if(currentFragment is AboutMeFragment) {
                showAddAbout(currentFragment)
            }
        }
    }

    private fun showAddAbout(aboutMe: AboutMeFragment) {
        val builder = AlertDialog.Builder(this)
        var aboutMeLayout: LinearLayout? = aboutMe.getRelativeLayout()
        builder.setTitle("Add a new info")

        val alertLayout = LinearLayout(this)
        alertLayout.orientation = LinearLayout.VERTICAL

        val field = EditText(this)
        field.hint = "Field"
        alertLayout.addView(field)

        val value = EditText(this)
        value.hint = "Value"
        alertLayout.addView(value)

        builder.setView(alertLayout)

        builder.setPositiveButton("OK") { dialog, _ ->
            val ll = LinearLayout(this)
            ll.orientation = LinearLayout.HORIZONTAL

            val fieldText = TextView(this)
            fieldText.text = field.text.toString()
            fieldText.layoutParams = aboutMe.getLayoutParams(RelativeLayout.BELOW, aboutMe.getLeftElement())
            ll.addView(fieldText)

            val valueText = TextView(this)
            valueText.setPadding(20, 0, 0, 0)
            valueText.text = value.text.toString()
            ll.addView(valueText)

            aboutMeLayout?.addView(ll)

            aboutMe.setLeftRighElement(fieldText, valueText)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showAddRecipe(recipesFragment: RecipesFragment) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a new recipe")

        val alertLayout = LinearLayout(this)
        alertLayout.orientation = LinearLayout.VERTICAL

        val recipeTitle = EditText(this)
        recipeTitle.hint = "Recipe title"
        alertLayout.addView(recipeTitle)

        val recipeIngredients = EditText(this)
        recipeIngredients.hint = "Ingredients"
        recipeIngredients.setLines(5)
        alertLayout.addView(recipeIngredients)

        val recipeInstruction = EditText(this)
        recipeInstruction.hint = "Instructions"
        alertLayout.addView(recipeInstruction)

        builder.setView(alertLayout)

        builder.setPositiveButton("OK") { dialog, _ ->
            val newRecipe = Recipe(recipeTitle.text.toString(), recipeIngredients.text.toString(), recipeInstruction.text.toString())
            recipesFragment.addRecipe(newRecipe)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun showAddBlog(blog: BlogFragment) {
        var linearLayout: LinearLayout? = blog.getlinearLayout()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add a new Blog")

        val editText = EditText(this)
        editText.hint = "Enter message"
        builder.setView(editText)

        builder.setPositiveButton("OK") { dialog, _ ->
            val tv = TextView(this)
            tv.text = editText.text.toString()
            linearLayout?.addView(tv)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun setupTabs() {
        tablayout = binding.tabLayout
        viewPager = binding.viewPager
        fab = binding.fab
        bottomNav = binding.bottomNav

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(RecipesFragment())
        adapter.addFragment(MealPlannerFragment())
        adapter.addFragment(BlogFragment())
        adapter.addFragment(ContactFragment())
        adapter.addFragment(AboutMeFragment())

        viewPager.adapter = adapter
        TabLayoutMediator(tablayout, viewPager) { tab, pos ->
            tab.text = getTabName(adapter.createFragment(pos).javaClass.simpleName)
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position in arrayOf(0, 2, 4)) {
                    fab.visibility = View.VISIBLE
                } else {
                    fab.visibility = View.GONE
                }
            }
        })

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.recipesNav -> viewPager.currentItem = 0
                R.id.mealPlannerNav -> viewPager.currentItem = 1
                R.id.blogsNav -> viewPager.currentItem = 2
            }
            true
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position in arrayOf(0, 1, 2)) {
                    bottomNav.menu.getItem(position).isChecked = true
                } else {
                    bottomNav.menu.forEach {
                        it.isChecked = false
                    }
                }

            }
        })

    }

    private val mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> bottomNav.selectedItemId = R.id.recipesNav
                1 -> bottomNav.selectedItemId = R.id.mealPlannerNav
                2 -> bottomNav.selectedItemId = R.id.blogsNav
            }
        }
    }



    fun getTabName(input: String): String {
        return input
            .replace("Fragment", "")
            .replace(Regex("([a-z])([A-Z])"), "$1 $2")

    }
}