package com.jhonjson.mvvmpractice.common

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jhonjson.base_library.common.clickNoRepeat
import com.jhonjson.base_library.common.loadRadius
import com.jhonjson.base_library.utils.ColorUtils
import com.jhonjson.mvvmpractice.R
import com.jhonjson.mvvmpractice.bean.ArticleBean
import com.jhonjson.mvvmpractice.constants.Constants

class ArticleAdapter (
    list: MutableList<ArticleBean.DatasBean>
) : BaseMultiItemQuickAdapter<ArticleBean.DatasBean,
        BaseViewHolder>(list) {

    /**
     * 子view点击回调接口。单独写一个接口目的是可以使用防止快速点击
     */
    private var onItemClickListener: OnChildItemClickListener? = null

    init {
        addItemType(Constants.ITEM_ARTICLE, R.layout.item_home_article)
        addItemType(Constants.ITEM_ARTICLE_PIC, R.layout.item_project)
    }

    fun setOnChildItemClickListener(onItemClickListener: OnChildItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun convert(helper: BaseViewHolder, item: ArticleBean.DatasBean) {
        when (helper.itemViewType) {
            //不带图片
            Constants.ITEM_ARTICLE -> {
                item.run {
                    helper.getView<View>(R.id.root).clickNoRepeat {
                        onItemClickListener?.onItemChildClick(
                            this@ArticleAdapter, it, helper.adapterPosition - headerLayoutCount
                        )
                    }
                    if (type == 1) {
                        helper.setText(R.id.tvTag, "置顶 ")
                        helper.setTextColor(R.id.tvTag, ColorUtils.parseColor(R.color.red))
                    } else {
                        helper.setText(R.id.tvTag, "")
                    }
                    helper.setText(
                        R.id.tvAuthor,
                        if (!TextUtils.isEmpty(author)) author else shareUser
                    )
                    helper.setText(R.id.tvDate, niceDate)
                    helper.setText(R.id.tvTitle, Html.fromHtml(title))
                    helper.setText(R.id.tvChapterName, superChapterName)
                    helper.getView<ImageView>(R.id.ivCollect)
                        .apply {
                            clickNoRepeat {
                                onItemClickListener?.onItemChildClick(
                                    this@ArticleAdapter,
                                    this,
                                    helper.adapterPosition - headerLayoutCount
                                )
                            }
                            if (item.collect) {
                                setImageResource(R.mipmap.article_collect)
                            } else {
                                setImageResource(R.mipmap.article_un_collect)
                            }
                        }
                }
            }

            //带图片
            Constants.ITEM_ARTICLE_PIC -> {
                item.apply {
                    //根布局
                    helper.getView<View>(R.id.root).clickNoRepeat {
                        onItemClickListener?.onItemChildClick(
                            this@ArticleAdapter, it, helper.adapterPosition - headerLayoutCount
                        )
                    }
                    //图片
                    envelopePic?.let {
                        helper.getView<ImageView>(R.id.ivTitle).loadRadius(mContext, it, 20)
                    }
                    //标题
                    helper.setText(R.id.tvTitle, title)
                    //描述信息
                    helper.setText(R.id.tvDes, desc)
                    //日期
                    helper.setText(R.id.tvNameData, "$niceDate | $author")
                    //收藏
                    helper.getView<ImageView>(R.id.ivCollect).apply {
                        clickNoRepeat {
                            onItemClickListener?.onItemChildClick(
                                //必须减headCount，否则角标会错乱
                                this@ArticleAdapter,
                                this,
                                helper.adapterPosition - headerLayoutCount
                            )
                        }
                        if (item.collect) {
                            setImageResource(R.mipmap.article_collect)
                        } else {
                            setImageResource(R.mipmap.article_un_collect)
                        }
                    }
                }
            }
        }
    }

    /**
     * 收藏，通过id做局部刷新
     */
    fun collectNotifyById(id: Int) {
        for (index in 0 until data.size) {
            if (id == data[index].id) {
                data[index].collect = true
                //必须加headCount，否则角标错乱
                notifyItemChanged(index + headerLayoutCount)
                return
            }
        }
    }

    /**
     * 取消收藏，通过id做局部刷新
     */
    fun unCollectNotifyById(id: Int) {
        for (index in 0 until data.size) {
            if (id == data[index].id) {
                data[index].collect = false
                //必须加headCount，否则角标错乱
                notifyItemChanged(index + headerLayoutCount)
                return
            }
        }
    }

    /**
     * 获取跳转至web界面的bundle
     */
    fun getBundle(position: Int): Bundle {
        return Bundle().apply {
            putString("loadUrl", data[position].link)
            putString("title", data[position].title)
            putString("author", data[position].author)
            putInt("id", data[position].id)
        }
    }
}