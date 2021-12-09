package com.me.cica;

import com.me.cica.bbox.BBoxConverter;
import com.me.cica.bbox.CocoBBox;
import com.me.cica.bbox.YoloBBox;
import com.me.cica.coco.data.json.ImageEntry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BBoxConverterTest {

    @Test
    public void testValidConvertCocoToYolo() {
        final ImageEntry img = new ImageEntry(0, 500, 500);
        final YoloBBox yoloBBox = BBoxConverter.INSTANCE.cocoToYolo(img, 0, new CocoBBox(0, 60, 250, 1));
        assertEquals(new YoloBBox(0, (float) 0.25, (float) 0.121, (float) 0.5, (float) 0.002), yoloBBox);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidConvertCocoToYolo() {
        final ImageEntry img = new ImageEntry(0, 500, 500);
        BBoxConverter.INSTANCE.cocoToYolo(img, 0, new CocoBBox(0, 0, 0, 0));
    }
}
