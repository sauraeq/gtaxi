package com.geelong.taxi_driver.History

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.geelong.taxi_driver.History.HistoryModel.HistoryResponse
import com.geelong.taxi_driver.History.WeekHistoryModel.WeeklyResponse
import com.geelong.taxi_driver.Notification.NotificationAdapter
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.databinding.EarningListBinding
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception


class WeekelyAdaptor(
    var context:Context,
    var historylist: ArrayList<WeeklyResponse.Data>,
): RecyclerView.Adapter<WeekelyAdaptor.ExerciseHodlder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHodlder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.earning_list,parent,false)
        return ExerciseHodlder(view)


    }

    override fun onBindViewHolder(holder: ExerciseHodlder, position: Int) {


        try {
            val data:String=historylist[position].user_name
            holder.user_name.text=historylist[position].user_name
            holder.amount.text=historylist[position].amount



            holder.time.text=historylist[position].time
            holder.pickup_address.text=historylist[position].pickup_address
            holder.drop_address.text=historylist[position].drop_address
            Picasso.get()
                .load(historylist[position].user_photo)
                .into(holder.profile_icon)

        }catch (e:Exception){

            Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
        }


    }
    override fun getItemCount(): Int {
        return historylist.size
    }




    inner class ExerciseHodlder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var user_name: TextView
        lateinit var amount: TextView
        lateinit var time: TextView
        lateinit var pickup_address: TextView
        lateinit var drop_address: TextView
        lateinit var profile_icon: CircleImageView

        init {
            user_name = itemView.findViewById(R.id.user_name)
            time = itemView.findViewById(R.id.time)
            pickup_address = itemView.findViewById(R.id.pickup_address)
            drop_address = itemView.findViewById(R.id.drop_address)
            amount=itemView.findViewById(R.id.amount)
            profile_icon=itemView.findViewById(R.id.profile_icon)


        }

    }

}

