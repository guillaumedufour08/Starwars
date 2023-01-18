package com.example.starwars.ui.components

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.example.starwars.R
import com.example.starwars.databinding.InfoWithImageCardBinding

class InfoWithImageCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {
    private val binding = InfoWithImageCardBinding.inflate(LayoutInflater.from(context), this)
    private val customAttributesStyle =
        context.obtainStyledAttributes(attrs, R.styleable.InfoWithImageCard, 0, 0)

    init {
        customizeCardView()
        applyAttributes()
    }

    fun setValue(value: String) {
        binding.valueTextView.text = value
    }

    // Modification de la card view qui englobe le layout
    private fun customizeCardView() {
        cardElevation = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8f,
            context.resources.displayMetrics
        )
        radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            8f,
            context.resources.displayMetrics
        )
    }

    // Attribuer les valeurs passées au XML
    private fun applyAttributes() {
        try {
            binding.apply {
                iconImageView.setImageResource(
                    customAttributesStyle.getResourceId(
                        R.styleable.InfoWithImageCard_iconSrc,
                        R.drawable.ic_baseline_groups_24
                    )
                )
                valueTextView.text =
                    customAttributesStyle.getString(R.styleable.InfoWithImageCard_valueText)
                titleTextView.text =
                    customAttributesStyle.getString(R.styleable.InfoWithImageCard_titleText)
            }
        } finally {
            customAttributesStyle.recycle()
        }
    }
}
