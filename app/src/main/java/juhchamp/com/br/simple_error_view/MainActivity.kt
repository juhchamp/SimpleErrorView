package juhchamp.com.br.simple_error_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import juhchamp.com.br.simple_error_view_library.ErrorViewStates
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

    override fun onTryAgainButtonClick() {
        // just for test, set error view state to IDLE after 5 seconds
        simpleErrorView.postDelayed({ simpleErrorView.viewState = ErrorViewStates.IDLE }, 5000L)
    }

    override fun onErrorViewShow() {
        calendarView.visibility = INVISIBLE
    }

    override fun onErrorViewHide() {
        calendarView.visibility = VISIBLE
        // just for test, show error view again after 5 seconds
        simpleErrorView.postDelayed({ simpleErrorView.show() }, 5000L)
    }

    //endregion
}
