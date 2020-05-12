package juhchamp.com.br.simple_error_view_library

interface SimpleErrorViewListeners {
    /**
     * When "try again" button is clicked.
     */
    fun onTryAgainButtonClick()

    /**
     * When error view hide.
     */
    fun onErrorViewHide()

    /**
     * When error view shown.
     */
    fun onErrorViewShow()
}