package com.geelong.taxi_driver.RideLtaer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geelong.taxi_driver.Notification.NotificationAdapter
import com.geelong.taxi_driver.Notification.NotificationModel.NotificationResponse
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.RideLtaer.RidemoduleApi.ridelaterResponse
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

class RidelaterAdapter (
    var context: Context,
    var notlist: ArrayList<ridelaterResponse.Data>,
): RecyclerView.Adapter<RidelaterAdapter.ExerciseHodlder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHodlder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.ridelaterlist, parent, false)
        return ExerciseHodlder(view)

    }

    override fun onBindViewHolder(holder: ExerciseHodlder, position: Int) {

        try {






            if (notlist[position].user_photo.equals("")){

            }else{
                Picasso.get()
                    .load(notlist[position].user_photo)
                    .into(holder.profileimage)
            }






            holder.name.text = notlist[position].user_name
            holder.pickup.text = notlist[position].pickup_address
            holder.drop.text = notlist[position].drop_address

           holder.price.text = "$"+notlist[position].amount


        } catch (e: Exception) {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int {

        return notlist.size
    }


    inner class ExerciseHodlder(itemView: View) : RecyclerView.ViewHolder(itemView) {


         var profileimage: CircleImageView
        var name: TextView
          var pickup: TextView
          var drop: TextView



        var price: TextView
       // lateinit var title: TextView


        init {


            profileimage = itemView.findViewById(R.id.u_image)
            name = itemView.findViewById(R.id.u_name)
            pickup = itemView.findViewById(R.id.u_pickup)

            drop = itemView.findViewById(R.id.u_drop)

            price = itemView.findViewById(R.id.u_ammount)


        }
    }



}
