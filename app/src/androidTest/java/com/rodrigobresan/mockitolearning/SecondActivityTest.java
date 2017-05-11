package com.rodrigobresan.mockitolearning;

import android.content.Context;
import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import view.SecondActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static view.SecondActivity.EXTRA_INTENT_EMAIL;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

@RunWith(AndroidJUnit4.class)
public class SecondActivityTest {

    private static final String EXTRA_INTENT_VALUE_EMAIL = "rcbresan@email.com";


    @Test
    public void shouldContainTheCorrectExtras() throws Exception {
        Context context = Mockito.mock(Context.class);

        Intent intent = SecondActivity.provideIntent(context, EXTRA_INTENT_VALUE_EMAIL);
        assertNotNull(intent);
        assertEquals(EXTRA_INTENT_VALUE_EMAIL, intent.getSerializableExtra(EXTRA_INTENT_EMAIL));
    }

}
