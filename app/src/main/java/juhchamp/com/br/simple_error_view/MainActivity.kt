package juhchamp.com.br.simple_error_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import juhchamp.com.br.simple_error_view_library.SimpleErrorViewListeners
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SimpleErrorViewListeners {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simpleErrorView.setLabel("Houve um erro: [ 404 ]")
        simpleErrorView.setButtonLabel("Tentar novamente")
        simpleErrorView.setViewListeners(this)
        // just for test, show error view after 5 seconds
        simpleErrorView.postDelayed({ simpleErrorView.show() }, 5000L)
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleErrorView.destroy()
    }

    //region SimpleErrorViewListeners

    override fun onErrorViewTryAgain() {
        simpleErrorView.load()
    }

    override fun onErrorViewShow() {
        textClock.visibility = INVISIBLE
    }

    override fun onErrorViewHide() {
        textClock.visibility = VISIBLE
        // just for test, show error view after 5 seconds again
        simpleErrorView.postDelayed({ simpleErrorView.show() }, 5000L)
    }

    override fun onErrorViewStartLoading() {
        // just for test, call unload after 5 seconds
        simpleErrorView.postDelayed({ simpleErrorView.unload() }, 5000L)
    }

    override fun onErrorViewFinishLoading() {
        // just for test, hide view when loading finished
        simpleErrorView.hide()
    }

    //endregion
}
