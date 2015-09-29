package ru.spb.deathlust.arithmeticclient;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String OPERATION = "operation";
    public static final String X = "x";
    public static final String Y = "y";

    public static final String PENDING_INTENT = "pending_intent";
    public static final int STATUS_FINISH = 0;
    public static final String RESULT = "result";
    public static final int STATUS_ERROR = 1;
    public static final String ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicked(View view) {
        String operation = ((Button) view).getText().toString();
        String xText = ((EditText)findViewById(R.id.x)).getText().toString();
        String yText = ((EditText)findViewById(R.id.y)).getText().toString();
        try {
            Intent intent = new Intent("ru.spb.deathlust.arithmeticservice.START_SERVICE");
            intent.putExtra(OPERATION, operation);
            intent.putExtra(X, Double.parseDouble(xText));
            intent.putExtra(Y, Double.parseDouble(yText));
            PendingIntent pi = createPendingResult(1, new Intent(), 0);
            intent.putExtra(PENDING_INTENT, pi);
            startService(intent);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.format_error), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView view = (TextView)findViewById(R.id.result);
        if (resultCode == STATUS_FINISH) {
            view.setText(Double.toString(data.getDoubleExtra(RESULT, 0)));
        }
        if (resultCode == STATUS_ERROR) {
            view.setText(data.getStringExtra(ERROR));
        }
    }
}
