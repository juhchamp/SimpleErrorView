package juhchamp.com.br.simple_error_view_library

interface SimpleErrorViewListeners {
    fun onErrorViewTryAgain()
    fun onErrorViewHide()
    fun onErrorViewShow()
    fun onErrorViewStartLoading()
    fun onErrorViewFinishLoading()
}