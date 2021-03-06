/*
 * Copyright (C) 2011 Alex Kuiper <http://www.nightwhistler.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sufficientlysecure.htmlspanner.handlers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import org.sufficientlysecure.htmlspanner.SpanStack;
import org.sufficientlysecure.htmlspanner.TagNodeHandler;

import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.net.URL;

/**
 * Handles image tags.
 * <p>
 * The default implementation tries to load images through a URL.openStream(),
 * override loadBitmap() to implement your own loading.
 *
 * @author Alex Kuiper
 */
public class ImageHandler extends TagNodeHandler {
    private Html.ImageGetter mImageGetter;

    public ImageHandler(Html.ImageGetter imageGetter) {
        this.mImageGetter = imageGetter;
    }

    @Override
    public void handleTagNode(TagNode node, SpannableStringBuilder builder,
                              int start, int end, SpanStack stack) {
        String src = node.getAttributeByName("src");

        builder.append("\uFFFC");

        final Drawable drawable = mImageGetter.getDrawable(src);
        if (drawable != null) {
            stack.pushSpan(new ImageSpan(drawable), start, builder.length());
        }
    }

    /**
     * Loads a Bitmap from the given url.
     *
     * @param url
     * @return a Bitmap, or null if it could not be loaded.
     */
    protected Bitmap loadBitmap(String url) {
        try {
            return BitmapFactory.decodeStream(new URL(url).openStream());
        } catch (IOException io) {
            return null;
        }
    }
}
