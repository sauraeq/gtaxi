package com.example.customnavigationdrawerexample

import android.content.Context
import android.content.SharedPreferences

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.R
import com.geelong.user.Util.Constants
import kotlinx.android.synthetic.main.row_nav_drawer.view.*
import java.text.DateFormat.getInstance


class NavigationRVAdapter(private var items: ArrayList<NavigationItemModel>,private var items1: ArrayList<NavigationItemModel>, private var currentPos: Int) :RecyclerView.Adapter<NavigationRVAdapter.NavigationItemViewHolder>() {

    private lateinit var context: Context
    lateinit var shrp: shareprefrences

    class NavigationItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationItemViewHolder {
        context = parent.context
        val navItem = LayoutInflater.from(parent.context).inflate(R.layout.row_nav_drawer, parent, false)
        shrp= shareprefrences(context)
        return NavigationItemViewHolder(navItem)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        var notification_cnt:String=shrp.getStringPreference("count").toString()
        // To highlight the selected Item, show different background color
        if (position == currentPos) {


            holder.itemView.setBackgroundResource(R.drawable.back_corner)
            holder.itemView.navigation_title.setTextColor(Color.BLACK)
           // holder.itemView.navigation_icon.setImageResource(R.drawable.ic_baseline_home_24)
            holder.itemView.navigation_icon.setImageResource(items1[position].icon)
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            holder.itemView.navigation_icon.setImageResource(items[position].icon)
            holder.itemView.navigation_title.setTextColor(Color.BLACK)
        }

        if(position==2){

     try {

                // Toast.makeText(context,notification_cnt.toString(),Toast.LENGTH_LONG).show( )
                if (notification_cnt.toInt() != 0) {

                    holder.itemView.red_notication.visibility=View.VISIBLE
                    holder.itemView.noti_count.visibility=View.VISIBLE
                    holder.itemView.noti_count.setText(notification_cnt)



                } else {
                    holder.itemView.red_notication.visibility=View.GONE
                    holder.itemView.noti_count.visibility=View.GONE
                }


            } catch (e: Exception) {


            }

        }
        else {
            holder.itemView.red_notication.visibility=View.GONE

              holder.itemView.noti_count.visibility=View.GONE


        }


       // holder.itemView.navigation_icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
       // holder.itemView.navigation_title.setTextColor(Color.BLACK)
        //val font = ResourcesCompat.getFont(context, R.font.mycustomfont)
        //holder.itemView.navigation_text.typeface = font
        //holder.itemView.navigation_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.toFloat())

        holder.itemView.navigation_title.text = items[position].title


    }
}