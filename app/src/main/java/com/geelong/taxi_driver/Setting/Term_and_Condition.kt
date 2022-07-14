package com.geelong.taxi_driver.Setting

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast

import com.geelong.taxi_driver.Login.Otp
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.Setting.TermAndCondition.TermAndConditionResource
import com.gogo.gogokull.api.APIUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_term_and_condition.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Term_and_Condition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_and_condition)
        arrow_term_and_condition.setOnClickListener {
            onBackPressed()

        }
        termcondition()
        
    }

    fun termcondition() {


        progress_loader_term.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
/*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*/


        var repassword = APIUtils.getServiceAPI()!!.
        termcondition()
        repassword.enqueue(object : Callback<TermAndConditionResource> {
            override fun onResponse(
                call: Call<TermAndConditionResource>,
                response: Response<TermAndConditionResource>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                          /*  dialog.dismiss()*/
                            progress_loader_term.visibility= View.GONE

                            term_conditon.setText(Html.fromHtml(response.body()!!.data[0].description))

                        } else {
                          /*  dialog.dismiss()*/
                            progress_loader_term.visibility= View.GONE
                            Toast.makeText(
                                this@Term_and_Condition,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                     /*   dialog.dismiss()*/
                        progress_loader_term.visibility= View.GONE
                        Toast.makeText(this@Term_and_Condition, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {

                }


            }

            override fun onFailure(call: Call<TermAndConditionResource>, t: Throwable) {
               /* dialog.dismiss()*/
                progress_loader_term.visibility= View.GONE
                //Toast.makeText(this@Term_and_Condition, t.toString(), Toast.LENGTH_SHORT).show()

                Toast.makeText(this@Term_and_Condition,"Your internate connection problem",Toast.LENGTH_SHORT).show()

            }
        })

    }

}