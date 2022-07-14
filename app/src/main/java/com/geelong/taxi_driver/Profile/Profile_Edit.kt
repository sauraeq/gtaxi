package com.geelong.taxi_driver.Profile

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.geelong.taxi_driver.LocalSaved.shareprefrences
import com.geelong.taxi_driver.Profile.ProfileModel.ProfileResponse
import com.geelong.taxi_driver.Profile.ProfileModel.UpdateResouce
import com.geelong.taxi_driver.R
import com.gogo.gogokull.api.APIUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.activity_profile_edit.rl_icon
import kotlinx.android.synthetic.main.activity_profile_overview.*
import kotlinx.android.synthetic.main.activity_term_and_condition.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class Profile_Edit : AppCompatActivity() {

    lateinit var imageView: ImageView

    private val pickImage = 100
    private var imageUri: Uri? = null

    lateinit var shrp: shareprefrences
    lateinit var imagepath:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)








        val languages = resources.getStringArray(R.array.programming_languages)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, languages)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.edit_gender)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)





        shrp= shareprefrences(this)

        try {
            Picasso.get()
                .load(shrp.getStringPreference("profile_photo").toString())
                .into(profile_upload_img)

            firstname_edit_mobile.setText(shrp.getStringPreference("mobile").toString())
        }catch (e:Exception){
        //    firstname_edit_mobile.setText(shrp.getStringPreference("mobile"))

        }


   /*     val emailToText: String = edit_email.getText().toString()


        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(this@Profile_Edit, "Email Verified !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this@Profile_Edit, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
        }*/







        edit_save.setOnClickListener {


            val emailToText: String = edit_email.getText().toString()


            if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
               // Toast.makeText(this@Profile_Edit, "Email Verified !", Toast.LENGTH_SHORT).show();

                profife_edit(firstname_edit_text.text.toString(),edit_email.text.toString(),edit_address.text.toString(),edit_gender.text.toString())

            } else {
                Toast.makeText(this@Profile_Edit, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
            }




        }





        back.setOnClickListener {
            onBackPressed()
            finish()


        }


        rl_icon.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT

            // pass the constant to compare it
            // with the returned requestCode

            // pass the constant to compare it
            // with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), pickImage)


        }


/*


        val languages = resources.getStringArray(R.array.programming_languages)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, languages)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.edit_gender)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
*/





    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == pickImage) {
                // Get the url of the image from data
                val selectedImageUri = data?.data
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    profile_upload_img.setImageURI(selectedImageUri)
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                    bitmapToFile(bitmap)

                }
            }
        }
    }

    private fun bitmapToFile(bitmap: Bitmap): Uri {


        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        imagepath = file.absolutePath
        editprofileimg()
        return Uri.parse(file.absolutePath)


    }



    override fun onResume() {
        super.onResume()

        // Fetching the stored data
        // from the SharedPreference

        firstname_edit_text.setText((shrp.getStringPreference("name")))
        edit_email.setText((shrp.getStringPreference("email")))
        edit_address.setText((shrp.getStringPreference("address")))
        edit_gender.setText((shrp.getStringPreference("gender")))


        // Setting the fetched data
        // in the EditTexts

    }

    private fun profife_edit(name:String,email:String,address:String,gender:String) {
        progress_loader_edit_profile.visibility= View.VISIBLE

        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
        //*{"emailid":"anuj@gmail.com","securityPin":"1234","securityPinCnf":"1234"}*//*
        var stringStringHashMap = HashMap<String, String>()
        stringStringHashMap.put("name", name)
        stringStringHashMap.put("email", email)
        stringStringHashMap.put("address", address)
        stringStringHashMap.put("gender", gender)
        stringStringHashMap.put("customer_id",shrp.getStringPreference("user_id").toString())

        var check = APIUtils.getServiceAPI()!!.profileedit(stringStringHashMap)
        check.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                try {
                    if (response.code() == 200) {
                        if (response.body()!!.success == "true") {




                            shrp.setStringPreference("name",response.body()!!.data.name)
                            shrp.setStringPreference("email",response.body()!!.data.email)
                            shrp.setStringPreference("address",response.body()!!.data.address)
                            shrp.setStringPreference("gender",response.body()!!.data.gender)

                            val intent = Intent(this@Profile_Edit, Profile_Overview::class.java)







                            startActivity(intent)
                           /* dialog.dismiss()*/
                            progress_loader_edit_profile.visibility= View.GONE
                            Toast.makeText(this@Profile_Edit, response.body()!!.msg, Toast.LENGTH_SHORT)
                                .show()


                        } else {
                         /*   dialog.dismiss()*/
                            progress_loader_edit_profile.visibility= View.GONE
                            Toast.makeText(this@Profile_Edit, response.body()!!.msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                  /*      dialog.dismiss()*/
                        progress_loader_edit_profile.visibility= View.GONE
                        Toast.makeText(this@Profile_Edit, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {

                }


            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
               /* dialog.dismiss()*/
                progress_loader_edit_profile.visibility= View.GONE
             //   Toast.makeText(this@Profile_Edit, t.toString(), Toast.LENGTH_SHORT).show()

               Toast.makeText(this@Profile_Edit,"something wrong",Toast.LENGTH_SHORT).show()
            }
        })

    }





    //updtae APi
    private  fun editprofileimg() {
        progress_loader_edit_profile.visibility= View.VISIBLE
        //val dialog: ProgressDialog = ProgressDialog.show(this, null, "Please Wait")
            val multiPartRepeatString = "application/image"

            var facility_image: MultipartBody.Part? = null


            val file1 = File(imagepath)

            val signPicBody1 = file1.asRequestBody(multiPartRepeatString.toMediaTypeOrNull())
            facility_image =
                MultipartBody.Part.createFormData("profile_photo", file1.name, signPicBody1)

            val driver_id: RequestBody =
                shrp.getStringPreference("user_id")?.toRequestBody(MultipartBody.FORM)!!


            var AddExerciseCall: Call<UpdateResouce> = APIUtils.getServiceAPI()!!.profileupdate(
                driver_id,
                facility_image,

            )

            AddExerciseCall.enqueue(object : retrofit2.Callback<UpdateResouce> {
                override fun onResponse(
                    call: Call<UpdateResouce>,
                    response: Response<UpdateResouce>
                ) {

                    try {
                        if (response.code() == 200) {

                            if (response.body()!!.success.equals("true")) {
                              /*  dialog.dismiss()*/
                                progress_loader_edit_profile.visibility= View.GONE
                                shrp.setStringPreference("profile_photo",response.body()!!.image)
                                Toast.makeText(this@Profile_Edit, response.body()!!.msg, Toast.LENGTH_SHORT)
                                    .show()

                            } else {
                              /*  dialog.dismiss()*/
                                progress_loader_edit_profile.visibility= View.GONE
                                Toast.makeText(this@Profile_Edit, response.body()!!.msg, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    } catch (e: Exception) {


                    }
                }

                override fun onFailure(call: Call<UpdateResouce>, t: Throwable) {
                   /* dialog.dismiss()*/
                    progress_loader_edit_profile.visibility= View.GONE
                   /*
                    Utils.showToast(this@Profile_Edit, t.message.toString())
                    startActivity(Intent(this@Profile_Edit, HomeActivity::class.java))
                    finishAffinity()*/
                   // Toast.makeText(this@Profile_Edit, t.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@Profile_Edit, "Your internate connection problem",Toast.LENGTH_SHORT).show()
                }

            })


        }


}
