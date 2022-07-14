package com.geelong.taxi_driver.Setting

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import com.geelong.taxi_driver.R
import com.geelong.taxi_driver.Setting.PrivacyAndPolicy.PrivacyAndPolicyResource
import com.geelong.taxi_driver.Setting.TermAndCondition.TermAndConditionResource
import com.gogo.gogokull.api.APIUtils
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_term_and_condition.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Privacy_Policy : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        arrow_privacy_policy.setOnClickListener {
            onBackPressed()
        }
        policyprivacy()
    }



    fun policyprivacy() {



        progress_loader_privacy.visibility= View.VISIBLE

        // val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
/*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*/


        var repassword = APIUtils.getServiceAPI()!!.
        ploicyprivacy()
        repassword.enqueue(object : Callback<PrivacyAndPolicyResource> {
            override fun onResponse(
                call: Call<PrivacyAndPolicyResource>,
                response: Response<PrivacyAndPolicyResource>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {
                         /*   dialog.dismiss()*/
                            progress_loader_privacy.visibility= View.GONE

                            t_privacy_policy.setText(Html.fromHtml(response.body()!!.data[0].description))

                        } else {
                           /* dialog.dismiss()*/
                            progress_loader_privacy.visibility= View.GONE

                            Toast.makeText(
                                this@Privacy_Policy,
                                response.body()!!.msg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
/*
                        dialog.dismiss()
*/
                        progress_loader_privacy.visibility= View.GONE
                        Toast.makeText(this@Privacy_Policy, response.body()!!.msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {

                }


            }

            override fun onFailure(call: Call<PrivacyAndPolicyResource>, t: Throwable) {
                /*dialog.dismiss()*/
                progress_loader_privacy.visibility= View.GONE
                //Toast.makeText(this@Privacy_Policy, t.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(this@Privacy_Policy,"Your internate connection problem",Toast.LENGTH_SHORT).show()

            } 
        })

    }

}