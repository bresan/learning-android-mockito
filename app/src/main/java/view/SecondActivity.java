package view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public class SecondActivity extends AppCompatActivity {

    public static Intent provideIntent(Context context, String email) {
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra("email", email);
        return intent;
    }
}

