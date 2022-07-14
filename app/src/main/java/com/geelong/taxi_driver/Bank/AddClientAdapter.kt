package com.rehablab.librarys.adpater

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.geelong.taxi_driver.Bank.GetClientResponse
import com.geelong.taxi_driver.R


class AddClientAdapter
    (var context: Context,
     var addclientlist: ArrayList<GetClientResponse.Data>):
    RecyclerView.Adapter<AddClientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddClientAdapter.ViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.bank_card,parent,false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddClientAdapter.ViewHolder, position: Int) {
        //holder.txt_user_list.text=addclientlist[position].last_name+","+addclientlist[position].first_name+"("+addclientlist[position].dob+")"



    }

    override fun getItemCount(): Int {
        return addclientlist.size
    }

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
      lateinit var txt_user_list:TextView
      init {
          txt_user_list = itemView.findViewById(R.id.write_edit)
      }



        fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            var item: GetClientResponse.Data = addclientlist!!.get(p2)
            Toast.makeText(context,item.toString(), Toast.LENGTH_LONG).show()
        }
    }
}