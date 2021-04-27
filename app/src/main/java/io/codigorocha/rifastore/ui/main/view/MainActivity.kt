package io.codigorocha.rifastore.ui.main.view

import android.os.Bundle
import io.codigorocha.rifastore.BaseActivity
import io.codigorocha.rifastore.R


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        val user = FirebaseAuth.getInstance().currentUser
//
//        Handler().postDelayed({
//            if (user != null) {
//                val mainIntent = Intent(this, MainActivity::class.java)
//                startActivity(mainIntent)
//                finish()
//            } else {
//                val signInIntent = Intent(this, SignInActivity::class.java)
//                startActivity(signInIntent)
//                finish()
//            }
//        }, 2000)
    }

    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

}