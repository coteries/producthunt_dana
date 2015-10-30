package com.example.kianfar.producthunt_danakianfar.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.kianfar.producthunt_danakianfar.content.Post;

public class ProductHuntLayout extends LinearLayout {

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    private Post post;

    public ProductHuntLayout(Context context) {
        super(context);
    }

    public ProductHuntLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductHuntLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProductHuntLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
