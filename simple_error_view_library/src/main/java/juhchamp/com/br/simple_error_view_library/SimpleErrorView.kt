package juhchamp.com.br.simple_error_view_library

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi

enum class ErrorViewStates {
    IDLE,
    LOADING
}

class SimpleErrorView: LinearLayout {

    private var mTextLabel: String? = "Error Label"
    private var mButtonLabel: String? = "Tentar novamente"

    private var errorLabelTv: TextView? = null
    private var tryAgainBtn: Button? = null
    private var progressBar: ProgressBar? = null

    private var listener: SimpleErrorViewListeners? = null
    var viewState: ErrorViewStates = ErrorViewStates.IDLE
        set(value) {
            if (value != field) {
                field = value
                handleState()
            }
        }

    constructor(context: Context?) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr, 0)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init (
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {

        LayoutInflater.from(context)
            .inflate(R.layout.simple_error_view, this, true)

        errorLabelTv = rootView!!.findViewById(R.id.errorText)
        tryAgainBtn = rootView!!.findViewById(R.id.tryAgainButton)
        progressBar = rootView!!.findViewById(R.id.progressBar)

        context!!.theme.obtainStyledAttributes(
                attrs, R.styleable.SimpleErrorView, defStyleAttr, defStyleRes
        ).apply {
            try {
                mTextLabel = getString(R.styleable.SimpleErrorView_label)
                mButtonLabel = getString(R.styleable.SimpleErrorView_buttonLabel)
            } finally {
                recycle()
            }
        }

        errorLabelTv!!.text = mTextLabel
        tryAgainBtn!!.text = mButtonLabel

        tryAgainBtn!!.setOnClickListener {
            /*if (listener == null) {
                throw NullPointerException("SimpleErrorViewListeners can be not a null! " +
                        "Implement the interface: SimpleErrorView.setViewListeners(object)")
            }*/
            viewState = ErrorViewStates.LOADING
            listener?.onTryAgainButtonClick()
        }
    }

    companion object {
        const val mTag = "SimpleErrorView"
    }

    private fun handleState() {
        when (viewState) {
            ErrorViewStates.IDLE -> {
                errorLabelTv!!.visibility = View.VISIBLE
                tryAgainBtn!!.visibility = View.VISIBLE
                progressBar!!.visibility = View.GONE
            }
            ErrorViewStates.LOADING -> {
                errorLabelTv!!.visibility = View.INVISIBLE
                tryAgainBtn!!.visibility = View.INVISIBLE
                progressBar!!.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Destroy view and invalidate.
     */
    fun destroy() {
        this.listener = null
        invalidate()
    }

    /**
     * Set view listeners.
     * @see [SimpleErrorViewListeners]
     */
    fun setViewListeners(listener: SimpleErrorViewListeners) {
        this.listener = listener
    }

    /**
     * Set error text label.
     * @param label [String] text for error.
     */
    fun setLabel(label: String) {
        mTextLabel = label
        errorLabelTv!!.text = mTextLabel
        invalidate()
        requestLayout()
    }

    /**
     * Set try again button text label.
     * @param label [String] text for the button.
     */
    fun setButtonLabel(label: String) {
        mButtonLabel = label
        tryAgainBtn!!.text = mButtonLabel
        invalidate()
        requestLayout()
    }

    /**
     * Show error view.
     */
    fun show() {
        viewState = ErrorViewStates.IDLE
        visibility = View.VISIBLE
        listener?.onErrorViewShow()
        invalidate()
        requestLayout()
    }

    /**
     * Hide error view.
     */
    fun hide() {
        viewState = ErrorViewStates.IDLE
        visibility = View.INVISIBLE
        listener?.onErrorViewHide()
        invalidate()
        requestLayout()
    }
}
