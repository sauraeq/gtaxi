package com.geelong.taxi_driver.Notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geelong.taxi_driver.History.HistoryModel.HistoryResponse
import com.geelong.taxi_driver.Notification.NotificationModel.NotificationResponse
import com.geelong.taxi_driver.R


import java.lang.Exception

class NotificationAdapter (
    var context:Context,
    var notlist: ArrayList<NotificationResponse.Data>,
): RecyclerView.Adapter<NotificationAdapter.ExerciseHodlder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHodlder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.notification_list, parent, false)
        return ExerciseHodlder(view)

    }

        override fun onBindViewHolder(holder: ExerciseHodlder, position: Int) {

            try {

                holder.title.text = notlist[position].title
                holder.created_date.text = notlist[position].created_date
                holder.description.text = notlist[position].description

            } catch (e: Exception) {

                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            }


        }

        override fun getItemCount(): Int {

            return notlist!!.size
        }


        inner class ExerciseHodlder(itemView: View) : RecyclerView.ViewHolder(itemView) {


            lateinit var created_date: TextView
            lateinit var description: TextView
            lateinit var title: TextView


            init {


                created_date = itemView.findViewById(R.id.u_created)
                description = itemView.findViewById(R.id.u_desciption)
                title = itemView.findViewById(R.id.ut_title)


            }
        }
    }











