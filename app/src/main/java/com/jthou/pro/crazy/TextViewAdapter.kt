package com.jthou.fuckyou

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.TextViewHolder
import java.util.*

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 24-11-2020
 */
class TextViewAdapter : RecyclerView.Adapter<TextViewHolder>() {

    companion object {
        private const val MAX_FONT_SIZE_USERNAME = 14f
        private const val MIN_FONT_SIZE_USERNAME = 10f
        private const val companyName = "北京虎嗅信息科技股份公司北京虎嗅信息科技股份公司"
        private final val random = Random()

        val data = MutableList<String>(200) {
            generate(it)
        }

        private fun generate(position: Int): String {
            return companyName.substring(0, random.nextInt(companyName.length) + 1)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val inflate = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text_view, parent, false)
        return TextViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(data[position])
    }

}